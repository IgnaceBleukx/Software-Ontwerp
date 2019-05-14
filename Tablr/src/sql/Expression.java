package sql;

import java.util.ArrayList;
import java.util.HashMap;

import domain.Table;

public abstract class Expression<T> {
	public abstract T eval(ArrayList<Table> tables, int rowNb,HashMap<String,String> tableNames);
}
