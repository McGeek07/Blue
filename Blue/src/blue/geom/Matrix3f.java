package blue.geom;

public class Matrix3f extends Matrix<Vector3f> {
	private static final long
		serialVersionUID = 1L;
	
	protected final Vector3f.Mutable
		v0 = new Vector3f.Mutable(),
		v1 = new Vector3f.Mutable(),
		v2 = new Vector3f.Mutable();
	
	public Matrix3f() {
		//do nothing
	}
	
	public Matrix3f(
			Matrix<?> m
			) {
		this.v0.set(m.row(0));
		this.v1.set(m.row(1));
		this.v2.set(m.row(2));
	}
	
	public Matrix3f(
			Vector r0,
			Vector r1,
			Vector r2
			) {
		this.v0.set(r0);
		this.v1.set(r1);
		this.v2.set(r2);
	}
	
	public Matrix3f(
			float a, float b, float c,
			float d, float e, float f,
			float g, float h, float i
			) {
		this.v0.set(a, b, c);
		this.v1.set(d, e, f);
		this.v2.set(g, h, i);
	}

	@Override
	public float get(int i, int j) {
		switch(i) {
			case 0: return v0.get(j);
			case 1: return v1.get(j);
			case 2: return v2.get(j);
		}
		return 0f;
	}

	@Override
	public Vector3f row(int i) {
		return new Vector3f(
				get(i, 0),
				get(i, 1),
				get(i, 2)
				);
	}

	@Override
	public Vector3f col(int j) {
		return new Vector3f(
				get(0, j),
				get(1, j),
				get(2, j)
				);
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
	public Matrix3f copy() {
		return new Matrix3f(this);
	}
}
