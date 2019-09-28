package blue.core;

import blue.Blue;
import blue.core.render.Renderable;
import blue.core.update.Updateable;
import blue.geom.Layout;
import blue.geom.Region2f;
import blue.util.Config;
import blue.util.Util;

public class Stage {	
	protected final Canvas
		canvas = new Canvas(this);
	protected final Window
		window = new Window(this);
	

	protected final Config
		config = new Config(
				WINDOW_AOT, window.aot,
				WINDOW_BORDER, window.border,
				WINDOW_DEVICE, window.device,
				WINDOW_LAYOUT, window.layout
				);
	
	public Config getConfig() {
		return config;
	}
	
	public void render(Renderable renderable) {
		
	}
	
	public void update(Updateable updateable) {
		
	}
	
	public void init() {		
		window.border = config.getBoolean(WINDOW_BORDER, window.border);		
		window.device = config.getInteger(WINDOW_DEVICE, window.device);
		window.layout = Layout.parseLayout(config.get(WINDOW_LAYOUT, window.layout));		
		window.title = config.get(WINDOW_TITLE, window.title);
		
		onInit();
		canvas.onInit();
		window.onInit();
	}
	
	public void exit() {
		window.onExit();
		canvas.onExit();	
		onExit();
	}
	
	public void attach() {
		onAttach();
		window.onAttach();
		canvas.onAttach();
	}
	
	public void detach() {
		canvas.onDetach();
		window.onDetach();
		onDetach();
	}
	
	public void onInit() { }	
	public void onExit() { }	
	public void onAttach() { }	
	public void onDetach() { }
	
	public static class Window {
		protected java.awt.Frame
			component;	
		protected boolean
			enabled;		
		protected Stage
			stage;	
		protected boolean
			aot    = true,
			border = false;
		protected int
			device = 0;
		protected Layout
			layout = Layout.DEFAULT;
		protected String
			title = Blue.VERSION.toString();
		
		public Window(Stage stage) {
			this.stage = stage;
		}
		
		public void onInit() {
			if(component != null)
				component.dispose();
			
			component = new java.awt.Frame();	
			Region2f a, b;
			if(border)
				a = Util.getMaximumWindowRegion(device);
			else 
				a = Util.getMaximumScreenRegion(device);
			b = layout.region(a);
			
			System.out.println(a);
			System.out.println(b);
			
			component.setBounds(
					(int)b.x(), (int)b.y(),
					(int)b.w(), (int)b.h()
					);
			component.setAlwaysOnTop( aot   );
			component.setUndecorated(!border);
			component.setTitle(title);
			
			component.setVisible(true);			
		}
		
		public void onExit() {
			
		}
		
		public void onAttach() {
			enabled = true;
		}
		
		public void onDetach() {
			enabled = false;
		}
	}
	
	public static class Canvas {
		protected java.awt.Canvas
			component;		
		protected boolean
			enabled;
		protected Stage
			stage;
		
		
		
		public Canvas(Stage stage) {
			this.stage = stage;
		}
		
		public void onInit() {
			if(enabled && component != null) {
				component.removeKeyListener(Input.INSTANCE);
				component.removeMouseListener(Input.INSTANCE);
				component.removeMouseWheelListener(Input.INSTANCE);
				component.removeMouseMotionListener(Input.INSTANCE);
			}
			
			component = new java.awt.Canvas();
			
			if(enabled && component != null) {
				component.addKeyListener(Input.INSTANCE);
				component.addMouseListener(Input.INSTANCE);
				component.addMouseWheelListener(Input.INSTANCE);
				component.addMouseMotionListener(Input.INSTANCE);
			}
		}
		
		public void onExit() {
		}
		
		public void onAttach() {
			enabled = true;
			if(component != null) {
				component.addKeyListener(Input.INSTANCE);
				component.addMouseListener(Input.INSTANCE);
				component.addMouseWheelListener(Input.INSTANCE);
				component.addMouseMotionListener(Input.INSTANCE);
			}
		}
		
		public void onDetach() {
			enabled = false;
			if(component != null) {
				component.removeKeyListener(Input.INSTANCE);
				component.removeMouseListener(Input.INSTANCE);
				component.removeMouseWheelListener(Input.INSTANCE);
				component.removeMouseMotionListener(Input.INSTANCE);
			}
		}
	}
	
	public static final String
		WINDOW_AOT = "window-aot",
		WINDOW_BORDER = "window-border",
		WINDOW_DEVICE = "window-device",
		WINDOW_LAYOUT = "window-layout",
		WINDOW_TITLE = "window-title";
}
