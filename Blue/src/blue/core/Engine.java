package blue.core;

import blue.core.event.Event;
import blue.core.event.Event.Broker;
import blue.core.event.Event.Dispatcher;
import blue.core.event.Event.Handle;
import blue.core.event.Event.Listener;
import blue.geom.Vector2f;
import blue.util.Config;

public final class Engine implements Runnable {	
	protected static final long
		ONE_SECOND = 1000000000,
		ONE_MILLIS =    1000000;
	protected static final Engine
		INSTANCE = new Engine();
	
	protected final Broker
		broker;
	protected final Handle
		handle;
	protected final Dispatcher
		dispatcher;
	
	protected Stage
		stage;
	protected Shell
		shell;
	protected Scene
		scene;

	protected boolean
		thread_async = true;
	protected int
		thread_fps = 60,
		thread_tps = 60;
	
	protected final Config
		config = new Config(
				THREAD_ASYNC, thread_async,
				THREAD_FPS, thread_fps,
				THREAD_TPS, thread_tps
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
		this.broker = new Broker();
		this.handle = new Handle();
		this.dispatcher = new Dispatcher(
				this.broker,
				this.handle
				);
		this.onSetStage(new Stage());
		this.onSetShell(new Shell());
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
	
	public static <T extends Event> boolean addListener(Class<T> type, Listener<T> listener) {
		return INSTANCE.handle.add(type, listener);
	}
	
	public static <T extends Event> boolean delListener(Class<T> type, Listener<T> listener) {
		return INSTANCE.handle.del(type, listener);
	}
	
	public static <T extends Event> void attachListener(Class<T> type, Listener<T> listener) {
		INSTANCE.handle.attach(type, listener);
	}
	
	public static <T extends Event> void detachListener(Class<T> type, Listener<T> listener) {
		INSTANCE.handle.detach(type, listener);
	}
	
	public static boolean addHandle(Handle handle) {
		return INSTANCE.broker.add(handle);
	}
	
	public static boolean delHandle(Handle handle) {
		return INSTANCE.broker.del(handle);
	}
	
	public static void attachHandle(Handle handle) {
		INSTANCE.broker.attach(handle);
	}
	
	public static void detachHandle(Handle handle) {
		INSTANCE.broker.detach(handle);
	}
	
	public static boolean addBroker(Broker broker) {
		return INSTANCE.broker.add(broker);
	}
	
	public static boolean delBroker(Broker broker) {
		return INSTANCE.broker.del(broker);
	}
	
	public static void attachBroker(Broker broker) {
		INSTANCE.broker.attach(broker);
	}
	
	public static void detachBroker(Broker broker) {
		INSTANCE.broker.detach(broker);
	}
	
	public static <T extends Event> void queue(T event) {
		INSTANCE.dispatcher.queue(event);
	}
	
	public static <T extends Event> void flush(T event) {
		INSTANCE.dispatcher.flush(event);
	}
	
	public static void setStage(Stage stage) {
		INSTANCE.onSetStage(stage);
	}
	
	public static void setShell(Shell shell) {
		INSTANCE.onSetShell(shell);
	}
	
	public static void setScene(Scene scene) {
		INSTANCE.onSetScene(scene);
	}
	
	public static Stage getStage() {
		return INSTANCE.stage;
	}
	
	public static Shell getShell() {
		return INSTANCE.shell;
	}
	
	public static Scene getScene() {
		return INSTANCE.scene;
	}
	
	public static Config getConfig() {
		return INSTANCE.config;
	}
	
	public void onSetStage(Stage stage) {
		if(this.stage != stage) {
			if(this.stage != null)
				this.stage.detach();
			this.stage = stage;
			if(this.stage != null)
				this.stage.attach();
		}
	}
	
	public void onSetShell(Shell shell) {
		if(this.shell != shell) {
			if(this.shell != null)
				this.shell.detach();
			this.shell = shell;
			if(this.shell != null)
				this.shell.attach();
		}
	}
	
	public void onSetScene(Scene scene) {
		if(this.scene != scene) {
			if(this.scene != null)
				this.scene.detach();
			this.scene = scene;
			if(this.scene != null)
				this.scene.attach();
		}
	}
	
	public void pollInputs() {
		Input.poll();
	}
	public void pollEvents() {
		broker.flushPending();
	}	
	
	public void update(double t, double dt, double fixed_dt) {
		pollInputs();
		pollEvents();
	}
	
	public void render(double t, double dt, double fixed_dt) {
		
	}
	
	public void onInit() {
		thread_async = config.getBoolean(THREAD_ASYNC, thread_async);
		thread_fps = config.getInteger(THREAD_FPS, thread_fps);
		thread_tps = config.getInteger(THREAD_TPS, thread_tps);
		
		if(stage != null) stage.init();
		if(shell != null) shell.init();
		if(scene != null) scene.init();		
	}
	
	public void onExit() {
		
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
	
	public static final String
		THREAD_ASYNC = "thread-async",
		THREAD_FPS = "thread-fps",
		THREAD_TPS = "thread-tps";
}
