package blue;

import blue.core.Engine;
import blue.core.Event;
import blue.core.Input.BtnAction;
import blue.core.Input.KeyAction;
import blue.core.Scene;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 5);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Engine.getConfig().set(Engine.WINDOW_DEVICE, 0);
		
		Event.attach(KeyAction.class, (event) -> {
			System.out.println("KEY [" + event.key + "] " + event.type);
		});
		Event.attach(BtnAction.class, (event) -> {
			System.out.println("BTN [" + event.btn + "] " + event.type);
		});
		
		Engine.setScene(new Scene() {
			@Override
			public void onInit() {
				System.out.println(Engine.getCanvasRegion());
			}
		});
		
		Engine.init();
	}
}
