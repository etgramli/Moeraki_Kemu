package de.htwg.se.util;

public class StringHelper {

	public static final String MT = "";
	
	public static final String getDefaultEmptyString(final String str) {
		return StringHelper.getDefaultString(str, MT);
	}
	
	public static final String getDefaultString(final String str, final String defaultStr) {
		return StringHelper.isEmpty(str) ? defaultStr : str;
	}
	
	public static final boolean isNotEmpty(final String str) {
		return !StringHelper.isEmpty(str);
	}
	
	public static final boolean isEmpty(final String str) {
		return str == null || str.isEmpty();
	}

}
