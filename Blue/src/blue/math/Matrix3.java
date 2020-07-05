package blue.math;

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
		mSet(m);
	}
	
	public Matrix3(
		int mode,
		Vector v0,
		Vector v1,
		Vector v2
	) {
		switch(mode) {
			case ROW_MAJOR: mSetRowMajor(v0, v1, v2); break;
			case COL_MAJOR: mSetColMajor(v0, v1, v2); break;
		}
	}
	
	public Matrix3(
		int mode,
		float a, float b, float c,
		float d, float e, float f,
		float g, float h, float i
	) {
		switch(mode) {
			case ROW_MAJOR: mSetRowMajor(a, b, c, d, e, f, g, h, i); break;
			case COL_MAJOR: mSetColMajor(a, b, c, d, e, f, g, h, i); break;
		}
	}
	
	protected void mSet(Matrix m) {
		xx = m.xx(); xy = m.xy(); xz = m.xz();
		yx = m.yx(); yy = m.yy(); yz = m.yz();
		zx = m.zx(); zy = m.zy(); zz = m.zz();
	}
	
	protected void mSetRowMajor(
		Vector r0,
		Vector r1,
		Vector r2
	) {
		xx = r0.x(); xy = r0.y(); xz = r0.z();
		yx = r1.x(); yy = r1.y(); yz = r1.z();
		zx = r2.x(); zy = r2.y(); zz = r2.z();
	}
	
	protected void mSetColMajor(
		Vector c0,
		Vector c1,
		Vector c2
	) {
		xx = c0.x(); xy = c1.x(); xz = c2.x();
		yx = c0.y(); yy = c1.y(); yz = c2.y();
		zx = c0.z(); zy = c1.z(); zz = c2.z();
	}
	
	protected void mSetRowMajor(
		float a, float b, float c,
		float d, float e, float f,
		float g, float h, float i
	) {
		xx = a; xy = b; xz = c;
		yx = d; yy = e; yz = f;
		zx = g; zy = h; zz = i;
	}
	
	protected void mSetColMajor(
		float a, float b, float c,
		float d, float e, float f,
		float g, float h, float i
	) {
		xx = a; xy = d; xz = g;
		yx = b; yy = e; yz = h;
		zx = c; zy = f; zz = i;
	}
	
	@Override
	public float xx() { return xx; }
	@Override
	public float xy() { return xy; }
	@Override
	public float xz() { return xz; }
	@Override
	public float yx() { return yx; }
	@Override
	public float yy() { return yy; }
	@Override
	public float yz() { return yz; }
	@Override
	public float zx() { return zx; }
	@Override
	public float zy() { return zy; }
	@Override
	public float zz() { return zz; }
	
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
		return 3;
	}
	
	@Override
	public int n() {
		return 3;
	}
	
	@Override
	public Matrix3 copy() {
		return new Matrix3(this);
	}
	
	@Override
	public String toString() {
		return Matrix3.toString(this, "%s");
	}
	
	public static String toString(Matrix3 m, String f) {
		return
			"[" + String.format(f, m.xx) + ", " + String.format(f, m.xy) + ", " + String.format(f, m.xz) + "]\n" +
			"[" + String.format(f, m.yx) + ", " + String.format(f, m.yy) + ", " + String.format(f, m.yz) + "]\n" +
			"[" + String.format(f, m.zx) + ", " + String.format(f, m.zy) + ", " + String.format(f, m.zz) + "]";
	}	
	
	protected static final <M extends Matrix3> M parseMatrix3(M m, String s) {
		if(m != null && s != null) {
			s = s.replace("[", "");
		    s = s.replace("]", "");
		    String[] t = s.split("\n");
		    
		    Vector3
		    	r0 = new Vector3(),
		    	r1 = new Vector3(),
		    	r2 = new Vector3();
		    switch(t.length) {
		        default:
		        case 3:
		        	Vector3.parseVector3(r2, t[2]);
		        case 2:
		        	Vector3.parseVector3(r1, t[1]);
		        case 1:
		        	Vector3.parseVector3(r0, t[0]);
		        case 0:
		    }
		    m.mSetRowMajor(r0, r1, r2);
		}	    
	    return m;
	}
	
	public static Matrix3 parseMatrix3(String s) {
		return Matrix3.parseMatrix3(new Matrix3(), s);
	}
	
	public static Matrix3 identity() {
		return new Matrix3(
			Matrix.ROW_MAJOR,
			1, 0, 0, 
			0, 1, 0, 
			0, 0, 1
		);
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
		
		public Matrix3.Mutable xx(float xx) {
			this.xx = xx;
			return this;
		}
		
		public Matrix3.Mutable xy(float xy) {
			this.xy = xy;
			return this;
		}
		
		public Matrix3.Mutable xz(float xz) {
			this.xz = xz;
			return this;
		}
		
		public Matrix3.Mutable yx(float yx) {
			this.yx = yx;
			return this;
		}
		
		public Matrix3.Mutable yy(float yy) {
			this.yy = yy;
			return this;
		}
		
		public Matrix3.Mutable yz(float yz) {
			this.yz = yz;
			return this;
		}
		
		public Matrix3.Mutable zx(float zx) {
			this.zx = zx;
			return this;
		}
		
		public Matrix3.Mutable zy(float zy) {
			this.zy = zy;
			return this;
		}
		
		public Matrix3.Mutable zz(float zz) {
			this.zz = zz;
			return this;
		}
		
		public Matrix3.Mutable row(int i, float x, float y, float z) {
			switch(i) {
				case 0: xx = x; xy = y; xz = z; break;
				case 1: yx = x; yy = y; yz = z; break;
				case 2: zx = x; zy = y; zz = z; break;
			}
			return this;
		}
		
		public Matrix3.Mutable row(int i, Vector r0) {
			switch(i) {
				case 0: xx = r0.x(); xy = r0.y(); xz = r0.z(); break;
				case 1: yx = r0.x(); yy = r0.y(); yz = r0.z(); break;
				case 2: zx = r0.x(); zy = r0.y(); zz = r0.z(); break;
			}
			return this;
		}
		
		public Matrix3.Mutable col(int j, float x, float y, float z) {
			switch(j) {
				case 0: xx = x; yx = y; zx = z; break;
				case 1: xy = x; yy = y; zy = z; break;
				case 2: xz = x; yz = y; zz = z; break;
			}
			return this;
		}
		
		public Matrix3.Mutable col(int j, Vector c0) {
			switch(j) {
				case 0: xx = c0.x(); yx = c0.y(); zx = c0.z(); break;
				case 1: xy = c0.x(); yy = c0.y(); zy = c0.z(); break;
				case 2: xz = c0.x(); yz = c0.y(); zz = c0.z(); break;
			}
			return this;
		}
		
		public Matrix3.Mutable set(Matrix m) {
			mSet(m);
			return this;
		}
		
		public Matrix3.Mutable setRowMajor(
			Vector r0,
			Vector r1,
			Vector r2
		) {
			mSetRowMajor(r0, r1, r2);
			return this;
		}
		
		public Matrix3.Mutable setRowMajor(
			float a, float b, float c, 
			float d, float e, float f,
			float g, float h, float i
		) {
			mSetRowMajor(a, b, c, d, e, f, g, h, i);
			return this;
		}
		
		public Matrix3.Mutable setColMajor(
			Vector c0,
			Vector c1,
			Vector c2
		) {
			mSetColMajor(c0, c1, c2);
			return this;
		}
		
		public Matrix3.Mutable setColMajor(
			float a, float b, float c, 
			float d, float e, float f,
			float g, float h, float i
		) {
			mSetColMajor(a, b, c, d, e, f, g, h, i);
			return this;
		}
		
		public static Matrix3.Mutable parseMatrix3(String s) {
			return Matrix3.parseMatrix3(new Matrix3.Mutable(), s);
		}
		
		public static Matrix3.Mutable identity() {
			return new Matrix3.Mutable(
				Matrix.ROW_MAJOR, 
				1, 0, 0, 
				0, 1, 0,
				0, 0, 1
			);
		}
	}
}
