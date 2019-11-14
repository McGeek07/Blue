package blue.util;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.TreeMap;

import blue.util.Util.ObjectToString;
import blue.util.Util.StringToObject;

public class Configuration {
	protected final TreeMap<String, String>
		map = new TreeMap<String, String>();
	
	public Configuration(Object... args) {
		int length = args.length - (args.length & 1);
		for(int i = 0; i < length; i += 2) {
			int 
				a = i + 0,
				b = i + 1;
			set(args[a], args[b]);			
		}
	}
	
	public void set(Object key, Object val) {
		String
			_key = key != null ? key.toString() : null,
			_val = val != null ? val.toString() : null;
		map.put(
				_key,
				_val
				);
	}
	
	public <OBJECT> void set(ObjectToString<OBJECT> o2s, Object key, OBJECT val) {
		String
			_key = key != null ?                                   key.toString() : null,
			_val = val != null ? o2s != null ? o2s.toString(val) : val.toString() : null;
		map.put(
				_key,
				_val
				);
	}

	public String get(Object key) {
		return get(key, null);
	}
	
	public String get(Object key, Object alt) {
		String 
			_key = key != null ? key.toString() : null,
			_alt = alt != null ? alt.toString() : null,
			_val = map.get(_key);
		return _val != null ? _val : _alt;
	}
	
	public <T> T get(StringToObject<T> s2o, Object key) {
		return get(s2o, key, null);
	}
	
	public <T> T get(StringToObject<T> s2o, Object key, T alt) {
		return Util.stringToObject(s2o, get(key), alt);
	}
	
	public int getInteger(Object key) {
		return getInteger(key, 0);
	}
	
	public int getInteger(Object key, int alt) {
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
	
	public void save(String path) {
		save(new File(path));
	}
	
	public void save(File   file) {
		Util.printToFile(file, false, map);
	}
	
	public void load(String path) {
		load(new File(path));
	}
	
	public void load(File   file) {
		Util.parseFromFile(file, map);
	}
	
	public void print(PrintStream out) {
		Util.print(out, map);
	}
	
	public void print(PrintWriter out) {
		Util.print(out, map);
	}
}
