package blue.util;

import java.util.Map;

public final class Configuration {
	
	private Configuration() {
		//hide constructor
	}

	public static <T extends Map<String, String>> T parse(T map, String s, String... tags) {
		String[] t = s.split("\\,");
		
		int i = 0;
		for(; i < t.length; i ++)
			if(t[i] != null) {
				String
					u[] = t[i].split("\\="),
					u0 = u.length > 1 ? u[0].trim() : null,
					u1 = u.length > 1 ? u[1].trim() : u[0].trim();
				if(u0 != null) {
					map.put(u0 , u1);
					t[i] = null;
				}			
			}
		int j = 0;
		for(String tag: tags)
			if(!map.containsKey(tag))
				for(; j < t.length; j ++)
					if(t[j] != null) {
						map.put(tag, t[j]);
						t[j] = null;
						break;
					}
		return map;
	}
	
	public static void map(Map<String, String> map, Object... args) {
		int length = args.length - (args.length & 1);
		for(int i = 0; i < length; i += 2) {
			int 
				a = i + 0,
				b = i + 1;
			setProperty(map, args[a], args[b]);
		}
	}
	
	public static String setProperty(Map<String, String> map, Object key, Object val) {
		String
			_key = key != null ? key.toString() : null,
			_val = val != null ? val.toString() : null;
		map.put(
				_key,
				_val
				);
		return _val;
	}
	
	public static <T> String setProperty(Map<String, String> map, ObjectToString<T> o2s, Object key, T val) {
		String
			_key = key != null ?                                   key.toString() : null,
			_val = val != null ? o2s != null ? o2s.toString(val) : val.toString() : null;
		map.put(
				_key,
				_val
				);
		return _val;
	}

	public static String getProperty(Map<String, String> map, Object key) {
		return getProperty(map, key, null);
	}
	
	public static String getProperty(Map<String, String> map, Object key, Object alt) {
		String 
			_key = key != null ? key.toString() : null,
			_alt = alt != null ? alt.toString() : null,
			_val = map.get(_key);
		return _val != null ? _val : _alt;
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
	
	public static int getPropertyAsInt(Map<String, String> map, Object key) {
		return getPropertyAsInt(map, key, 0);
	}
	
	public static int getPropertyAsInt(Map<String, String> map, Object key, int alt) {
		return stringToInt(getProperty(map, key), alt);
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
	
	public static float getPropertyAsFloat(Map<String, String> map, Object key) {
		return getPropertyAsFloat(map, key, 0f);
	}
	
	public static float getPropertyAsFloat(Map<String, String> map, Object key, float alt) {
		return stringToFloat(getProperty(map, key), alt);
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
	
	public static long getPropertyAsLong(Map<String, String> map, Object key) {
		return getPropertyAsLong(map, key, 0l);
	}
	
	public static long getPropertyAsLong(Map<String, String> map, Object key, long alt) {
		return stringToLong(getProperty(map, key), alt);
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
	
	public static double getPropertyAsDouble(Map<String, String> map, Object key) {
		return getPropertyAsDouble(map, key, 0d);
	}
	
	public static double getPropertyAsDouble(Map<String, String> map, Object key, double alt) {
		return stringToDouble(getProperty(map, key), alt);
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
	
	public static boolean getPropertyAsBoolean(Map<String, String> map, Object key) {
		return getPropertyAsBoolean(map, key, false);
	}
	
	public static boolean getPropertyAsBoolean(Map<String, String> map, Object key, boolean alt) {
		return stringToBoolean(getProperty(map, key), alt);
	}
	
	public static <T> T stringToObject(StringToObject<T> s2o, String s) {
		return stringToObject(s2o, s, null);
	}	
	
	public static <T> T stringToObject(StringToObject<T> s2o, String s, T t) {
		return s2o != null && s != null ? s2o.toObject(s) : t;
	}
	
	public static <T> T getPropertyAsObject(Map<String, String> map, StringToObject<T> s2o, Object key) {
		return getPropertyAsObject(map, s2o, key, null);
	}
	
	public static <T> T getPropertyAsObject(Map<String, String> map, StringToObject<T> s2o, Object key, T alt) {
		return stringToObject(s2o, getProperty(map, key), alt);
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
	
	public static final ObjectToString<?>
		OBJECT_TO_STRING = Object::toString;
}
