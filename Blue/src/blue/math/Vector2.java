package blue.math;

import java.util.HashMap;

import blue.util.Config;

public class Vector2 extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		x,
		y;
	
	public Vector2() {
		//do nothing
	}
	
	public Vector2(Vector v) {
		x = v.x();
		y = v.y();
	}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
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
	public int n() {
		return 2;
	}

	@Override
	public Vector2 copy() {
		return new Vector2(this);
	}
	
	@Override
	public String toString() {
		return Vector2.toString(this, "%s");
	}
	
	public static String toString(Vector2 v, String f) {
		return 
			"<" 
			+ String.format(f, v.x) + ", "
			+ String.format(f, v.y) + ">";
	}
	
	protected static final <V extends Vector2> V fromString(V v, String s) {
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
	        
	        HashMap<String, String> map = Config.parse(new HashMap<>(), s, "x", "y");
	        v.x = Config.getPropertyAsFloat(map, "x");
	        v.y = Config.getPropertyAsFloat(map, "y");
		}        
        return v;
	}
	
	public static Vector2 fromString(String s) {
		return Vector2.fromString(new Vector2(), s);
	}
	
	public static class Mutable extends Vector2 {
		private static final long 
			serialVersionUID = 1L;

		public Mutable() {
			super();
		}
		
		public Mutable(Vector v) {
			super(v);
		}
		
		public Mutable(float x, float y) {
			super(x, y);
		}
		
		public Vector2.Mutable set(Vector v) {
			x = v.x();
			y = v.y();
			return this;
		}
		
		public Vector2.Mutable set(float x, float y) {
			this.x = x;
			this.y = y;
			return this;
		}
		
		public Vector2.Mutable x(float x) {
			this.x = x;
			return this;
		}
		
		public Vector2.Mutable y(float y) {
			this.y = y;
			return this;
		}
		
		@Override
		public Vector2.Mutable copy() {
			return new Vector2.Mutable(this);
		}
		
		public static Vector2.Mutable fromString(String s) {
			return Vector2.fromString(new Vector2.Mutable(), s);
		}
	}
}
