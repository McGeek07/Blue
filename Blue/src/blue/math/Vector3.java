package blue.math;

import blue.util.Util;

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
	
	protected static final <V extends Vector3> V parseVector3(V v, String s) {
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
	        
	        String[] t = s.split("\\,");
	        switch (t.length) {
	            default:
	            case 3:
	            	v.z = Util.stringToFloat(t[2]);
	            case 2:
	                v.y = Util.stringToFloat(t[1]);
	            case 1:
	                v.x = Util.stringToFloat(t[0]);
	            case 0:
	        }
		}        
        return v;
	}
	
	public static Vector3 parseVector3(String str) {
		return Vector3.parseVector3(new Vector3(), str);
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
		
		public static Vector3.Mutable parseVector3(String str) {
			return Vector3.parseVector3(new Vector3.Mutable(), str);
		}
	}
}
