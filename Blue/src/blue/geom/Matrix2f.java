package blue.geom;

public class Matrix2f extends Matrix<Vector2f> {
	private static final long 
		serialVersionUID = 1L;
	
	protected final Vector2f.Mutable
		v0 = new Vector2f.Mutable(),
		v1 = new Vector2f.Mutable();
	
	public Matrix2f() {
		//do nothing
	}
	
	public Matrix2f(
			Matrix<?> m
			) {
		this.v0.set(m.row(0));
		this.v1.set(m.row(1));
	}
	
	public Matrix2f(
			Vector r0,
			Vector r1
			) {
		this.v0.set(r0);
		this.v1.set(r1);
	}
	
	public Matrix2f(
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
	public Vector2f row(int i) {
		return new Vector2f(
				get(i, 0),
				get(i, 1)
				);
	}

	@Override
	public Vector2f col(int j) {
		return new Vector2f(
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
	
	public static Matrix2f identity() {
		return new Matrix2f(
				1f, 0f,
				0f, 1f
				);
	}

	@Override
	public Matrix<Vector2f> copy() {
		return new Matrix2f(this);
	}
}
