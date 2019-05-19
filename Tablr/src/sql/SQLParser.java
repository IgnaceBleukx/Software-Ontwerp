package sql;

import java.io.IOException;

import sql.*;

import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Utils.DebugPrinter;

public class SQLParser extends StreamTokenizer {
	
	private static HashMap<String, Integer> keywords = new HashMap<>();
	public static final int
		TT_IDENT = -9,
		TT_SELECT = -10,
		TT_OR = -11,
		TT_AND = -12,
		TT_TRUE = -13,
		TT_FALSE = -14,
		TT_AS = -15,
		TT_FROM = -16,
		TT_INNER = -17,
		TT_JOIN = -18,
		TT_ON = -19,
		TT_WHERE = -20;
	
	static {
		keywords.put("SELECT", TT_SELECT);
		keywords.put("OR", TT_OR);
		keywords.put("AND", TT_AND);
		keywords.put("TRUE", TT_TRUE);
		keywords.put("FALSE", TT_FALSE);
		keywords.put("AS", TT_AS);
		keywords.put("FROM", TT_FROM);
		keywords.put("INNER", TT_INNER);
		keywords.put("JOIN", TT_JOIN);
		keywords.put("ON", TT_ON);
		keywords.put("WHERE", TT_WHERE);
	}
	
	public static class ParseException extends RuntimeException {}
	
	public static Query parseQuery(String text) { 
		if (text.equals(""))
			return null;
		Query result = new SQLParser(text).parseQuery();
		result.setSQL(text);
		return result;
	}
	
	@Override
	public int nextToken() {
		try {
			super.nextToken();
			if (ttype == TT_WORD) {
				Integer kwd = keywords.get(sval);
				if (kwd != null)
					ttype = kwd;
				else
					ttype = TT_IDENT;
			}
			return ttype;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public SQLParser(String text) {
		super(new StringReader(text));
		ordinaryChar('.');
		wordChars('_', '_');
		nextToken();
	}
	
	public RuntimeException error() {
		return new ParseException();
	}
	
	public void expect(int ttype) {
		if (this.ttype != ttype)
			throw new RuntimeException("Expected " + ttype + ", found " + this.ttype);
		nextToken();
	}
	
	public String expectIdent() {
		if (ttype != TT_IDENT)
			throw error();
		String result = sval;
		nextToken();
		return result;
	}
	
	public CellIDExpression parseCellId() {
		String rowId = expectIdent();
		expect('.');
		String colName = expectIdent();
		return new CellIDExpression(rowId, colName);
	}
	
	public Expression parsePrimaryExpr() {
		switch (ttype) {
		case TT_TRUE:
			nextToken();
			return new BooleanExpression(true);
		case TT_FALSE:
			nextToken();
			return new BooleanExpression(false);
		case TT_NUMBER: {
			int value = (int)nval;
			nextToken();
			return new NumberExpression(value);
		}
		case '"': {
			String value = sval;
			nextToken();
			return new StringExpression(value);
		}
		case TT_IDENT: return parseCellId();
		case '(': {
			nextToken();
			Expression result = parseExpr();
			expect(')');
			return new BracketExpression<>(result);
		}
		default: throw error();
		}
	}
	
	public Expression parseSum() {
		Expression e = parsePrimaryExpr();
		for (;;) {
			switch (ttype) {
			case '+':
				nextToken();
				e = new PlusExpression(e,parsePrimaryExpr());
				break;
			case '-':
				e = new MinusExpression(e,parsePrimaryExpr());
				nextToken();
				break;
			default:
				return e;
			}
		}
	}
		
	public Expression parseRelationalExpr() {
		Expression e = parseSum();
		switch (ttype) {
			case '=': {nextToken(); return new EqualsExpression(e,parseSum());}
			case '<': {nextToken(); return new SmallerThanExpression(e,parseSum());} 
			case '>': {nextToken(); return new GreaterThanExpression(e,parseSum());}
		default:
			return e;
		}
	}
	
	public Expression parseConjunction() {
		Expression e = parseRelationalExpr();
		switch (ttype) {
		case TT_AND:
			nextToken();
			return new ANDExpression(e,parseConjunction());
		default:
			return e;
		}
	}
	
	public Expression parseDisjunction() {
		Expression e = parseConjunction();
		switch (ttype) {
		case TT_OR:
			nextToken();
			return new ORExpression(e, parseDisjunction());
		default:
			return e;
		}
	}

	public Expression parseExpr() { 
		return parseDisjunction();
	}
	
	public static CellIDExpression parseCellIDExpression(String e) {
		return new CellIDExpression(e.split("\\.")[0], e.split("\\.")[1]);
	}
	
	public Query parseQuery() {
		StringBuilder result = new StringBuilder();
		Query q = new Query();
		expect(TT_SELECT);
		
		//Read ColumnSpecs of SELECT clause
		result.append("SELECT ");
		for (;;) {
			Expression e = parseExpr();
			expect(TT_AS);
			String colName = expectIdent();
			result.append(e + " AS " + colName);
			q.addColumnSpec(new ColumnSpec((CellIDExpression) e,colName));
			if (ttype == ',') {
				nextToken();
				result.append(", ");
			} else
				break;
		}
		
		
		//Read TableSpecs of FROM clause
		expect(TT_FROM);
		result.append(" FROM ");
		TableSpec prevSpec = null;
		{
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			prevSpec = new SimpleTableSpec(tableName, rowId);
			result.append(tableName + " AS " + rowId);
			
		}
		while (ttype == TT_INNER) {
			nextToken();
			expect(TT_JOIN);
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			expect(TT_ON);
			CellIDExpression cell1 = parseCellId();
			expect('=');
			CellIDExpression cell2 = parseCellId();
			SimpleTableSpec rightTable = new SimpleTableSpec(tableName,rowId);
			prevSpec = new JoinTableSpec(prevSpec, rightTable, cell1, cell2);
			result.append(" INNER JOIN " + tableName + " AS " + rowId + " ON " + cell1 + " = " + cell2);
		}
		q.setTableSpecs(prevSpec);
		
		
		//Read expression of WHERE clause
		expect(TT_WHERE);
		Expression cond = parseExpr();
		result.append(" WHERE " + cond);
		q.setExpression(cond);
		DebugPrinter.print(q);
		return q;
	}
	
	public static void main(String[] args) {
		Query q = parseQuery("SELECT student.name AS name, student.program AS program "
					        +"FROM enrollments AS enrollment INNER JOIN students AS student "
					        +"ON enrollment.student_id = student.student_id "
					        +"WHERE enrollment.course_id = \"SWOP\"");
		System.out.println(q);
	}
	
}
