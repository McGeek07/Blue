package blue.math;

public class Matrix2 extends Matrix {
	private static final long 
		serialVersionUID = 1L;
	protected float
		xx, xy,
		yx, yy;
	
	public Matrix2() {
		//do nothing
	}
	
	public Matrix2(Matrix m) {
		mSet(m);
	}
	
	public Matrix2(
		int mode,
		Vector v0,
		Vector v1
	) {
		switch(mode) {
			case ROW_MAJOR: mSetRowMajor(v0, v1); break;
			case COL_MAJOR: mSetColMajor(v0, v1); break;
		}
	}
	
	public Matrix2(
		int mode,
		float a, float b,
		float c, float d
	) {
		switch(mode) {
			case ROW_MAJOR: mSetRowMajor(a, b, c, d); break;
			case COL_MAJOR: mSetColMajor(a, b, c, d); break;
		}
	}
	
	protected void mSet(Matrix m) {
		xx = m.xx(); xy = m.xy();		
		yx = m.yx(); yy = m.yy();
	}
	
	protected void mSetRowMajor(
		Vector r0,
		Vector r1
	) {
		xx = r0.x(); xy = r0.y();
		yx = r1.x(); yy = r1.y();
	}
	
	protected void mSetColMajor(
		Vector c0,
		Vector c1
	) {
		xx = c0.x(); xy = c1.x();
		yx = c0.y(); yy = c1.y();
	}
	
	protected void mSetRowMajor(
		float a, float b,
		float c, float d
	) {
		xx = a; xy = b;
		yx = c; yy = d;
	}
	
	protected void mSetColMajor(
		float a, float b,
		float c, float d
	) {
		xx = a; xy = c;
		yx = b; yy = d;
	}
	
	@Override
	public float xx() { return xx; }
	@Override
	public float xy() { return xy; }
	@Override
	public float yx() { return yx; }
	@Override
	public float yy() { return yy; }

	@Override
	public Vector2 row(int i) {
		switch(i) {
			case 0: return new Vector2(xx, xy);
			case 1: return new Vector2(yx, yy);
		}
		return null;
	}

	@Override
	public Vector2 col(int j) {
		switch(j) {
			case 0: return new Vector2(xx, yx);
			case 1: return new Vector2(xy, yy);
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
	public Matrix2 copy() {
		return new Matrix2(this);
	}
	
	@Override
	public String toString() {
		return Matrix2.toString(this, "%s");
	}
	
	public static String toString(Matrix2 m, String f) {
		return
			"[" + String.format(f, m.xx) + ", " + String.format(f, m.xy) + "]\n" +
			"[" + String.format(f, m.yx) + ", " + String.format(f, m.yy) + "]";			
	}	
	
	protected static final <M extends Matrix2> M parseMatrix2(M m, String s) {
		if(m != null && s != null) {
			s = s.replace("[", "");
	        s = s.replace("]", "");
	        String[] t = s.split("\n");
	        
	        Vector2
	        	r0 = new Vector2(),
	        	r1 = new Vector2();    
	        switch(t.length) {
		        default:
		        case 2:
		        	Vector2.parseVector2(r1, t[1]);
		        case 1:
		        	Vector2.parseVector2(r0, t[0]);
		        case 0:
	        }
	        m.mSetRowMajor(r0, r1);
		}       
        return m;
	}
	
	public static Matrix2 parseMatrix2(String s) {
		return Matrix2.parseMatrix2(new Matrix2(), s);
	}
	
	public static Matrix2 identity() {
		return new Matrix2(
			Matrix.ROW_MAJOR,
			1, 0, 
			0, 1
		);
	}
	
	public static class Mutable extends Matrix2 {
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
			Vector v1
		) {
			super(mode, v0, v1);
		}
		
		public Mutable(
			int mode,
			float a, float b,
			float c, float d
		) {
			super(mode, a, b, c, d);
		}
		
		public Matrix2.Mutable xx(float xx) {
			this.xx = xx;
			return this;
		}
		
		public Matrix2.Mutable xy(float xy) {
			this.xy = xy;
			return this;
		}
		
		public Matrix2.Mutable yx(float yx) {
			this.yx = yx;
			return this;
		}
		
		public Matrix2.Mutable yy(float yy) {
			this.yy = yy;
			return this;
		}
		
		public Matrix2.Mutable row(int i, float x, float y) {
			switch(i) {
				case 0: xx = x; xy = y; break;
				case 1: yx = x; yy = y; break;
			}
			return this;
		}
		
		public Matrix2.Mutable row(int i, Vector r0) {
			switch(i) {
				case 0: xx = r0.x(); xy = r0.y(); break;
				case 1: yx = r0.x(); yy = r0.y(); break;
			}
			return this;
		}
		
		public Matrix2.Mutable col(int j, float x, float y) {
			switch(j) {
				case 0: xx = x; yx = y; break;
				case 1: xy = x; yy = y; break;
			}
			return this;
		}
		
		public Matrix2.Mutable col(int j, Vector c0) {
			switch(j) {
				case 0: xx = c0.x(); yx = c0.y(); break;
				case 1: xy = c0.x(); yy = c0.y(); break;
			}
			return this;
		}
		
		public Matrix2.Mutable set(Matrix m) {
			mSet(m);
			return this;
		}
		
		public Matrix2.Mutable setRowMajor(
			Vector r0,
			Vector r1
		) {
			mSetRowMajor(r0, r1);
			return this;
		}
		
		public Matrix2.Mutable setRowMajor(
			float a, float b,
			float c, float d
		) {
			mSetRowMajor(a, b, c, d);
			return this;
		}
		
		public Matrix2.Mutable setColMajor(
			Vector c0,
			Vector c1
		) {
			mSetColMajor(c0, c1);
			return this;
		}
		
		public Matrix2.Mutable setColMajor(
			float a, float b,
			float c, float d
		) {
			mSetColMajor(a, b, c, d);
			return this;
		}
		
		public static Matrix2.Mutable parseMatrix2(String s) {
			return Matrix2.parseMatrix2(new Matrix2.Mutable(), s);
		}
		
		public static Matrix2.Mutable identity() {
			return new Matrix2.Mutable(
				Matrix.ROW_MAJOR,
				1, 0,
				0, 1
			);
		}
	}
}
