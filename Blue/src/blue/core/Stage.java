package blue.core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.net.URL;
import java.util.Map;

import blue.Blue;
import blue.core.Event.Broker;
import blue.core.Event.Handle;
import blue.core.Event.Listener;
import blue.core.Renderable.RenderContext;
import blue.core.Updateable.UpdateContext;
import blue.math.Bounds2;
import blue.math.Box;
import blue.math.Layout;
import blue.math.Region2;
import blue.math.Vector;
import blue.math.Vector2;
import blue.math.Vector4;
import blue.util.Configuration;
import blue.util.FileUtility;
import blue.util.ImageUtility;

public class Stage extends Module {
	protected static final Stage
		MODULE = new Stage();
	public static final Cursor
		NULL_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
			ImageUtility.createBufferedImage(0, 16, 16),
			new Point(0, 0),
			null
		);

	protected String
		debug;
	protected Vector4
		debug_background = new Vector4(  0,   0,   0, 192),
		debug_foreground = new Vector4(255, 255, 255, 192);
	protected String
		debug_font_name = "Monospaced";
	protected int
		debug_font_size = 16;

	protected float
		thread_fps = 60f,
		thread_tps = 60f;
	
	protected Vector4
		canvas_background = Vector4.fromColor4i(Color.BLACK),
		window_background = Vector4.fromColor4i(Color.BLACK);
	protected Layout  
		canvas_layout = new Layout(),
		window_layout = new Layout();
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
	
	protected Metrics
		debug_metrics = metrics;
	protected Color
		debug_background_color,
		debug_foreground_color;
	protected Font
		debug_font;
	
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
		Configuration.map(cfg,
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
		return Configuration.setProperty(MODULE.cfg, key, val);
	}
	
	public static String getProperty(Object key, Object alt) {
		return Configuration.getProperty(MODULE.cfg, key, alt);
	}
	
	public static void saveConfiguration(URL    path) {
		saveConfiguration(FileUtility.getFile(path));
	}
	
	public static void saveConfiguration(String path) {
		saveConfiguration(FileUtility.getFile(path));
	}
	
	public static void saveConfiguration(File   file) {
		FileUtility.printToFile(file, false, MODULE.cfg);
	}
	
	public static void loadConfiguration(URL    path) {
		loadConfiguration(FileUtility.getFile(path));
	}
	
	public static void loadConfiguration(String path) {
		loadConfiguration(FileUtility.getFile(path));
	}
	
	public static void loadConfiguration(File   file) {
		FileUtility.parseFromFile(file, MODULE.cfg);
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
	
	public static void setCursor(Cursor cursor) {
		MODULE.window.setCursor(cursor);
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
	
	public static Metrics getMetrics() {
		return MODULE.metrics;
	}
	
	public static <T extends Module> void showMetrics(Class<T> type) {
		MODULE.debug_metrics = Metrics.getByType(type);
	}
	
	public static <T extends Module> void showMetrics(String   name) {
		MODULE.debug_metrics = Metrics.getByName(name);
	}
	
	public static void showMetrics(Metrics metrics) {
		MODULE.debug_metrics = metrics;
	}
	
	public static Region2 getMaximumScreenRegion(int i) {
		GraphicsDevice        gd = ImageUtility.getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		
		return new Region2(
			bounds.x,
			bounds.y,
			bounds.width,
			bounds.height
		);
	}
	
	public static Region2 getMaximumWindowRegion(int i) {
		GraphicsDevice        gd = ImageUtility.getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		Insets    insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		
		return new Region2(
			bounds.x + insets.left,
			bounds.y + insets.top ,
			bounds.width  - insets.left - insets.right ,
			bounds.height - insets.top  - insets.bottom
		);
	}
	
	public static Bounds2 getMaximumScreenBounds(int i) {
		GraphicsDevice        gd = ImageUtility.getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		
		return new Bounds2(
			bounds.x,
			bounds.y,
			bounds.x + bounds.width,
			bounds.y + bounds.height
		);
	}
	
	public static Bounds2 getMaximumWindowBounds(int i) {
		GraphicsDevice        gd = ImageUtility.getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		Insets    insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		
		return new Bounds2(
			bounds.x + insets.left,
			bounds.y + insets.top ,
			bounds.x + bounds.width  - insets.left - insets.right ,
			bounds.y + bounds.height - insets.top  - insets.bottom
		);
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
				true
			);
			
			context.translate(
				(int)(window_w - canvas_w * canvas_scale) / 2,
				(int)(window_h - canvas_h * canvas_scale) / 2
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
				true
			);
			
			if(scene != null)
				scene.render(context);
		context = context.pop();
		
		if(debug_metrics != null) {
			
			g.setFont(debug_font);			
			FontMetrics fm = g.getFontMetrics();			
			String[] lines = new String[debug_metrics.map.size()];
			
			int
				i = 0,
				w = 0,
				h = 0;
			for(Map.Entry<String, String> metric: debug_metrics.map.entrySet()) {
				
				String line = metric.getKey() + ": " + metric.getValue();
				lines[i ++] = line;
				
				w = Math.max(w, fm.stringWidth(line));
				h += fm.getHeight();
			}
			
			w += w > 0 ? 4 : 0;
			h += h > 0 ? 4 : 0;
			
			g.setColor(debug_background_color);
			g.fillRect(0, 0, w, h);
			g.setColor(debug_foreground_color);
			
			for(i = 0; i < lines.length; i ++)
				g.drawString(lines[i], 2, 2 + fm.getLeading() + fm.getAscent() + i * fm.getHeight());
		}		
		
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
		debug = Configuration.getProperty(cfg, DEBUG, debug);
		debug_background = Configuration.getPropertyAsObject(cfg, Vector4::parseVector4, DEBUG_BACKGROUND, debug_background);
		debug_foreground = Configuration.getPropertyAsObject(cfg, Vector4::parseVector4, DEBUG_FOREGROUND, debug_foreground);
		debug_font_name  = Configuration.getProperty     (cfg, DEBUG_FONT_NAME, debug_font_name);
		debug_font_size  = Configuration.getPropertyAsInt(cfg, DEBUG_FONT_SIZE, debug_font_size);
		
		thread_fps = Configuration.getPropertyAsFloat(cfg, THREAD_FPS, thread_fps);
		thread_tps = Configuration.getPropertyAsFloat(cfg, THREAD_TPS, thread_tps);
		canvas_background = Configuration.getPropertyAsObject(cfg, Vector4::parseVector4, CANVAS_BACKGROUND, canvas_background);
		window_background = Configuration.getPropertyAsObject(cfg, Vector4::parseVector4, WINDOW_BACKGROUND, window_background);
		canvas_layout = Configuration.getPropertyAsObject(cfg, Layout::parseLayout, CANVAS_LAYOUT, canvas_layout);
		window_layout = Configuration.getPropertyAsObject(cfg, Layout::parseLayout, WINDOW_LAYOUT, window_layout);
		window_border = Configuration.getPropertyAsBoolean(cfg, WINDOW_BORDER, window_border);
		window_device = Configuration.getPropertyAsInt    (cfg, WINDOW_DEVICE, window_device);
		window_title  = Configuration.getProperty(cfg, WINDOW_TITLE, window_title);		
		
		debug_metrics = Metrics.getByName(debug);
		debug_background_color  = Vector.toColor4i(debug_background);
		debug_foreground_color  = Vector.toColor4i(debug_foreground);
		debug_font = new Font(debug_font_name, Font.PLAIN, debug_font_size);
		
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
			a = getMaximumWindowRegion(window_device);
		else
			a = getMaximumScreenRegion(window_device);
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
			
			metrics.setMetric(Metric.FPS, String.format("%1$d hz @ %2$.2f [%3$.2f - %4$.2f] ms", render_hz, avg_render_dt, min_render_dt, max_render_dt));
			metrics.setMetric(Metric.TPS, String.format("%1$d hz @ %2$.2f [%3$.2f - %4$.2f] ms", update_hz, avg_update_dt, min_update_dt, max_update_dt));
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
				case GAIN_FOCUS: 
					Input.INSTANCE.onGainFocus();
					scene         .onGainFocus();
					break;
				case LOSE_FOCUS:
					Input.INSTANCE.onLoseFocus();
					scene         .onLoseFocus();
					break;
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
		
		metrics.setMetric(Metric.CANVAS, String.format("%1$d x %2$d @ %3$.2f%%", canvas_w, canvas_h, canvas_scale * 100));
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
		DEBUG_BACKGROUND  = "debug-background",
		DEBUG_FOREGROUND  = "debug-foreground",
		DEBUG_FONT_NAME   = "debug-font-name",
		DEBUG_FONT_SIZE   = "debug-font-size",
		THREAD_FPS        = "thread-fps",
		THREAD_TPS        = "thread-tps",
		WINDOW_BACKGROUND = "window-background",
		WINDOW_LAYOUT     = "window-layout",
		WINDOW_DEVICE     = "window-device",
		WINDOW_BORDER     = "window-border",
		WINDOW_TITLE      = "window-title";
	
	public static class Metric {
		public static final String
			CANVAS = "Canvas",
			FPS = "FPS",
			TPS = "TPS";
	}
}
