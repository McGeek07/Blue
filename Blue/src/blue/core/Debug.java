package blue.core;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Debug {
	public static final int
		INFO  = 2,
		WARN  = 1,
		ERROR = 0;
	
	protected static final Map<Integer, Logger>
		log = new HashMap<>();
	protected static int
		lvl = INFO;
	
	public static final Logger
		info  = get(INFO).id("INFO").attach(System.out),
		warn  = get(WARN).id("WARNING").attach(System.err),
		error = get(ERROR).id("ERROR").attach(System.err);
	
	public static final Logger get(int lvl) {
		Logger logger = log.get(lvl);
		if(logger == null)
			log.put(lvl, logger = new Logger(lvl));
		return logger;
	}
	
	public static final void   lvl(int lvl) {
		Debug.lvl = lvl;
	}
	
	public static final <T> void log(int lvl, Class<T> obj, Object msg) {
		get(lvl).log(obj, msg);
	}
	
	public static class Logger {
		public final Set<PrintStream>
			streams = new HashSet<>();
		public final Set<PrintWriter>
			writers = new HashSet<>();
		public final int
			lvl;
		
		public String
			id = "";
		
		private Logger(int lvl) { 
			this.lvl = lvl;
		}
		
		public <T> void log(Class<T> obj, Object msg) {
			if(lvl <= Debug.lvl && msg != null) {
				String out = id + " " + (obj != null ? "[" + obj.getName() + "]: " + msg.toString() : msg.toString());
				for(PrintStream stream: streams)
					stream.println(out);
				for(PrintWriter writer: writers)
					writer.println(out);
			}
		}
		
		public Logger id(String id) {
			this.id = id;
			return  this;
		}
		
		public Logger attach(PrintStream stream) {
			streams.add   (stream);
			return this;
		}
		
		public Logger detach(PrintStream stream) {
			streams.remove(stream);
			return this;
		}
		
		public Logger attach(PrintWriter writer) {
			writers.add   (writer);
			return this;
		}
		
		public Logger detach(PrintWriter writer) {
			writers.remove(writer);
			return this;
		}
	}
}
