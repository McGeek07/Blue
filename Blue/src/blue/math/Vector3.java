package blue.math;

import java.util.HashMap;

import blue.util.Config;

public class Vector3 extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		x,
		y,
		z;
	
	public Vector3() {
		//do nothing
	}
	
	public Vector3(Vector v) {
		x = v.x();
		y = v.y();
		z = v.z();
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	public int n() {
		return 3;
	}
	
	@Override
	public Vector3 copy() {
		return new Vector3(this);
	}
	
	@Override
	public String toString() {
		return Vector3.toString(this, "%s");
	}
	
	public static String toString(Vector3 v, String f) {
		return 
			"<" 
			+ String.format(f, v.x) + ", "
			+ String.format(f, v.y) + ", "
			+ String.format(f, v.z) + ">";
	}
	
	protected static final <V extends Vector3> V fromString(V v, String s) {
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
	        
	        HashMap<String, String> map = Config.parse(new HashMap<>(), s, "x", "y", "z");
	        v.x = Config.getPropertyAsFloat(map, "x");
	        v.y = Config.getPropertyAsFloat(map, "y");
	        v.z = Config.getPropertyAsFloat(map, "z");
		}        
        return v;
	}
	
	public static Vector3 fromString(String s) {
		return Vector3.fromString(new Vector3(), s);
	}
	
	public static class Mutable extends Vector3 {
		private static final long 
			serialVersionUID = 1L;
	
		public Mutable() {
			super();
		}
		
		public Mutable(Vector v) {
			super(v);
		}
		
		public Mutable(float x, float y, float z) {
			super(x, y, z);
		}
		
		public Vector3.Mutable set(Vector v) {
			x = v.x();
			y = v.y();
			z = v.z();
			return this;
		}
		
		public Vector3.Mutable set(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
			return this;
		}
		
		public Vector3.Mutable x(float x) {
			this.x = x;
			return this;
		}
		
		public Vector3.Mutable y(float y) {
			this.y = y;
			return this;
		}
		
		public Vector3.Mutable z(float z) {
			this.z = z;
			return this;
		}
		
		@Override
		public Vector3.Mutable copy() {
			return new Vector3.Mutable(this);
		}
		
		public static Vector3.Mutable fromString(String s) {
			return Vector3.fromString(new Vector3.Mutable(), s);
		}
	}
}
