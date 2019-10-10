package blue.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import blue.core.Engine.Canvas;
import blue.core.Engine.Window;
import blue.core.Input.Action.Type;
import blue.core.Input.KeyAction;
import blue.core.render.RenderContext;
import blue.geom.Vector;
import blue.geom.Vector2f;
import blue.geom.Vector3f;
import blue.geom.Vector4f;
import blue.util.Config;
import blue.util.Executable;
import blue.util.Util;

public class Debug {
	protected static final Debug
		INSTANCE = new Debug();
	
	public static final String
		NEW_LINE = "\n";
	
	public static final int
		DEBUG = 0,
		ERROR = 1;
	
	public static final String		
		CONSOLE_FONT_NAME  = "console-font-name",
		CONSOLE_FONT_SIZE  = "console-font-size",
		CONSOLE_BACKGROUND = "console-background",
		CONSOLE_FOREGROUND = "console-foreground",
		CONSOLE_SIZE	   = "console-size",
		DEBUG_COLOR		   = "debug-color",
		ERROR_COLOR		   = "error-color",
		METRICS_FONT_NAME  = "metrics-font-name",
		METRICS_FONT_SIZE  = "metrics-font-size",
		METRICS_BACKGROUND = "metrics-background",
		METRICS_FOREGROUND = "metrics-foreground",
		METRICS_PADDING    = "metrics-padding";
	
	protected static final TreeMap<String, Command>
		COMMAND = new TreeMap<>();
	protected static final TreeMap<Integer, Logger>
		LOGGERS  = new TreeMap<>();	
	
	protected static int
		LEVEL;
	
	protected static Vector3f
		debug_color = new Vector3f(.5f, .5f,  1f),
		error_color = new Vector3f( 1f, .5f, .5f);
	protected static Color
		debug = Vector.toColor3f(debug_color),
		error = Vector.toColor3f(error_color);
	public static final Logger
		out = new Logger(DEBUG, "DEBUG", debug),
		err = new Logger(ERROR, "ERROR", error);
	
	protected static final Config
		CONFIG = new Config(	
				CONSOLE_FONT_NAME, Console.console_font_name,
				CONSOLE_FONT_SIZE, Console.console_font_size,
				CONSOLE_BACKGROUND, Console.console_background,
				CONSOLE_FOREGROUND, Console.console_foreground,
				CONSOLE_SIZE, Console.console_size,
				DEBUG_COLOR, debug_color,
				ERROR_COLOR, error_color,
				METRICS_FONT_NAME, Metrics.metrics_font_name,
				METRICS_FONT_SIZE, Metrics.metrics_font_size,
				METRICS_BACKGROUND, Metrics.metrics_background,
				METRICS_FOREGROUND, Metrics.metrics_foreground,
				METRICS_PADDING, Metrics.metrics_padding				
				);		
	
	private Debug() {
		Event.attach(KeyAction.class, (event) -> {	
			if(event.type == Type.UP_ACTION)
				switch(event.key) {
					case Input.KEY_F1: Engine.exit(); break;
					case Input.KEY_F2: Debug.toggleMetrics(); break;
					case Input.KEY_F3: Debug.toggleConsole(); break;
				}
		});
	}
	
	protected static void onInit() {
		debug_color = Vector3f.parseVector3f(CONFIG.get(DEBUG_COLOR, debug_color));
		error_color = Vector3f.parseVector3f(CONFIG.get(ERROR_COLOR, error_color));
		debug = Vector.toColor3f(debug_color);
		error = Vector.toColor3f(error_color);
		
		StyleConstants.setForeground(out.style, debug);
		StyleConstants.setForeground(err.style, error);
		
		Console.onInit();
		Metrics.onInit();
	}
	
	protected static void onExit() {
		Console.onExit();
		Metrics.onExit();
	}
	
	public static Config getConfig() {
		return CONFIG;
	}
	
	public static void toggleConsole() {
		if(Console.show_console)
			hideConsole();
		else
			showConsole();
	}
	
	public static void showConsole() {
		Console.show_console = true;
		Console.onShow();
	}
	
	public static void hideConsole() {
		Console.show_console = false;
		Console.onHide();
	}
	
	public static void toggleMetrics() {
		if(Metrics.show_metrics)
			hideMetrics();
		else
			showMetrics();
	}
	
	public static void showMetrics() {
		Metrics.show_metrics = true;
	}
	
	public static void hideMetrics() {
		Metrics.show_metrics = false;
	}
	
	public static void evaluate(String expr) {
		expr = expr.trim().toUpperCase();
		
		LinkedList<Command>
			com_stack = new LinkedList<>();
		LinkedList<String>
			arg_stack = new LinkedList<>();
		String 
			token = "";
		int
			literal = 0,
			nesting = 0;
		boolean
			valid = false;
		
		for(int i = 0; i < expr.length(); i ++) {
			char c = expr.charAt(i);
			if(c == '"') {
				literal ^= 1;
			} else {
				if(literal > 0) {
					token += c;				
				} else if (c == ' ') {
					//do nothing
				} else if (c == '(') {
					if(!token.isEmpty()) {
						Command com = COMMAND.get(token);
						if(com != null) {
							com_stack.push( com);
							arg_stack.push(null);
							nesting ++;
							token = "";
						} else {
							bad_alias(token);
							return;
						}							
					} else {
						bad_token(c, i);
						return;
					}
				} else if (c == ',') {	
					if(nesting > 0) {
						arg_stack.push(token);
						token = "";
					} else {
						bad_token(c, i);
						return;
					}
				} else if (c == ')') {
					if(nesting > 0) {						
						int 
							arg_count = 0;
						for(String arg: arg_stack)
							if(arg != null)
								arg_count ++;
							else
								break;
						
						if(!token.isEmpty() || arg_count > 0) {
							arg_stack.push(token);
							arg_count ++;
						}
						
						String[]
							arg_array = new String[arg_count];
						for(int j = 0; j < arg_array.length; j ++)
							arg_array[j] = arg_stack.poll();
						arg_stack.poll();
						
						
						Command 
							com = com_stack.poll();
						arg_array = com.execute(arg_array);
						for(int j = 0; j < arg_array.length; j ++)
							arg_stack.push(arg_array[j]);	
						
						nesting --;
						token = "";
						valid = true;
					} else {
						bad_token(c, i);
						return;
					}
				} else
					token += c;
			}
		}

		if(literal > 0) {
			missing_token('"');
			return;
		}
		if(nesting > 0) {
			missing_token(')');
			return;
		}
		if(!valid) {
			bad_expression(expr);
			return;
		}
		
		if(arg_stack.size() > 0) {			
			int 
				arg_count = 0;
			for(String arg: arg_stack)
				if(arg != null)
					arg_count ++;
				else
					break;
			
			String[]
				arg_array = new String[arg_count];
			for(int j = 0; j < arg_array.length; j ++)
				arg_array[j] = arg_stack.poll();
			arg_stack.poll();
			
			out.log(Arrays.toString(arg_array));
		}
	}
	
	private static void bad_expression(String expr) {
		err.log("Bad Expression '" + expr + "'");
	}
	
	private static void bad_alias(String alias) {
		err.log("Bad Alias '" + alias + "'");
	}
	
	private static void bad_token(char c, int i) {
		err.log("Bad Token '" + c + "' at index [" + i + "]");
	}
	
	private static void missing_token(char c) {
		err.log("Missing Token '" + c + "'");
	}
	
	public static class Command implements Executable<String, String> {
		protected String
			name,
			desc;
		protected Executable<String, String>[]
			exes;
		
		@SafeVarargs
		public Command(String name, String desc, Executable<String, String>... exes) {
			if(name != null) {
				if(COMMAND.get(name = name.trim().toUpperCase()) != null)
					throw new IllegalArgumentException("Duplicate Func '" + name + "'");
				this.name = name;
				this.desc = desc;
				this.exes = exes;
				COMMAND.put(this.name, this);
			} else
				this.exes = exes;
		}

		@Override
		public String[] execute(String[] args) {
			for(int i = 0; i < exes.length; i ++)
				args = exes[i].execute(args);
			return args;
		}
	}
	
	public static class Logger {
		protected int
			level;
		protected String
			title;
		protected Style
			style;
		
		public Logger(int level, String title) {
			if(LOGGERS.get(level) != null)
				throw new IllegalArgumentException();
			this.level = level;
			this.title = title;
			this.style = Console.output.getStyledDocument().addStyle(null, null);
			LOGGERS.put(this.level, this);
		}
		
		public Logger(int level, String title, Color color) {
			this(level, title);
			StyleConstants.setForeground(style, color);
		}
		
		public void log(Object msg) {
			if(level >= Debug.LEVEL) {
				String[] tmp = ("" + msg).split(NEW_LINE);
				for(int i = 0; i < tmp.length; i ++) {
					tmp[i] = tmp[i].trim() + NEW_LINE;
					try {
						Console.output.getDocument().insertString(
								Console.output.getDocument().getLength(),
								"[" + title + "] " + tmp[i],
								style
								);
					} catch (BadLocationException ble) {
						ble.printStackTrace();
					}
				}
			}
		}
	}
	
	protected static class Console {
		protected static String
			console_font_name = "Monospaced";
		protected static int
			console_font_size = 16;
		protected static Vector3f
			console_background = new Vector3f(1f, 1f, 1f),
			console_foreground = new Vector3f(0f, 0f, 0f);
		protected static Vector2f
			console_size = new Vector2f(640, 480);
		
		protected static Font
			font;
		protected static Color
			background,
			foreground;
		
		protected static JFrame
			window;
		protected static JTextPane
			output = new JTextPane();
		protected static JScrollPane
			scroll = new JScrollPane(output);
		protected static JTextField
			input = new JTextField();
		
		protected static StyledDocument
			document = output.getStyledDocument();
		
		protected static boolean
			show_console;
		
		protected static void onInit() {
			Console.console_font_name = CONFIG.get   (CONSOLE_FONT_NAME, Console.console_font_name);
			Console.console_font_size = CONFIG.getInt(CONSOLE_FONT_SIZE, Console.console_font_size);
			Console.console_background = Vector3f.parseVector3f(CONFIG.get(CONSOLE_BACKGROUND, Console.console_background));
			Console.console_foreground = Vector3f.parseVector3f(CONFIG.get(CONSOLE_FOREGROUND, Console.console_foreground));
			Console.console_size       = Vector2f.parseVector2f(CONFIG.get(CONSOLE_SIZE      , Console.console_size      ));
			
			font = new Font(
					console_font_name,
					Font.PLAIN,
					console_font_size
					);			
			background = Vector.toColor3f(console_background);
			foreground = Vector.toColor3f(console_foreground);
			
			if(window != null)
				window.dispose();
			
			window = new JFrame();
			
			window.setLayout(new BorderLayout());
			window.add(scroll, BorderLayout.CENTER);
			window.add(input , BorderLayout.SOUTH );
			
			output.setFont(font);
			output.setBackground(background);
			output.setForeground(foreground);
			output.setCaretColor(foreground);
			output.setEditable(false);
			
			input.setFont(font);
			input.setBackground(background);
			input.setForeground(foreground);
			input.setCaretColor(foreground);
			
			window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			window.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					Debug.hideConsole();
				}
			});
			
			window.setSize(
					(int)console_size.x(),
					(int)console_size.y()
					);
			input.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					//do nothing
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					//do nothing
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					//do nothing
				}				
			});
			input.addActionListener((ae) -> {
				String x = input.getText();
				input.setText("");
				Debug.evaluate(x);
			});
		}
		
		protected static void onExit() {
			if(window != null)
				window.dispose();
		}
		
		protected static void onShow() {
			if(window != null) {
				window.setLocationRelativeTo(Window.window);
				window.setVisible(true);
				input.requestFocus();
			}
		}
		
		protected static void onHide() {
			if(window != null) {
				window.dispose();
			}
		}
	}
	
	protected static class Metrics {		
		protected static String
			metrics_font_name = "Monospaced";
		protected static int
			metrics_font_size = 16;	
		protected static Vector4f
			metrics_background = new Vector4f(0f, 0f, 0f, .8f),
			metrics_foreground = new Vector4f(1f, 1f, 1f, .8f),
			metrics_padding    = new Vector4f(2f, 2f, 2f,  2f);
		
		protected static boolean
			show_metrics;
		protected static Font
			font;
		protected static Color
			background,
			foreground;
		
		private Metrics() {
			//do nothing
		}
		
		protected static void onInit() {
			Metrics.metrics_font_name = CONFIG.get   (METRICS_FONT_NAME, Metrics.metrics_font_name);
			Metrics.metrics_font_size = CONFIG.getInt(METRICS_FONT_SIZE, Metrics.metrics_font_size);
			
			Metrics.metrics_background = Vector4f.parseVector4f(CONFIG.get(METRICS_BACKGROUND, Metrics.metrics_background));
			Metrics.metrics_foreground = Vector4f.parseVector4f(CONFIG.get(METRICS_FOREGROUND, Metrics.metrics_foreground));
			Metrics.metrics_padding    = Vector4f.parseVector4f(CONFIG.get(METRICS_PADDING   , Metrics.metrics_padding   ));
			
			font = new java.awt.Font(
					metrics_font_name,
					Font.PLAIN,
					metrics_font_size
					);
			background = Vector.toColor4f(metrics_background);
			foreground = Vector.toColor4f(metrics_foreground);
		}
		
		protected static void onExit() {
			//do nothing
		}
		
		protected static void onRender(RenderContext context) {
			if(show_metrics) {
				int
					padding_t = (int)metrics_padding.x(),
					padding_r = (int)metrics_padding.y(),
					padding_b = (int)metrics_padding.z(),
					padding_l = (int)metrics_padding.w();
				
				context.g2D.setFont(font);
				FontMetrics fm = context.g2D.getFontMetrics();				
				
				String[] debug_info = {
						"Debug",
						"[F1] Exit Engine",
						"[F2] Toggle Metrics",
						"[F3] Toggle Console",
						"",
						"Engine",
						"Render: " + Engine.render_hz_metric + " hz @ " + String.format("%2.3f", Engine.render_dt_metric) + " ms",
						"Update: " + Engine.update_hz_metric + " hz @ " + String.format("%2.3f", Engine.update_dt_metric) + " ms",
						"Canvas: " + Canvas.foreground_w + " x " + Canvas.foreground_h
				};		
		
				int
					debug_info_w = 0,
					debug_info_h = fm.getHeight() * debug_info.length;
				
				for(int i = 0; i < debug_info.length; i ++)
					debug_info_w = Math.max(debug_info_w, fm.stringWidth(debug_info[i]));
				
				debug_info_w += padding_l + padding_r;
				debug_info_h += padding_t + padding_b;
				
				context.g2D.setColor(background);			
				context.g2D.fillRect(
							0,
							0,
							debug_info_w,
							debug_info_h
						);		
				context.g2D.setColor(foreground);
				for(int i = 0; i < debug_info.length; i ++)
					context.g2D.drawString(debug_info[i], padding_l, padding_t + fm.getAscent() + fm.getHeight() * i);
			}
		}
	}
	
	public static final Command
		HELP = new Command("HELP", "Print a list of available commands", (args) -> {
			if(args.length > 0) {
				for(String arg: args) {
					Command command = COMMAND.get(arg);
					if(command != null)
						out.log(command.name + ": " + command.desc);
				}
					
			} else {
				COMMAND.forEach((name, command) -> {
					out.log(command.name + ": " + command.desc);
				});
			}			
			return new String[0];
		}),
		ADD = new Command("ADD", "", (args) -> {
			float sum = 0;
			for(int i = 0; i < args.length; i ++)
				sum += Util.stringToFloat(args[i]);
			return new String[] { "" + sum };
		}),
		SAY = new Command("SAY", "", (args) -> {
			for(String arg: args)
				out.log(arg);
			return new String[0];
		});
}
