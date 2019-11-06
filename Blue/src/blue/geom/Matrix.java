package blue.geom;

import java.io.Serializable;

import blue.util.Copyable;

public abstract class Matrix<T extends Vector> implements Serializable, Copyable<Matrix<T>> {
	private static final long 
		serialVersionUID = 1L;
	
	public abstract float get(int i, int j);
	public abstract T row(int i);
	public abstract T col(int j);
	public int m() { return 0; }
	public int n() { return 0; }
	
	public static Matrix2f add(Matrix2f a, Matrix2f b) {
		return new Matrix2f(
				a.v0.x + b.v0.x, a.v0.y + b.v0.y,
				a.v1.x + b.v1.x, a.v1.y + b.v1.y
				);
	}
	public static Matrix3f add(Matrix3f a, Matrix3f b) {
		return new Matrix3f(
				a.v0.x + b.v0.x, a.v0.y + b.v0.y, a.v0.z + b.v0.z,
				a.v1.x + b.v1.x, a.v1.y + b.v1.y, a.v1.z + b.v1.z,
				a.v2.x + b.v2.x, a.v2.y + b.v2.y, a.v2.z + a.v2.z
				);
	}
	public static Matrix4f add(Matrix4f a, Matrix4f b) {
		return new Matrix4f(
				a.v0.x + b.v0.x, a.v0.y + b.v0.y, a.v0.z + b.v0.z, a.v0.w + b.v0.w,
				a.v1.x + b.v1.x, a.v1.y + b.v1.y, a.v1.z + b.v1.z, a.v1.w + b.v1.w,
				a.v2.x + b.v2.x, a.v2.y + b.v2.y, a.v2.z + b.v2.z, a.v2.w + b.v2.w,
				a.v3.x + b.v2.x, a.v3.y + b.v3.y, a.v3.z + b.v3.z, a.v3.w + b.v3.w
				);
	}
}
