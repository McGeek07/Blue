package blue.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import blue.core.Input.Action.Type;
import blue.core.Engine.Canvas;
import blue.core.Input.KeyAction;
import blue.core.render.RenderContext;
import blue.geom.Vector;
import blue.geom.Vector4f;
import blue.util.Config;

public class Debug {
	protected static final Debug
		INSTANCE = new Debug();
	
	public static final String		
		CONSOLE_FONT_NAME  = "console-font-name",
		CONSOLE_FONT_SIZE  = "console-font-size",
		CONSOLE_BACKGROUND = "console-background",
		CONSOLE_FOREGROUND = "console-foreground",
		CONSOLE_SIZE	   = "console-size",
		METRICS_FONT_NAME  = "metrics-font-name",
		METRICS_FONT_SIZE  = "metrics-font-size",
		METRICS_BACKGROUND = "metrics-background",
		METRICS_FOREGROUND = "metrics-foreground",
		METRICS_PADDING    = "metrics-padding";
	
	protected static final Config
		CONFIG = new Config(				
				METRICS_FONT_NAME, Metrics.metrics_font_name,
				METRICS_FONT_SIZE, Metrics.metrics_font_size,
				METRICS_BACKGROUND, Metrics.metrics_background,
				METRICS_FOREGROUND, Metrics.metrics_foreground,
				METRICS_PADDING, Metrics.metrics_padding				
				);
	
	protected boolean
		show_metrics,
		show_console;
	
	private Debug() {
		Event.attach(KeyAction.class, (event) -> {	
			if(event.type == Type.UP_ACTION)
				switch(event.key) {
					case Input.KEY_F1: Engine.exit(); break;
					case Input.KEY_F2: Debug.toggleMetrics(); break;
					//case Input.KEY_F3: Debug.toggleConsole(); break;
				}
		});
	}
	
	protected static void onInit() {
		Console.onInit();
		Metrics.onInit();
	}
	
	protected static void onExit() {
		Console.onExit();
		Metrics.onExit();
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
	
	protected static class Console {
		protected static void onInit() {
			
		}
		
		protected static void onExit() {
			
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
	
	public static class Command {
		
	}
}
