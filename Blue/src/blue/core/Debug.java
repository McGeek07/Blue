package blue.core;

import java.awt.Color;
import java.awt.Font;

import blue.core.render.RenderContext;
import blue.core.render.Renderable;
import blue.core.update.UpdateContext;
import blue.core.update.Updateable;
import blue.geom.Vector4f;
import blue.util.Config;

public class Debug implements Renderable, Updateable {
	protected static final Debug
		INSTANCE = new Debug();
	
	protected boolean
		debug;
	protected String
		debug_font_name;
	protected int
		debug_font_size;	
	protected Vector4f
		debug_background = new Vector4f(0f, 0f, 0f, .8f),
		debug_foreground = new Vector4f(1f, 1f, 1f, .8f);
	
	protected final Config
		config = new Config();
	
	protected Font
		font;
	protected Color
		background,
		foreground;
	protected boolean
		show_metrics,
		show_console;
	
	private Debug() {
		
	}
	
	
	
	public static void init() {
		
	}
	
	public static void exit() {
		
	}
	
	public static void showMetrics() {
		INSTANCE.show_metrics = true;
	}
	
	public static void hideMetrics() {
		INSTANCE.show_metrics = false;
	}	
	
	public static void toggleMetrics() {
		if(INSTANCE.show_metrics)
			Debug.hideMetrics();
		else
			Debug.showMetrics();
	}
	
	public static void showConsole() {
		INSTANCE.show_console = true;
	}
	
	public static void hideConsole() {
		INSTANCE.show_console = false;
	}	
	
	public static void toggleConsole() {
		if(INSTANCE.show_console)
			Debug.hideConsole();
		else
			Debug.showConsole();
	}
	
	public void onInit() {
		
	}
	
	public void onExit() {
		
	}

	@Override
	public void render(RenderContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(UpdateContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public static class Console {
		protected java.awt.Window
			window;
	}	
	
	public static class Command {
		
	}
	
	public static final String
		DEBUG_FONT_NAME = "debug-font-name";
}
