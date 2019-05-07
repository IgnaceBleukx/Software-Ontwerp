package Utils;

public class DebugPrinter {

	public static void print(Object message) {
		 StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		 System.out.println("["+stackTraceElements[2]+"]: " + message);
		
	}
	
}
