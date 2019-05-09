package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import domain.*;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;

import org.junit.Test;

import sql.CellIDExpression;
import sql.JoinTableSpec;
import sql.SimpleTableSpec;

public class SQLTests {
	@Test
	public void testJOIN() throws InvalidNameException, InvalidQueryException {
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
		
		table1.printTable();
		table2.printTable();
		
		ArrayList<Table> tables = new ArrayList<>();
		tables.add(table1);
		tables.add(table2);

		SimpleTableSpec s1 = new SimpleTableSpec("Table1", "Table1");
		SimpleTableSpec s2 = new SimpleTableSpec("Table2", "Table2");
		JoinTableSpec s = new JoinTableSpec(s1, s2, 
				new CellIDExpression("Table1", "colB"), 
				new CellIDExpression("Table2", "colC"));
		Table result = s.resolve(tables);
		result.printTable();
		
		
	}
}