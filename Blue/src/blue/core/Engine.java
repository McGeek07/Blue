package blue.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import blue.Blue;
import blue.core.Renderable.RenderContext;
import blue.core.Updateable.UpdateContext;
import blue.geom.Layout;
import blue.geom.Region2;
import blue.geom.Vector;
import blue.geom.Vector2;
import blue.geom.Vector4;
import blue.util.Util;
import blue.util.Util.ObjectToString;
import blue.util.Util.StringToObject;

public class Engine implements Runnable {
	protected static final Engine
		INSTANCE = new Engine();
	
	protected Vector4
		canvas_background = new Vector4(  0,   0,   0, 255),
		canvas_foreground = new Vector4(255, 255, 255, 255),
		debug_background  = new Vector4(  0,   0,   0, 127),
		debug_foreground  = new Vector4(255, 255, 255, 127),
		window_background = new Vector4(  0,   0,   0, 255);
	protected Layout
		canvas_layout = Layout.DEFAULT;
	protected boolean
		debug = false;
	protected String
		debug_font_name = "Monospaced";
	protected int
		debug_font_size = 14;
	
	protected float
		engine_fps = 60,
		engine_tps = 60;
	protected boolean
		window_border = true;
	protected int
		window_device = 0;
	protected Layout
		window_layout = Layout.DEFAULT;
	protected String
		window_title = Blue.VERSION.toString();
	
	protected final Map<String, String>
		cfg = Util.configure( 
				new TreeMap<String, String>(),
				CANVAS_BACKGROUND, canvas_background,
				CANVAS_FOREGROUND, canvas_foreground,
				CANVAS_LAYOUT, canvas_layout,
				WINDOW_BORDER, window_border,
				WINDOW_DEVICE, window_device,
				WINDOW_LAYOUT, window_layout
				);
	
	protected Color
		canvas_background_color,
		canvas_foreground_color,
		debug_background_color,
		debug_foreground_color,
		window_background_color;
	protected Font
		debug_font;
	
	protected int
		window_w,
		window_h,
		canvas_w,
		canvas_h;
	protected float
		canvas_scale;
	
	protected int
		render_hz,
		update_hz;
	protected float
		render_dt,
		update_dt;
	
	protected Thread
		thread;
	protected boolean
		running;	
	
	protected Scene
		scene;
	
	protected java.awt.Frame
		window;
	protected java.awt.Canvas
		canvas;
	
	protected final RenderContext
		render_context = new RenderContext();
	protected final UpdateContext
		update_context = new UpdateContext();
	
	private Engine() {		
		Event.attach(SceneEvent.class, (event) -> {
			INSTANCE.onSetScene(event);
		});
		Event.attach(WindowEvent.class, (event) -> {
			INSTANCE.onResize(event);
		});
		Event.attach(FocusEvent.class, (event) -> {
			switch(event) {
				case GAIN_FOCUS: INSTANCE.onGainFocus(event);
				case LOSE_FOCUS: INSTANCE.onLoseFocus(event);
			}
		});
	}
	
	public static synchronized void loop() {
		if(!INSTANCE.running) {
			INSTANCE.thread = new Thread(INSTANCE, Blue.VERSION.toString());
			INSTANCE.running = true;
			INSTANCE.thread.start();
		}
	}
	
	public static synchronized void stop() {
		if( INSTANCE.running) {
			INSTANCE.running = false;
		}
	}
	
	public static synchronized void init() {
		if( INSTANCE.running)
			INSTANCE.onInit();
		else
			Engine.loop();
	}
	
	public static synchronized void exit() {
		if(!INSTANCE.running)
			INSTANCE.onExit();
		else
			Engine.stop();
	}
	
	public static void configure(Object... args) {
		Util.configure(INSTANCE.cfg, args);
	}
	
	public static void setProperty(Object key, Object val) {
		Util.setEntry(INSTANCE.cfg, key, val);
	}
	
	public static <T> void setProperty(ObjectToString<T> o2s, Object key, T val) {
		Util.setEntry(INSTANCE.cfg, o2s, key, val);
	}
	
	public static String getProperty(Object key) {
		return Util.getEntry(INSTANCE.cfg, key);
	}
	
	public static String getProperty(Object key, Object alt) {
		return Util.getEntry(INSTANCE.cfg, key, alt);
	}
	
	public static <T> T getProperty(StringToObject<T> s2o, Object key) {
		return Util.getEntry(INSTANCE.cfg, s2o, key);
	}
	
	public static <T> T getProperty(StringToObject<T> s2o, Object key, T alt) {
		return Util.getEntry(INSTANCE.cfg, s2o, key, alt);
	}
	
	public static int getPropertyAsInt(Object key) {
		return Util.getEntryAsInt(INSTANCE.cfg, key);
	}
	
	public static int getPropertyAsInt(Object key, int alt) {
		return Util.getEntryAsInt(INSTANCE.cfg, key, alt);
	}
	
	public static long getPropertyAsLong(Object key) {
		return Util.getEntryAsLong(INSTANCE.cfg, key);
	}
	
	public static long getPropertyAsLong(Object key, long alt) {
		return Util.getEntryAsLong(INSTANCE.cfg, key, alt);
	}
	
	public static float getPropertyAsFloat(Object key) {
		return Util.getEntryAsFloat(INSTANCE.cfg, key);
	}
	
	public static float getPropertyAsFloat(Object key, float alt) {
		return Util.getEntryAsFloat(INSTANCE.cfg, key, alt);
	}
	
	public static double getPropertyAsDouble(Object key) {
		return Util.getEntryAsDouble(INSTANCE.cfg, key);
	}
	
	public static double getPropertyAsDouble(Object key, double alt) {
		return Util.getEntryAsDouble(INSTANCE.cfg, key, alt);
	}
	
	public static boolean getPropertyAsBoolean(Object key) {
		return Util.getEntryAsBoolean(INSTANCE.cfg, key);
	}
	
	public static boolean getPropertyAsBoolean(Object key, boolean alt) {
		return Util.getEntryAsBoolean(INSTANCE.cfg, key, alt);
	}
	
	public static void loadConfiguration(String path) {
		loadConfiguration(new File(path));
	}
	
	public static void loadConfiguration(File   file) {
		Util.parseFromFile(file, INSTANCE.cfg);
	}
	
	public static void saveConfiguration(String path) {
		saveConfiguration(new File(path));
	}
	
	public static void saveConfiguration(File   file) {
		Util.printToFile(file, false, INSTANCE.cfg);
	}
	
	public static final Region2 window() {
		return new Region2(
				INSTANCE.window_w,
				INSTANCE.window_h
				);
	}
	
	public static final Region2 canvas() {
		return new Region2(
				INSTANCE.canvas_w,
				INSTANCE.canvas_h
				);
	}
	
	public static final Vector2 windowToCanvas(float x, float y) {
		x = (x - INSTANCE.window_w / 2) / INSTANCE.canvas_scale + INSTANCE.canvas_w / 2;
		y = (y - INSTANCE.window_h / 2) / INSTANCE.canvas_scale + INSTANCE.canvas_h / 2;
		return new Vector2(x, y);
	}
	
	public static final Vector2 canvasToWindow(float x, float y) {
		x = (x - INSTANCE.canvas_w / 2) * INSTANCE.canvas_scale + INSTANCE.window_w / 2;
		y = (y - INSTANCE.canvas_h / 2) * INSTANCE.canvas_scale + INSTANCE.window_h / 2;
		return new Vector2(x, y);
	}
	
	public static final Vector2 canvasToWindow(Vector2 v) {
		return canvasToWindow(v.x(), v.y());
	}
	
	public static final Vector2 windowToCanvas(Vector2 v) {
		return windowToCanvas(v.x(), v.y());
	}
	
	public static void setScene(Scene scene) {
		Event.queue(new SceneEvent(scene));
	}
	
	public static void mouseMoved(Vector2 mouse) {
		INSTANCE.onMouseMoved(mouse);
	}
	
	public static void wheelMoved(float   wheel) {
		INSTANCE.onWheelMoved(wheel);
	}
	
	public static void keyDn(int key) {
		INSTANCE.onKeyDn(key);
	}
	
	public static void keyUp(int key) {
		INSTANCE.onKeyUp(key);
	}
	
	public static void btnDn(int btn) {
		INSTANCE.onBtnDn(btn);
	}
	
	public static void btnUp(int btn) {
		INSTANCE.onBtnUp(btn);
	}
	
	public void onInit() {		
		canvas_background = getProperty(Vector4::parseVector4, CANVAS_BACKGROUND, canvas_background);
		canvas_foreground = getProperty(Vector4::parseVector4, CANVAS_FOREGROUND, canvas_foreground);
		canvas_layout     = getProperty(Layout::parseLayout, CANVAS_LAYOUT, canvas_layout);
		debug		      = getPropertyAsBoolean(DEBUG, debug);
		debug_background  = getProperty(Vector4::parseVector4, DEBUG_BACKGROUND, debug_background);
		debug_font_name   = getProperty     (DEBUG_FONT_NAME, debug_font_name);
		debug_font_size   = getPropertyAsInt(DEBUG_FONT_SIZE, debug_font_size);
		debug_foreground  = getProperty(Vector4::parseVector4, DEBUG_FOREGROUND, debug_foreground);
		engine_fps	      = getPropertyAsFloat(ENGINE_FPS, engine_fps);
		engine_tps	      = getPropertyAsFloat(ENGINE_TPS, engine_tps);
		window_background = getProperty(Vector4::parseVector4, WINDOW_BACKGROUND, window_background);
		window_border     = getPropertyAsBoolean(WINDOW_BORDER, window_border);
		window_device     = getPropertyAsInt    (WINDOW_DEVICE, window_device);
		window_layout     = getProperty(Layout::parseLayout, WINDOW_LAYOUT, window_layout);
		window_title      = getProperty(WINDOW_TITLE, window_title);		
		
		canvas_background_color = Vector.toColor4i(canvas_background);
		canvas_foreground_color = Vector.toColor4i(canvas_foreground);
		debug_background_color = Vector4.toColor4i(debug_background);
		debug_foreground_color = Vector4.toColor4i(debug_foreground);
		window_background_color = Vector.toColor4i(window_background);
		debug_font = new Font(debug_font_name, Font.PLAIN, debug_font_size);
		
		if(window != null)
			window.dispose();
		
		window = new java.awt.Frame() ;
		canvas = new java.awt.Canvas();
		
		window.add(canvas);
		
		Region2 a, b;
		if(window_border)
			a = Util.computeMaximumWindowRegion(window_device);
		else
			a = Util.computeMaximumScreenRegion(window_device);
		b = window_layout.region(a);
		
		window.setUndecorated(!window_border);
		window.setBounds(
				(int)b.x(), (int)b.y(),
				(int)b.w(), (int)b.h()
				);
		window.setTitle(window_title);
		
		Insets insets = window.getInsets();
		a = new Region2(
			Vector.add(b.loc(), insets.left               , insets.top                ),
			Vector.sub(b.dim(), insets.left + insets.right, insets.top + insets.bottom)
		);		 
		b = canvas_layout.region(a);
		window_w = (int)a.w();
		window_h = (int)a.h();
		canvas_w = (int)b.w();
		canvas_h = (int)b.h();
		canvas_scale = Math.min(
				(float)window_w / canvas_w,
				(float)window_h / canvas_h
				);		
		
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent we) {
				Engine.exit();
			}
		});
		window.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(java.awt.event.WindowEvent we) {
				Event.queue(FocusEvent.GAIN_FOCUS);
			}
			@Override
			public void windowLostFocus(java.awt.event.WindowEvent we) {
				Event.queue(FocusEvent.LOSE_FOCUS);
			}
		});	
		
		canvas.setFocusable(true);
		canvas.setFocusTraversalKeysEnabled(false);
		
		canvas.addKeyListener		 (Input.INSTANCE);
		canvas.addMouseListener	     (Input.INSTANCE);
		canvas.addMouseWheelListener (Input.INSTANCE);
		canvas.addMouseMotionListener(Input.INSTANCE);
		canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent ce) {
				int
					window_w = (int)canvas.getWidth (),
					window_h = (int)canvas.getHeight();
				if(
						INSTANCE.window_w != window_w ||
						INSTANCE.window_h != window_h ){
					Event.queue(new WindowEvent(
							window_w,
							window_h
							));
				}				
			}
		});
		
		window.setVisible(true);
		canvas.requestFocus();
	}
	
	public void onExit() {
		if(scene != null)
			scene.onDetach();
		if(window != null)
			window.dispose();
	}
	
	protected BufferStrategy
		b;	
	public void onRender(float t, float dt, float fixed_dt) {		
		if(b == null || b.contentsLost()) {
			canvas.createBufferStrategy(2);
			b = canvas.getBufferStrategy();
		}
		
		Graphics2D 
			g = (Graphics2D)b.getDrawGraphics();
		
		render_context.g  = g ;
		render_context.t  = t ;
		render_context.dt = dt;		
		render_context.fixed_dt = fixed_dt;
		render_context.canvas_w = window_w;
		render_context.canvas_h = window_h;				
		
		render_context.push();
			render_context.color(window_background_color);
			render_context.rect(
					0, 0,
					window_w,
					window_h,
					true
					);
			render_context.mov(
					(window_w - canvas_w * canvas_scale) / 2,
					(window_h - canvas_h * canvas_scale) / 2
					);
			render_context.sca(
					canvas_scale,
					canvas_scale
					);
			render_context.clip(
					0,
					0,
					canvas_w,
					canvas_h
					);
			render_context.canvas_w = canvas_w;
			render_context.canvas_h = canvas_h;
			
			render_context.push();		
				render_context.color(canvas_background_color);
				render_context.rect(
						0, 0,
						canvas_w,
						canvas_h,
						true
						);
				render_context.color(canvas_foreground_color);
				if(scene != null) 
					render_context.render(scene);
			render_context.pop();
			
		render_context.pop();
		
		if(debug) {
			render_context.push();
			
			render_context.font(debug_font);
			FontMetrics fm = render_context.g.getFontMetrics();
			
			String[] debug_info = {
						String.format("FPS: %1$d hz @ %2$.2f ms%n", render_hz, render_dt),
						String.format("TPS: %1$d hz @ %2$.2f ms%n", update_hz, update_dt),
						String.format("Canvas: %1$d x %2$d @ %3$.1f%%", canvas_w, canvas_h, canvas_scale * 100)
					};
			int
				debug_info_w = 0,
				debug_info_h = 0,
				fm_h = fm.getAscent() + fm.getDescent() + fm.getLeading();
			for(int i = 0; i < debug_info.length; i ++) {
				int w = fm.stringWidth(debug_info[i]);
				if(w > debug_info_w)
					debug_info_w = w;
				debug_info_h += fm_h;
			}
			debug_info_h += fm_h;
			
			int
				x = 0,
				y = 0;
			
			render_context.color(debug_background_color);
			render_context.rect(
					0, 0,
					debug_info_w,
					debug_info_h,
					true
					);
			render_context.color(debug_foreground_color);
			for(int i = 0; i < debug_info.length; i ++)
				render_context.text(debug_info[i], x, y += fm_h);				
			render_context.pop();
		}
		
		g.dispose();
		b.show();		
	}	
	
	public void onUpdate(float t, float dt, float fixed_dt) {
		Input.poll();
		Event.poll();
		
		update_context.t  = t ;
		update_context.dt = dt;
		update_context.fixed_dt = fixed_dt;
		update_context.canvas_w = canvas_w;
		update_context.canvas_h = canvas_h;
		
		update_context.push();
		if(scene != null) 
			update_context.update(scene);
		update_context.pop();
	}
	
	public void onSetScene(SceneEvent se) {
		if(INSTANCE.scene != null)
			INSTANCE.scene.onDetach();
		INSTANCE.scene = se.scene;
		if(INSTANCE.scene != null)
			INSTANCE.scene.onAttach();
	}
	
	public void onMouseMoved(Vector2 mouse) {
		if(scene != null) scene.onMouseMoved(mouse);
	}
	
	public void onWheelMoved(float   wheel) {
		if(scene != null) scene.onWheelMoved(wheel);
	}
	
	public void onKeyDn(int key) {
		if(scene != null) scene.onKeyDn(key);
	}
	
	public void onKeyUp(int key) {
		if(scene != null) scene.onKeyUp(key);
	}
	
	public void onBtnDn(int btn) {
		if(scene != null) scene.onBtnDn(btn);
	}
	
	public void onBtnUp(int btn) {
		if(scene != null) scene.onBtnUp(btn);
	}
	
	public void onResize(WindowEvent we) {
		window_w = we.window_w;
		window_h = we.window_h;
		
		Region2 a, b;								
		a = new Region2(
				window_w, 
				window_h
		);
		b = canvas_layout.region(a);
		canvas_w = (int)b.w();
		canvas_h = (int)b.h();
		canvas_scale = Math.min(
				(float)window_w / canvas_w,
				(float)window_h / canvas_h
				);
		if(scene != null)
			scene.onResize();
	}
	
	public void onGainFocus(FocusEvent fe) {
		Input.INSTANCE.onGainFocus();
		 if(scene != null)
			 scene.onGainFocus();
	}
	
	public void onLoseFocus(FocusEvent fe) {
		Input.INSTANCE.onLoseFocus();
		if(scene != null)
			scene.onLoseFocus();
	}
	
	private static final long
		ONE_SECOND = 1000000000L, //one second in nanoseconds
		ONE_MILLIS =	1000000L; //one millis in nanoseconds

	@Override
	public void run() {
		try {
			onInit();
			long
				render_fixed_nanos = engine_fps > 0 ? (long)(ONE_SECOND / engine_fps) : 0, //fixed time-per-render in nanoseconds
				update_fixed_nanos = engine_tps > 0 ? (long)(ONE_SECOND / engine_tps) : 0; //fixed time-per-update in nanoseconds
			float
				render_fixed_dt = (float)render_fixed_nanos / ONE_SECOND,				   //fixed time-per-render in seconds
				update_fixed_dt = (float)update_fixed_nanos / ONE_SECOND;				   //fixed time-per-update in seconds
			long
				render_lag_nanos = 0,													   //dynamic render lag in nanoseconds
				update_lag_nanos = 0,													   //dynamic update lag in nanoseconds
				render_avg_nanos = 0,													   //dynamic average time-per-render in nanoseconds
				update_avg_nanos = 0,													   //dynamic average time-per-update in nanoseconds
				render_nanos = 0,														   //elapsed render time in nanoseconds				
				update_nanos = 0;														   //elapsed update time in nanoseconds
			int
				render_count = 0,														   //number of render calls in the last one second
				update_count = 0;														   //number of update calls in the last one second
			long
				elapsed_nanos = 0,														   //current elapsed time in nanoseconds
				current_nanos = System.nanoTime();										   //current time in nanoseconds
			while(running) {
				long delta_nanos = - current_nanos + (current_nanos = System.nanoTime());  //delta time in nanoseconds
				render_nanos  += delta_nanos;
				update_nanos  += delta_nanos;
				elapsed_nanos += delta_nanos;
				
				if(update_nanos + update_lag_nanos >= update_fixed_nanos) {
					float
						update_t  = (float)current_nanos / ONE_SECOND,
						update_dt = (float) update_nanos / ONE_SECOND;
					onUpdate(update_t, update_dt, update_fixed_dt);
					
					update_lag_nanos = Util.clamp(update_lag_nanos + update_nanos - update_fixed_nanos, - update_fixed_nanos, update_fixed_nanos);
					update_avg_nanos += update_nanos;
					update_nanos = 0;
					update_count ++;
				}
				
				if(render_nanos + render_lag_nanos >= render_fixed_nanos) {
					float
						render_t  = (float)current_nanos / ONE_SECOND,
						render_dt = (float) render_nanos / ONE_SECOND;
					onRender(render_t, render_dt, render_fixed_dt);
					
					render_lag_nanos = Util.clamp(render_lag_nanos + render_nanos - render_fixed_nanos, - render_fixed_nanos, render_fixed_nanos);
					render_avg_nanos += render_nanos;
					render_nanos = 0;
					render_count ++;
				}
				
				if(elapsed_nanos >= ONE_SECOND) {
					render_dt = (float)render_avg_nanos / render_count / ONE_MILLIS;
					update_dt = (float)update_avg_nanos / update_count / ONE_MILLIS;
					render_hz = render_count;
					update_hz = update_count;
					render_avg_nanos = 0;
					update_avg_nanos = 0;
					render_count  = 0;
					update_count  = 0;
					elapsed_nanos = 0;
				}
				
				long sync = Math.min(
					render_fixed_nanos - render_nanos - render_lag_nanos,
					update_fixed_nanos - update_nanos - update_lag_nanos
					) / ONE_MILLIS;
				if(sync > 0) Thread.sleep(1);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			onExit();
		}
	}
	
	public static final String
		CANVAS_BACKGROUND = "canvas-background",
		CANVAS_FOREGROUND = "canvas-foreground",
		CANVAS_LAYOUT     = "canvas-layout",
		DEBUG             = "debug",
		DEBUG_BACKGROUND  = "debug-background",
		DEBUG_FONT_NAME   = "debug-font-name",
		DEBUG_FONT_SIZE   = "debug-font-size",
		DEBUG_FOREGROUND  = "debug-foreground",
		ENGINE_FPS        = "engine-fps",
		ENGINE_TPS        = "engine-tps",
		WINDOW_BACKGROUND = "window-background",
		WINDOW_BORDER     = "window-border",
		WINDOW_DEVICE     = "window-device",
		WINDOW_LAYOUT     = "window-layout",
		WINDOW_TITLE      = "window-title";
	
	private static class SceneEvent {
		public final Scene
			scene;		
		
		public SceneEvent(Scene scene) {
			this.scene = scene;
		}
	}
	
	private static class WindowEvent {
		public final int
			window_w,
			window_h;
		
		public WindowEvent(int window_w, int window_h) {
			this.window_w = window_w;
			this.window_h = window_h;
		}
	}
	
	private static enum FocusEvent {
		GAIN_FOCUS,
		LOSE_FOCUS;
	}
}
