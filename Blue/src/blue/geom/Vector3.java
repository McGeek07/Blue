package blue.geom;

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
		this.x = v.x();
		this.y = v.y();
		this.z = v.z();
	}
	
	public Vector3(float x, float y, float z) {
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
	
	public static String toString(Vector3 v3, String format) {
		return 
				"<" 
				+ String.format(format, v3.x) + ", "
				+ String.format(format, v3.y) + ", "
				+ String.format(format, v3.z) + ">";
	}
	
	protected static final <V extends Vector3> V parseVector3(V v3, String str) {
		if(v3 == null)
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
			this.x = v.x();
			this.y = v.y();
			this.z = v.z();
			return this;
		}
		
		public Vector3.Mutable set(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
			return this;
		}
		
		public Vector3.Mutable setX(float x) {
			this.x = x;
			return this;
		}
		
		public Vector3.Mutable setY(float y) {
			this.y = y;
			return this;
		}
		
		public Vector3.Mutable setZ(float z) {
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
