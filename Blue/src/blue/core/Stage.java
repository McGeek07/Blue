package blue.core;

import blue.core.render.Renderable;
import blue.core.update.Updateable;
import blue.util.Config;

public class Stage {
	protected final Canvas
		canvas = new Canvas(this);
	protected final Window
		window = new Window(this);
	protected final Config
		config = new Config();
	
	public Config getConfig() {
		return config;
	}
	
	public void render(Renderable renderable) {
		
	}
	
	public void update(Updateable updateable) {
		
	}
	
	public void onInit() { 
		window.onInit();
		canvas.onInit();
	}
	
	public void onExit() { 
		canvas.onExit();
		window.onExit();		
	}
	
	public void onAttach() {
		window.onAttach();
		canvas.onAttach();
	}
	
	public void onDetach() {
		canvas.onDetach();
		window.onDetach();
	}
	
	public static class Window {
		protected final java.awt.Frame
			component = new java.awt.Frame();	
		protected Stage
			stage;
		
		public Window(Stage stage) {
			this.stage = stage;
			
			this.component.add(this.stage.canvas.component);
		}
		
		public void onInit() {
			
		}
		
		public void onExit() {
			
		}
		
		public void onAttach() {
			
		}
		
		public void onDetach() {
			
		}
	}
	
	public static class Canvas {
		protected final java.awt.Canvas
			component = new java.awt.Canvas();
		protected Stage
			stage;
		
		public Canvas(Stage stage) {
			this.stage = stage;
		}
		
		public void onInit() {
			
		}
		
		public void onExit() {
			
		}
		
		public void onAttach() {
			component.addKeyListener(Input.INSTANCE);
			component.addMouseListener(Input.INSTANCE);
			component.addMouseWheelListener(Input.INSTANCE);
			component.addMouseMotionListener(Input.INSTANCE);
		}
		
		public void onDetach() {
			component.removeKeyListener(Input.INSTANCE);
			component.removeMouseListener(Input.INSTANCE);
			component.removeMouseWheelListener(Input.INSTANCE);
			component.removeMouseMotionListener(Input.INSTANCE);
		}
	}
}
