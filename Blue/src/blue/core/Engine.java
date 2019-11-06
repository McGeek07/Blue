package blue.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import blue.core.Renderable.RenderContext;
import blue.core.Updateable.UpdateContext;
import blue.geom.Bounds2f;
import blue.geom.Layout;
import blue.geom.Region2f;
import blue.geom.Vector;
import blue.geom.Vector2f;
import blue.geom.Vector4f;
import blue.util.Config;
import blue.util.Util;

public class Engine {
	protected static final Engine
		INSTANCE = new Engine();
	public static final String
		CANVAS_BACKGROUND = "canvas-background",
		CANVAS_FOREGROUND = "canvas-foreground",
		CANVAS_LAYOUT = "canvas-layout",
		THREAD_FPS = "thread-fps",
		THREAD_TPS = "thread-tps",
		WINDOW_BORDER = "window-border",
		WINDOW_DEVICE = "window-device",
		WINDOW_LAYOUT = "window-layout",
		WINDOW_TITLE  = "window-title" ;
	
	protected static int
		thread_fps = 60,
		thread_tps = 60;
	
	protected static final Config
		CONFIG = new Config(
				CANVAS_BACKGROUND, Canvas.canvas_background,
				CANVAS_FOREGROUND, Canvas.canvas_foreground,
				CANVAS_LAYOUT, Canvas.canvas_layout,
				WINDOW_BORDER, Window.window_border,
				WINDOW_DEVICE, Window.window_device,
				WINDOW_LAYOUT, Window.window_layout
				);

	protected static double
		render_dt_metric,
		update_dt_metric;	
	protected static int
		render_hz_metric,
		update_hz_metric;
	
	protected static boolean
		running;
	protected static Thread
		thread;
	
	protected static Scene
		scene;
	
	private Engine() {
		//do nothing
	}	
	
	public static synchronized void init() {
		if(!running) {
			thread = new Thread(loop());
			running = true;
			thread.start();
		} else
			Engine.onInit();
	}
	
	public static synchronized void exit() {
		if( running) {
			running = false;
		} else
			Engine.onExit();
	}
	
	public static void setScene(Scene scene) {
		if(Engine.scene != null)
			Engine.scene.detach();
		Engine.scene = scene;
		if(Engine.scene != null)
			Engine.scene.attach();
	}
	
	public static Config getConfig() {
		return CONFIG;
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	protected static void onInit() {		
		thread_fps = CONFIG.getInt(THREAD_FPS, thread_fps);
		thread_tps = CONFIG.getInt(THREAD_TPS, thread_tps);

		Window.onInit();	
		Canvas.onInit();
		if(scene != null)
			scene.attach();
	}
	
	protected static void onExit() {
		if(scene != null)
			scene.detach();
		Canvas.onExit();
		Window.onExit();
	}
	
	protected static void onWheelMoved(float    wheel) {
		if(scene != null) scene.onWheelMoved(wheel);
	}
	
	protected static void onMouseMoved(Vector2f mouse) {
		if(scene != null) scene.onMouseMoved(mouse);
	}
	
	protected static void onKeyDn(int key) {
		if(scene != null) scene.onKeyDn(key);
	}
	
	protected static void onKeyUp(int key) {
		if(scene != null) scene.onKeyUp(key);
	}
	
	protected static void onBtnDn(int btn) {
		if(scene != null) scene.onBtnDn(btn);
	}
	
	protected static void onBtnUp(int btn) {
		if(scene != null) scene.onBtnUp(btn);
	}
	
	protected static Vector2f windowToCanvas(Vector2f v) {
		return windowToCanvas(v.x(), v.y());
	}
	
	protected static Vector2f windowToCanvas(float x, float y) {
		x = (x - Canvas.background_w / 2) / Canvas.canvas_scale + Canvas.foreground_w / 2;
		y = (y - Canvas.background_h / 2) / Canvas.canvas_scale + Canvas.foreground_h / 2;
		return new Vector2f(x, y);
	}
	
	protected static Vector2f canvasToWindow(Vector2f v) {
		return canvasToWindow(v.x(), v.y());
	}
	
	protected static Vector2f canvasToWindow(float x, float y) {
		x = (x - Canvas.foreground_w / 2) * Canvas.canvas_scale + Canvas.background_w / 2;
		y = (y - Canvas.foreground_h / 2) * Canvas.canvas_scale + Canvas.background_h / 2;
		return new Vector2f(x, y);
	}
	
	private static final RenderContext
		render_context0 = new RenderContext(),
		render_context1 = new RenderContext();
	private static BufferStrategy
		b;
	private static void onRender(long frame, double t, double dt, double fixed_dt) {		 
		Canvas.background_w = (int)Canvas.background_canvas.getWidth() ;
		Canvas.background_h = (int)Canvas.background_canvas.getHeight();
		
		Canvas.canvas_scale = Math.min(
			(float)Canvas.background_w / Canvas.foreground_w,
			(float)Canvas.background_h / Canvas.foreground_h
			);
		
		render_context0.frame = frame;
		render_context0.t  = t ;
		render_context0.dt = dt;
		render_context0.fixed_dt = fixed_dt;
		render_context0.bounds.set(
				0, 0,
				Canvas.background_w,
				Canvas.background_h
				);
		
		render_context1.frame = frame;
		render_context1.t  = t ;
		render_context1.dt = dt;
		render_context1.fixed_dt = fixed_dt;
		render_context1.bounds.set(
				0, 0,
				Canvas.foreground_w,
				Canvas.foreground_h
				);
		
		if(b == null || b.contentsLost()) {
			Canvas.background_canvas.createBufferStrategy(2);
			b = Canvas.background_canvas.getBufferStrategy();
		}
		 
		render_context0.g2D = (Graphics2D)b.getDrawGraphics();
		render_context1.g2D = (Graphics2D)Canvas.foreground_canvas.createGraphics();
		 
		render_context0.g2D.setColor(Canvas.background_color);
		render_context0.g2D.fillRect(
				0, 0,
				Canvas.background_w,
				Canvas.background_h
				);
		render_context1.g2D.setColor(Canvas.foreground_color);
		render_context1.g2D.fillRect(
				0, 0,
				Canvas.foreground_w,
				Canvas.foreground_h
				);
		 
		RenderContext context = render_context1.push();
		if(scene != null) scene.render(context);
		context = context.pop();
		 
		context = render_context0.push();		
		context.g2D.translate(
				Canvas.background_w / 2,
				Canvas.background_h / 2
				);
	 	context.g2D.scale(
				Canvas.canvas_scale,
				Canvas.canvas_scale
				);
		context.g2D.drawImage(
				Canvas.foreground_canvas,
				- Canvas.foreground_w / 2,
				- Canvas.foreground_h / 2,
				null
				);
		context = context.pop();
		
		render_context0.g2D.dispose();
		render_context1.g2D.dispose();
		b.show();
	}
	
	private static final UpdateContext
		update_context = new UpdateContext();
	private static void onUpdate(long frame, double t, double dt, double fixed_dt) {
		update_context.frame = frame;
		update_context.t  = t ;
		update_context.dt = dt;
		update_context.fixed_dt = fixed_dt;
		update_context.bounds.set(
				0, 0,
				Canvas.foreground_w,
				Canvas.foreground_h
				);
		
		Input.INSTANCE.poll();
		Event.INSTANCE.poll();
		
		UpdateContext context = update_context.push();
		if(scene != null) scene.update(context);
		context = context.pop();
	}
	
	private static final long
		ONE_SECOND = 1000000000L, //one second in nanoseconds
		ONE_MILLIS =    1000000L; //one millis in nanoseconds
	private static Runnable loop() {
		return () -> {
			try {
				onInit();
				long
					render_time = thread_fps > 0 ? ONE_SECOND / thread_fps : 0,
					update_time = thread_tps > 0 ? ONE_SECOND / thread_tps : 0;
				double
					render_fixed_dt = (double)render_time / ONE_SECOND,
					update_fixed_dt = (double)update_time / ONE_SECOND;
				long
					render_elapsed = 0,
					update_elapsed = 0,
					render_frame = 0,
					update_frame = 0,
					render_lag = 0,
					update_lag = 0,
					render_avg = 0,
					update_avg = 0,
					render_ct = 0,
					update_ct = 0,
					elapsed = 0,
					t = System.nanoTime();			
				while(running) {
					long dt = - t + (t = System.nanoTime());
					render_elapsed += dt;
					update_elapsed += dt;
					elapsed += dt;
					
					if(update_elapsed + update_lag >= update_time) {
						onUpdate(++ update_frame, (double)t / ONE_SECOND, (double)update_elapsed / ONE_SECOND, update_fixed_dt);						
						update_lag = Util.clamp(update_lag + update_elapsed - update_time, - update_time, update_time);
						update_avg += update_elapsed;
						update_elapsed = 0;
						update_ct ++;
					}
					
					if(render_elapsed + render_lag >= render_time) {
						onRender(++ render_frame, (double)t / ONE_SECOND, (double)render_elapsed / ONE_SECOND, render_fixed_dt);						
						render_lag = Util.clamp(render_lag + render_elapsed - render_time, - render_time, render_time);
						render_avg += render_elapsed;
						render_elapsed = 0;
						render_ct ++;
					}
					
					if(elapsed >= ONE_SECOND) {
						render_dt_metric = (double)render_avg / render_ct / ONE_MILLIS;
						update_dt_metric = (double)update_avg / update_ct / ONE_MILLIS;
						render_hz_metric = (int)render_ct;
						update_hz_metric = (int)update_ct;
						render_avg = 0;
						update_avg = 0;
						render_ct = 0;
						update_ct = 0;
						elapsed = 0;
					}
					
					long sync = Math.min(
						render_time - render_elapsed - render_lag,
						update_time - update_elapsed - update_lag
						) / ONE_MILLIS - 1;
					if(sync > 0) Thread.sleep(1);
				}
			} catch(Exception ex) {
				System.err.println("A fatal exception has occurred");
				ex.printStackTrace();
			} finally {
				onExit();
			}
		};
	}
	
	protected static class Canvas {
		protected static Vector4f
			canvas_background = new Vector4f(0f, 0f, 0f, 1f),
			canvas_foreground = new Vector4f(1f, 1f, 1f, 1f);
		protected static Layout
			canvas_layout = Layout.DEFAULT;
		
		protected static java.awt.Canvas
			background_canvas;
		protected static BufferedImage
			foreground_canvas;
		
		protected static java.awt.Color
			background_color,
			foreground_color;
		
		protected static int
			background_w,
			background_h,
			foreground_w,
			foreground_h;
		protected static float
			canvas_scale;
		
		protected static final Region2f.Mutable
			init_region = new Region2f.Mutable();
		protected static final Region2f.Mutable
			init_bounds = new Region2f.Mutable();		
		
		protected static void onInit() {
			canvas_background = Vector4f.parseVector4f(CONFIG.get(CANVAS_BACKGROUND, canvas_background));
			canvas_foreground = Vector4f.parseVector4f(CONFIG.get(CANVAS_FOREGROUND, canvas_foreground));
			canvas_layout = Layout.parseLayout(CONFIG.get(CANVAS_LAYOUT, canvas_layout));
			
			background_color = Vector.toColor4f(canvas_background);
			foreground_color = Vector.toColor4f(canvas_foreground);
			
			Region2f a = canvas_layout.region(Window.init_region);
			
			init_region.set(0, 0, a.w(), a.h());
			init_bounds.set(0, 0, a.w(), a.h());
			
			foreground_w = (int)a.w();
			foreground_h = (int)a.h();
			
			background_canvas = new java.awt.Canvas() {
				private static final long 
					serialVersionUID = 1L;
				@Override
				public void update(Graphics g) {
					if(isShowing()) paint(g);
				}
			};
			foreground_canvas = new BufferedImage(
					foreground_w,
					foreground_h,
					BufferedImage.TYPE_INT_ARGB
					);
			background_canvas.setFocusable(true);
			background_canvas.setIgnoreRepaint(true);
			background_canvas.setFocusTraversalKeysEnabled(false);
			
			background_canvas.addKeyListener(        Input.INSTANCE);
			background_canvas.addMouseListener(      Input.INSTANCE);
			background_canvas.addMouseWheelListener( Input.INSTANCE);
			background_canvas.addMouseMotionListener(Input.INSTANCE);		
			
			Window.window.add(background_canvas);
			Window.window.revalidate();
			Engine.b = null;
			
			background_canvas.requestFocus();
		}
		
		protected static void onExit() {
			
		}
	}
	
	protected static class Window {
		protected static boolean
			window_border = true;
		protected static int
			window_device = 0;
		protected static Layout
			window_layout = Layout.DEFAULT;
		protected static String
			window_title;
		
		protected static java.awt.Frame
			window;
		
		protected static final Region2f.Mutable
			init_region = new Region2f.Mutable();
		protected static final Bounds2f.Mutable
			init_bounds = new Bounds2f.Mutable();
		
		protected static void onInit() {
			window_border = CONFIG.getBoolean(WINDOW_BORDER, window_border);
			window_device = CONFIG.getInt    (WINDOW_DEVICE, window_device);
			window_layout = Layout.parseLayout(CONFIG.get(WINDOW_LAYOUT, window_layout));		
			window_title = CONFIG.get(WINDOW_TITLE, window_title);
			
			if(window != null)
				window.dispose();
			
			window = new java.awt.Frame() {
				private static final long 
					serialVersionUID = 1L;
				@Override
				public void update(Graphics g) {
					if(isShowing()) paint(g);
				}
			};
			
			Region2f a, b;
			if(window_border)
				a = Util.getMaximumWindowRegion(window_device);
			else 
				a = Util.getMaximumScreenRegion(window_device);
			b = window_layout.region(a);
			
			init_region.set(0, 0, b.w(), b.h());
			init_bounds.set(0, 0, b.w(), b.h());
			
			window.setBounds(
					(int)b.x(), (int)b.y(),
					(int)b.w(), (int)b.h()
					);
			window.setUndecorated(!window_border);	
			window.setTitle(window_title);		
			window.setIgnoreRepaint(true);
			
			window.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					Engine.exit();
				}
			});
			
			window.setVisible(true);
		}
		
		protected static void onExit() {
			if(window != null)
				window.dispose();
		}
	}	
}
