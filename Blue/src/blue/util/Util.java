package blue.util;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;

public final class Util {
	
	private Util() {
		//hide constructor
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> typeOf(T t) {
		return (Class<T>)t.getClass();
	}
	
	public static int clamp(int x, int a, int b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
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
	
	public static double clamp(double x, double a, double b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static int wrap(int x, int a, int b) {
		if(x < a) return b + (a - x) % (b - a + 1);
		if(x > b) return a + (x - b) % (b - a + 1);
		return x;
	}
	
	public static float wrap(float x, float a, float b) {
		if(x < a) return b + (a - x) % (b - a + 1);
		if(x > b) return a + (x - b) % (b - a + 1);
		return x;
	}
	
	public static long wrap(long x, long a, long b) {
		if(x < a) return b + (a - x) % (b - a + 1);
		if(x > b) return a + (x - b) % (b - a + 1);
		return x;
	}
	
	public static double wrap(double x, double a, double b) {
		if(x < a) return b + (a - x) % (b - a + 1);
		if(x > b) return a + (x - b) % (b - a + 1);
		return x;
	}
	
	public static int sign(int x) {
		return x != 0 ? x > 0 ? 1 : -1 : 0;
	}
	
	public static float sign(float x) {
		return x != 0f ? x > 0f ? 1f : -1f : 0f;
	}
	
	public static long sign(long x) {
		return x != 0 ? x > 0 ? 1 : -1 : 0;
	}
	
	public static double sign(double x) {
		return x != 0. ? x > 0. ? 1. : -1. : 0.;
	}
	
	public static <T> void print(PrintStream out, T[] list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void print(PrintWriter out, T[] list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void print(PrintStream out, Iterable<T> list) {
		for(T t: list) out.println(t);
	}
	
	public static <T> void print(PrintWriter out, Iterable<T> list) {
		for(T t: list) out.println(t);
	}
	
	public static <K, V> void print(PrintStream out, Map<K, V> map) {
		map.forEach((key, val) -> {
			out.println(key + "=" + val);
		});
	}
	
	public static <K, V> void print(PrintWriter out, Map<K, V> map) {
		map.forEach((key, val) -> {
			out.println(key + "=" + val);
		});
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
