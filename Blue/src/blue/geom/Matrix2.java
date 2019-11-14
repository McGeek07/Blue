package blue.geom;

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
		this.mSet(m);
	}
	
	public Matrix2(
			int mode,
			Vector v0,
			Vector v1
			) {
		this.mSet(mode, v0, v1);
	}
	
	public Matrix2(
			int mode,
			float a, float b,
			float c, float d
			) {
		this.mSet(mode, a, b, c, d);
	}
	
	protected void mSet(Matrix m) {
		this.xx = m.xx(); this.xy = m.xy();		
		this.yx = m.yx(); this.yy = m.yy();
	}
	
	protected void mSet(
			int mode,
			Vector v0,
			Vector v1
			) {
		switch(mode) {
			case ROW_MAJOR: mSetRowMajor(v0, v1); break;
			case COL_MAJOR: mSetColMajor(v0, v1); break;
		}
	}
	
	protected void mSet(
			int mode,
			float a, float b,
			float c, float d
			) {
		switch(mode) {
			case ROW_MAJOR: mSetRowMajor(a, b, c, d); break;
			case COL_MAJOR: mSetColMajor(a, b, c, d); break;
		}
	}
	
	protected void mSetRowMajor(
			Vector v0,
			Vector v1
			) {
		mSetRowMajor(
				v0.x(), v0.y(),
				v1.x(), v1.y()
				);
	}
	
	protected void mSetColMajor(
			Vector v0,
			Vector v1
			) {
		mSetColMajor(
				v0.x(), v0.y(),
				v1.x(), v1.y()
				);
	}
	
	protected void mSetRowMajor(
			float a, float b,
			float c, float d
			) {
		this.xx = a; this.xy = b;
		this.yx = c; this.yy = d;
	}
	
	protected void mSetColMajor(
			float a, float b,
			float c, float d
			) {
		this.xx = a; this.xy = c;
		this.yx = b; this.yy = d;
	}
	
	@Override
	public float xx() { return this.xx; }
	@Override
	public float xy() { return this.xy; }
	@Override
	public float yx() { return this.yx; }
	@Override
	public float yy() { return this.yy; }

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
	
	public static String toString(Matrix2 m2, String format) {
		return
				"[" + String.format(format, m2.xx) + ", " + String.format(format, m2.xy) + "]\n" +
				"[" + String.format(format, m2.yx) + ", " + String.format(format, m2.yy) + "]";				
	}	
	
	protected static final <M extends Matrix2> M fromString(M m2, String str) {
		if(m2 == null)
			throw new IllegalArgumentException("Null Matrix");
        if (str == null)
            throw new IllegalArgumentException("Null String");
        if ((str = str.trim()).isEmpty())
            throw new IllegalArgumentException("Empty String");
        str = str.replace("[", "");
        str = str.replace("]", "");
        String[] tmp = str.split("\n");
        
        Vector2
        	v0 = new Vector2(),
        	v1 = new Vector2();      
        switch(tmp.length) {
	        default:
	        case 2:
	        	Vector2.fromString(v1, tmp[1]);
	        case 1:
	        	Vector2.fromString(v0, tmp[0]);
	        case 0:
        }
        m2.mSetRowMajor(v0, v1);
        
        return m2;
	}
	
	public static Matrix2 fromString(String str) {
		return Matrix2.fromString(new Matrix2(), str);
	}
	
	public static Matrix2 identity() {
		return new Matrix2(Matrix.ROW_MAJOR, 1, 0, 0, 1);
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
		
		public Matrix2.Mutable set(Matrix m) {
			this.mSet(m);
			return this;
		}
		
		public Matrix2.Mutable set(
				int mode, 
				Vector v0,
				Vector v1
				) {
			this.mSet(mode, v0, v1);
			return this;
		}
		
		public Matrix2.Mutable set(
				int mode,
				float a, float b,
				float c, float d
				) {
			this.mSet(mode, a,  b, c, d);
			return this;
		}
		
		public Matrix2.Mutable setRowMajor(
				Vector v0,
				Vector v1
				) {
			this.mSetRowMajor(v0, v1);
			return this;
		}
		
		public Matrix2.Mutable setRowMajor(
				float a, float b,
				float c, float d
				) {
			this.mSetRowMajor(a, b, c, d);
			return this;
		}
		
		public Matrix2.Mutable setColMajor(
				Vector v0,
				Vector v1
				) {
			this.mSetColMajor(v0, v1);
			return this;
		}
		
		public Matrix2.Mutable setColMajor(
				float a, float b,
				float c, float d
				) {
			this.mSetColMajor(a, b, c, d);
			return this;
		}
		
		public static Matrix2.Mutable fromString(String str) {
			return Matrix2.fromString(new Matrix2.Mutable(), str);
		}
		
		public static Matrix2.Mutable identity() {
			return new Matrix2.Mutable(Matrix.ROW_MAJOR, 1, 0, 0, 1);
		}
	}
}
