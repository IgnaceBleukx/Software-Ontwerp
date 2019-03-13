package domain;


/**
 * Class holding an email type
 *
 */
public class Email {

	public static boolean isValid(String string) {
		return string.contains('@');
	}

}
