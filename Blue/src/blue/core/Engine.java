package blue.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import blue.Blue;
import blue.core.Renderable.RenderContext;
import blue.core.Updateable.UpdateContext;
import blue.geom.Layout;
import blue.geom.Region2;
import blue.geom.Vector;
import blue.geom.Vector2;
import blue.geom.Vector4;
import blue.util.Configuration;
import blue.util.Util;

public class Engine implements Runnable {
	protected static final Engine
		INSTANCE = new Engine();
	
	protected Vector4
		canvas_background = new Vector4(  0,   0,   0, 255),
		canvas_foreground = new Vector4(255, 255, 255, 255);
	protected boolean
		debug = true;
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
	
	protected final Configuration
		cfg = new Configuration(
				WINDOW_BORDER, window_border,
				WINDOW_DEVICE, window_device,
				WINDOW_LAYOUT, window_layout
				);
	protected Color
		foreground,
		background;
	protected int
		render_hz,
		update_hz;
	protected float
		render_dt,
		update_dt;
	protected int
		canvas_w,
		canvas_h;
	
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
		//do nothing
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
	
	public static Configuration getConfiguration() {
		return INSTANCE.cfg;
	}
	
	public static void setScene(Scene scene) {
		if(INSTANCE.scene != null)
			INSTANCE.scene.onDetach();
		INSTANCE.scene = scene;
		if(INSTANCE.scene != null)
			INSTANCE.scene.onAttach();
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
		canvas_background = cfg.get(Vector4::parseVector4, CANVAS_BACKGROUND, canvas_background);
		canvas_foreground = cfg.get(Vector4::parseVector4, CANVAS_FOREGROUND, canvas_foreground);
		debug         = cfg.getBoolean(DEBUG, debug);
		engine_fps    = cfg.getFloat(ENGINE_FPS, engine_fps);
		engine_tps    = cfg.getFloat(ENGINE_TPS, engine_tps);
		window_border = cfg.getBoolean(WINDOW_BORDER, window_border);
		window_device = cfg.getInteger(WINDOW_DEVICE, window_device);
		window_layout = cfg.get(Layout::parseLayout, WINDOW_LAYOUT, window_layout);
		window_title  = cfg.get(WINDOW_TITLE, window_title);		
		
		background = Vector.toColor4i(canvas_background);
		foreground = Vector.toColor4i(canvas_foreground);
		
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
		
		window.setBounds(
				(int)b.x(), (int)b.y(),
				(int)b.w(), (int)b.h()
				);
		window.setTitle(window_title);
		
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				Engine.exit();
			}
		});
		
		canvas.setFocusable(true);
		canvas.setFocusTraversalKeysEnabled(false);
		
		canvas.addKeyListener        (Input.INSTANCE);
		canvas.addMouseListener      (Input.INSTANCE);
		canvas.addMouseWheelListener (Input.INSTANCE);
		canvas.addMouseMotionListener(Input.INSTANCE);		
		
		window.setVisible(true);
		canvas.requestFocus();
		
		if(scene != null)
			scene.onInit();
	}
	
	public void onExit() {
		if(scene != null)
			scene.onExit();
		if(window != null)
			window.dispose();
	}	
	
	protected BufferStrategy
		b;	
	public void onRender(float t, float dt, float fixed_dt) {
		canvas_w = (int)canvas.getWidth ();
		canvas_h = (int)canvas.getHeight();
		
		if(b == null || b.contentsLost()) {
			canvas.createBufferStrategy(2);
			b = canvas.getBufferStrategy();
		}		
		
		Graphics2D g  = (Graphics2D)b.getDrawGraphics();		
		render_context.g  = g ;
		render_context.t  = t ;
		render_context.dt = dt;		
		render_context.fixed_dt = fixed_dt;
		render_context.canvas_w = canvas_w;
		render_context.canvas_h = canvas_h;
		
		render_context.color(background);
		render_context.rect(
				0, 0,
				canvas_w,
				canvas_h,
				true
				);
		render_context.color(foreground);
		
		render_context.push();
		if(scene != null) 
			scene.onRender(render_context);
		render_context.pop();
		
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
			scene.onUpdate(update_context);
		update_context.pop();
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
	
	private static final long
		ONE_SECOND = 1000000000L, //one second in nanoseconds
		ONE_MILLIS =    1000000L; //one millis in nanoseconds

	@Override
	public void run() {
		try {
			onInit();
			long
				render_fixed_nanos = engine_fps > 0 ? (long)(ONE_SECOND / engine_fps) : 0, //fixed time-per-render in nanoseconds
				update_fixed_nanos = engine_tps > 0 ? (long)(ONE_SECOND / engine_tps) : 0; //fixed time-per-update in nanoseconds
			float
				render_fixed_dt = (float)render_fixed_nanos / ONE_SECOND, 				   //fixed time-per-render in seconds
				update_fixed_dt = (float)update_fixed_nanos / ONE_SECOND; 				   //fixed time-per-update in seconds
			long
				render_lag_nanos = 0,													   //dynamic render lag in nanoseconds
				update_lag_nanos = 0,													   //dynamic update lag in nanoseconds
				render_avg_nanos = 0,													   //dynamic average time-per-render in nanoseconds
				update_avg_nanos = 0,													   //dynamic average time-per-update in nanoseconds
				render_nanos = 0,                                                          //elapsed render time in nanoseconds				
				update_nanos = 0;														   //elapsed update time in nanoseconds
			int
				render_count = 0,														   //number of render calls in the last one second
				update_count = 0;														   //number of update calls in the last one second
			long
				elapsed_nanos = 0,                                                         //current elapsed time in nanoseconds
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
					
					System.out.printf("FPS: %1$d hz @ %2$.2f ms%n", render_hz, render_dt);
					System.out.printf("TPS: %1$d hz @ %2$.2f ms%n", update_hz, update_dt);
				}
				
				long sync = Math.min(
					render_fixed_nanos - render_nanos - render_lag_nanos,
					update_fixed_nanos - update_nanos - update_lag_nanos
					) / ONE_MILLIS - 1;
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
		DEBUG         = "debug",
		ENGINE_FPS    = "engine-fps",
		ENGINE_TPS    = "engine-tps",
		WINDOW_BORDER = "window-border",
		WINDOW_DEVICE = "window-device",
		WINDOW_LAYOUT = "window-layout",
		WINDOW_TITLE  = "window-title";
}
