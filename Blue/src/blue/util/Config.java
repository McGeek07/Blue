package blue.util;

import static blue.util.StringUtility.stringToBoolean;
import static blue.util.StringUtility.stringToDouble;
import static blue.util.StringUtility.stringToFloat;
import static blue.util.StringUtility.stringToInt;
import static blue.util.StringUtility.stringToLong;
import static blue.util.StringUtility.stringToObject;

import java.util.Map;

import blue.util.StringUtility.ObjectToString;
import blue.util.StringUtility.StringToObject;

public final class Config {
	
	private Config() {
		//hide constructor
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
	
	public static int getPropertyAsInt(Map<String, String> map, Object key) {
		return getPropertyAsInt(map, key, 0);
	}
	
	public static int getPropertyAsInt(Map<String, String> map, Object key, int alt) {
		return stringToInt(getProperty(map, key), alt);
	}
	
	public static float getPropertyAsFloat(Map<String, String> map, Object key) {
		return getPropertyAsFloat(map, key, 0f);
	}
	
	public static float getPropertyAsFloat(Map<String, String> map, Object key, float alt) {
		return stringToFloat(getProperty(map, key), alt);
	}	
	
	public static long getPropertyAsLong(Map<String, String> map, Object key) {
		return getPropertyAsLong(map, key, 0l);
	}
	
	public static long getPropertyAsLong(Map<String, String> map, Object key, long alt) {
		return stringToLong(getProperty(map, key), alt);
	}
	
	public static double getPropertyAsDouble(Map<String, String> map, Object key) {
		return getPropertyAsDouble(map, key, 0d);
	}
	
	public static double getPropertyAsDouble(Map<String, String> map, Object key, double alt) {
		return stringToDouble(getProperty(map, key), alt);
	}
	
	public static boolean getPropertyAsBoolean(Map<String, String> map, Object key) {
		return getPropertyAsBoolean(map, key, false);
	}
	
	public static boolean getPropertyAsBoolean(Map<String, String> map, Object key, boolean alt) {
		return stringToBoolean(getProperty(map, key), alt);
	}	
	
	public static <T> T getPropertyAsObject(Map<String, String> map, StringToObject<T> s2o, Object key) {
		return getPropertyAsObject(map, s2o, key, null);
	}
	
	public static <T> T getPropertyAsObject(Map<String, String> map, StringToObject<T> s2o, Object key, T alt) {
		return stringToObject(s2o, getProperty(map, key), alt);
	}

	public static <T extends Map<String, String>> T parse(T map, String s, String... tags) {
		String[] t = s.split("[\\,|\n]");
		
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
}
