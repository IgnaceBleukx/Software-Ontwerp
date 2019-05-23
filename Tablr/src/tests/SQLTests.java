package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import domain.*;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;
import facades.Tablr;

import org.junit.Test;

import canvaswindow.CanvasWindow;
import canvaswindow.MyCanvasWindow;
import Utils.DebugPrinter;
import sql.ANDExpression;
import sql.BooleanExpression;
import sql.BracketExpression;
import sql.CellIDExpression;
import sql.ColumnSpec;
import sql.EqualsExpression;
import sql.GreaterThanExpression;
import sql.JoinTableSpec;
import sql.MinusExpression;
import sql.NumberExpression;
import sql.ORExpression;
import sql.PlusExpression;
import sql.Query;
import sql.QueryExecutor;
import sql.SQLParser;
import sql.SimpleTableSpec;
import sql.SmallerThanExpression;
import sql.StringExpression;
import sql.TableSpec;


public class SQLTests {

	@Test
	public void testJOIN() throws InvalidNameException, InvalidQueryException {

		ArrayList<Table> tables = createTables1();

		SimpleTableSpec s1 = new SimpleTableSpec("Table1", "Table1");
		SimpleTableSpec s2 = new SimpleTableSpec("Table2", "Table2");
		JoinTableSpec s = new JoinTableSpec(s1, s2, 
				new CellIDExpression("Table1", "colB"), 
				new CellIDExpression("Table2", "colC"));
		Table result = s.resolve(tables);
		result.printTable();
		String[] columnNamesExpected = new String[]{
				"Table1.colA",
				"Table1.colB",
				"Table2.colD"
		};
		
		for (int i=0;i<3;i++) {
			assertTrue(result.getColumns().get(i).getName().equals(columnNamesExpected[i]));
		}
		
		int[] column1 = new int[]{7,7,8,8,9,9,11,11};
		int[] column3 = new int[]{-1,-2,-4,-5,-1,-2,-4,-5};
		
		for (int i=0;i<8;i++) {
			assertTrue(result.getColumns().get(0).getValueAt(i).equals(column1[i]));
			assertTrue(result.getColumns().get(2).getValueAt(i).equals(column3[i]));
		}
		
	}
	
	@Test
	public void testSimpleTableSpec() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createTables1();

		SimpleTableSpec s1 = new SimpleTableSpec("Table1", "Table1");
		SimpleTableSpec s2 = new SimpleTableSpec("Table2", "Table2");
		Table result = s1.resolve(tables);
		result.printTable();
		
		assertTrue(result.getColumnNames().get(0).equals("Table1.colA"));
		assertTrue(result.getColumnNames().get(1).equals("Table1.colB"));
		assertEquals(result.getColumns().get(0).getCells().size(),4);
		assertEquals(result.getColumns().get(0).getCells().get(0).getValue(),7);
	}
	
	@Test
	public void testWHEREQuery() throws InvalidNameException, InvalidQueryException {
		String query = "SELECT t.colA AS a, t.colB AS b FROM Table1 AS t WHERE t.colA = 7";
		Query q = SQLParser.parseQuery(query);
		Table result = QueryExecutor.executeQuery(q, createTables1());
		
		assertTrue(result.getColumnNames().get(0).equals("a"));
		assertTrue(result.getColumnNames().get(1).equals("b"));
		assertEquals(result.getColumns().get(0).getValueAt(0),7);
		assertEquals(result.getColumns().get(1).getValueAt(0),1);
	}
	
	@Test
	public void testWHERE() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createTables1();
		Query q = new Query();
		TableSpec s = new SimpleTableSpec("Table1","Table1");
		EqualsExpression e = new EqualsExpression(new CellIDExpression("Table1","colA"),new NumberExpression(7));
		q.setExpression(e);
		q.setTableSpecs(s);
		HashMap<String,String> alias = new HashMap<>();
		alias.put("Table1","Table1");
		//Rename column to simulate situation after JOIN
		tables.get(0).getColumns().get(0).setName("Table1.colA");
		tables.get(0).getColumns().get(1).setName("Table1.colB");
		
		Table newTable = q.resolveWhere(tables.get(0),alias);
		newTable.printTable();
		assertEquals(1,newTable.getRows());
		assertEquals(7,newTable.getColumns().get(0).getValueAt(0));
		assertEquals(1,newTable.getColumns().get(1).getValueAt(0));
		//SELECT * FROM Table1 AS Table1 WHERE Table1.colA = 7
	}
	
	@Test
	public void testWHERE2() throws InvalidNameException, InvalidQueryException {
		TableSpec s = new SimpleTableSpec("Table1","t");
		HashMap<String,String> alias = new HashMap<String,String>();
		alias.put("Table1", "Table1");
		Table t = createTableMixedTypes();
		t.getColumns().get(0).setName("Table1.colA");
		t.getColumns().get(1).setName("Table1.colB");
		t.getColumns().get(2).setName("Table1.colC");
		t.getColumns().get(3).setName("Table1.colD");
		EqualsExpression e = new EqualsExpression(new CellIDExpression("Table1","colB"),new BooleanExpression(null));
		Query q = new Query();
		q.setTableSpecs(s);
		q.setExpression(e);
		Table newTable = q.resolveWhere(t,alias);
		newTable.printTable();
		
		assertTrue(newTable.getColumns().get(0).getCells().size() == 2);
		assertNull(newTable.getColumns().get(1).getCells().get(0).getValue());
		
		
	}
	
	@Test
	public void testSELECT() throws InvalidNameException  {
		ArrayList<Table> tables = createTables1();
		
		//Manually modify column names,
		//to simulate executing from a JOIN
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
		assertTrue(newTable.getColumnNames().get(0).equals("newColA"));
		assertEquals(newTable.getColumns().get(0).getValueAt(0),7);
		assertEquals(newTable.getColumns().get(0).getValueAt(1),8);
		assertEquals(newTable.getColumns().get(0).getValueAt(2),9);
		assertEquals(newTable.getColumns().get(0).getValueAt(3),11);
	}
	
	@Test
	public void testAND() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createTables1();
		String query = "SELECT t.colA AS a FROM Table1 AS t WHERE (t.colA >5 AND t.colA < 10)";
		Query q = SQLParser.parseQuery(query);
		ComputedTable t = QueryExecutor.executeQuery(q, tables);
		//t.printTable();
		assertEquals(3,t.getRows());
		assertEquals(7,t.getColumns().get(0).getValueAt(0));
		assertEquals(8,t.getColumns().get(0).getValueAt(1));
		assertEquals(9,t.getColumns().get(0).getValueAt(2));

	}
	
	@Test
	public void testExampleMovies () throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createExampleTablesMovie();
		String query = "SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7";
	    Query q = SQLParser.parseQuery(query);
	    Table table = QueryExecutor.executeQuery(q, tables);
	    //table.printTable();
	    assertEquals("Star Wars",table.getColumns().get(0).getValueAt(0));
	    assertEquals("WALL.E", table.getColumns().get(0).getValueAt(1));
	}
	
	@Test
	public void testExampleStudent() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createExampleTablesStudents();
		String query = "SELECT student.name AS name, student.program AS program "+
						"FROM enrollments AS enrollment INNER JOIN students AS student "+
						"ON enrollment.student_id = student.student_id "+
						"WHERE enrollment.course_id = \"SWOP\"";
		Query q = SQLParser.parseQuery(query);
		Table table = QueryExecutor.executeQuery(q, tables);
		//table.printTable();
		assertEquals("Piet",table.getColumns().get(0).getValueAt(0));
		assertEquals("Joris",table.getColumns().get(0).getValueAt(1));
		assertEquals("Korneel",table.getColumns().get(0).getValueAt(2));
		assertEquals("Schakeljaar Toegepaste Informatica",table.getColumns().get(1).getValueAt(0));
		assertEquals("Informatica",table.getColumns().get(1).getValueAt(1));
		assertEquals("Fysica",table.getColumns().get(1).getValueAt(2));
		assertEquals("name",table.getColumnNames().get(0));
		assertEquals("program",table.getColumnNames().get(1));

	}
	
	@Test
	public void testQueryNoWhere() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createTables1();
		Query q = SQLParser.parseQuery("SELECT t1.colA AS result "
		        +"FROM Table1 AS t1 INNER JOIN Table2 AS t2 "
		        +"ON t1.colB = t2.colC "
		        +"WHERE TRUE");
		Table t = QueryExecutor.executeQuery(q, tables);
		
		assertTrue(t.getColumns().get(0).getName().equals("result"));
		int[] results = new int[]{7,7,8,8,9,9,11,11};
		for (int i=0;i<8;i++) {
			assertEquals(t.getColumns().get(0).getValueAt(i),results[i]);
		}
	}
	
	@Test
	public void testWhere() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createTables1();
		Query q = SQLParser.parseQuery("SELECT t1.colA AS col1, t1.colB AS col2 "
		        +"FROM Table1 AS t1 "
		        +"WHERE t1.colA = 7");
		Table t = QueryExecutor.executeQuery(q, tables);
		
		assertTrue(t.getColumnNames().get(0).equals("col1"));
		assertTrue(t.getColumnNames().get(1).equals("col2"));
		assertEquals(t.getColumns().get(0).getValueAt(0),7);
		assertEquals(t.getColumns().get(1).getValueAt(0),1);
	}
	

	@Test
	public void testBasicArithmicExpression() throws InvalidQueryException {
		//(7-(5+2)
		NumberExpression n7 = new NumberExpression(7);
		NumberExpression n5 = new NumberExpression(5);
		NumberExpression n2 = new NumberExpression(2);
		BracketExpression<Integer> ex = new BracketExpression<>(
				new MinusExpression(n7, new PlusExpression(n5, n2)));
		assertEquals(Integer.valueOf(0),ex.eval(null, 0, null));
		
		//7+5+2
		PlusExpression ex2 = new PlusExpression(n7, new PlusExpression(n5,n2));
		assertEquals(ex2.eval(null, 0, null),Integer.valueOf(14));
	}
	
	@Test
	public void testLogicalExpressions() throws InvalidQueryException {
		//(true && false) || true
		//Expected: true
		BooleanExpression t = new BooleanExpression(true);
		BooleanExpression f = new BooleanExpression(false);
		ORExpression e = new ORExpression(new ANDExpression(t, f), t);
		assertEquals(true,e.eval(null, 0, null));
		
		//(true || true) && false
		//Expected: false
		ANDExpression e2 = new ANDExpression(new ORExpression(t, t), f);
		assertEquals(false, e2.eval(null, 0, null));
	}
	
	@Test
	public void testBasicCompareExpression() throws InvalidQueryException {
		//7 > 5
		NumberExpression n7 = new NumberExpression(7);
		NumberExpression n5 = new NumberExpression(5);
		assertTrue(new GreaterThanExpression(n7,n5).eval(null,0,null));
		assertFalse(new SmallerThanExpression(n7, n5).eval(null, 0, null));
	}
	
	@Test (expected = InvalidQueryException.class)
	public void testInvalidCellIdType() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createExampleTablesStudents();
		Query q = SQLParser.parseQuery(
				"SELECT students.student_id AS res FROM students AS students WHERE students.name > 7");
		//name > 7 mag niet werken
		QueryExecutor.executeQuery(q, tables);
	}
	
	@Test 
	public void testValidCellIdType() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createExampleTablesStudents();
		Query q = SQLParser.parseQuery(
				"SELECT students.name AS res FROM students AS students WHERE students.name = \"Joris\"");
		ComputedTable res = QueryExecutor.executeQuery(q, tables);
		assertEquals(1, res.getRows());
		assertEquals(1, res.getColumns().size());
		assertEquals("Joris",(String)res.getColumns().get(0).getCells().get(0).getValue());
	}
	
	@Test
	public void testNestedJoinsSameTable() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createExampleTablesStudents();
		Query q = SQLParser.parseQuery(
				"SELECT students1.name AS res FROM students AS students1 INNER JOIN students AS students2 ON students1.student_id = students2.student_id INNER JOIN students AS students3 ON students2.student_id = students3.student_id WHERE TRUE");
		ComputedTable res = QueryExecutor.executeQuery(q, tables);
		//res.printTable();
		
		assertEquals(4, res.getRows());
		assertEquals(1, res.getColumns().size());
		assertEquals("Jan",(String)res.getColumns().get(0).getCells().get(0).getValue());
		assertEquals("Piet",(String)res.getColumns().get(0).getCells().get(1).getValue());
		assertEquals("Joris",(String)res.getColumns().get(0).getCells().get(2).getValue());
		assertEquals("Korneel",(String)res.getColumns().get(0).getCells().get(3).getValue());
	}
	
	

	
	@Test
	public void testSomeQueries() throws InvalidNameException, InvalidQueryException {
		ArrayList<Table> tables = createExampleTablesStudents();
		Query q = SQLParser.parseQuery(
				"SELECT students.name AS name, students.program AS program, enrollments.course_id AS course_id FROM students AS students INNER JOIN enrollments AS enrollments ON students.student_id = enrollments.student_id WHERE TRUE");
		ComputedTable res = QueryExecutor.executeQuery(q, tables);
		//res.printTable();
		
		assertEquals(8,res.getRows());
		assertEquals(3,res.getColumns().size());
		assertEquals("Jan",res.getColumns().get(0).getCells().get(0).getValue());
		assertEquals("Piet",res.getColumns().get(0).getCells().get(3).getValue());
		assertEquals("Korneel",res.getColumns().get(0).getCells().get(6).getValue());
		
		assertEquals("BVP",res.getColumns().get(2).getCells().get(0).getValue());
		assertEquals("AB",res.getColumns().get(2).getCells().get(3).getValue());
		assertEquals("BVP",res.getColumns().get(2).getCells().get(6).getValue());

	}
	
	@Test
	public void testToStrings() {
		//(true && false) || true
		//Expected: true
		BooleanExpression t = new BooleanExpression(true);
		BooleanExpression f = new BooleanExpression(false);
		ORExpression e = new ORExpression(new ANDExpression(t, f), t);
		assertEquals(e.toString(),"OrExpression(ANDExpression(BooleanExpression(true) AND BooleanExpression(false)) OR BooleanExpression(true))");
		
		//(7-(5+2)
		NumberExpression n7 = new NumberExpression(7);
		NumberExpression n5 = new NumberExpression(5);
		NumberExpression n2 = new NumberExpression(2);
		BracketExpression<Integer> ex = new BracketExpression<>(
				new MinusExpression(n7, new PlusExpression(n5, n2)));
		assertEquals(ex.toString(),"BracketExpression(MinusExpression(NumberExpression(7) - PlusExpression(NumberExpression(5) + NumberExpression(2))))");
		
		//7 > 5, 7 < 5
		GreaterThanExpression ex2 = new GreaterThanExpression(n7,n5);
		SmallerThanExpression ex3 = new SmallerThanExpression(n7, n5);
		assertEquals(ex2.toString(),"GreaterThanExpression(NumberExpression(7) > NumberExpression(5))");
		assertEquals(ex3.toString(),"SmallerThanExpression(NumberExpression(7) < NumberExpression(5))");

	}
	
	@Test
	public void testCellIDExpressionMultipleTables() {
		ArrayList<Table> tables = createExampleTablesStudents();
		
		//Manually renaming columns, this is how they would 
		//appear during query execution
		tables.get(0).getColumns().stream().forEach((c)->c.setName("students.students."+c.getName()));
		HashMap<String,String> aliases  = new HashMap<>();
		aliases.put("students", "students");
		CellIDExpression e = new CellIDExpression("students", "name");
		assertEquals("Jan", (String)e.eval(tables, 0, aliases));
		assertEquals("Piet", (String)e.eval(tables, 1, aliases));
		assertEquals("Joris", (String)e.eval(tables, 2, aliases));

	}
	
	
	
//  ________________________________________________________
//  ________________________________________________________
//	  _____     _     _           
//	  |_   _|_ _| |__ | | ___  ___ 
//	    | |/ _` | '_ \| |/ _ \/ __|
//	    | | (_| | |_) | |  __/\__ \
//	    |_|\__,_|_.__/|_|\___||___/
//  ________________________________________________________
//  ________________________________________________________	
	
	/**
	 * Table:
	 * colA	 colB    colC    	 colD
	 *  1	 true  	 ""    "test@email.1.com"
	 *  null false "test2"        ""
	 *  3	 null  "test3" "test@email.3.com"
	 *	4	 null  "test4" "test@email.4.com"
	 * @throws InvalidNameException 
	 */
	public Table createTableMixedTypes() throws InvalidNameException {
		Cell<Integer> A1 = new Cell<Integer>(1);
		Cell<Integer> A2 = new Cell<Integer>(null);
		Cell<Integer> A3 = new Cell<Integer>(3);
		Cell<Integer> A4 = new Cell<Integer>(4);
		
		Column colA = new Column("colA",new ArrayList<Cell<?>>(Arrays.asList(A1,A2,A3,A4)),Type.INTEGER,0);
		
		Cell<Boolean> B1 = new Cell<Boolean>(true);
		Cell<Boolean> B2 = new Cell<Boolean>(false);
		Cell<Boolean> B3 = new Cell<Boolean>(null);
		Cell<Boolean> B4 = new Cell<Boolean>(null);
		
		Column colB = new Column("colB",new ArrayList<Cell<?>>(Arrays.asList(B1,B2,B3,B4)),Type.INTEGER,0);
	
		
		Cell<String> C1 = new Cell<String>("");
		Cell<String> C2 = new Cell<String>("test2");
		Cell<String> C3 = new Cell<String>("test3");
		Cell<String> C4 = new Cell<String>("test4");
	
		Column colC = new Column("colC",new ArrayList<Cell<?>>(Arrays.asList(C1,C2,C3,C4)),Type.INTEGER,0);
	
		Cell<String> D1 = new Cell<String>("test@email.1.com");
		Cell<String> D2 = new Cell<String>("");
		Cell<String> D3 = new Cell<String>("test@email.3.com");
		Cell<String> D4 = new Cell<String>("test@email.4.com");
		
		Column colD = new Column("colD",new ArrayList<Cell<?>>(Arrays.asList(D1,D2,D3,D4)),Type.INTEGER,0);
	
		StoredTable table = new StoredTable("Table1");
		table.addColumn(colA);
		table.addColumn(colB);
		table.addColumn(colC);
		table.addColumn(colD);
		
		//table.printTable();
		
		return table;
		
	}

	/**
	 * Creates a list of Tables containing two Tables:
	 * Table1
	 *	colA colB 
	 *    7   1
	 *    8   2
	 *    9   1
	 *   11   2
	 *
	 * Table2
	 * colC colD 
	 *    1  -1
	 *    1  -2
	 *    2  -4
	 *    2  -5
	 * @throws InvalidNameException Will never happen with well-defined tables.
	 */
	public ArrayList<Table> createTables1() throws InvalidNameException {
	
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
		
		Cell<Integer> CellA11 = new Cell<Integer>(7);
		Cell<Integer> CellA12 = new Cell<Integer>(1);
		Cell<Integer> CellA21 = new Cell<Integer>(8);
		Cell<Integer> CellA22 = new Cell<Integer>(2);
		Cell<Integer> CellA31 = new Cell<Integer>(9);
		Cell<Integer> CellA32 = new Cell<Integer>(1);
		Cell<Integer> CellA41 = new Cell<Integer>(11);
		Cell<Integer> CellA42 = new Cell<Integer>(2);
		
		
		Cell<Integer> CellB11 = new Cell<Integer>(1);
		Cell<Integer> CellB12 = new Cell<Integer>(-1);
		Cell<Integer> CellB21 = new Cell<Integer>(1);
		Cell<Integer> CellB22 = new Cell<Integer>(-2);
		Cell<Integer> CellB31 = new Cell<Integer>(2);
		Cell<Integer> CellB32 = new Cell<Integer>(-4);
		Cell<Integer> CellB41 = new Cell<Integer>(2);
		Cell<Integer> CellB42 = new Cell<Integer>(-5);
		
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
		colA.setTable(table1);
		cols1.add(colB);
		colB.setTable(table1);
		ArrayList<Column> cols2 = new ArrayList<Column>();
		cols2.add(colC);
		colC.setTable(table2);
		cols2.add(colD);
		colD.setTable(table2);
		
		table1.addAllColumns(cols1);
		table2.addAllColumns(cols2);
		
		ArrayList<Table> tables = new ArrayList<>();
		tables.add(table1);
		tables.add(table2);
		return tables;
	}

	/**
	 * Creates a list containing one Table:
	 * 
	 * @return
	 * @throws InvalidNameException 
	 */
	public ArrayList<Table> createTables2() throws InvalidNameException {
		StoredTable table1 = new StoredTable("Table1");
		Column colA = new Column("colA",null,Type.INTEGER, 0);
		Column colB = new Column("colB",null,Type.INTEGER, 0);
		Column colC = new Column("colC",null,Type.INTEGER, 0);
		Column colD = new Column("colD",null,Type.INTEGER, 0);
		Column colE = new Column("colE",null,Type.INTEGER, 0);
		
		Cell<Integer> A1 = new Cell<Integer>(1);
		Cell<Integer> A2 = new Cell<Integer>(6);
		Cell<Integer> A3 = new Cell<Integer>(11);
		Cell<Integer> A4 = new Cell<Integer>(16);
		Cell<Integer> A5 = new Cell<Integer>(21);
		
		Cell<Integer> B1 = new Cell<Integer>(2);
		Cell<Integer> B2 = new Cell<Integer>(7);
		Cell<Integer> B3 = new Cell<Integer>(12);
		Cell<Integer> B4 = new Cell<Integer>(17);
		Cell<Integer> B5 = new Cell<Integer>(22);
		
		Cell<Integer> C1 = new Cell<Integer>(3);
		Cell<Integer> C2 = new Cell<Integer>(8);
		Cell<Integer> C3 = new Cell<Integer>(13);
		Cell<Integer> C4 = new Cell<Integer>(18);
		Cell<Integer> C5 = new Cell<Integer>(23);
		
		Cell<Integer> D1 = new Cell<Integer>(4);
		Cell<Integer> D2 = new Cell<Integer>(9);
		Cell<Integer> D3 = new Cell<Integer>(14);
		Cell<Integer> D4 = new Cell<Integer>(19);
		Cell<Integer> D5 = new Cell<Integer>(24);
		
		Cell<Integer> E1 = new Cell<Integer>(5);
		Cell<Integer> E2 = new Cell<Integer>(10);
		Cell<Integer> E3 = new Cell<Integer>(15);
		Cell<Integer> E4 = new Cell<Integer>(20);
		Cell<Integer> E5 = new Cell<Integer>(25);
		
		colA.addCell(A1); 
		colA.addCell(A2); 
		colA.addCell(A3); 
		colA.addCell(A4); 
		colA.addCell(A5); 
		
		colB.addCell(B1); 
		colB.addCell(B2); 
		colB.addCell(B3); 
		colB.addCell(B4); 
		colB.addCell(B5);
		
		colC.addCell(C1); 
		colC.addCell(C2); 
		colC.addCell(C3); 
		colC.addCell(C4); 
		colC.addCell(C5);
		
		colD.addCell(D1); 
		colD.addCell(D2); 
		colD.addCell(D3); 
		colD.addCell(D4); 
		colD.addCell(D5);
		
		colE.addCell(E1); 
		colE.addCell(E2); 
		colE.addCell(E3); 
		colE.addCell(E4); 
		colE.addCell(E5);
		
		ArrayList<Column> cols1 = new ArrayList<Column>();
		cols1.add(colA);
		cols1.add(colB);
		cols1.add(colC);
		cols1.add(colD);
		cols1.add(colE);
		
		table1.addAllColumns(cols1);
		
		ArrayList<Table> tables = new ArrayList<>();
		tables.add(table1);
		return tables;
	}

	/**
	 * This method returns an arraylist of one table containing movie titles and imdb_scores
	 * @return
	 * @throws InvalidNameException
	 */
	public ArrayList<Table> createExampleTablesMovie() throws InvalidNameException{
		ArrayList<Table> tables = new ArrayList<Table>();
		StoredTable movies = new StoredTable("movies");
		ArrayList<Cell<?>> titles = new ArrayList<Cell<?>>();
		titles.add(new Cell<String>("Star Wars"));
		titles.add(new Cell<String>("Pulp Fiction"));
		titles.add(new Cell<String>("Fight Club"));
		titles.add(new Cell<String>("Avengers: The Endgame"));
		titles.add(new Cell<String>("WALL.E"));
		
		ArrayList<Cell<?>> scores = new ArrayList<Cell<?>>();
		scores.add(new Cell<Integer>(8));
		scores.add(new Cell<Integer>(6));
		scores.add(new Cell<Integer>(7));
		scores.add(new Cell<Integer>(5));
		scores.add(new Cell<Integer>(9));
		
		
		Column title = new Column("title", titles, Type.STRING,"");
		Column score = new Column("imdb_score", scores, Type.INTEGER,null);
		
		movies.addColumn(title);
		movies.addColumn(score);
		tables.add(movies);
		return tables;
	}

	/**
	 * This method returns an array list with two tables, one containing student information and one containing enrollments information
	 * Table "students" 
	 *   name        	  student_id	       program
	 *    Jan				12345			Informatica
	 *    Piet				23456		    Schakeljaar Toegepaste Informatica	
	 *    Joris				34567			Informatica
	 *    Korneel			45678			Fysica
	 *    
	 * Table "enrollments"
	 *   student_id			 course_id
	 *     23456			    SWOP
	 *     45678				BVP
	 *     23456				AB
	 *     34567				SWOP
	 *     12345				BVP
	 *     12345				AB
	 *     45678				SWOP
	 *     23456				BVP
	 *     12346				SWOP
	 *     
	 *    
	 *   
	 *      
	 */
	public ArrayList<Table> createExampleTablesStudents(){
		ArrayList<Table> tables = new ArrayList<Table>();
		//First table
		StoredTable students = new StoredTable("students");
		ArrayList<Cell<?>> studentNames =new ArrayList<Cell<?>>();
		studentNames.add(new Cell<String>("Jan"));
		studentNames.add(new Cell<String>("Piet"));
		studentNames.add(new Cell<String>("Joris"));
		studentNames.add(new Cell<String>("Korneel"));
		Column studentStudendNames = new Column("name",studentNames,Type.STRING,"");
		
		ArrayList<Cell<?>> student_student_ids =new ArrayList<Cell<?>>();
		student_student_ids.add(new Cell<Integer>(12345));
		student_student_ids.add(new Cell<Integer>(23456));
		student_student_ids.add(new Cell<Integer>(34567));
		student_student_ids.add(new Cell<Integer>(45678));
		Column studentStudentIds = new Column("student_id",student_student_ids,Type.INTEGER,null);
		
		ArrayList<Cell<?>> student_student_programs = new ArrayList<Cell<?>>();
		student_student_programs.add(new Cell<String>("Informatica"));
		student_student_programs.add(new Cell<String>("Schakeljaar Toegepaste Informatica"));
		student_student_programs.add(new Cell<String>("Informatica"));
		student_student_programs.add(new Cell<String>("Fysica"));
		Column studentStudentPrograms = new Column("program",student_student_programs,Type.STRING,"");
		
		students.addColumn(studentStudentIds);
		students.addColumn(studentStudendNames);
		students.addColumn(studentStudentPrograms);
		tables.add(students);
		
		//Second table
		StoredTable enrollments = new StoredTable("enrollments");
		ArrayList<Cell<?>> enrollments_student_ids = new ArrayList<Cell<?>>();
		enrollments_student_ids.add(new Cell<Integer>(23456));
		enrollments_student_ids.add(new Cell<Integer>(45678));
		enrollments_student_ids.add(new Cell<Integer>(23456));
		enrollments_student_ids.add(new Cell<Integer>(34567));
		enrollments_student_ids.add(new Cell<Integer>(12345));
		enrollments_student_ids.add(new Cell<Integer>(12345));
		enrollments_student_ids.add(new Cell<Integer>(45678));
		enrollments_student_ids.add(new Cell<Integer>(23456));
		enrollments_student_ids.add(new Cell<Integer>(12346));
		Column enrollmentStudentIds = new Column("student_id",enrollments_student_ids,Type.INTEGER,null);
		
		ArrayList<Cell<?>> enrollments_course_ids = new ArrayList<Cell<?>>();
		enrollments_course_ids.add(new Cell<String>("SWOP"));
		enrollments_course_ids.add(new Cell<String>("BVP"));
		enrollments_course_ids.add(new Cell<String>("AB"));
		enrollments_course_ids.add(new Cell<String>("SWOP"));
		enrollments_course_ids.add(new Cell<String>("BVP"));
		enrollments_course_ids.add(new Cell<String>("AB"));
		enrollments_course_ids.add(new Cell<String>("SWOP"));
		enrollments_course_ids.add(new Cell<String>("BVP"));
		enrollments_course_ids.add(new Cell<String>("SWOP"));
		Column enrollmentsCourseIds = new Column("course_id",enrollments_course_ids, Type.STRING,"");
		
		enrollments.addColumn(enrollmentStudentIds);
		enrollments.addColumn(enrollmentsCourseIds);
		tables.add(enrollments);
	
		return tables;
	}
	
}