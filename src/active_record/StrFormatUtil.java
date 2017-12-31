package active_record;

public class StrFormatUtil {
	
	/**
	 * Methods returns a string based on parameter string preceded with white spaces that the length of the output string equals parameter "length"  
	 * @param string
	 * @param length
	 * @return string of certain length
	 */
	public static String fixedLeftLengthString(String string, int length) {
	    return String.format("%1$"+length+ "s", string);
	}
	
	/**
	 * Methods returns a string based on parameter string followed by white spaces that the length of the output string equals parameter "length"
	 * @param string
	 * @param length
	 * @return string of certain length
	 */
	public static String fixedRightLengthString(String string, int length) {
	    return String.format("%1$-"+length+ "s", string);
	}
}
