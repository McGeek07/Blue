package blue.util;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class GraphicsUtility {
	
	private GraphicsUtility() {
		//hide constructor
	}
	
	public static GraphicsDevice getGraphicsDevice(int i) {
		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		if(i >= 0 && i < gd.length)
			return gd[i];
		else if (gd.length > 0)
			return gd[0];
		else
			return null;
	}	
	
	public static BufferedImage createBufferedImage(int i, int w, int h) {
		return createBufferedImage(i, w, h, Transparency.TRANSLUCENT);
	}
	
	public static VolatileImage createVolatileImage(int i, int w, int h) {
		return createVolatileImage(i, w, h, Transparency.TRANSLUCENT);
	}
	
	public static BufferedImage createBufferedImage(int i, int w, int h, int transparency) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		return gc.createCompatibleImage(w, h, transparency);
	}
	
	public static VolatileImage createVolatileImage(int i, int w, int h, int transparency) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		return gc.createCompatibleVolatileImage(w, h, transparency);
	}
}
