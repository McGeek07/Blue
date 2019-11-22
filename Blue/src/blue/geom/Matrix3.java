package blue.geom;

public class Matrix3 extends Matrix {
	private static final long 
		serialVersionUID = 1L;
	protected float
		xx, xy, xz,
		yx, yy, yz,
		zx, zy, zz;
	
	public Matrix3() {
		//do nothing
	}
	
	public Matrix3(Matrix m) {		
		this.mSet(m);
	}
	
	public Matrix3(
			int mode,
			Vector v0,
			Vector v1,
			Vector v2
			) {
		this.mSet(mode, v0, v1, v2);
	}
	
	public Matrix3(
			int mode,
			float a, float b, float c,
			float d, float e, float f,
			float g, float h, float i
			) {
		this.mSet(mode, a, b, c, d, e, f, g, h, i);
	}
	
	protected Matrix3 mSet(Matrix m) {
		this.xx = m.xx(); this.xy = m.xy(); this.xz = m.xz();
		this.yx = m.yx(); this.yy = m.yy(); this.yz = m.yz();
		this.zx = m.zx(); this.zy = m.zy(); this.zz = m.zz();
		return this;
	}
	
	protected Matrix3 mSet(
			int mode,
			Vector v0,
			Vector v1,
			Vector v2
			) {
		switch(mode) {
			case ROW_MAJOR: return mSetRowMajor(v0, v1, v2);
			case COL_MAJOR: return mSetColMajor(v0, v1, v2);
		}
		return this;
	}
	
	protected Matrix3 mSet(
			int mode,
			float a, float b, float c, 
			float d, float e, float f,
			float g, float h, float i
			) {
		switch(mode) {
			case ROW_MAJOR: return mSetRowMajor(a, b, c, d, e, f, g, h, i);
			case COL_MAJOR: return mSetColMajor(a, b, c, d, e, f, g, h, i);
		}
		return this;
	}
	
	protected Matrix3 mSetRowMajor(
			Vector v0,
			Vector v1,
			Vector v2
			) {
		return mSetRowMajor(
				v0.x(), v0.y(), v0.z(),
				v1.x(), v1.y(), v1.z(),
				v2.x(), v2.y(), v2.z()
				);
	}
	
	protected Matrix3 mSetColMajor(
			Vector v0,
			Vector v1,
			Vector v2
			) {
		return mSetColMajor(
				v0.x(), v0.y(), v0.z(),
				v1.x(), v1.y(), v1.z(),
				v2.x(), v2.y(), v2.z()
				);
	}
	
	protected Matrix3 mSetRowMajor(
			float a, float b, float c,
			float d, float e, float f,
			float g, float h, float i
			) {
		this.xx = a; this.xy = b; this.xz = c;
		this.yx = d; this.yy = e; this.yz = f;
		this.zx = g; this.zy = h; this.zz = i;
		return this;
	}
	
	protected Matrix3 mSetColMajor(
			float a, float b, float c,
			float d, float e, float f,
			float g, float h, float i
			) {
		this.xx = a; this.xy = d; this.xz = g;
		this.yx = b; this.yy = e; this.yz = h;
		this.zx = c; this.zy = f; this.zz = i;
		return this;
	}
	
	@Override
	public float xx() { return this.xx; }
	@Override
	public float xy() { return this.xy; }
	@Override
	public float xz() { return this.xz; }
	@Override
	public float yx() { return this.yx; }
	@Override
	public float yy() { return this.yy; }
	@Override
	public float yz() { return this.yz; }
	@Override
	public float zx() { return this.zx; }
	@Override
	public float zy() { return this.zy; }
	@Override
	public float zz() { return this.zz; }
	
	@Override
	public Vector3 row(int i) {
		switch(i) {
			case 0: return new Vector3(xx, xy, xz);
			case 1: return new Vector3(yx, yy, yz);
			case 2: return new Vector3(zx, zy, zz);
		}
		return null;
	}
	
	@Override
	public Vector3 col(int j) {
		switch(j) {
			case 0: return new Vector3(xx, yx, zx);
			case 1: return new Vector3(xy, yy, zy);
			case 2: return new Vector3(xz, yz, zz);
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
	public Matrix3 copy() {
		return new Matrix3(this);
	}
	
	@Override
	public String toString() {
		return Matrix3.toString(this, "%s");
	}
	
	public static String toString(Matrix3 m3, String format) {
		return
				"[" + String.format(format, m3.xx) + ", " + String.format(format, m3.xy) + ", " + String.format(format, m3.xz) + "]\n" +
				"[" + String.format(format, m3.yx) + ", " + String.format(format, m3.yy) + ", " + String.format(format, m3.yz) + "]\n" +
				"[" + String.format(format, m3.zx) + ", " + String.format(format, m3.zy) + ", " + String.format(format, m3.zz) + "]";
	}	
	
	protected static final <M extends Matrix3> M parseMatrix3(M m3, String str) {
		if(m3 == null)
			throw new IllegalArgumentException("Null Matrix");
	    if (str == null)
	        throw new IllegalArgumentException("Null String");
	    if ((str = str.trim()).isEmpty())
	        throw new IllegalArgumentException("Empty String");
	    str = str.replace("[", "");
	    str = str.replace("]", "");
	    String[] tmp = str.split("\n");
	    
	    Vector3
	    	v0 = new Vector3(),
	    	v1 = new Vector3(),
	    	v2 = new Vector3();
	    switch(tmp.length) {
	        default:
	        case 3:
	        	Vector3.parseVector3(v2, tmp[2]);
	        case 2:
	        	Vector3.parseVector3(v1, tmp[1]);
	        case 1:
	        	Vector3.parseVector3(v0, tmp[0]);
	        case 0:
	    }
	    m3.mSetRowMajor(v0, v1, v2);
	    
	    return m3;
	}
	
	public static Matrix3 parseMatrix3(String str) {
		return Matrix3.parseMatrix3(new Matrix3(), str);
	}
	
	public static Matrix3 identity() {
		return new Matrix3(Matrix.ROW_MAJOR, 1, 0, 0, 0, 1, 0, 0, 0, 1);
	}
	
	public static class Mutable extends Matrix3 {
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
				Vector v2
				) {
			super(mode, v0, v1, v2);
		}
		
		public Mutable(
				int mode,
				float a, float b, float c, 
				float d, float e, float f,
				float g, float h, float i
				) {
			super(mode, a, b, c, d, e, f, g, h, i);
		}
		
		public Matrix3.Mutable set(Matrix m) {
			this.mSet(m);
			return this;
		}
		
		public Matrix3.Mutable set(
				int mode, 
				Vector v0,
				Vector v1,
				Vector v2
				) {
			this.mSet(mode, v0, v1, v2);
			return this;
		}
		
		public Matrix3.Mutable set(
				int mode,
				float a, float b, float c,
				float d, float e, float f,
				float g, float h, float i
				) {
			this.mSet(mode, a,  b, c, d, e, f, g, h, i);
			return this;
		}
		
		public Matrix3.Mutable setRowMajor(
				Vector v0,
				Vector v1,
				Vector v2
				) {
			this.mSetRowMajor(v0, v1, v2);
			return this;
		}
		
		public Matrix3.Mutable setRowMajor(
				float a, float b, float c, 
				float d, float e, float f,
				float g, float h, float i
				) {
			this.mSetRowMajor(a, b, c, d, e, f, g, h, i);
			return this;
		}
		
		public Matrix3.Mutable setColMajor(
				Vector v0,
				Vector v1,
				Vector v2
				) {
			this.mSetColMajor(v0, v1, v2);
			return this;
		}
		
		public Matrix3.Mutable setColMajor(
				float a, float b, float c, 
				float d, float e, float f,
				float g, float h, float i
				) {
			this.mSetColMajor(a, b, c, d, e, f, g, h, i);
			return this;
		}
		
		public static Matrix3.Mutable parseMatrix3(String str) {
			return Matrix3.parseMatrix3(new Matrix3.Mutable(), str);
		}
		
		public static Matrix3.Mutable identity() {
			return new Matrix3.Mutable(Matrix.ROW_MAJOR, 1, 0, 0, 0, 1, 0, 0, 0, 1);
		}
	}
}
