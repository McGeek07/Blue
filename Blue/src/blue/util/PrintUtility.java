package blue.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;

public class PrintUtility {
	
	private PrintUtility() {
		//hide constructor
	}
	
	public static <T> void print(T[] list) {
		print(System.out, list);
	}
	
	public static <T> void print(Iterable<T> list) {
		print(System.out, list);
	}
	
	public static <K, V> void print(Map<K, V> map) {
		print(System.out, map);
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
}
