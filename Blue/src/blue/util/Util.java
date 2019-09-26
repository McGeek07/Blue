package blue.util;

public final class Util {
	
	private Util() {
		//do nothing
	}
	
	public static int stringToInt(String str) {
		return stringToInt(str, 0);
	}
	
	public static int stringToInt(String str, int alt) {
		try {
			return Integer.parseInt(str);
		} catch(Exception e) {
			return alt;
		}
	}
	
	public static float stringToFloat(String str) {
		return stringToFloat(str, 0f);
	}
	
	public static float stringToFloat(String str, float alt) {
		try {
			return Float.parseFloat(str);
		} catch(Exception e) {
			return alt;
		}
	}
	
	public static long stringToLong(String str) {
		return stringToLong(str, 0l);
	}
	
	public static long stringToLong(String str, long alt) {
		try {
			return Long.parseLong(str);
		} catch(Exception e) {
			return alt;
		}
	}
	
	public static double stringToDouble(String str) {
		return stringToDouble(str, 0.);
	}
	
	public static double stringToDouble(String str, double alt) {
		try {
			return Double.parseDouble(str);
		} catch(Exception e) {
			return alt;
		}
	}
	
	public static boolean stringToBoolean(String str) {
		return stringToBoolean(str, false);
	}
	
	public static boolean stringToBoolean(String str, boolean alt) {
		try {
			return Boolean.parseBoolean(str);
		} catch(Exception e) {
			return alt;
		}
	}
}
