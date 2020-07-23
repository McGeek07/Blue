package blue.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;

public class Debug {
	public static final Logger
		info = Logger.create(System.out),
		warn = Logger.create(System.err);
	
	private Debug() {
		//hide constructor
	}
	
	public static void info(Object event) {
		info.log("INFO", event);
	}
	
	public static void warn(Object event) {
		warn.log("WARN", event);
	}
	
	public static void info(Object trace, Object event) {
		info.log(trace, event);
	}
	
	public static void warn(Object trace, Object event) {
		warn.log(trace, event);
	}	
	
	public static void log(PrintStream out, Object trace, Object event) {
		trace = trace != null ? trace : new Object() { };
		if(trace instanceof String) {
			out.printf("[%1$s] %2$s%n", trace, event);
		} else {
			String
				_class = trace.getClass().getEnclosingMethod().getDeclaringClass().getName(),
				_trace = trace.getClass().getEnclosingMethod()                    .getName();
			out.printf("[%1$s.%2$s] %3$s%n", _class, _trace, event);
		}
	}
	
	public static void log(PrintWriter out, Object trace, Object event) {
		trace = trace != null ? trace : new Object() { };
		if(trace instanceof String) {
			out.printf("[%1$s] %2$s%n", trace, event);
		} else {
			String
				_class = trace.getClass().getEnclosingMethod().getDeclaringClass().getName(),
				_trace = trace.getClass().getEnclosingMethod()                    .getName();
			out.printf("[%1$s.%2$s] %3$s%n", _class, _trace, event);
		}
	}
	
	public static <T> void log(PrintStream out, T[] list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void log(PrintWriter out, T[] list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void log(PrintStream out, Iterable<T> list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void log(PrintWriter out, Iterable<T> list) {
		for(T t: list) out.println(t);
	}
	
	public static <K, V> void log(PrintStream out, Map<K, V> map) {
		map.forEach((key, val) -> {
			out.println(key + "=" + val);
		});
	}
	
	public static <K, V> void log(PrintWriter out, Map<K, V> map) {
		map.forEach((key, val) -> {
			out.println(key + "=" + val);
		});
	}
	
	public static interface Logger {
		
		public void log(Object trace, Object event);
		
		public static Logger create(PrintStream out) {
			return (trace, event) -> { Debug.log(out, trace, event); };
		}
		
		public static Logger create(PrintWriter out) {
			return (trace, event) -> { Debug.log(out, trace, event); };
		}
	}		
}
