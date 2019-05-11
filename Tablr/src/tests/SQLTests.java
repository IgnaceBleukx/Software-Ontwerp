package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import domain.*;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;

import org.junit.Test;

import sql.CellIDExpression;
import sql.ColumnSpec;
import sql.EqualsExpression;
import sql.JoinTableSpec;
import sql.Query;
import sql.QueryExecutor;
import sql.SQLParser;
import sql.SimpleTableSpec;
import sql.StringExpression;

public class SQLTests {

	@Test
	public void testJOIN() throws InvalidNameException, InvalidQueryException {

		ArrayList<Table> tables = createTables();

		SimpleTableSpec s1 = new SimpleTableSpec("Table1", "Table1");
		SimpleTableSpec s2 = new SimpleTableSpec("Table2", "Table2");
		JoinTableSpec s = new JoinTableSpec(s1, s2, 
				new CellIDExpression("Table1", "colB"), 
				new CellIDExpression("Table2", "colC"));
		Table result = s.resolve(tables);
		result.printTable();
		
		
	}
	
	@Test
	public void testSELECT() throws InvalidNameException  {
		ArrayList<Table> tables = createTables();
		tables.get(0).getColumns().get(0).setName("Table1.colA");
		tables.get(0).getColumns().get(1).setName("Table1.colB");
		Query q = new Query();
		HashMap<String,String> aliases = new HashMap<String,String>();
		aliases.put("Table1", "Table1");
		q.addColumnSpec(new ColumnSpec(new CellIDExpression("Table1","colA"),"newColA"));
		Table newTable = q.selectColumns(tables.get(0),aliases);
		newTable.printTable();
		//[
		//	ColumnSpec(cellIDExpression(Table1.colA) AS newColA), 
		//  = SELECT Table1.colA as newColA
		//]
	}
	
	@Test
	public void testQueryNoWhere() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createTables();
		Query q = SQLParser.parseQuery("SELECT t1.colA AS result "
		        +"FROM Table1 AS t1 INNER JOIN Table2 AS t2 "
		        +"ON t1.colB = t2.colC "
		        +"WHERE TRUE");
		Table t = QueryExecutor.executeQuery(q, tables);
		//t.printTable();
	}
	
	@Test
	public void testWhere() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createTables();
		Query q = SQLParser.parseQuery("SELECT t1.colA AS col1, t1.colB AS col2 "
		        +"FROM Table1 AS t1 "
		        +"WHERE t1.colA = 7");
		Table t = QueryExecutor.executeQuery(q, tables);
		//t.printTable();
	}
	
	public ArrayList<Table> createTables() throws InvalidNameException {
		//
		//Table1
		//colA colB 
		//   7   1
		//   8   2
		//   9   1
		//  11   2

		//Table2
		//colC colD 
		//   1  -1
		//   1  -2
		//   2  -4
		//   2  -5
		StoredTable table1 = new StoredTable("Table1");
		StoredTable table2 = new StoredTable("Table2");
		
		Column colA = new Column("colA",null,Type.INTEGER, 0);
		Column colB = new Column("colB",null,Type.INTEGER, 0);
		Column colC = new Column("colC",null,Type.INTEGER, 0);
		Column colD = new Column("colD",null,Type.INTEGER, 0);
		
		Cell CellA11 = new Cell<Integer>(7);
		Cell CellA12 = new Cell<Integer>(1);
		Cell CellA21 = new Cell<Integer>(8);
		Cell CellA22 = new Cell<Integer>(2);
		Cell CellA31 = new Cell<Integer>(9);
		Cell CellA32 = new Cell<Integer>(1);
		Cell CellA41 = new Cell<Integer>(11);
		Cell CellA42 = new Cell<Integer>(2);
		
		
		Cell CellB11 = new Cell<Integer>(1);
		Cell CellB12 = new Cell<Integer>(-1);
		Cell CellB21 = new Cell<Integer>(1);
		Cell CellB22 = new Cell<Integer>(-2);
		Cell CellB31 = new Cell<Integer>(2);
		Cell CellB32 = new Cell<Integer>(-4);
		Cell CellB41 = new Cell<Integer>(2);
		Cell CellB42 = new Cell<Integer>(-5);
		
		colA.addCell(CellA11);
		colA.addCell(CellA21);
		colA.addCell(CellA31);
		colA.addCell(CellA41);
		
		colB.addCell(CellA12);
		colB.addCell(CellA22);
		colB.addCell(CellA32);
		colB.addCell(CellA42);
		
		colC.addCell(CellB11);
		colC.addCell(CellB21);
		colC.addCell(CellB31);
		colC.addCell(CellB41);
		
		colD.addCell(CellB12);
		colD.addCell(CellB22);
		colD.addCell(CellB32);
		colD.addCell(CellB42);
		
		ArrayList<Column> cols1 = new ArrayList<Column>();
		cols1.add(colA);
		cols1.add(colB);
		ArrayList<Column> cols2 = new ArrayList<Column>();
		cols2.add(colC);
		cols2.add(colD);
		
		table1.addAllColumns(cols1);
		table2.addAllColumns(cols2);
		
		ArrayList<Table> tables = new ArrayList<>();
		tables.add(table1);
		tables.add(table2);
		return tables;
	}
}