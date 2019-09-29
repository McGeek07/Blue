package blue.core;

import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import blue.Blue;
import blue.core.render.RenderContext;
import blue.core.update.UpdateContext;
import blue.geom.Layout;
import blue.geom.Region2f;
import blue.geom.Vector;
import blue.geom.Vector2f;
import blue.geom.Vector4f;
import blue.util.Config;
import blue.util.Util;

public final class Engine implements Runnable {	
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
		thread_async = true;
	protected int
		thread_fps = 60,
		thread_tps = 60;
	
	protected final Config
		config = new Config(
				BUFFER_BACKGROUND, canvas.buffer_background,
				CANVAS_BACKGROUND, canvas.canvas_background,
				CANVAS_W, canvas.canvas_w,
				CANVAS_H, canvas.canvas_h,
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
	protected int
		fps,
		tps;
	protected double
		f_dt,
		t_dt;
	
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
		
	}
	
	public static void hide() {
		
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
	
	public static Scene getScene() {
		return INSTANCE.scene;
	}
	
	public static Config getConfig() {
		return INSTANCE.config;
	}
	
	public void pollInputs() {
		Input.INSTANCE.poll();
	}
	public void pollEvents() {
		Event.INSTANCE.poll();
	}	
	
	public void update(double t, double dt, double fixed_dt) {
		pollInputs();
		pollEvents();
		canvas.update(t, dt, fixed_dt);
	}
	
	public void render(double t, double dt, double fixed_dt) {
		canvas.render(t, dt, fixed_dt);
	}
	
	public void onInit() {
		canvas.buffer_background = Vector4f.parseVector4f(config.get(BUFFER_BACKGROUND, canvas.buffer_background));
		canvas.canvas_background = Vector4f.parseVector4f(config.get(CANVAS_BACKGROUND, canvas.canvas_background));
		canvas.canvas_w = config.getInteger(CANVAS_W, canvas.canvas_w);
		canvas.canvas_h = config.getInteger(CANVAS_H, canvas.canvas_h);
		
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
		window.onExit();
		canvas.onExit();
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
						t_lag = Math.max(t_elapsed - t_time, 0);
						t_avg += t_elapsed;
						t_elapsed = 0;
						t_ct ++;
					}
					
					if(f_elapsed + f_lag >= f_time) {
						render((double)t / ONE_SECOND, (double)f_elapsed / ONE_SECOND, f_fixed_dt);
						f_lag = Math.max(f_elapsed - f_time, 0);
						f_avg += f_elapsed;
						f_elapsed = 0;
						f_ct ++;
					}
					
					if(elapsed >= ONE_SECOND) {
						f_dt = (double)f_avg / f_ct / ONE_MILLIS;
						t_dt = (double)t_avg / t_ct / ONE_MILLIS;
						fps = (int)f_ct;
						tps = (int)t_ct;
						elapsed = 0;
						f_avg = 0;
						t_avg = 0;
						f_ct = 0;
						t_ct = 0;						

						System.out.println("FPS: " + fps + " - " + String.format("%2.3f", f_dt) + " ms");
						System.out.println("TPS: " + tps + " - " + String.format("%2.3f", t_dt) + " ms");
					}
					
					long sync = Math.min(
							t_time - t_elapsed - t_lag,
							f_time - f_elapsed - f_lag
							) / ONE_MILLIS;
					if(sync > 0) Thread.sleep(1);
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
						m_lag = Math.max(m_elapsed - m_time, 0);
						m_avg += m_elapsed;
						m_elapsed = 0;
						m_ct ++;
					}
					
					if(elapsed >= ONE_SECOND) {
						double m_dt = (double)m_avg / m_ct / ONE_MILLIS;
						f_dt = m_dt;
						t_dt = m_dt;						
						fps = (int)m_ct;
						tps = (int)m_ct;						
						elapsed = 0;
						m_avg = 0;
						m_ct = 0;
						
						System.out.println("FPS: " + fps + " - " + String.format("%2.3f", f_dt) + " ms");
						System.out.println("TPS: " + tps + " - " + String.format("%2.3f", t_dt) + " ms");
					}
					
					long sync = (m_time - m_elapsed - m_lag) / ONE_MILLIS;
					if(sync > 0) Thread.sleep(1);
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
	}
	
	private static class Canvas {
		protected java.awt.Canvas
			component;	
		protected Engine
			engine;	
		
		public Vector4f
			buffer_background = new Vector4f(0f, 0f, 0f, 1f),
			canvas_background = new Vector4f(0f, 0f, 0f, 1f);			
		public int
			canvas_w,
			canvas_h;
		
		protected java.awt.Color
			buffer_color,
			canvas_color;
		protected BufferedImage
			canvas;
		protected Graphics2D
			buffer_gfx,
			canvas_gfx;
		
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
			
			buffer_color = Vector.toColor4f(buffer_background);
			canvas_color = Vector.toColor4f(canvas_background);
			
			if(canvas_w > 0 && canvas_h > 0) {
				canvas = new BufferedImage(
						canvas_w,
						canvas_h,
						BufferedImage.TYPE_INT_ARGB
						);
			} else
				canvas = null;
			
			component.addKeyListener(Input.INSTANCE);
			component.addMouseListener(Input.INSTANCE);
			component.addMouseWheelListener(Input.INSTANCE);
			component.addMouseMotionListener(Input.INSTANCE);			
		}
		
		public void onExit() {
			
		}	
		
		protected BufferStrategy
			buffer;
		public void render(double t, double dt, double fixed_dt) {
			int
				buffer_w = this.component.getWidth() ,
				buffer_h = this.component.getHeight();	
			
			render_context.t  = t ;
			render_context.dt = dt;
			render_context.fixed_dt = fixed_dt;
			
			if(buffer == null || buffer.contentsLost()) {
				component.createBufferStrategy(2);
				buffer = component.getBufferStrategy();
			}
			
			if(canvas != null) {
				render_context.region.set(
						canvas_w,
						canvas_h
						);				
				buffer_gfx = (Graphics2D)buffer.getDrawGraphics();
				canvas_gfx = (Graphics2D)canvas.createGraphics() ;				
				render_context.g2D = canvas_gfx;
				
				canvas_gfx.setColor(canvas_color);
				canvas_gfx.fillRect(
						0, 0,
						canvas_w,
						canvas_h
						);
				
				if(engine.scene != null) engine.scene.render(render_context);	
				
				buffer_gfx.setColor(buffer_color);
				buffer_gfx.fillRect(
						0, 0,
						buffer_w,
						buffer_h
						);
				float scale = Math.min(
						buffer_w,
						buffer_h
						);				
				this.buffer_gfx.translate(
						buffer_w / 2,
						buffer_h / 2
						);
				this.buffer_gfx.scale(
						scale,
						scale
						);
				this.buffer_gfx.drawImage(
						this.canvas,
						null,
						- canvas_w / 2,
						- canvas_h / 2
						);

				canvas_gfx.dispose();
				buffer_gfx.dispose();
				buffer.show();
			} else {
				render_context.region.set(
						buffer_w,
						buffer_h
						);
				buffer_gfx = (Graphics2D)buffer.getDrawGraphics();
				render_context.g2D = buffer_gfx;
				
				buffer_gfx.setColor(buffer_color);
				buffer_gfx.fillRect(
						0, 0,
						buffer_w,
						buffer_h
						);
				
				if(engine.scene != null) engine.scene.render(render_context);
				
				buffer_gfx.dispose();
				buffer.show();
			}		
		}
		
		public void update(double t, double dt, double fixed_dt) {
			int
				buffer_w = component.getWidth() ,
				buffer_h = component.getHeight();
			
			update_context.t  = t ;
			update_context.dt = dt;
			update_context.fixed_dt = fixed_dt;
			
			if(canvas != null) {
				update_context.region.set(
						canvas_w,
						canvas_h
						);
				if(engine.scene != null) engine.scene.update(update_context);
			} else {
				render_context.region.set(
						buffer_w,
						buffer_h
						);
				if(engine.scene != null) engine.scene.update(update_context);
			}			
		}
	}
	
	public static final String
		BUFFER_BACKGROUND = "buffer-background",
		CANVAS_BACKGROUND = "canvas-background",
		CANVAS_W = "canvas-w",
		CANVAS_H = "canvas-h",
		THREAD_ASYNC = "thread-async",
		THREAD_FPS = "thread-fps",
		THREAD_TPS = "thread-tps",
		WINDOW_AOT = "window-aot",
		WINDOW_BORDER = "window-border",
		WINDOW_DEVICE = "window-device",
		WINDOW_LAYOUT = "window-layout",
		WINDOW_TITLE = "window-title";
}
