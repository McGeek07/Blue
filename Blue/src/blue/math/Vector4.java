package blue.math;

import java.util.HashMap;

import blue.util.Util;

public class Vector4 extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		x,
		y,
		z,
		w;
	
	public Vector4() {
		//do nothing
	}
	
	public Vector4(Vector v) {
		x = v.x();
		y = v.y();
		z = v.z();
		w = v.w();
	}
	
	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	@Override
	public float x() {
		return x;
	}
	
	@Override
	public float y() {
		return y;
	}
	
	@Override
	public float z() {
		return z;
	}
	
	@Override
	public float w() {
		return w;
	}
	
	@Override
	public int n() {
		return 4;
	}
	
	@Override
	public Vector4 copy() {
		return new Vector4(this);
	}
	
	@Override
	public String toString() {
		return Vector4.toString(this, "%s");
	}
	
	public static String toString(Vector4 v, String f) {
		return 
			"<" 
			+ String.format(f, v.x) + ", "
			+ String.format(f, v.y) + ", "
			+ String.format(f, v.z) + ", "
			+ String.format(f, v.w) + ">";
	}
	
	protected static final <V extends Vector4> V parseVector4(V v, String s) {
		if(v != null && s != null) {
			int
		        i = s.indexOf("<"),
		        j = s.indexOf(">");
		    if (i >= 0 || j >= 0) {
		        if (j > i)
		            s = s.substring(++i, j);
		        else
		            s = s.substring(++i);
		    }
		    
		    java.util.Map<String, String> t = Util.parse(new HashMap<>(), s, "x", "y", "z", "w");
	        v.x = Util.getEntryAsFloat(t, "x");
	        v.y = Util.getEntryAsFloat(t, "y");
	        v.z = Util.getEntryAsFloat(t, "z");
	        v.w = Util.getEntryAsFloat(t, "w");
		}	    
	    return v;
	}
	
	public static Vector4 parseVector4(String str) {
		return Vector4.parseVector4(new Vector4(), str);
	}
	
	public static class Mutable extends Vector4 {
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
		
		public Vector4.Mutable set(Vector v) {
			x = v.x();
			y = v.y();
			z = v.z();
			w = v.w();
			return this;
		}
		
		public Vector4.Mutable set(float x, float y, float z, float w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
			return this;
		}
		
		public Vector4.Mutable x(float x) {
			this.x = x;
			return this;
		}
		
		public Vector4.Mutable y(float y) {
			this.y = y;
			return this;
		}
		
		public Vector4.Mutable z(float z) {
			this.z = z;
			return this;
		}
		
		public Vector4.Mutable w(float w) {
			this.w = w;
			return this;
		}
		
		@Override
		public Vector4.Mutable copy() {
			return new Vector4.Mutable(this);
		}
		
		public static Vector4.Mutable parseVector4(String str) {
			return Vector4.parseVector4(new Vector4.Mutable(), str);
		}
	}
}
