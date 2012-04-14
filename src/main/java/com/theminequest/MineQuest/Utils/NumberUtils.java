package com.theminequest.MineQuest.Utils;

import java.util.regex.Pattern;

public class NumberUtils {

	/**
	 * Check if a string can be parsed correctly. This is taken
	 * straight from the Java API.
	 * @param myString String to parse and retrieve a Double
	 * @return Double value, or null if the string is invalid.
	 */
	public static Double parseDouble(String myString){
		final String Digits     = "(\\p{Digit}+)";
		final String HexDigits  = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally 
		// signed decimal integer.
		final String Exp        = "[eE][+-]?"+Digits;
		final String fpRegex    =
				("[\\x00-\\x20]*"+  // Optional leading "whitespace"
						"[+-]?(" + // Optional sign character
						"NaN|" +           // "NaN" string
						"Infinity|" +      // "Infinity" string

	                 // A decimal floating-point string representing a finite positive
	                 // number without a leading sign has at most five basic pieces:
	                 // Digits . Digits ExponentPart FloatTypeSuffix
	                 // 
	                 // Since this method allows integer-only strings as input
	                 // in addition to strings of floating-point literals, the
	                 // two sub-patterns below are simplifications of the grammar
	                 // productions from the Java Language Specification, 2nd 
	                 // edition, section 3.10.2.

	                 // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
	                 "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

	                 // . Digits ExponentPart_opt FloatTypeSuffix_opt
	                 "(\\.("+Digits+")("+Exp+")?)|"+

	           // Hexadecimal strings
	           "((" +
	           // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
	           "(0[xX]" + HexDigits + "(\\.)?)|" +

	            // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
	            "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

	            ")[pP][+-]?" + Digits + "))" +
	            "[fFdD]?))" +
						"[\\x00-\\x20]*");// Optional trailing "whitespace"

		if (Pattern.matches(fpRegex, myString))
			return Double.valueOf(myString); // Will not throw NumberFormatException
		else {
			return null;
		}

	}
	
	/**
	 * Check if a string can be parsed correctly. This is taken
	 * straight from the Java API.
	 * @param myString String to parse and retrieve a Float
	 * @return Float value, or null if the string is invalid.
	 */
	public static Float parseFloat(String myString){
		final String Digits     = "(\\p{Digit}+)";
		final String HexDigits  = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally 
		// signed decimal integer.
		final String Exp        = "[eE][+-]?"+Digits;
		final String fpRegex    =
				("[\\x00-\\x20]*"+  // Optional leading "whitespace"
						"[+-]?(" + // Optional sign character
						"NaN|" +           // "NaN" string
						"Infinity|" +      // "Infinity" string

	                 // A decimal floating-point string representing a finite positive
	                 // number without a leading sign has at most five basic pieces:
	                 // Digits . Digits ExponentPart FloatTypeSuffix
	                 // 
	                 // Since this method allows integer-only strings as input
	                 // in addition to strings of floating-point literals, the
	                 // two sub-patterns below are simplifications of the grammar
	                 // productions from the Java Language Specification, 2nd 
	                 // edition, section 3.10.2.

	                 // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
	                 "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

	                 // . Digits ExponentPart_opt FloatTypeSuffix_opt
	                 "(\\.("+Digits+")("+Exp+")?)|"+

	           // Hexadecimal strings
	           "((" +
	           // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
	           "(0[xX]" + HexDigits + "(\\.)?)|" +

	            // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
	            "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

	            ")[pP][+-]?" + Digits + "))" +
	            "[fFdD]?))" +
						"[\\x00-\\x20]*");// Optional trailing "whitespace"

		if (Pattern.matches(fpRegex, myString))
			return Float.valueOf(myString); // Will not throw NumberFormatException
		else {
			return null;
		}

	}

}
