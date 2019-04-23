package Utils;

/**
 * Class used to cast string to boolean 
 */
public class BooleanCaster {
	/**
	 * Casts a string to a boolean value. All case variants of 'true' return true, all case variants of 'false' return false.
	 * An empty string casts to false. Other values throw a ClassCastError.
	 * @param s		String to cast
	 * @return		The boolean interpretation of the string
	 */
	public static Boolean cast(String s) {
		if (s.toLowerCase().equals("true"))
			return true;
		else if (s.toLowerCase().equals("false"))
			return false;
		else if (s.equals(""))
			return null;
			
		throw new ClassCastException("Unable to cast string to boolean: "+s);
	}
}
