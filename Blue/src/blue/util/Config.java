package blue.util;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.TreeMap;

public class Config {
	protected final TreeMap<String, String>
		index = new TreeMap<String, String>();
	
	public Config(Object... args) {
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
			_key = key != null ? "" + key : null,
			_val = val != null ? "" + val : null;
		index.put(
				_key,
				_val
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
		return get(key, null);
	}
	
	public String get(Object key, Object alt) {
		String 
			_key = key != null ? "" + key : null,
			_alt = alt != null ? "" + alt : null,
			_val = index.get(_key);
		return _val != null ? _val : _alt;
	}
	
	public void save(String path) {
		save(new File(path));
	}
	
	public void save(File file  ) {
		Util.printToFile(file, false, index);
	}
	
	public void load(String path) {
		load(new File(path));
	}
	
	public void load(File file  ) {
		Util.parseFromFile(file, index);
	}
	
	public void print(PrintStream out) {
		Util.print(out, index);
	}
	
	public void print(PrintWriter out) {
		Util.print(out, index);
	}
}
