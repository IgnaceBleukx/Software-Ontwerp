# Software-Ontwerp
Tom De Backer, Ignace Bleukx, Martijn Slaets, Quinten Bruynseraede

## Specification guidelines 
### Class specification
Each class is preceded by a short comment explaining what the class does.

*Example*
```
/**
 * 	Class representing a Table with some properties. A table consists of table and rows.
 */
public class ClassA extends ClassB {}
```

### Method specification
Each method is preceded by a short comment explaining what the method does, as well as a formal specification of the parameters and a description of how the state of the program changes after applying this method. This state change may be described using *@note* and *@post* tags, depending on how formal the description can be made. As such, *@post* tags are preferred.

Post conditions may be specified using formal or informal notation. Formal notation is indented one tab and preceded by *|*.

*Example*
```
/**
	 * Adds a row to the table.
	 * @param 	row
	 * 			The row to be added to the table.
	 * @post	The table contains one more row 
	 * 			| new.getNumberOfRows() = old.getNumberOfRows() + 1
	 * @note	When the table is full, an exception is thrown
	 */
	public void addRow(Row row) throws TooManyRowsException {}
```

### Variable specification
Each field is preceded by a short description of its meaning.

*Example*
```
/**
	 * Variable registering the initial radius of a planetoid.	
	 */
	private final double initialRadius;
  ```
