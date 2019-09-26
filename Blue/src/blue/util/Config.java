package blue.util;

import java.util.TreeMap;

public class Config {
	protected final TreeMap<String, String>
		index = new TreeMap<String, String>();
	
	public Config(Object... args) {
		int length = args.length - (args.length & 1);
		for(int i = 0; i < length; i ++) {
			int 
				a = i + 0,
				b = i + 1;
			set(args[a], args[b]);			
		}
	}
	
	public void set(Object key, Object val) {
		index.put(
				"" + key,
				"" + val
				);
	}
	
	public int getInt(Object key) {
		return getInt(key, 0);
	}
	
	public int getInt(Object key, int alt) {
		return Util.stringToInt(get(key), alt);
	}
	
	public float getFloat(Object key) {
		return getFloat(key, 0f);
	}
	
	public float getFloat(Object key, float alt) {
		return Util.stringToFloat(get(key), alt);
	}
	
	public long getLong(Object key) {
		return getLong(key, 0l);
	}
	
	public long getLong(Object key, long alt) {
		return Util.stringToLong(get(key), alt);
	}
	
	public double getDouble(Object key) {
		return getDouble(key, 0.);
	}
	
	public double getDouble(Object key, double alt) {
		return Util.stringToDouble(get(key), alt);
	}
	
	public boolean getBoolean(Object key) {
		return getBoolean(key, false);
	}
	
	public boolean getBoolean(Object key, boolean alt) {
		return Util.stringToBoolean(get(key), alt);
	}
	
	public String get(Object key) {
		return index.get("" + key);
	}
}
