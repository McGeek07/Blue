package blue.util;

public class StringUtility {
	
	private StringUtility() {
		//hide constructor
	}
	
	public static int stringToInt(String s) {
		return stringToInt(s, 0);
	}	
	
	public static int stringToInt(String s, int i) {
		try {
			return Integer.parseInt(s);
		} catch(Exception e) {
			return i;
		}
	}
	
	public static float stringToFloat(String s) {
		return stringToFloat(s, 0f);
	}
	
	public static float stringToFloat(String str, float f) {
		try {
			return Float.parseFloat(str);
		} catch(Exception e) {
			return f;
		}
	}
	
	public static long stringToLong(String s) {
		return stringToLong(s, 0l);
	}
	
	public static long stringToLong(String s, long l) {
		try {
			return Long.parseLong(s);
		} catch(Exception e) {
			return l;
		}
	}
	
	public static double stringToDouble(String s) {
		return stringToDouble(s, 0.);
	}
	
	public static double stringToDouble(String s, double d) {
		try {
			return Double.parseDouble(s);
		} catch(Exception e) {
			return d;
		}
	}
	
	public static boolean stringToBoolean(String s) {
		return stringToBoolean(s, false);
	}
	
	public static boolean stringToBoolean(String s, boolean b) {
		try {
			return Boolean.parseBoolean(s);
		} catch(Exception e) {
			return b;
		}
	}
	
	public static <T> T stringToObject(StringToObject<T> s2o, String s) {
		return stringToObject(s2o, s, null);
	}	
	
	public static <T> T stringToObject(StringToObject<T> s2o, String s, T t) {
		return s2o != null && s != null ? s2o.toObject(s) : t;
	}
	
	public static <T> String objectToString(ObjectToString<T> o2s, T t) {
		return t != null ? o2s != null ? o2s.toString(t) : t.toString() : null;
	}	

	public static interface ObjectToString<T> {
		public String toString(T obj);
	}
	
	public static interface StringToObject<T> {
		public T toObject(String str);
	}
}
