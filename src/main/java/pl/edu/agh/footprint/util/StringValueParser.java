package pl.edu.agh.footprint.util;

/**
 * This class contains utility methods used for converting {@link String}s to one of desired specific types.
 *
 * @author Bartłomiej Grochal
 */
public class StringValueParser {

	private StringValueParser() {
	}


	/**
	 * Returns (primitive) double value parsed from given {@code numberString}.
	 *
	 * @throws IllegalArgumentException when given number string does not conform with pattern defining floating-point
	 *                                  numbers.
	 */
	public static double parseDouble(String numberString) throws IllegalArgumentException {
		try {
			return Double.parseDouble(numberString);
		} catch (NumberFormatException exc) {
			throw new IllegalArgumentException("Given value is not a number: " + numberString + ".");
		}
	}

}
