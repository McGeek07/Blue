package blue.util;

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
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public final class Files {
	
	private Files() {
		//hide constructor
	}
	
	public static File file(String path) {
		try {
			return new File(path);
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to convert path '" + path + "' to file.");
			e.printStackTrace();
		}
		return null;
	}
	
	public static File file(URL    path) {
		try {
			return new File(path.toURI());
		} catch(URISyntaxException use) {
			return new File(path.getPath());
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to convert path '" + path + "' to file.");
			e.printStackTrace();
		}
		return null;
	}
	
	public static File create(URL    path) {
		return create(file(path));
	}
	
	public static File create(String path) {
		return create(file(path));
	}
	
	public static File create(File file) {
		if(!file.exists())
			try {
				if(file.getParentFile() != null)
					file.getParentFile().mkdirs();
				file.createNewFile();
			} catch(Exception e) {
				Debug.warn(new Object() {/* trace */}, "Unable to create file \"" + file + "\"");
				e.printStackTrace();
			}
		return file;
	}
	
	public static File delete(URL    path) {
		return delete(file(path));
	}
	
	public static File delete(String path) {
		return delete(file(path));
	}
	
	public static File delete(File file) {
		if(file.exists()) {
			try {
				file.delete();
			} catch(Exception e) {
				Debug.warn(new Object() {/* trace */}, "Unable to delete file \"" + file + "\"");
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static ObjectOutputStream createObjectOutputStream(URL    path, boolean append) {
		return createObjectOutputStream(file(path), append);
	}
	
	public static ObjectOutputStream createObjectOutputStream(String path, boolean append) {
		return createObjectOutputStream(file(path), append);
	}
	
	public static ObjectOutputStream createObjectOutputStream(File   file, boolean append) {
		try {
			return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(create(file), append)));
		} catch (IOException ioe) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static ObjectInputStream createObjectInputStream(URL    path) {
		return createObjectInputStream(file(path));
	}
	
	public static ObjectInputStream createObjectInputStream(String path) {
		return createObjectInputStream(file(path));
	}
	
	public static ObjectInputStream createObjectInputStream(File   file) {
		try {
			return new ObjectInputStream(new BufferedInputStream(new FileInputStream(create(file))));
		} catch(IOException ioe) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static BufferedWriter createBufferedWriter(URL    path, boolean append) {
		return createBufferedWriter(file(path), append);
	}
	
	public static BufferedWriter createBufferedWriter(String path, boolean append) {
		return createBufferedWriter(file(path), append);
	}
	
	public static BufferedWriter createBufferedWriter(File   file, boolean append) {
		try {
			return new BufferedWriter(new FileWriter(create(file), append));
		} catch(IOException ioe) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static PrintWriter createPrintWriter(URL    path, boolean append) {
		return createPrintWriter(file(path), append);
	}
	
	public static PrintWriter createPrintWriter(String path, boolean append) {
		return createPrintWriter(file(path), append);
	}
	
	public static PrintWriter createPrintWriter(File   file, boolean append) {
		try {
			return new PrintWriter(new BufferedWriter(new FileWriter(create(file), append)));
		} catch(IOException ioe) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static BufferedReader createBufferedReader(URL    path) {
		return createBufferedReader(file(path));
	}
	
	public static BufferedReader createBufferedReader(String path) {
		return createBufferedReader(file(path));
	}
	
	public static BufferedReader createBufferedReader(File   file) {
		try {
			return new BufferedReader(new FileReader(create(file)));
		} catch(IOException ioe) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static <T> void write(URL    path, boolean append, T obj) {
		write(file(path), append, obj);
	}
	
	public static <T> void write(String path, boolean append, T obj) {
		write(file(path), append, obj);
	}
	
	public static <T> void write(File   file, boolean append, T obj) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(obj);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void write(URL    path, boolean append, T[] list) {
		write(file(path), append, list);
	}
	
	public static <T> void write(String path, boolean append, T[] list) {
		write(file(path), append, list);
	}
	
	public static <T> void write(File   file, boolean append, T[] list) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(list);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void write(URL    path, boolean append, Iterable<T> list) {
		write(file(path), append, list);
	}
	
	public static <T> void write(String path, boolean append, Iterable<T> list) {
		write(file(path), append, list);
	}
	
	public static <T> void write(File   file, boolean append, Iterable<T> list) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(list);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> T read(URL    path) {
		return read(file(path));
	}
	
	public static <T> T read(String path) {
		return read(file(path));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T read(File   file) {
		T obj = null;
		try(ObjectInputStream in = createObjectInputStream(file)) {
			obj = (T)in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static <T> List<T> read(URL    path, List<T> list) {
		return read(file(path), list);
	}
	
	public static <T> List<T> read(String path, List<T> list) {
		return read(file(path), list);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> read(File   file, List<T> list) {
		try(ObjectInputStream in = createObjectInputStream(file)) {
			Object o = in.readObject();
			if(o instanceof Iterable)
				for(T t: (Iterable<T>)o)
					list.add(t);
			else
				for(T t: (T[])o)
					list.add(t);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static <T> void print(URL    path, boolean append, T obj) {
		print(file(path), append, obj);
	}
	
	public static <T> void print(String path, boolean append, T obj) {
		print(file(path), append, obj);
	}
	
	public static <T> void print(File   file, boolean append, T obj) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			out.write(String.format(obj + "%n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void print(URL    path, boolean append, T[] list) {
		print(file(path), append, list);
	}
	
	public static <T> void print(String path, boolean append, T[] list) {
		print(file(path), append, list);
	}
	
	public static <T> void print(File   file, boolean append, T[] list) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			for(T t: list)
				out.write(String.format(t + "%n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void print(URL    path, boolean append, Iterable<T> list) {
		print(file(path), append, list);
	}
	
	public static <T> void print(String path, boolean append, Iterable<T> list) {
		print(file(path), append, list);
	}
	
	public static <T> void print(File   file, boolean append, Iterable<T> list) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			for(T t: list)
				out.write(String.format(t + "%n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <K, V> void print(URL    path, boolean append, Map<K, V> map) {
		print(file(path), append, map);
	}
	
	public static <K, V> void print(String path, boolean append, Map<K, V> map) {
		print(file(path), append, map);
	}
	
	public static <K, V> void print(File   file, boolean append, Map<K, V> map) {
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
	
	public static String parse(URL    path) {
		return parse(file(path));
	}
	
	public static String parse(String path) {
		return parse(file(path));
	}
	
	public static String parse(File   file) {
		StringBuilder sb = new StringBuilder();
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) sb.append(in.readLine());
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return sb.toString();
	}
	
	public static List<String> parse(URL    path, List<String> list) {
		return parse(file(path), list);
	}
	
	public static List<String> parse(String path, List<String> list) {
		return parse(file(path), list);
	}
	
	public static List<String> parse(File   file, List<String> list) {
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) list.add(in.readLine());
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return list;
	}
	
	public static Map<String, String> parse(URL    path, Map<String, String> map) {
		return parse(file(path), map);
	}
	
	public static Map<String, String> parse(String path, Map<String, String> map) {
		return parse(file(path), map);
	}
	
	public static Map<String, String> parse(File   file, Map<String, String> map) {
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
	
	public static void clear(URL    path) {
		clear(file(path));
	}
	
	public static void clear(String path) {
		clear(file(path));
	}
	
	public static void clear(File   file) {
		try(BufferedWriter out = createBufferedWriter(file, false)) {
			out.write("");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
