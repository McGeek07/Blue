package blue.geom;

public class Matrix3 extends Matrix<Vector3> {
	private static final long
		serialVersionUID = 1L;
	
	protected final Vector3.Mutable
		v0 = new Vector3.Mutable(),
		v1 = new Vector3.Mutable(),
		v2 = new Vector3.Mutable();
	
	public Matrix3() {
		//do nothing
	}
	
	public Matrix3(
			Matrix<?> m
			) {
		this.v0.set(m.row(0));
		this.v1.set(m.row(1));
		this.v2.set(m.row(2));
	}
	
	public Matrix3(
			Vector r0,
			Vector r1,
			Vector r2
			) {
		this.v0.set(r0);
		this.v1.set(r1);
		this.v2.set(r2);
	}
	
	public Matrix3(
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
	public Vector3 row(int i) {
		return new Vector3(
				get(i, 0),
				get(i, 1),
				get(i, 2)
				);
	}

	@Override
	public Vector3 col(int j) {
		return new Vector3(
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
	public Matrix3 copy() {
		return new Matrix3(this);
	}
	
	public static Matrix3 identity() {
		return new Matrix3(
				1f, 0f, 0f,
				0f, 1f, 0f,
				0f, 0f, 1f
				);
	}
}
