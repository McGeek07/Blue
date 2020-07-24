package blue;

import blue.core.Engine;
import blue.core.Module.Metrics;
import blue.core.Stage;
import blue.util.Debug;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 2, 15);
	
	public static void main(String[] args) {		
		Debug.info(new Object() { /* trace */ }, VERSION);
		
		Stage.setProperty(Stage.DEBUG, Metrics.STAGE_METRICS);
		
		Engine.init();
	}
}