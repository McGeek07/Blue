package blue.util;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.TreeMap;

import blue.util.Util.ObjectToString;
import blue.util.Util.StringToObject;

public class Configuration implements Serializable {
	private static final long 
		serialVersionUID = 1L;
	protected final TreeMap<String, String>
		map  =  new TreeMap<String, String>();
	
	public Configuration(Object... args) {
		Util.map(map, args);
	}
	
	public void set(Object... args) {
		Util.map(map, args);
	}
	
	public void set(Object key, Object val) {
		Util.setEntry(map, key, val);
	}
	
	public <T> void set(ObjectToString<T> o2s, Object key, T val) {
		Util.setEntry(map, o2s, key, val);
	}
	
	public String get(Object key) {
		return Util.getEntry(map, key);
	}
	
	public String get(Object key, Object alt) {
		return Util.getEntry(map, key, alt);
	}
	
	public <T> T get(StringToObject<T> s2o, Object key) {
		return Util.getEntry(map, s2o, key);
	}
	
	public <T> T get(StringToObject<T> s2o, Object key, T alt) {
		return Util.getEntry(map, s2o, key, alt);
	}
	
	public int getInt(Object key) {
		return Util.getEntryAsInt(map, key);
	}
	
	public int getInt(Object key, int alt) {
		return Util.getEntryAsInt(map, key, alt);
	}
	
	public float getFloat(Object key) {
		return Util.getEntryAsFloat(map, key);
	}
	
	public float getFloat(Object key, float alt) {
		return Util.getEntryAsFloat(map, key, alt);
	}
	
	public long getLong(Object key) {
		return Util.getEntryAsLong(map, key);
	}
	
	public long getLong(Object key, long alt) {
		return Util.getEntryAsLong(map, key, alt);
	}
	
	public double getDouble(Object key) {
		return Util.getEntryAsDouble(map, key);
	}
	
	public double getDouble(Object key, double alt) {
		return Util.getEntryAsDouble(map, key, alt);
	}
	
	public boolean getBoolean(Object key) {
		return Util.getEntryAsBoolean(map, key);
	}
	
	public boolean getBoolean(Object key, boolean alt) {
		return Util.getEntryAsBoolean(map, key, alt);
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
