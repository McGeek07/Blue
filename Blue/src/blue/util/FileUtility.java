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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public final class FileUtility {
	
	private FileUtility() {
		//hide constructor
	}
	
	public static File getFile(URL    path) {
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
	
	public static File getFile(String path) {
		try {
			return new File(path);
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to convert path '" + path + "' to file.");
			e.printStackTrace();
		}
		return null;
	}
	
	public static File createFile(URL    path) {
		return createFile(getFile(path));
	}
	
	public static File createFile(String path) {
		return createFile(getFile(path));
	}
	
	public static File createFile(File file) {
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
	
	public static File deleteFile(URL    path) {
		return deleteFile(getFile(path));
	}
	
	public static File deleteFile(String path) {
		return deleteFile(getFile(path));
	}
	
	public static File deleteFile(File file) {
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
		return createObjectOutputStream(getFile(path), append);
	}
	
	public static ObjectOutputStream createObjectOutputStream(String path, boolean append) {
		return createObjectOutputStream(getFile(path), append);
	}
	
	public static ObjectOutputStream createObjectOutputStream(File   file, boolean append) {
		try {
			return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(createFile(file), append)));
		} catch (Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static ObjectInputStream createObjectInputStream(URL    path) {
		return createObjectInputStream(getFile(path));
	}
	
	public static ObjectInputStream createObjectInputStream(String path) {
		return createObjectInputStream(getFile(path));
	}
	
	public static ObjectInputStream createObjectInputStream(File   file) {
		try {
			return new ObjectInputStream(new BufferedInputStream(new FileInputStream(createFile(file))));
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static BufferedWriter createBufferedWriter(URL    path, boolean append) {
		return createBufferedWriter(getFile(path), append);
	}
	
	public static BufferedWriter createBufferedWriter(String path, boolean append) {
		return createBufferedWriter(getFile(path), append);
	}
	
	public static BufferedWriter createBufferedWriter(File   file, boolean append) {
		try {
			return new BufferedWriter(new FileWriter(createFile(file), append));
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static PrintWriter createPrintWriter(URL    path, boolean append) {
		return createPrintWriter(getFile(path), append);
	}
	
	public static PrintWriter createPrintWriter(String path, boolean append) {
		return createPrintWriter(getFile(path), append);
	}
	
	public static PrintWriter createPrintWriter(File   file, boolean append) {
		try {
			return new PrintWriter(new BufferedWriter(new FileWriter(createFile(file), append)));
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static BufferedReader createBufferedReader(URL    path) {
		return createBufferedReader(getFile(path));
	}
	
	public static BufferedReader createBufferedReader(String path) {
		return createBufferedReader(getFile(path));
	}
	
	public static BufferedReader createBufferedReader(File   file) {
		try {
			return new BufferedReader(new FileReader(createFile(file)));
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static <T> void writeToFile(URL    path, boolean append, T obj) {
		writeToFile(getFile(path), append, obj);
	}
	
	public static <T> void writeToFile(String path, boolean append, T obj) {
		writeToFile(getFile(path), append, obj);
	}
	
	public static <T> void writeToFile(File   file, boolean append, T obj) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(obj);
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to write to file \"" + file + "\"");
			e.printStackTrace();
		}
	}
	
	public static <T> void writeToFile(URL    path, boolean append, T[] list) {
		writeToFile(getFile(path), append, list);
	}
	
	public static <T> void writeToFile(String path, boolean append, T[] list) {
		writeToFile(getFile(path), append, list);
	}
	
	public static <T> void writeToFile(File   file, boolean append, T[] list) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(list);
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to write to file \"" + file + "\"");
			e.printStackTrace();
		}
	}
	
	public static <T> void writeToFile(URL    path, boolean append, Iterable<T> list) {
		writeToFile(getFile(path), append, list);
	}
	
	public static <T> void writeToFile(String path, boolean append, Iterable<T> list) {
		writeToFile(getFile(path), append, list);
	}
	
	public static <T> void writeToFile(File   file, boolean append, Iterable<T> list) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(list);
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to write to file \"" + file + "\"");
			e.printStackTrace();
		}
	}
	
	public static <T> T readFromFile(URL    path) {
		return readFromFile(getFile(path));
	}
	
	public static <T> T readFromFile(String path) {
		return readFromFile(getFile(path));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readFromFile(File   file) {
		T obj = null;
		try(ObjectInputStream in = createObjectInputStream(file)) {
			obj = (T)in.readObject();
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to read from file \"" + file + "\"");
			e.printStackTrace();
		}
		return obj;
	}
	
	public static <T> List<T> readFromFile(URL    path, List<T> list) {
		return readFromFile(getFile(path), list);
	}
	
	public static <T> List<T> readFromFile(String path, List<T> list) {
		return readFromFile(getFile(path), list);
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
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to read from file \"" + file + "\"");
			e.printStackTrace();
		}
		return list;
	}
	
	public static <T> void printToFile(URL    path, boolean append, T obj) {
		printToFile(getFile(path), append, obj);
	}
	
	public static <T> void printToFile(String path, boolean append, T obj) {
		printToFile(getFile(path), append, obj);
	}
	
	public static <T> void printToFile(File   file, boolean append, T obj) {
		try(PrintWriter out = createPrintWriter(file, append)) {
			out.println(obj);
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to print to file \"" + file + "\"");
			e.printStackTrace();
		}
	}
	
	public static <T> void printToFile(URL    path, boolean append, T[] list) {
		printToFile(getFile(path), append, list);
	}
	
	public static <T> void printToFile(String path, boolean append, T[] list) {
		printToFile(getFile(path), append, list);
	}
	
	public static <T> void printToFile(File   file, boolean append, T[] list) {
		try(PrintWriter out = createPrintWriter(file, append)) {
			for(T t: list) out.println(t);
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to print to file \"" + file + "\"");
			e.printStackTrace();
		}
	}
	
	public static <T> void printToFile(URL    path, boolean append, Iterable<T> list) {
		printToFile(getFile(path), append, list);
	}
	
	public static <T> void printToFile(String path, boolean append, Iterable<T> list) {
		printToFile(getFile(path), append, list);
	}
	
	public static <T> void printToFile(File   file, boolean append, Iterable<T> list) {
		try(PrintWriter out = createPrintWriter(file, append)) {
			for(T t: list) out.println(t);
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to print to file \"" + file + "\"");
			e.printStackTrace();
		}
	}
	
	public static <K, V> void printToFile(URL    path, boolean append, Map<K, V> map) {
		printToFile(getFile(path), append, map);
	}
	
	public static <K, V> void printToFile(String path, boolean append, Map<K, V> map) {
		printToFile(getFile(path), append, map);
	}
	
	public static <K, V> void printToFile(File   file, boolean append, Map<K, V> map) {
		try(PrintWriter out = createPrintWriter(file, append)) {
			map.forEach((key, val) -> {
				out.println(key + ":" + val);
			});
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to print to file \"" + file + "\"");
			e.printStackTrace();
		}
	}
	
	public static String parseFromFile(URL    path) {
		return parseFromFile(getFile(path));
	}
	
	public static String parseFromFile(String path) {
		return parseFromFile(getFile(path));
	}
	
	public static String parseFromFile(File   file) {
		StringBuilder sb = new StringBuilder();
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) sb.append(in.readLine());
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to parse from file \"" + file + "\"");
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static List<String> parseFromFile(URL    path, List<String> list) {
		return parseFromFile(getFile(path), list);
	}
	
	public static List<String> parseFromFile(String path, List<String> list) {
		return parseFromFile(getFile(path), list);
	}
	
	public static List<String> parseFromFile(File   file, List<String> list) {
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) list.add(in.readLine());
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to parse from file \"" + file + "\"");
			e.printStackTrace();
		}
		return list;
	}
	
	public static Map<String, String> parseFromFile(URL    path, Map<String, String> map) {
		return parseFromFile(getFile(path), map);
	}
	
	public static Map<String, String> parseFromFile(String path, Map<String, String> map) {
		return parseFromFile(getFile(path), map);
	}
	
	public static Map<String, String> parseFromFile(File   file, Map<String, String> map) {
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) {
				String 
					line = in.readLine();
				int
					i = line.indexOf(':');		
				String
					key = i >= 0 ? line.substring(0, i).trim() : line.trim(),
					val = i >= 0 ? line.substring(++ i).trim() :          "";
				map.put(key, val);
			}
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to parse from file \"" + file + "\"");
			e.printStackTrace();
		}
		return map;
	}
	
	public static void clearFile(URL    path) {
		clearFile(getFile(path));
	}
	
	public static void clearFile(String path) {
		clearFile(getFile(path));
	}
	
	public static void clearFile(File   file) {
		try(BufferedWriter out = createBufferedWriter(file, false)) {
			out.write("");
		} catch(Exception e) {
			Debug.warn(new Object() {/* trace */}, "Unable to clear file \"" + file + "\"");
			e.printStackTrace();
		}
	}
}
