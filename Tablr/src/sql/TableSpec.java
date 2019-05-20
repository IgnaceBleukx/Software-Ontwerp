package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;
import exceptions.InvalidNameException;
import exceptions.InvalidQueryException;

public abstract class TableSpec {

	public abstract HashMap<String, String> findTableNameAliases();

	public abstract Table resolve(ArrayList<Table> tables) throws InvalidQueryException;

	public abstract ArrayList<String> getName();

}
