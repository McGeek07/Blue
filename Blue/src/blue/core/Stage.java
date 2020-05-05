package blue.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferStrategy;
import java.io.File;

import blue.Blue;
import blue.core.Renderable.RenderContext;
import blue.core.Updateable.UpdateContext;
import blue.geom.Box;
import blue.geom.Layout;
import blue.geom.Region2;
import blue.geom.Vector;
import blue.geom.Vector2;
import blue.geom.Vector4;
import blue.util.Util;
import blue.util.event.Broker;
import blue.util.event.Handle;
import blue.util.event.Listener;

public class Stage extends Module {
	protected static final Stage
		MODULE = new Stage();
	protected float
		thread_fps = 60f,
		thread_tps = 60f;
	protected Vector4
		canvas_background = Vector4.fromColor4i(Color.BLACK),
		window_background = Vector4.fromColor4i(Color.BLACK);
	protected Layout  
		canvas_layout = Layout.DEFAULT,
		window_layout = Layout.DEFAULT;
	protected int 
		window_device = 0;	
	protected boolean 
		window_border = true;
	protected String 
		window_title  = Blue.VERSION.toString();
	
	protected java.awt.Frame
		window;
	protected java.awt.Canvas
		canvas;
	
	protected Scene
		scene;
	
	protected Color
		canvas_background_color,
		window_background_color;
	protected int
		window_w,
		window_h,
		canvas_w,
		canvas_h;
	protected float
		canvas_scale;
	
	protected float
		avg_render_dt,
		min_render_dt,
		max_render_dt,
		
		avg_update_dt,
		min_update_dt,
		max_update_dt;
	protected int
		render_hz,
		update_hz;
	
	private Stage() {
		Util.map(cfg,
			CANVAS_LAYOUT, canvas_layout,
			WINDOW_BORDER, window_border,
			WINDOW_DEVICE, window_device,
			WINDOW_LAYOUT, window_layout
		);
		handle.attach(SceneEvent.class, (event) -> {
			onSceneEvent(event);
		});
		handle.attach(WindowEvent.class, (event) -> {
			onWindowEvent(event);
		});
		handle.attach(CanvasEvent.class, (event) -> {
			onCanvasEvent(event);
		});
	}
	
	public static void init() {
		Engine.init(MODULE);
	}
	
	public static void stop() {
		Engine.stop(MODULE);
	}
	
	public static String setProperty(Object key, Object val) {
		return Util.setEntry(MODULE.cfg, key, val);
	}
	
	public static String getProperty(Object key, Object alt) {
		return Util.getEntry(MODULE.cfg, key, alt);
	}
	
	public static void saveConfig(String path) {
		saveConfig(new File(path));
	}
	
	public static void saveConfig(File   file) {
		Util.printToFile(file, false, MODULE.cfg);
	}
	
	public static void loadConfig(String path) {
		loadConfig(new File(path));
	}
	
	public static void loadConfig(File   file) {
		Util.parseFromFile(file, MODULE.cfg);
	}
	
	public static <T> void attach(Class<T> type, Listener<T> listener) {
		MODULE.handle.attach(type, listener);
	}
	
	public static <T> void detach(Class<T> type, Listener<T> listener) {
		MODULE.handle.detach(type, listener);
	}
	
	public static void attach(Broker broker) {
		MODULE.broker.attach(broker);
	}
	
	public static void detach(Broker broker) {
		MODULE.broker.detach(broker);
	}
	
	public static void attach(Handle handle) {
		MODULE.broker.attach(handle);
	}
	
	public static void detach(Handle handle) {
		MODULE.broker.detach(handle);
	}
	
	public static <T> void queue(T event) {
		MODULE.broker.queue(event);
	}
	
	public static <T> void flush(T event) {
		MODULE.broker.flush(event);
	}
	
	public static void poll() {
		MODULE.broker.poll();
	}
	
	public static void setScene(Scene scene) {
		queue(new SceneEvent(scene));
	}
	
	public static void mouseMoved(Vector2 mouse) {
		if(MODULE.scene != null)
			MODULE.scene.onMouseMoved(mouse);
	}
	
	public static void wheelMoved(float   wheel) {
		if(MODULE.scene != null)
			MODULE.scene.onWheelMoved(wheel);
	}
	
	public static void btnDn(int btn) {
		if(MODULE.scene != null)
			MODULE.scene.onBtnDn(btn);
	}
	
	public static void btnUp(int btn) {
		if(MODULE.scene != null)
			MODULE.scene.onBtnUp(btn);
	}
	
	public static void keyDn(int key) {
		if(MODULE.scene != null)
			MODULE.scene.onKeyDn(key);
	}
	
	public static void keyUp(int key) {
		if(MODULE.scene != null)
			MODULE.scene.onKeyUp(key);
	}
	
	public static Box<?> bounds() {
		return new Region2(
				MODULE.canvas_w,
				MODULE.canvas_h
				);
	}
	
	public static final Vector2 mouseToPixel(float x, float y) {
		x = (x - MODULE.window_w / 2) / MODULE.canvas_scale + MODULE.canvas_w / 2;
		y = (y - MODULE.window_h / 2) / MODULE.canvas_scale + MODULE.canvas_h / 2;
		return new Vector2(x, y);
	}
	
	public static final Vector2 pixelToMouse(float x, float y) {
		x = (x - MODULE.canvas_w / 2) * MODULE.canvas_scale + MODULE.window_w / 2;
		y = (y - MODULE.canvas_h / 2) * MODULE.canvas_scale + MODULE.window_h / 2;
		return new Vector2(x, y);
	}
	
	public static final Vector2 mouseToPixel(Vector2 v) {
		return mouseToPixel(v.x(), v.y());
	}
	
	public static final Vector2 pixelToMouse(Vector2 v) {
		return pixelToMouse(v.x(), v.y());
	}
	
	protected BufferStrategy
		b;
	public void render(float t, float dt, float fixed_dt) {
		if(b == null || b.contentsLost()) {
			canvas.createBufferStrategy(2);
			b = canvas.getBufferStrategy();
		}
		
		Graphics2D
			g = (Graphics2D)b.getDrawGraphics();
		
		g.fillRect(
				0, 0,
				window_w,
				window_h
				);
		
		RenderContext context = new RenderContext();
		
		context.g  = g ;
		context.t  = t ;
		context.dt = dt;		
		context.fixed_dt = fixed_dt;
		context.canvas_w = canvas_w;
		context.canvas_h = canvas_h;
		
		context = context.push();
			context.color(window_background_color);
			context.rect(
					0, 0, 
					window_w, 
					window_h, 
					true);
			
			context.translate(
					(window_w - canvas_w * canvas_scale) / 2,
					(window_h - canvas_h * canvas_scale) / 2
					);
			context.scale(
					canvas_scale,
					canvas_scale
					);
			context.clip(
					0, 0,
					canvas_w,
					canvas_h
					);
			
			context.color(canvas_background_color);
			context.rect(
					0, 0,
					canvas_w,
					canvas_h,
					true);
			
			if(scene != null)
				scene.render(context);
		context = context.pop();
		
		g.dispose();
		b.show();
	}
	
	public void update(float t, float dt, float fixed_dt) {
		Input.poll();
		      poll();
		
		UpdateContext context = new UpdateContext();
		
		context.t  = t ;
		context.dt = dt;
		context.fixed_dt = fixed_dt;
		context.canvas_w = canvas_w;
		context.canvas_h = canvas_h;
		
		context = context.push();
		if(scene != null)
			scene.update(context);
		context = context.pop();
	}
	
	@Override
	public void onInit() {		
		thread_fps = Util.getEntryAsFloat(cfg, THREAD_FPS, thread_fps);
		thread_tps = Util.getEntryAsFloat(cfg, THREAD_TPS, thread_tps);
		canvas_background = Util.getEntry(cfg, Vector4::parseVector4, CANVAS_BACKGROUND, canvas_background);
		window_background = Util.getEntry(cfg, Vector4::parseVector4, WINDOW_BACKGROUND, window_background);
		canvas_layout = Util.getEntry(cfg, Layout::parseLayout, CANVAS_LAYOUT, canvas_layout);
		window_layout = Util.getEntry(cfg, Layout::parseLayout, WINDOW_LAYOUT, window_layout);
		window_border = Util.getEntryAsBoolean(cfg, WINDOW_BORDER, window_border);
		window_device = Util.getEntryAsInt    (cfg, WINDOW_DEVICE, window_device);
		window_title  = Util.getEntry(cfg, WINDOW_TITLE, window_title);
		
		canvas_background_color = Vector.toColor4i(canvas_background);
		window_background_color = Vector.toColor4i(window_background);
		
		if(window != null)
			window.dispose();
		
		window = new java.awt.Frame() ;
		canvas = new java.awt.Canvas();
		window.add(canvas);
		
		Region2
			a, b;
		if(window_border)
			a = Util.getMaximumWindowRegion(window_device);
		else
			a = Util.getMaximumScreenRegion(window_device);
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
				queue(WindowEvent.GAIN_FOCUS);
			}
			@Override
			public void windowLostFocus  (java.awt.event.WindowEvent we) {
				queue(WindowEvent.LOSE_FOCUS);
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
			public void componentResized(java.awt.event.ComponentEvent ce) {
				queue(new CanvasEvent(
						(int)canvas.getWidth() ,
						(int)canvas.getHeight()
						));
			}
		});
		
		window.setVisible(true);
		canvas.requestFocus();
		
		fixed_render_nanos = thread_fps > 0 ? (long)(ONE_SECOND / thread_fps) : 0;
		avg_render_nanos = 0;
		min_render_nanos = Long.MAX_VALUE;
		max_render_nanos = 0;
		render_nanos = 0;
		render_ct = 0;		
		
		fixed_update_nanos = thread_tps > 0 ? (long)(ONE_SECOND / thread_tps) : 0;
		avg_update_nanos = 0;
		min_update_nanos = Long.MAX_VALUE;
		max_update_nanos = 0;
		update_nanos = 0;
		update_ct = 0;
		
		fixed_render_dt = (float)fixed_render_nanos / ONE_SECOND;
		fixed_update_dt = (float)fixed_update_nanos / ONE_SECOND;
		
		elapsed_nanos = 0;
		current_nanos = System.nanoTime();
	}
	
	@Override
	public void onStop() {
		if(window != null)
			window.dispose();
	}	
	
	private static final long
		ONE_SECOND = 1000000000,
		ONE_MILLIS =    1000000;
	
	private long
		//render timer
		fixed_render_nanos,		
		avg_render_nanos,
		min_render_nanos,
		max_render_nanos,
		render_nanos,
		render_ct,
		//update timer
		fixed_update_nanos,
		avg_update_nanos,
		min_update_nanos,
		max_update_nanos,
		update_nanos,
		update_ct,
		//timer
		elapsed_nanos,
		current_nanos;
	private float
		fixed_render_dt,
		fixed_update_dt;
	
	@Override
	public void onStep() throws InterruptedException {
		long delta_nanos = - current_nanos + (current_nanos = System.nanoTime());
		render_nanos  += delta_nanos;
		update_nanos  += delta_nanos;
		elapsed_nanos += delta_nanos;
		
		if(render_nanos >= fixed_render_nanos) {
			float
				t  = (float)current_nanos / ONE_SECOND,
				dt = (float) render_nanos / ONE_SECOND;				
			
			render(t, dt, fixed_render_dt);
			
			avg_render_nanos += render_nanos;
			if(render_nanos < min_render_nanos) min_render_nanos = render_nanos;
			if(render_nanos > max_render_nanos) max_render_nanos = render_nanos;
			render_nanos = 0;
			render_ct ++;
		}
		
		if(update_nanos >= fixed_update_nanos) {
			float
				t  = (float)current_nanos / ONE_SECOND,
				dt = (float) update_nanos / ONE_SECOND;
			
			update(t, dt, fixed_update_dt);
			
			avg_update_nanos += update_nanos;
			if(update_nanos < min_update_nanos) min_update_nanos = update_nanos;
			if(update_nanos > max_update_nanos) max_update_nanos = update_nanos;
			update_nanos = 0;
			update_ct ++;
		}
		
		if(elapsed_nanos >= ONE_SECOND) {				
			avg_render_dt = (float)avg_render_nanos / render_ct / ONE_MILLIS;
			min_render_dt = (float)min_render_nanos / ONE_MILLIS;
			max_render_dt = (float)max_render_nanos / ONE_MILLIS;
			render_hz = (int)render_ct;
			avg_render_nanos = 0;
			min_render_nanos = Long.MAX_VALUE;
			max_render_nanos = 0;
			render_ct = 0;
			
			avg_update_dt = (float)avg_update_nanos / update_ct / ONE_MILLIS;
			min_update_dt = (float)min_update_nanos / ONE_MILLIS;
			max_update_dt = (float)max_update_nanos / ONE_MILLIS;
			update_hz = (int)update_ct;
			avg_update_nanos = 0;
			min_update_nanos = Long.MAX_VALUE;
			max_update_nanos = 0;
			update_ct = 0;
			
			elapsed_nanos = 0;
			
			System.out.printf("FPS: %1$d hz @ %2$.2f [%3$.2f - %4$.2f] ms%n", render_hz, avg_render_dt, min_render_dt, max_render_dt);
			System.out.printf("TPS: %1$d hz @ %2$.2f [%3$.2f - %4$.2f] ms%n", update_hz, avg_update_dt, min_update_dt, max_update_dt);
		}
		
		long sync = Math.min(
			fixed_render_nanos - render_nanos,
			fixed_update_nanos - update_nanos
			) / ONE_MILLIS;
		if(sync > 1) Thread.sleep(1);		
	}
	
	public void onSceneEvent(SceneEvent event) {
		if(scene != null)
			scene.onDetach();
		scene = event.scene;
		if(scene != null)
			scene.onAttach();
	}
	
	public void onWindowEvent(WindowEvent event) {
		if(scene != null)
			switch(event) {
				case GAIN_FOCUS: scene.onGainFocus(); break;
				case LOSE_FOCUS: scene.onLoseFocus(); break;
			}
	}

	public void onCanvasEvent(CanvasEvent event) {		
		Region2	a, b;		
		a = new Region2(
				this.window_w = event.window_w,
				this.window_h = event.window_h
		);
		b = canvas_layout.region(a);
		this.canvas_w = (int)b.w();
		this.canvas_h = (int)b.h();
		this.canvas_scale = Math.min(
				(float)this.window_w / this.canvas_w,
				(float)this.window_h / this.canvas_h
				);
		if(scene != null)
			scene.onResize();	
	}
	
	public static class SceneEvent {
		public final Scene
			scene;
		
		public SceneEvent(Scene scene) {
			this.scene = scene;
		}
	}
	
	public static enum  WindowEvent {
		GAIN_FOCUS,
		LOSE_FOCUS;
	}
	
	public static class CanvasEvent {
		public final int
			window_w,
			window_h;
		
		public CanvasEvent(int window_w, int window_h) {
			this.window_w = window_w;
			this.window_h = window_h;
		}
	}
	
	public static final String
		CANVAS_BACKGROUND = "canvas-background",
		CANVAS_LAYOUT     = "canvas-layout",
		DEBUG             = "debug",
		THREAD_FPS        = "thread-fps",
		THREAD_TPS        = "thread-tps",
		WINDOW_BACKGROUND = "window-background",
		WINDOW_LAYOUT     = "window-layout",
		WINDOW_DEVICE     = "window-device",
		WINDOW_BORDER     = "window-border",
		WINDOW_TITLE      = "window-title";
}
