package blue.geom;

import blue.util.Util;

public class Vector4f extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		x,
		y,
		z,
		w;
	
	public Vector4f() {
		//do nothing
	}
	
	public Vector4f(Vector v) {
		this.x = v.x();
		this.y = v.y();
		this.z = v.z();
		this.w = v.w();
	}
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	@Override
	public float x() {
		return this.x;
	}
	
	@Override
	public float y() {
		return this.y;
	}
	
	@Override
	public float z() {
		return this.z;
	}
	
	@Override
	public float w() {
		return this.w;
	}
	
	@Override
	public int n() {
		return 4;
	}
	
	@Override
	public Vector4f copy() {
		return new Vector4f(this);
	}
	
	@Override
	public String toString() {
		return toString(this, "%s");
	}
	
	public static String toString(Vector4f v4, String format) {
		return 
				"<" 
				+ String.format(format, v4.x) + ", "
				+ String.format(format, v4.y) + ", "
				+ String.format(format, v4.z) + ", "
				+ String.format(format, v4.w) + ">";
	}
	
	protected static final <V extends Vector4f> V parseVector4f(V v4, String str) {
		if(v4 == null)
	        throw new IllegalArgumentException("Null Vector");
	    if (str == null)
	        throw new IllegalArgumentException("Null String");
	    if ((str = str.trim()).isEmpty())
	        throw new IllegalArgumentException("Empty String");
	    int
	        a = str.indexOf("<"),
	        b = str.indexOf(">");
	    if (a >= 0 || b >= 0) {
	        if (b > a) {
	            str = str.substring(++a, b);
	        } else {
	            str = str.substring(++a);
	        }
	    }
	    String[] temp = str.split("\\,");
	    float[] arr = new float[temp.length];
	    for (int i = 0; i < temp.length; i++) {
	        arr[i] = Util.stringToFloat(temp[i]);
	    }
	    switch (arr.length) {
	        default:
	        case 4:
	        	v4.w = arr[W];
	        case 3:
	        	v4.z = arr[Z];
	        case 2:
	            v4.y = arr[Y];
	        case 1:
	            v4.x = arr[X];
	        case 0:
	    }
	    return v4;
	}
	
	public static Vector4f parseVector4f(String str) {
		return Vector4f.parseVector4f(new Vector4f(), str);
	}
	
	public static class Mutable extends Vector4f {
		private static final long 
			serialVersionUID = 1L;
	
		public Mutable() {
			super();
		}
		
		public Mutable(Vector v) {
			super(v);
		}
		
		public Mutable(float x, float y, float z, float w) {
			super(x, y, z, w);
		}
		
		public Vector4f.Mutable set(Vector v) {
			this.x = v.x();
			this.y = v.y();
			this.z = v.z();
			this.w = v.w();
			return this;
		}
		
		public Vector4f.Mutable set(float x, float y, float z, float w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
			return this;
		}
		
		public Vector4f.Mutable setX(float x) {
			this.x = x;
			return this;
		}
		
		public Vector4f.Mutable setY(float y) {
			this.y = y;
			return this;
		}
		
		public Vector4f.Mutable setZ(float z) {
			this.z = z;
			return this;
		}
		
		public Vector4f.Mutable setW(float w) {
			this.w = w;
			return this;
		}
		
		@Override
		public Vector4f.Mutable copy() {
			return new Vector4f.Mutable(this);
		}
		
		public static Vector4f.Mutable parseVector4f(String str) {
			return Vector4f.parseVector4f(new Vector4f.Mutable(), str);
		}
	}
}
