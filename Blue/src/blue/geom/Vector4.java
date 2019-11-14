package blue.geom;

import blue.util.Util;
import blue.util.Util.ObjectToString;
import blue.util.Util.StringToObject;

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
		this.x = v.x();
		this.y = v.y();
		this.z = v.z();
		this.w = v.w();
	}
	
	public Vector4(float x, float y, float z, float w) {
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
	public Vector4 copy() {
		return new Vector4(this);
	}
	
	@Override
	public String toString() {
		return Vector4.toString(this, "%s");
	}
	
	public static String toString(Vector4 v4, String format) {
		return 
				"<" 
				+ String.format(format, v4.x) + ", "
				+ String.format(format, v4.y) + ", "
				+ String.format(format, v4.z) + ", "
				+ String.format(format, v4.w) + ">";
	}
	
	protected static final <V extends Vector4> V fromString(V v4, String str) {
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
	    String[] tmp = str.split("\\,");
	    float[] arr = new float[tmp.length];
	    for (int i = 0; i < tmp.length; i++) {
	        arr[i] = Util.stringToFloat(tmp[i]);
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
	
	public static Vector4 fromString(String str) {
		return Vector4.fromString(new Vector4(), str);
	}
	
	public static final ObjectToString<Vector4>
		VECTOR4_TO_STRING = Vector4::toString;
	public static final StringToObject<Vector4>
		STRING_TO_VECTOR4 = Vector4::fromString;
	
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
			this.x = v.x();
			this.y = v.y();
			this.z = v.z();
			this.w = v.w();
			return this;
		}
		
		public Vector4.Mutable set(float x, float y, float z, float w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
			return this;
		}
		
		public Vector4.Mutable setX(float x) {
			this.x = x;
			return this;
		}
		
		public Vector4.Mutable setY(float y) {
			this.y = y;
			return this;
		}
		
		public Vector4.Mutable setZ(float z) {
			this.z = z;
			return this;
		}
		
		public Vector4.Mutable setW(float w) {
			this.w = w;
			return this;
		}
		
		@Override
		public Vector4.Mutable copy() {
			return new Vector4.Mutable(this);
		}
		
		public static Vector4.Mutable fromString(String str) {
			return Vector4.fromString(new Vector4.Mutable(), str);
		}
	}
}
