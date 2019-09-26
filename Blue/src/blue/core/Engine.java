package blue.core;

import blue.geom.Vector2f;
import blue.util.Config;

public final class Engine implements Runnable {
	protected static final Engine
		INSTANCE = new Engine();
	
	protected Thread
		thread;
	protected boolean
		running;
	
	protected Stage
		stage;
	protected Shell
		shell;
	protected Scene
		scene;
	
	protected Config
		config;
	
	private Engine() {
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
				this.stage.onDetach();
			this.stage = stage;
			if(this.stage != null)
				this.stage.onAttach();
		}
	}
	
	public void onSetShell(Shell shell) {
		if(this.shell != shell) {
			if(this.shell != null)
				this.shell.onDetach();
			this.shell = shell;
			if(this.shell != null)
				this.shell.onAttach();
		}
	}
	
	public void onSetScene(Scene scene) {
		if(this.scene != scene) {
			if(this.scene != null)
				this.scene.onDetach();
			this.scene = scene;
			if(this.scene != null)
				this.scene.onAttach();
		}
	}
	
	public void onInit() {
		
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
			
			while(running) {
				
			}
		} catch(Exception e) {
			
		} finally {
			onExit();
		}
	}
}
