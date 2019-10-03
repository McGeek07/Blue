package blue.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import blue.Blue;
import blue.core.render.RenderContext;
import blue.core.render.Renderable;
import blue.core.update.UpdateContext;
import blue.core.update.Updateable;
import blue.geom.Bounds2f;
import blue.geom.Layout;
import blue.geom.Region2f;
import blue.geom.Vector;
import blue.geom.Vector2f;
import blue.geom.Vector4f;
import blue.util.Config;
import blue.util.Util;

public final class Engine implements Runnable, Renderable, Updateable {
	protected static final long
		ONE_SECOND = 1000000000,
		ONE_MILLIS =    1000000;
	protected static final Engine
		INSTANCE = new Engine();
	
	protected final Canvas
		canvas = new Canvas(this);
	protected final Window
		window = new Window(this);
	protected Scene
		scene;

	protected boolean
		debug = true;
	protected boolean
		thread_async = true;
	protected int
		thread_fps = 60,
		thread_tps = 60;
	
	protected final Config
		config = new Config(
				CANVAS_BACKGROUND, canvas.canvas_background,
				CANVAS_FOREGROUND, canvas.canvas_foreground,
				CANVAS_GFX_AA, canvas.canvas_gfx_aa,
				CANVAS_GFX_NB, canvas.canvas_gfx_nb,
				DEBUG, debug,
				THREAD_ASYNC, thread_async,
				THREAD_FPS, thread_fps,
				THREAD_TPS, thread_tps,
				WINDOW_AOT, window.window_aot,
				WINDOW_BORDER, window.window_border,
				WINDOW_DEVICE, window.window_device,
				WINDOW_LAYOUT, window.window_layout
				);
	
	protected Thread
		thread;	
	protected boolean
		running;
	
	protected boolean
		debug_metrics = true;
	protected Font
		debug_font = new Font("Monospaced", Font.PLAIN, 14);
	protected Color
		debug_background = new Color(0f, 0f, 0f, .8f),
		debug_foreground = new Color(1f, 1f, 1f, .8f);
	
	protected int
		fps,
		tps;
	protected double
		f_ms,
		t_ms;
	
	private Engine() {
		//do nothing
	}
	
	public static void init() {
		if(!INSTANCE.running) {
			INSTANCE.thread = new Thread(INSTANCE);
			INSTANCE.running = true;
			INSTANCE.thread.start();
		}
	}
	
	public static void exit() {
		if( INSTANCE.running) {
			INSTANCE.running = false;
		}
	}
	
	public static void show() {
		INSTANCE.window.onShow();
	}
	
	public static void hide() {
		INSTANCE.window.onHide();
	}
	
	public static void setScene(Scene scene) {
		if(INSTANCE.scene != scene) {
			if(INSTANCE.scene != null)
				INSTANCE.scene.detach();
			INSTANCE.scene = scene;
			if(INSTANCE.scene != null)
				INSTANCE.scene.attach();
		}
	}
	
	public static Config getConfig() {
		return INSTANCE.config;
	}
	
	public static Scene getScene() {
		return INSTANCE.scene;
	}
	
	public static Region2f getCanvasRegion() {
		return INSTANCE.canvas.getRegion();
	}
	
	public static Bounds2f getCanvasBounds() {
		return INSTANCE.canvas.getBounds();
	}
	
	public static Region2f getWindowRegion() {
		return INSTANCE.window.getRegion();
	}
	
	public static Bounds2f getWindowBounds() {
		return INSTANCE.window.getBounds();
	}
	
	public static void toggleMetrics() {
		INSTANCE.debug_metrics = !INSTANCE.debug_metrics;
	}
	
	public static void toggleConsole() {
		
	}
	
	public void pollInputs() {
		Input.INSTANCE.poll();
	}
	public void pollEvents() {
		Event.INSTANCE.poll();
	}
	
	@Override
	public void render(RenderContext context) {		
		if(scene != null) {
			context = context.push();
			scene.render(context);
			context = context.pop();
		}
		
		if(debug && debug_metrics) {
			int
				padding_t = 2,
				padding_l = 2,
				padding_b = 2,
				padding_r = 2;
			context.g2D.setFont(debug_font);
			FontMetrics fm = context.g2D.getFontMetrics();
			String[]
					debug_info = {
						"Debug",
						"[F1] Exit App",
						"[F2] Toggle Metrics",
						"[F3] Toggle Console",
						"",
						"Engine",
						"Async: " + thread_async,
						"  FPS: " + fps + " - " + String.format("%2.3f", f_ms) + " ms",
						"  TPS: " + tps + " - " + String.format("%2.3f", t_ms) + " ms",
						"",
						"Window " + window.getRegion(),
						"   AOT: " + window.window_aot,
						"Border: " + window.window_border,
						"Device: " + window.window_device,
						"Layout: " + window.window_layout,
						"",
						"Canvas " + canvas.getRegion(),
						"GFX AA: " + canvas.canvas_gfx_aa,
						"GFX NB: " + canvas.canvas_gfx_nb
					};
			int 
					debug_info_w = 0,
					debug_info_h = fm.getHeight() * debug_info.length;
			for(int i = 0; i < debug_info.length; i ++)
				debug_info_w = Math.max(debug_info_w, fm.stringWidth(debug_info[i]));
			debug_info_w += padding_l + padding_r;
			debug_info_h += padding_t + padding_b;
			
			context.g2D.setColor(debug_background);			
			context.g2D.fillRect(
						0,
						0,
						debug_info_w,
						debug_info_h
					);		
			context.g2D.setColor(debug_foreground);
			for(int i = 0; i < debug_info.length; i ++)
				context.g2D.drawString(debug_info[i], padding_l, padding_t + fm.getAscent() + fm.getHeight() * i);
		}
	}
	
	@Override
	public void update(UpdateContext context) {
		pollInputs();
		pollEvents();
		if(scene != null) {
			context = context.push();
			scene.update(context);
			context = context.pop();
		}
	}
	
	public void render(double t, double dt, double fixed_dt) {
		canvas.render(this, t, dt, fixed_dt);
	}
	
	public void update(double t, double dt, double fixed_dt) {
		canvas.update(this, t, dt, fixed_dt);
	}
	
	public void onInit() {
		canvas.canvas_background = Vector4f.parseVector4f(config.get(CANVAS_BACKGROUND, canvas.canvas_background));
		canvas.canvas_foreground = Vector4f.parseVector4f(config.get(CANVAS_FOREGROUND, canvas.canvas_foreground));
		canvas.canvas_gfx_aa = config.getBoolean(CANVAS_GFX_AA, canvas.canvas_gfx_aa);
		canvas.canvas_gfx_nb = config.getInteger(CANVAS_GFX_NB, canvas.canvas_gfx_nb);
		
		debug = config.getBoolean(DEBUG, debug);
		
		thread_async = config.getBoolean(THREAD_ASYNC, thread_async);
		thread_fps = config.getInteger(THREAD_FPS, thread_fps);
		thread_tps = config.getInteger(THREAD_TPS, thread_tps);
		
		window.window_aot = config.getBoolean(WINDOW_AOT, window.window_aot);
		window.window_border = config.getBoolean(WINDOW_BORDER, window.window_border);		
		window.window_device = config.getInteger(WINDOW_DEVICE, window.window_device);
		window.window_layout = Layout.parseLayout(config.get(WINDOW_LAYOUT, window.window_layout));		
		window.window_title = config.get(WINDOW_TITLE, window.window_title);
		
		canvas.onInit();
		window.onInit();
		if(scene != null)
			scene.onInit();
	}
	
	public void onExit() {
		if(scene != null)
			scene.onExit();
		window.onExit();
		canvas.onExit();
	}
	
	public void onShow() {
		window.onShow();
		canvas.onShow();
	}
	
	public void onHide() {
		canvas.onHide();
		window.onHide();
	}
	
	public void onMouseMoved(Vector2f mouse) { 
		if(scene != null) scene.onMouseMoved(mouse);
	}	
	public void onWheelMoved(float    wheel) { 
		if(scene != null) scene.onWheelMoved(wheel);
	}	
	public void onKeyDnAction(int key) { 
		if(scene != null) scene.onKeyDnAction(key);
	}	
	public void onKeyUpAction(int key) { 
		if(scene != null) scene.onKeyUpAction(key);
	}
	public void onBtnDnAction(int btn) { 
		if(scene != null) scene.onBtnDnAction(btn);
	}
	public void onBtnUpAction(int btn) { 
		if(scene != null) scene.onBtnUpAction(btn);
	}

	@Override
	public void run() {
		try {
			onInit();	
			update(0, 0, 0);
			render(0, 0, 0);
			long
				f_time = thread_fps > 0 ? ONE_SECOND / thread_fps : 0,
				t_time = thread_tps > 0 ? ONE_SECOND / thread_tps : 0,
				m_time = Math.min(f_time, t_time);
			double
				f_fixed_dt = (double)f_time / ONE_SECOND,
				t_fixed_dt = (double)t_time / ONE_SECOND,
				m_fixed_dt = (double)m_time / ONE_SECOND;
			long
				f_elapsed = 0,
				t_elapsed = 0,
				m_elapsed = 0,
				elapsed = 0,
				f_lag = 0,
				t_lag = 0,
				m_lag = 0,
				f_avg = 0,
				t_avg = 0,
				m_avg = 0,
				f_ct = 0,
				t_ct = 0,
				m_ct = 0,
				t = System.nanoTime();
			
			if(thread_async)
				while(running) {
					long dt = - t + (t = System.nanoTime());
					f_elapsed += dt;
					t_elapsed += dt;
					elapsed += dt;
					
					if(t_elapsed + t_lag >= t_time) {
						update((double)t / ONE_SECOND, (double)t_elapsed / ONE_SECOND, t_fixed_dt);
						t_lag = Util.clamp(t_lag + t_elapsed - t_time, - t_time, t_time);
						t_avg += t_elapsed;
						t_elapsed = 0;
						t_ct ++;
					}
					
					if(f_elapsed + f_lag >= f_time) {
						render((double)t / ONE_SECOND, (double)f_elapsed / ONE_SECOND, f_fixed_dt);
						f_lag = Util.clamp(f_lag + f_elapsed - f_time, - t_time, t_time);
						f_avg += f_elapsed;
						f_elapsed = 0;
						f_ct ++;
					}
					
					if(elapsed >= ONE_SECOND) {
						f_ms = (double)f_avg / f_ct / ONE_MILLIS;
						t_ms = (double)t_avg / t_ct / ONE_MILLIS;
						fps = (int)f_ct;
						tps = (int)t_ct;
						elapsed = 0;
						f_avg = 0;
						t_avg = 0;
						f_ct = 0;
						t_ct = 0;
					}
					
					long sync = Math.min(
							t_time - t_elapsed,
							f_time - f_elapsed
							) / ONE_MILLIS;
					if(sync > 1) Thread.sleep(1);
				}
			else
				while(running) {
					long dt = - t + (t = System.nanoTime());
					m_elapsed += dt;
					elapsed   += dt;
					
					if(m_elapsed + m_lag >= m_time) {
						double
							m_t  = (double) t         / ONE_SECOND,
							m_dt = (double) m_elapsed / ONE_SECOND;
						update(m_t, m_dt, m_fixed_dt);
						render(m_t, m_dt, m_fixed_dt);
						m_lag = Util.clamp(m_lag + m_elapsed - m_time, - m_time, m_time);
						m_avg += m_elapsed;
						m_elapsed = 0;
						m_ct ++;
					}
					
					if(elapsed >= ONE_SECOND) {
						double m_dt = (double)m_avg / m_ct / ONE_MILLIS;
						f_ms = m_dt;
						t_ms = m_dt;						
						fps = (int)m_ct;
						tps = (int)m_ct;						
						elapsed = 0;
						m_avg = 0;
						m_ct = 0;
					}
					
					long sync = (m_time - m_elapsed - m_lag) / ONE_MILLIS;
					if(sync > 1) Thread.sleep(1);
				}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			onExit();
		}
	}
	
	private static class Window {
		protected java.awt.Frame
			component;			
		protected Engine
			engine;	
		protected boolean
			window_aot    = true,
			window_border = true;
		protected int
			window_device = 0;
		protected Layout
			window_layout = Layout.DEFAULT;
		protected String
			window_title = Blue.VERSION.toString();
		
		public Window(Engine engine) {
			this.engine = engine;
		}
		
		public void onInit() {
			if(component != null)
				component.dispose();
			
			component = new java.awt.Frame();
			
			Region2f a, b;
			if(window_border)
				a = Util.getMaximumWindowRegion(window_device);
			else 
				a = Util.getMaximumScreenRegion(window_device);
			b = window_layout.region(a);
			
			component.setBounds(
					(int)b.x(), (int)b.y(),
					(int)b.w(), (int)b.h()
					);
			component.setAlwaysOnTop( window_aot   );
			component.setUndecorated(!window_border);			
			component.setIgnoreRepaint(true);
			component.setTitle(window_title);
			
			component.add(engine.canvas.component);
			component.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					Engine.exit();
				}
			});
			
			component.setVisible(true);			
		}
		
		public void onExit() {
			if(component != null)
				component.dispose();
		}
		
		public void onShow() {
			if(component != null)
				component.setVisible(true);
		}
		
		public void onHide() {
			if(component != null)
				component.setVisible(false);
		}
		
		public Region2f getRegion() {
			Rectangle rectangle = component.getBounds();
			return new Region2f(
					(int)rectangle.getMinX(),
					(int)rectangle.getMinY(),
					(int)(rectangle.getMaxX() - rectangle.getMinX()),
					(int)(rectangle.getMaxY() - rectangle.getMinY())
					);
		}
		
		public Bounds2f getBounds() {
			Rectangle rectangle = component.getBounds();
			return new Bounds2f(
					(int)rectangle.getMinX(),
					(int)rectangle.getMinY(),
					(int)rectangle.getMaxX(),
					(int)rectangle.getMaxY()
					);
		}
	}
	
	private static class Canvas {
		protected java.awt.Canvas
			component;	
		protected Engine
			engine;	
		
		public Vector4f
			canvas_background = new Vector4f(0f, 0f, 0f, 1f),
			canvas_foreground = new Vector4f(1f, 1f, 1f, 1f);
		public boolean
			canvas_gfx_aa = false;
		public int
			canvas_gfx_nb = 2;
		
		protected java.awt.Color
			background,
			foreground;
		
		protected final RenderContext
			render_context = new RenderContext();
		protected final UpdateContext
			update_context = new UpdateContext();
		
		public Canvas(Engine engine) {
			this.engine = engine;
		}
		
		public void onInit() {				
			component = new java.awt.Canvas();
			component.setIgnoreRepaint(true );		
			
			background = Vector.toColor4f(canvas_background);
			foreground = Vector.toColor4f(canvas_foreground);
			
			component.setFocusable(true);
			component.setFocusTraversalKeysEnabled(false);
			component.addKeyListener(        Input.INSTANCE);
			component.addMouseListener(      Input.INSTANCE);
			component.addMouseWheelListener( Input.INSTANCE);
			component.addMouseMotionListener(Input.INSTANCE);	
			
			component.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent ke) {
					if(engine.debug)
						switch(ke.getKeyCode()) {
							case KeyEvent.VK_F1: Engine.exit(); break;
							case KeyEvent.VK_F2: Engine.toggleMetrics(); break;
							case KeyEvent.VK_F3: Engine.toggleConsole(); break;
						}
				}
			});
		}
		
		public void onExit() { }		
		public void onShow() { }
		public void onHide() { }
		
		protected BufferStrategy
			buffer_stratgey;
		public void render(Renderable renderable, double t, double dt, double fixed_dt) {			
			
			if(buffer_stratgey == null || buffer_stratgey.contentsLost()) {
				component.createBufferStrategy(  canvas_gfx_nb);
				buffer_stratgey = component.getBufferStrategy();
			}			

			int
				canvas_w = this.component.getWidth() ,
				canvas_h = this.component.getHeight();
			
			render_context.g2D = (Graphics2D)buffer_stratgey.getDrawGraphics();
			render_context.region.set(
					canvas_w,
					canvas_h
					);
			render_context.t  = t ;
			render_context.dt = dt;
			render_context.fixed_dt = fixed_dt;			
			
			render_context.g2D.setColor(background);
			render_context.g2D.fillRect(
					0, 0,
					canvas_w,
					canvas_h
					);
			render_context.g2D.setColor(foreground);
			if(canvas_gfx_aa)
				render_context.g2D.setRenderingHint(
						RenderingHints.KEY_ANTIALIASING  ,
						RenderingHints.VALUE_ANTIALIAS_ON
						);
			
			renderable.render(render_context);			
			
			render_context.g2D.dispose();
			buffer_stratgey.show();	
		}
		
		public void update(Updateable updateable, double t, double dt, double fixed_dt) {
			int
				canvas_w = component.getWidth() ,
				canvas_h = component.getHeight();		
			
			update_context.region.set(
					canvas_w,
					canvas_h
					);
			update_context.t  = t ;
			update_context.dt = dt;
			update_context.fixed_dt = fixed_dt;
			
			updateable.update(update_context);
		}
		
		public Region2f getRegion() {
			Rectangle rectangle = component.getBounds();
			return new Region2f(
					0, 0,
					(int)(rectangle.getMaxX() - rectangle.getMinX()),
					(int)(rectangle.getMaxY() - rectangle.getMinY())
					);
		}
		
		public Bounds2f getBounds() {
			Rectangle rectangle = component.getBounds();
			return new Bounds2f(
					0, 0,
					(int)rectangle.getMaxX(),
					(int)rectangle.getMaxY()
					);
		}
	}
	
	public static final String
		CANVAS_BACKGROUND = "canvas-background",
		CANVAS_FOREGROUND = "canvas-foreground",
		CANVAS_GFX_AA = "canvas-gfx-aa",
		CANVAS_GFX_NB = "canvas-gfx-nb",
		DEBUG = "debug",
		THREAD_ASYNC = "thread-async",
		THREAD_FPS = "thread-fps",
		THREAD_TPS = "thread-tps",
		WINDOW_AOT = "window-aot",
		WINDOW_BORDER = "window-border",
		WINDOW_DEVICE = "window-device",
		WINDOW_LAYOUT = "window-layout",
		WINDOW_TITLE = "window-title";
}
