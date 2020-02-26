package _blue.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import _blue.Blue;
import blue.util.Configuration;
import blue.util.Util;

public class Engine implements Runnable {
	protected static final Engine
		INSTANCE = new Engine();
	protected float
		engine_fps = 60,
		engine_tps = 0;
	protected int
		window_w = 640,
		window_h = 480;
	protected String
		window_title = Blue.VERSION.toString();
	
	protected Configuration
		cfg = new Configuration(
				WINDOW_W, window_w,
				WINDOW_H, window_h
				);
	
	protected boolean
		running;
	protected Thread
		thread;
	
	protected long
		window;
	protected int
		render_hz,
		update_hz;
	protected float
		render_dt,
		update_dt;
	
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
	
	public void onInit() {
		engine_fps = cfg.getFloat(ENGINE_FPS, engine_fps);
		engine_tps = cfg.getFloat(ENGINE_TPS, engine_tps);
		window_w = cfg.getInteger(WINDOW_W, window_w);
		window_h = cfg.getInteger(WINDOW_H, window_h);
		window_title = cfg.get(WINDOW_TITLE, window_title);
		
		if(!glfwInit()) {
			System.err.println("ERROR [_blue.core.Engine]: Failed to initialize GLFW.");
			return;
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE  , GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		window = glfwCreateWindow(window_w, window_h, window_title, NULL, NULL);
		if(window == NULL) {
			System.err.println("ERROR [_blue.core.Engine]: Failed to create window.");
			return;
		}
		
		try(MemoryStack stack = stackPush()) {
			IntBuffer 
				w = stack.mallocInt(1),
				h = stack.mallocInt(1);
			glfwGetWindowSize(window, w, h);
			
			GLFWVidMode vm = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(
					window,
					(vm.width()  - w.get(0)) / 2,
					(vm.height() - h.get(0)) / 2
					);
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		
		glfwShowWindow(window);
		
		GL.createCapabilities();
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public void onExit() {
		
	}
	
	public void onRender(float t, float dt, float fixed_dt) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(window);
	}
	
	public void onUpdate(float t, float dt, float fixed_dt) {
		glfwPollEvents();
	}
	
	private static final long
		ONE_SECOND = 1000000000,
		ONE_MILLIS =    1000000;
	
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
					
					System.out.printf("FPS: %1$d hz @ %2$.2f ms%n", render_hz, render_dt);
					System.out.printf("TPS: %1$d hz @ %2$.2f ms%n", update_hz, update_dt);
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
		ENGINE_FPS = "engine-fps",
		ENGINE_TPS = "engine-tps",
		WINDOW_W = "window-w",
		WINDOW_H = "window-h",
		WINDOW_TITLE = "window-title";
}
