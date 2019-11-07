package blue.geom;

public class Matrix2 extends Matrix<Vector2> {
	private static final long 
		serialVersionUID = 1L;
	
	protected final Vector2.Mutable
		v0 = new Vector2.Mutable(),
		v1 = new Vector2.Mutable();
	
	public Matrix2() {
		//do nothing
	}
	
	public Matrix2(
			Matrix<?> m
			) {
		this.v0.set(m.row(0));
		this.v1.set(m.row(1));
	}
	
	public Matrix2(
			Vector r0,
			Vector r1
			) {
		this.v0.set(r0);
		this.v1.set(r1);
	}
	
	public Matrix2(
			float a, float b,
			float c, float d
			) {
		this.v0.set(a, b);
		this.v1.set(c, d);
	}

	@Override
	public float get(int i, int j) {
		switch(i) {
			case 0: return v0.get(j);
			case 1: return v1.get(j);
		}
		return 0f;
	}

	@Override
	public Vector2 row(int i) {
		return new Vector2(
				get(i, 0),
				get(i, 1)
				);
	}

	@Override
	public Vector2 col(int j) {
		return new Vector2(
				get(0, j),
				get(1, j)
				);
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
	public Matrix<Vector2> copy() {
		return new Matrix2(this);
	}
	
	@Override
	public String toString() {
		return toString(this, "%s");
	}
	
	public static Matrix2 identity() {
		return new Matrix2(
				1f, 0f,
				0f, 1f
				);
	}
	
	public static String toString(Matrix2 m2, String format) {
		return 
				"[" + String.format(format, m2.v0.x) + ", " + String.format(format, m2.v0.y) + "]\n" +
				"[" + String.format(format, m2.v1.x) + ", " + String.format(format, m2.v1.y) + "]";
	}
	
	protected static final <M extends Matrix2> M parseMatrix2f(M m2, String str) {
		if(m2 == null)
            throw new IllegalArgumentException("Null Matrix");
        if (str == null)
            throw new IllegalArgumentException("Null String");
        if ((str = str.trim()).isEmpty())
            throw new IllegalArgumentException("Empty String");
        str = str.replace("[", "");
        str = str.replace("]", "");
        String[] temp = str.split("\n");
        
        switch(temp.length) {
	        default:
	        case 2:
	        	Vector2.parseVector2(m2.v1, temp[1]);
	        case 1:
	        	Vector2.parseVector2(m2.v0, temp[0]);
	        case 0:
        }
        return m2;
	}
	
	public static Matrix2 parseMatrix2f(String str) {
		return Matrix2.parseMatrix2f(new Matrix2(), str);
	}
	
	public static class Mutable extends Matrix2 {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable() {
			super();
		}
		
		public Mutable(
				Matrix<?> m
				) {
			super(m);
		}
		
		public Mutable(
				Vector r0,
				Vector r1
				) {
			super(r0, r1);
		}
		
		public Mutable(
				float a, float b,
				float c, float d
				) {
			super(a, b, c, d);
		}
		
		public Matrix2.Mutable set(
				Matrix<?> m
				) {
			this.v0.set(m.row(0));
			this.v1.set(m.row(1));
			return this;
		}
		
		public Matrix2.Mutable set(
				Vector r0,
				Vector r1
				) {
			this.v0.set(r0);
			this.v1.set(r1);
			return this;
		} 
		
		public Matrix2.Mutable set(
				float a, float b,
				float c, float d
				) {
			this.v0.set(a, b);
			this.v1.set(c, d);
			return this;
		}
		
		@Override
		public Matrix2.Mutable copy() {
			return new Matrix2.Mutable(this);
		}
		
		public static Matrix2.Mutable parseMatrix2f(String str) {
			return Matrix2.parseMatrix2f(new Matrix2.Mutable(), str);
		}
	}
}
