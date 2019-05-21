package exceptions;

/**
 * Thrown when a query is not valid during execution.
 */
public class InvalidQueryException extends Exception {
	public InvalidQueryException(String message){
		super(message)
;	}
	
}
