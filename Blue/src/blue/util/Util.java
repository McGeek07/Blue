package blue.util;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import blue.geom.Bounds2;
import blue.geom.Region2;

public final class Util {
	
	private Util() {
		//do nothing
	}
	
	public static int clamp(int x, int a, int b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static float clamp(float x, float a, float b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static long clamp(long x, long a, long b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static double clamp(double x, double a, double b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static int sign(int x) {
		return x != 0 ? x > 0 ? 1 : -1 : 0;
	}
	
	public static float sign(float x) {
		return x != 0f ? x > 0f ? 1f : -1f : 0f;
	}
	
	public static long sign(long x) {
		return x != 0 ? x > 0 ? 1 : -1 : 0;
	}
	
	public static double sign(double x) {
		return x != 0. ? x > 0. ? 1. : -1. : 0.;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> typeOf(T t) {
		return (Class<T>)t.getClass();
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
		if(str != null) {
    		str = str.trim().toLowerCase();
        	switch(str) {
            	case "0":
            	case "f":
            	case "false":
            		return false;
            	case "1":
            	case "t":
            	case "true":
            		return true;            		
        	}
    	}
    	return alt;
	}
	
	public static <T> String objectToString(ObjectToString<T> o2s, T obj) {
		return obj != null ? o2s != null ? o2s.toString(obj) : obj.toString() : null;
	}	
	public static <T> T stringToObject(StringToObject<T> s2o, String str) {
		return stringToObject(s2o, str, null);
	}	
	public static <T> T stringToObject(StringToObject<T> s2o, String str, T alt) {
		return s2o != null && str != null ? s2o.toObject(str) : alt;
	}	

	public static interface ObjectToString<T> {
		public String toString(T obj);
	}
	public static interface StringToObject<T> {
		public T toObject(String str);
	}
	
	public static final ObjectToString<?>
		OBJECT_TO_STRING = Object::toString;
	
	public static Map<String, String> set(Map<String, String> map, Object... args) {
		int length = args.length - (args.length & 1);
		for(int i = 0; i < length; i += 2) {
			int 
				a = i + 0,
				b = i + 1;
			setEntry(map, args[a], args[b]);
		}		
		return map;
	}
	
	public static Map<String, String> setEntry(Map<String, String> map, Object key, Object val) {
		String
			_key = key != null ? key.toString() : null,
			_val = val != null ? val.toString() : null;
		map.put(
				_key,
				_val
				);
		return map;
	}
	
	public static <T> Map<String, String> setEntry(Map<String, String> map, ObjectToString<T> o2s, Object key, T val) {
		String
			_key = key != null ?                                   key.toString() : null,
			_val = val != null ? o2s != null ? o2s.toString(val) : val.toString() : null;
		map.put(
				_key,
				_val
				);
		return map;
	}

	public static String getEntry(Map<String, String> map, Object key) {
		return getEntry(map, key, null);
	}
	
	public static String getEntry(Map<String, String> map, Object key, Object alt) {
		String 
			_key = key != null ? key.toString() : null,
			_alt = alt != null ? alt.toString() : null,
			_val = map.get(_key);
		return _val != null ? _val : _alt;
	}
	
	public static <T> T getEntry(Map<String, String> map, StringToObject<T> s2o, Object key) {
		return getEntry(map, s2o, key, null);
	}
	
	public static <T> T getEntry(Map<String, String> map, StringToObject<T> s2o, Object key, T alt) {
		return Util.stringToObject(s2o, getEntry(map, key), alt);
	}
	
	public static int getEntryAsInt(Map<String, String> map, Object key) {
		return getEntryAsInt(map, key, 0);
	}
	
	public static int getEntryAsInt(Map<String, String> map, Object key, int alt) {
		return Util.stringToInt(getEntry(map, key), alt);
	}
	
	public static long getEntryAsLong(Map<String, String> map, Object key) {
		return getEntryAsLong(map, key, 0l);
	}
	
	public static long getEntryAsLong(Map<String, String> map, Object key, long alt) {
		return Util.stringToLong(getEntry(map, key), alt);
	}
	
	public static float getEntryAsFloat(Map<String, String> map, Object key) {
		return getEntryAsFloat(map, key, 0f);
	}
	
	public static float getEntryAsFloat(Map<String, String> map, Object key, float alt) {
		return Util.stringToFloat(getEntry(map, key), alt);
	}
	
	public static double getEntryAsDouble(Map<String, String> map, Object key) {
		return getEntryAsDouble(map, key, 0d);
	}
	
	public static double getEntryAsDouble(Map<String, String> map, Object key, double alt) {
		return Util.stringToDouble(getEntry(map, key), alt);
	}
	
	public static boolean getEntryAsBoolean(Map<String, String> map, Object key) {
		return getEntryAsBoolean(map, key, false);
	}
	
	public static boolean getEntryAsBoolean(Map<String, String> map, Object key, boolean alt) {
		return Util.stringToBoolean(getEntry(map, key), alt);
	}
	
	public static GraphicsDevice getGraphicsDevice(int i) {
		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		if(i >= 0 && i < gd.length)
			return gd[i];
		else if (gd.length > 0)
			return gd[0];
		else
			return null;
	}
	
	public static Region2 computeMaximumScreenRegion(int i) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		
		return new Region2(
				bounds.x,
				bounds.y,
				bounds.width,
				bounds.height
				);
	}
	
	public static Region2 computeMaximumWindowRegion(int i) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		Insets    insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		
		return new Region2(
				bounds.x + insets.left,
				bounds.y + insets.top ,
				bounds.width  - insets.left - insets.right ,
				bounds.height - insets.top  - insets.bottom
				);		
	}
	
	public static Bounds2 computeMaximumScreenBounds(int i) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		
		return new Bounds2(
				bounds.x,
				bounds.y,
				bounds.x + bounds.width,
				bounds.y + bounds.height
				);
	}
	
	public static Bounds2 computeMaximumWindowBounds(int i) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		Insets    insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		
		return new Bounds2(
				bounds.x + insets.left,
				bounds.y + insets.top ,
				bounds.x + bounds.width  - insets.left - insets.right ,
				bounds.y + bounds.height - insets.top  - insets.bottom
				);		
	}
	
	public static BufferedImage createBufferedImage(int i, int w, int h) {
		return createBufferedImage(w, h, Transparency.TRANSLUCENT);
	}
	
	public static VolatileImage createVolatileImage(int i, int w, int h) {
		return createVolatileImage(w, h, Transparency.TRANSLUCENT);
	}
	
	public static BufferedImage createBufferedImage(int i, int w, int h, int transparency) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		return gc.createCompatibleImage(w, h, transparency);
	}
	
	public static VolatileImage createVolatileImage(int i, int w, int h, int transparency) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		return gc.createCompatibleVolatileImage(w, h, transparency);
	}
	
	
	public static <T> void print(PrintStream out, T[] list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void print(PrintWriter out, T[] list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void print(PrintStream out, Iterable<T> list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void print(PrintWriter out, Iterable<T> list) {
		for(T t: list) out.println(t);
	}
	
	public static <K, V> void print(PrintStream out, Map<K, V> map) {
		map.forEach((key, val) -> {
			out.println(key + "=" + val);
		});
	}
	
	public static <K, V> void print(PrintWriter out, Map<K, V> map) {
		map.forEach((key, val) -> {
			out.println(key + "=" + val);
		});
	}
	
	public static File createFile(File file) {
		if(!file.exists())
			try {
				if(file.getParentFile() != null)
					file.getParentFile().mkdirs();
				file.createNewFile();
			} catch(Exception ex) {
				System.err.println("[blue.util.Util.createFile] Unable to create file \"" + file + "\"");
				ex.printStackTrace();
			}
		return file;
	}
	
	public static File deleteFile(File file) {
		if(file.exists()) {
			try {
				file.delete();
			} catch(Exception ex) {
				System.err.println("[blue.util.Util.deleteFile] Unable to delete file \"" + file + "\"");
				ex.printStackTrace();
			}
		}
		return file;
	}
	
	public static ObjectOutputStream createObjectOutputStream(String path, boolean append) {
		return createObjectOutputStream(new File(path), append);
	}
	
	public static ObjectOutputStream createObjectOutputStream(File   file, boolean append) {
		try {
			return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(createFile(file), append)));
		} catch (IOException ioe) {
			System.err.println("[blue.util.Util.createObjectOutputStream] Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static ObjectInputStream createObjectInputStream(String path) {
		return createObjectInputStream(new File(path));
	}
	
	public static ObjectInputStream createObjectInputStream(File   file) {
		try {
			return new ObjectInputStream(new BufferedInputStream(new FileInputStream(createFile(file))));
		} catch(IOException ioe) {
			System.err.println("[blue.util.Util.createObjectInputStream] Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static BufferedWriter createBufferedWriter(String path, boolean append) {
		return createBufferedWriter(new File(path), append);
	}
	
	public static BufferedWriter createBufferedWriter(File   file, boolean append) {
		try {
			return new BufferedWriter(new FileWriter(createFile(file), append));
		} catch(IOException ioe) {
			System.err.println("[blue.util.Util.createBufferedWriter] Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static PrintWriter createPrintWriter(String path, boolean append) {
		return createPrintWriter(new File(path), append);
	}
	
	public static PrintWriter createPrintWriter(File   file, boolean append) {
		try {
			return new PrintWriter(new BufferedWriter(new FileWriter(createFile(file), append)));
		} catch(IOException ioe) {
			System.err.println("[blue.util.Util.createPrintWriter] Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static BufferedReader createBufferedReader(String path) {
		return createBufferedReader(new File(path));
	}
	
	public static BufferedReader createBufferedReader(File   file) {
		try {
			return new BufferedReader(new FileReader(createFile(file)));
		} catch(IOException ioe) {
			System.err.println("[blue.util.Util.createBufferedReader] Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static <T> void writeToFile(String path, boolean append, T obj) {
		writeToFile(new File(path), append, obj);
	}
	
	public static <T> void writeToFile(File   file, boolean append, T obj) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(obj);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void writeToFile(String path, boolean append, T[] list) {
		writeToFile(new File(path), append, list);
	}
	
	public static <T> void writeToFile(File   file, boolean append, T[] list) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(list);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void writeToFile(String path, boolean append, Iterable<T> list) {
		writeToFile(new File(path), append, list);
	}
	
	public static <T> void writeToFile(File   file, boolean append, Iterable<T> list) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(list);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> T readFromFile(String path) {
		return readFromFile(new File(path));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readFromFile(File   file) {
		T obj = null;
		try(ObjectInputStream in = createObjectInputStream(file)) {
			obj = (T)in.readObject();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}
	
	public static <T> List<T> readFromFile(String path, List<T> list) {
		return readFromFile(new File(path), list);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> readFromFile(File   file, List<T> list) {
		try(ObjectInputStream in = createObjectInputStream(file)) {
			Object o = in.readObject();
			if(o instanceof Iterable)
				for(T t: (Iterable<T>)o)
					list.add(t);
			else
				for(T t: (T[])o)
					list.add(t);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static <T> void printToFile(String path, boolean append, T obj) {
		printToFile(new File(path), append, obj);
	}
	
	public static <T> void printToFile(File   file, boolean append, T obj) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			out.write(String.format(obj + "%n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void printToFile(String path, boolean append, T[] list) {
		printToFile(new File(path), append, list);
	}
	
	public static <T> void printToFile(File   file, boolean append, T[] list) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			for(T t: list)
				out.write(String.format(t + "%n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void printToFile(String path, boolean append, Iterable<T> list) {
		printToFile(new File(path), append, list);
	}
	
	public static <T> void printToFile(File   file, boolean append, Iterable<T> list) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			for(T t: list)
				out.write(String.format(t + "%n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <K, V> void printToFile(String path, boolean append, Map<K, V> map) {
		printToFile(new File(path), append, map);
	}
	
	public static <K, V> void printToFile(File   file, boolean append, Map<K, V> map) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			map.forEach((key, val) -> {
				try {
					out.write(String.format(key + "=" + val + "%n"));
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			});
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static String parseFromFile(String path) {
		return parseFromFile(new File(path));
	}
	
	public static String parseFromFile(File   file) {
		StringBuilder sb = new StringBuilder();
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) sb.append(in.readLine());
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return sb.toString();
	}
	
	public static List<String> parseFromFile(String path, List<String> list) {
		return parseFromFile(new File(path), list);
	}
	
	public static List<String> parseFromFile(File   file, List<String> list) {
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) list.add(in.readLine());
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return list;
	}
	
	public static Map<String, String> parseFromFile(String path, Map<String, String> map) {
		return parseFromFile(new File(path), map);
	}
	
	public static Map<String, String> parseFromFile(File   file, Map<String, String> map) {
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) {
				String 
					line = in.readLine();
				String[] 
					temp = line.split("\\=");
				String
					key = temp.length > 0 ? temp[0]: "",
					val = temp.length > 1 ? temp[1]: "";
				map.put(key, val);
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return map;
	}
	
	public static void clearFile(String path) {
		clearFile(new File(path));
	}
	
	public static void clearFile(File   file) {
		try(BufferedWriter out = createBufferedWriter(file, false)) {
			out.write("");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
