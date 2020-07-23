package blue.util;

public interface Log {
	public static final Log
		info = Log.create(System.out),
		warn = Log.create(System.err);
	
	public void log(Object trace, Object event);
	
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
	
	public static void log(java.io.PrintStream out, Object trace, Object event) {
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
	
	public static void log(java.io.PrintWriter out, Object trace, Object event) {
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
	
	public static Log create(java.io.PrintStream out) {
		return (trace, event) -> { log(out, trace, event); };
	}
	
	public static Log create(java.io.PrintWriter out) {
		return (trace, event) -> { log(out, trace, event); };
	}
}
