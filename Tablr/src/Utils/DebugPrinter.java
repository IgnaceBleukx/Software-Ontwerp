package Utils;

/**
 * Provides a way to print debug messages 
 * that can easily be traced back to their
 * place in the code
 *
 */
public class DebugPrinter {

	/**
	 * Prints to Standard out
	 * @param message		Object to print
	 */
	public static void print(Object message) {
		 StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		 System.out.println(""+stackTraceElements[2]+": " + message);
		
	}
	
}
