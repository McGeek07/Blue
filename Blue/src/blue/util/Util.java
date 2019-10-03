package blue.util;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import blue.geom.Bounds2f;
import blue.geom.Region2f;

public final class Util {
	
	private Util() {
		//do nothing
	}
	
	public static float clamp(float x, float a, float b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static long clamp(long x, long a, long b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> typeOf(T t) {
		return (Class<T>)t.getClass();
	}
	
	public static int stringToInt(String str) {
		return stringToInt(str, 0);
	}
	
	public static int stringToInt(String str, int alt) {
		try {
			return Integer.parseInt(str);
		} catch(Exception e) {
			return alt;
		}
	}
	
	public static float stringToFloat(String str) {
		return stringToFloat(str, 0f);
	}
	
	public static float stringToFloat(String str, float alt) {
		try {
			return Float.parseFloat(str);
		} catch(Exception e) {
			return alt;
		}
	}
	
	public static long stringToLong(String str) {
		return stringToLong(str, 0l);
	}
	
	public static long stringToLong(String str, long alt) {
		try {
			return Long.parseLong(str);
		} catch(Exception e) {
			return alt;
		}
	}
	
	public static double stringToDouble(String str) {
		return stringToDouble(str, 0.);
	}
	
	public static double stringToDouble(String str, double alt) {
		try {
			return Double.parseDouble(str);
		} catch(Exception e) {
			return alt;
		}
	}
	
	public static boolean stringToBoolean(String str) {
		return stringToBoolean(str, false);
	}
	
	public static boolean stringToBoolean(String str, boolean alt) {
		if(str != null) {
    		str = str.trim().toLowerCase();
        	switch(str) {
            	case "0":
            	case "f":
            	case "false":
            		return false;
            	case "1":
            	case "t":
            	case "true":
            		return true;            		
        	}
    	}
    	return alt;
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
	
	public static Region2f getMaximumScreenRegion(int i) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		
		return new Region2f(
				bounds.x,
				bounds.y,
				bounds.width,
				bounds.height
				);
	}
	
	public static Region2f getMaximumWindowRegion(int i) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		Insets    insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		
		return new Region2f(
				bounds.x + insets.left,
				bounds.y + insets.top ,
				bounds.width  - insets.left - insets.right ,
				bounds.height - insets.top  - insets.bottom
				);		
	}
	
	public static Bounds2f getMaximumScreenBounds(int i) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		
		return new Bounds2f(
				bounds.x,
				bounds.y,
				bounds.x + bounds.width,
				bounds.y + bounds.height
				);
	}
	
	public static Bounds2f getMaximumWindowBounds(int i) {
		GraphicsDevice        gd = getGraphicsDevice(i);
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		
		Rectangle bounds = gc.getBounds();
		Insets    insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		
		return new Bounds2f(
				bounds.x + insets.left,
				bounds.y + insets.top ,
				bounds.x + bounds.width  - insets.left - insets.right ,
				bounds.y + bounds.height - insets.top  - insets.bottom
				);		
	}
}
