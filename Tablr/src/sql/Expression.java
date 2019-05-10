package sql;

import java.util.ArrayList;

import domain.Table;

public abstract class Expression<T> {
	public abstract T eval(ArrayList<Table> tables, int rowNb);
}
