package blue.geom;

import blue.util.Util;

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
		this.x = v.x();
		this.y = v.y();
	}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
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
	
	public static String toString(Vector2 v2, String format) {
		return 
				"<" 
				+ String.format(format, v2.x) + ", "
				+ String.format(format, v2.y) + ">";
	}
	
	protected static final <V extends Vector2> V parseVector2(V v2, String str) {
		if(v2 == null)
            throw new IllegalArgumentException("Null Vector");
        if (str == null)
            throw new IllegalArgumentException("Null String");
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
        String[] tmp = str.split("\\,");
        float[] arr = new float[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            arr[i] = Util.stringToFloat(tmp[i]);
        }
        switch (arr.length) {
            default:
            case 2:
                v2.y = arr[Y];
            case 1:
                v2.x = arr[X];
            case 0:
        }
        return v2;
	}
	
	public static Vector2 parseVector2(String str) {
		return Vector2.parseVector2(new Vector2(), str);
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
			this.x = v.x();
			this.y = v.y();
			return this;
		}
		
		public Vector2.Mutable set(float x, float y) {
			this.x = x;
			this.y = y;
			return this;
		}
		
		public Vector2.Mutable setX(float x) {
			this.x = x;
			return this;
		}
		
		public Vector2.Mutable setY(float y) {
			this.y = y;
			return this;
		}
		
		@Override
		public Vector2.Mutable copy() {
			return new Vector2.Mutable(this);
		}
		
		public static Vector2.Mutable parseVector2(String str) {
			return Vector2.parseVector2(new Vector2.Mutable(), str);
		}
	}
}
