package blue.math;

public final class Math {
	public static final float
		PI = (float)java.lang.Math.PI;
	
	private Math() {
		//hide constructor
	}
	
	public static int   clamp(int   x, int   a, int   b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static float clamp(float x, float a, float b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static long   clamp(long   x, long   a, long   b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static double clamp(double x, double a, double b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	public static int   wrap(int   x, int   a, int   b) {
		if(x < a) return b + (a - x) % (b - a + 1);
		if(x > b) return a + (x - b) % (b - a + 1);
		return x;
	}
	
	public static float wrap(float x, float a, float b) {
		if(x < a) return b + (a - x) % (b - a + 1);
		if(x > b) return a + (x - b) % (b - a + 1);
		return x;
	}
	
	public static long   wrap(long   x, long   a, long   b) {
		if(x < a) return b + (a - x) % (b - a + 1);
		if(x > b) return a + (x - b) % (b - a + 1);
		return x;
	}
	
	public static double wrap(double x, double a, double b) {
		if(x < a) return b + (a - x) % (b - a + 1);
		if(x > b) return a + (x - b) % (b - a + 1);
		return x;
	}
	
	public static int   sign(int   x) {
		return x != 0 ? x > 0 ? 1 : -1 : 0;
	}
	
	public static float sign(float x) {
		return x != 0f ? x > 0f ? 1f : -1f : 0f;
	}
	
	public static long   sign(long   x) {
		return x != 0 ? x > 0 ? 1 : -1 : 0;
	}
	
	public static double sign(double x) {
		return x != 0. ? x > 0. ? 1. : -1. : 0.;
	}
	
	public static float sqrt(float x) {
		return (float)java.lang.Math.sqrt(x);
	}
	
	public static float sin(float x) {
		return (float)java.lang.Math.sin(x);
	}
	
	public static float cos(float x) {
		return (float)java.lang.Math.cos(x);
	}
	
	public static float tan(float x) {
		return (float)java.lang.Math.tan(x);
	}
	
	public static float atan2(float y, float x) {
		return (float)java.lang.Math.atan2(y, x);
	}
	
	public static int abs(int x) {
		return x >= 0 ? x : -x;
	}
	
	public static float abs(float x) {
		return x >= 0 ? x : -x;
	}
	
	public static long abs(long x) {
		return x >= 0 ? x : -x;
	}
	
	public static double abs(double x) {
		return x >= 0 ? x : -x;
	}
	
	public static int   min(int   a, int   b) {
		return a < b ? a : b;
	}
	
	public static int   min(int   a, int   b, int  ... c) {
		int   min = min(a, b);
		for(int   x : c)
			if(x < min)
				min = x;
		return min;
	}
	
	public static float min(float a, float b) {
		return a < b ? a : b;
	}
	
	public static float min(float a, float b, float... c) {
		float min = min(a, b);
		for(float x : c)
			if(x < min)
				min = x;
		return min;
	}
	
	public static long   min(long   a, long   b) {
		return a < b ? a : b;
	}
	
	public static long   min(long   a, long   b, long  ... c) {
		long   min = min(a, b);
		for(long   x : c)
			if(x < min)
				min = x;
		return min;
	}
	
	public static double min(double a, double b) {
		return a < b ? a : b;
	}
	
	public static double min(double a, double b, double... c) {
		double min = min(a, b);
		for(double x : c)
			if(x < min)
				min = x;
		return min;
	}
	
	public static int   max(int   a, int   b) {
		return a > b ? a : b;
	}
	
	public static int   max(int   a, int   b, int  ... c) {
		int   max = max(a, b);
		for(int   x : c)
			if(x > max)
				max = x;
		return max;
	}
	
	public static float max(float a, float b) {
		return a > b ? a : b;
	}
	
	public static float max(float a, float b, float... c) {
		float max = max(a, b);
		for(float x : c)
			if(x > max)
				max = x;
		return max;
	}
	
	public static long   max(long   a, long   b) {
		return a > b ? a : b;
	}
	
	public static long   max(long   a, long   b, long  ... c) {
		long   max = max(a, b);
		for(long   x : c)
			if(x > max)
				max = x;
		return max;
	}
	
	public static double max(double a, double b) {
		return a > b ? a : b;
	}
	
	public static double max(double a, double b, double... c) {
		double max = max(a, b);
		for(double x : c)
			if(x > max)
				max = x;
		return max;
	}
}
