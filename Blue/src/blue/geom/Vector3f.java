package blue.geom;

import blue.util.Util;

public class Vector3f extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		x,
		y,
		z;
	
	public Vector3f() {
		//do nothing
	}
	
	public Vector3f(Vector v) {
		this.x = v.x();
		this.y = v.y();
		this.z = v.z();
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	public Vector3f copy() {
		return new Vector3f(this);
	}
	
	@Override
	public String toString() {
		return toString(this, "%s");
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Vector3f) return this.equals((Vector3f)o);
		if(o instanceof Vector2f) return this.equals((Vector2f)o);
		return false;
	}
	
	public boolean equals(Vector3f v) {
		return Vector.compare(this, v) == 0;
	}
	
	public boolean equals(Vector2f v) {
		return Vector.compare(this, v) == 0;
	}
	
	public static String toString(Vector3f v3, String format) {
		return 
				"<" 
				+ String.format(format, v3.x) + ", "
				+ String.format(format, v3.y) + ", "
				+ String.format(format, v3.z) + ">";
	}
	
	protected static final <V extends Vector3f> V parseVector3f(V v3, String str) {
		if(v3 == null)
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
            case 3:
            	v3.z = arr[Z];
            case 2:
                v3.y = arr[Y];
            case 1:
                v3.x = arr[X];
            case 0:
        }
        return v3;
	}
	
	public static Vector3f parseVector3f(String str) {
		return Vector3f.parseVector3f(new Vector3f(), str);
	}
	
	public static class Mutable extends Vector3f {
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
		
		public Vector3f.Mutable set(Vector v) {
			this.x = v.x();
			this.y = v.y();
			this.z = v.z();
			return this;
		}
		
		public Vector3f.Mutable set(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
			return this;
		}
		
		public Vector3f.Mutable setX(float x) {
			this.x = x;
			return this;
		}
		
		public Vector3f.Mutable setY(float y) {
			this.y = y;
			return this;
		}
		
		public Vector3f.Mutable setZ(float z) {
			this.z = z;
			return this;
		}
		
		@Override
		public Vector3f.Mutable copy() {
			return new Vector3f.Mutable(this);
		}
		
		public static Vector3f.Mutable parseVector3f(String str) {
			return Vector3f.parseVector3f(new Vector3f.Mutable(), str);
		}
	}
}
