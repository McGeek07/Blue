package blue.geom;

public class Matrix4 extends Matrix {
	private static final long 
		serialVersionUID = 1L;
	protected float
		xx, xy, xz, xw,
		yx, yy, yz, yw,
		zx, zy, zz, zw,
		wx, wy, wz, ww;
	
	public Matrix4() {
		//do nothing
	}
	
	public Matrix4(Matrix m) {		
		this.mSet(m);
	}
	
	public Matrix4(
			int mode,
			Vector v0,
			Vector v1,
			Vector v2,
			Vector v3
			) {
		this.mSet(mode, v0, v1, v2, v3);
	}
	
	public Matrix4(
			int mode,
			float a, float b, float c, float d, 
			float e, float f, float g, float h, 
			float i, float j, float k, float l,
			float m, float n, float o, float p
			) {
		this.mSet(mode, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
	}
	
	protected Matrix4 mSet(Matrix m) {
		this.xx = m.xx(); this.xy = m.xy(); this.xz = m.xz(); this.xw = m.xw();
		this.yx = m.yx(); this.yy = m.yy(); this.yz = m.yz(); this.yw = m.yx();
		this.zx = m.zx(); this.zy = m.zy(); this.zz = m.zz(); this.zw = m.zw();
		this.wx = m.wx(); this.wy = m.wy(); this.wz = m.wz(); this.ww = m.ww();
		return this;
	}
	
	protected Matrix4 mSet(
			int mode,
			Vector v0,
			Vector v1,
			Vector v2,
			Vector v3
			) {
		switch(mode) {
			case ROW_MAJOR: return mSetRowMajor(v0, v1, v2, v3);
			case COL_MAJOR: return mSetColMajor(v0, v1, v2, v3);
		}
		return this;
	}
	
	protected Matrix4 mSet(
			int mode,
			float a, float b, float c, float d,
			float e, float f, float g, float h, 
			float i, float j, float k, float l,
			float m, float n, float o, float p
			) {
		switch(mode) {
			case ROW_MAJOR: return mSetRowMajor(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
			case COL_MAJOR: return mSetColMajor(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
		}
		return this;
	}
	
	protected Matrix4 mSetRowMajor(
			Vector v0,
			Vector v1,
			Vector v2,
			Vector v3
			) {
		return mSetRowMajor(
				v0.x(), v0.y(), v0.z(), v0.w(),
				v1.x(), v1.y(), v1.z(), v1.w(),
				v2.x(), v2.y(), v2.z(), v2.w(),
				v3.x(), v3.y(), v3.z(), v3.w()
				);
	}
	
	protected Matrix4 mSetColMajor(
			Vector v0,
			Vector v1,
			Vector v2,
			Vector v3
			) {
		return mSetColMajor(
				v0.x(), v0.y(), v0.z(), v0.w(),
				v1.x(), v1.y(), v1.z(), v1.w(),
				v2.x(), v2.y(), v2.z(), v2.w(),
				v3.x(), v3.y(), v3.z(), v3.w()
				);
	}
	
	protected Matrix4 mSetRowMajor(
			float a, float b, float c, float d,
			float e, float f, float g, float h, 
			float i, float j, float k, float l,
			float m, float n, float o, float p
			) {
		this.xx = a; this.xy = b; this.xz = c; this.xw = d;
		this.yx = e; this.yy = f; this.yz = g; this.yw = h;
		this.zx = i; this.zy = j; this.zz = k; this.zw = l;
		this.wx = m; this.wy = n; this.wz = o; this.ww = p;
		return this;
	}
	
	protected Matrix4 mSetColMajor(
			float a, float b, float c, float d,
			float e, float f, float g, float h, 
			float i, float j, float k, float l,
			float m, float n, float o, float p
			) {
		this.xx = a; this.xy = e; this.xz = i; this.xw = m;
		this.yx = b; this.yy = f; this.yz = j; this.yw = n;
		this.zx = c; this.zy = g; this.zz = k; this.zw = o;
		this.wx = d; this.wy = h; this.wz = l; this.ww = p;
		return this;
	}
	
	@Override
	public float xx() { return this.xx; }
	@Override
	public float xy() { return this.xy; }
	@Override
	public float xz() { return this.xz; }
	@Override
	public float xw() { return this.xw; }
	@Override
	public float yx() { return this.yx; }
	@Override
	public float yy() { return this.yy; }
	@Override
	public float yz() { return this.yz; }
	@Override
	public float yw() { return this.yw; }
	@Override
	public float zx() { return this.zx; }
	@Override
	public float zy() { return this.zy; }
	@Override
	public float zz() { return this.zz; }
	@Override
	public float zw() { return this.zw; }
	@Override
	public float wx() { return this.wx; }
	@Override
	public float wy() { return this.wy; }
	@Override
	public float wz() { return this.wz; }
	@Override
	public float ww() { return this.ww; }
	
	@Override
	public Vector4 row(int i) {
		switch(i) {
			case 0: return new Vector4(xx, xy, xz, xw);
			case 1: return new Vector4(yx, yy, yz, yw);
			case 2: return new Vector4(zx, zy, zz, zw);
			case 3: return new Vector4(wx, wy, wz, ww);
		}
		return null;
	}
	
	@Override
	public Vector4 col(int j) {
		switch(j) {
			case 0: return new Vector4(xx, yx, zx, wx);
			case 1: return new Vector4(xy, yy, zy, wy);
			case 2: return new Vector4(xz, yz, zz, wz);
			case 3: return new Vector4(xw, yw, zw, ww);
		}
		return null;
	}
	
	@Override
	public int m() {
		return 2;
	}
	
	@Override
	public int n() {
		return 2;
	}
	
	@Override
	public Matrix4 copy() {
		return new Matrix4(this);
	}
	
	@Override
	public String toString() {
		return Matrix4.toString(this, "%s");
	}
	
	public static String toString(Matrix4 m4, String format) {
		return
				"[" + String.format(format, m4.xx) + ", " + String.format(format, m4.xy) + ", " + String.format(format, m4.xz) + ", " + String.format(format, m4.xw) + "]\n" +
				"[" + String.format(format, m4.yx) + ", " + String.format(format, m4.yy) + ", " + String.format(format, m4.yz) + ", " + String.format(format, m4.yw) + "]\n" +
				"[" + String.format(format, m4.zx) + ", " + String.format(format, m4.zy) + ", " + String.format(format, m4.zz) + ", " + String.format(format, m4.zw) + "]\n" +
				"[" + String.format(format, m4.wx) + ", " + String.format(format, m4.wy) + ", " + String.format(format, m4.wz) + ", " + String.format(format, m4.ww) + "]";
	}	
	
	protected static final <M extends Matrix4> M parseMatrix4(M m4, String str) {
		if(m4 == null)
			throw new IllegalArgumentException("Null Matrix");
	    if (str == null)
	        throw new IllegalArgumentException("Null String");
	    str = str.replace("[", "");
	    str = str.replace("]", "");
	    String[] tmp = str.split("\n");
	    
	    Vector4
	    	v0 = new Vector4(),
	    	v1 = new Vector4(),
	    	v2 = new Vector4(),
	    	v3 = new Vector4();
	    switch(tmp.length) {
	        default:
	        case 4:
	        	Vector4.parseVector4(v3, tmp[3]);
	        case 3:
	        	Vector4.parseVector4(v2, tmp[2]);
	        case 2:
	        	Vector4.parseVector4(v1, tmp[1]);
	        case 1:
	        	Vector4.parseVector4(v0, tmp[0]);
	        case 0:
	    }
	    m4.mSetRowMajor(v0, v1, v2, v3);
	    
	    return m4;
	}
	
	public static Matrix4 parseMatrix4(String str) {
		return Matrix4.parseMatrix4(new Matrix4(), str);
	}
	
	public static Matrix4 identity() {
		return new Matrix4(Matrix.ROW_MAJOR, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
	}
	
	public static class Mutable extends Matrix4 {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable() {
			super();
		}
		
		public Mutable(Matrix m) {
			super(m);
		}
		
		public Mutable(
				int mode,
				Vector v0,
				Vector v1,
				Vector v2,
				Vector v3
				) {
			super(mode, v0, v1, v2, v3);
		}
		
		public Mutable(
				int mode,
				float a, float b, float c, float d,
				float e, float f, float g, float h, 
				float i, float j, float k, float l, 
				float m, float n, float o, float p
				) {
			super(mode, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
		}
		
		public Matrix4.Mutable set(Matrix m) {
			this.mSet(m);
			return this;
		}
		
		public Matrix4.Mutable set(
				int mode, 
				Vector v0,
				Vector v1,
				Vector v2,
				Vector v3
				) {
			this.mSet(mode, v0, v1, v2, v3);
			return this;
		}
		
		public Matrix4.Mutable set(
				int mode,
				float a, float b, float c, float d,
				float e, float f, float g, float h, 
				float i, float j, float k, float l, 
				float m, float n, float o, float p
				) {
			this.mSet(mode, a,  b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
			return this;
		}
		
		public Matrix4.Mutable setRowMajor(
				Vector v0,
				Vector v1,
				Vector v2,
				Vector v3
				) {
			this.mSetRowMajor(v0, v1, v2, v3);
			return this;
		}
		
		public Matrix4.Mutable setRowMajor(
				float a, float b, float c, float d,
				float e, float f, float g, float h, 
				float i, float j, float k, float l, 
				float m, float n, float o, float p
				) {
			this.mSetRowMajor(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
			return this;
		}
		
		public Matrix4.Mutable setColMajor(
				Vector v0,
				Vector v1,
				Vector v2,
				Vector v3
				) {
			this.mSetColMajor(v0, v1, v2, v3);
			return this;
		}
		
		public Matrix4.Mutable setColMajor(
				float a, float b, float c, float d,
				float e, float f, float g, float h, 
				float i, float j, float k, float l, 
				float m, float n, float o, float p
				) {
			this.mSetColMajor(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
			return this;
		}
		
		public static Matrix4.Mutable parseMatrix4(String str) {
			return Matrix4.parseMatrix4(new Matrix4.Mutable(), str);
		}
		
		public static Matrix4.Mutable identity() {
			return new Matrix4.Mutable(Matrix.ROW_MAJOR, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		}
	}
}
