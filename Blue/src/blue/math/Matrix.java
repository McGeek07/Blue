package blue.math;

import java.io.Serializable;

import blue.util.Copyable;

public abstract class Matrix implements Serializable, Copyable<Matrix> {
	private static final long 
		serialVersionUID = 1L;
	public static final int
		ROW_MAJOR = 0,
		COL_MAJOR = 1;
	
	public float xx() { return 0f; }
	public float xy() { return 0f; }
	public float xz() { return 0f; }
	public float xw() { return 0f; }
	
	public float yx() { return 0f; }
	public float yy() { return 0f; }
	public float yz() { return 0f; }
	public float yw() { return 0f; }
	
	public float zx() { return 0f; }
	public float zy() { return 0f; }
	public float zz() { return 0f; }
	public float zw() { return 0f; }
	
	public float wx() { return 0f; }
	public float wy() { return 0f; }
	public float wz() { return 0f; }
	public float ww() { return 0f; }
	
	public abstract Vector row(int i);
	public abstract Vector col(int j);
	public int m() { return 0; }
	public int n() { return 0; }
	
	/* IMMUTABLE ADDITION */
	public static Matrix2 add(Matrix2 a, Matrix2 b) {
		return new Matrix2(
			Matrix.ROW_MAJOR,
			a.xx + b.xx, a.xy + b.xy,
			a.yx + b.yx, a.yy + b.yy
		);
	}
	
	public static Matrix3 add(Matrix3 a, Matrix3 b) {
		return new Matrix3(
			Matrix.ROW_MAJOR,
			a.xx + b.xx, a.xy + b.xy, a.xz + b.xz,
			a.yx + b.yx, a.yy + b.yy, a.yz + b.yz,
			a.zx + b.zx, a.zy + b.zy, a.zz + b.zz
		);
	}
	
	public static Matrix4 add(Matrix4 a, Matrix4 b) {
		return new Matrix4(
			Matrix.ROW_MAJOR,
			a.xx + b.xx, a.xy + b.xy, a.xz + b.xz, a.xw + b.xw,
			a.yx + b.yx, a.yy + b.yy, a.yz + b.yz, a.yw + b.yw,
			a.zx + b.zx, a.zy + b.zy, a.zz + b.zz, a.zw + b.zw,
			a.wx + b.wx, a.wy + b.wy, a.wz + b.wz, a.ww + b.ww
		);
	}
	
	/* IMMUTABLE SUBTRACTION */
	public static Matrix2 sub(Matrix2 a, Matrix2 b) {
		return new Matrix2(
			Matrix.ROW_MAJOR,
			a.xx - b.xx, a.xy - b.xy,
			a.yx - b.yx, a.yy - b.yy
		);
	}
	
	public static Matrix3 sub(Matrix3 a, Matrix3 b) {
		return new Matrix3(
			Matrix.ROW_MAJOR,
			a.xx - b.xx, a.xy - b.xy, a.xz - b.xz,
			a.yx - b.yx, a.yy - b.yy, a.yz - b.yz,
			a.zx - b.zx, a.zy - b.zy, a.zz - b.zz
		);
	}
	
	public static Matrix4 sub(Matrix4 a, Matrix4 b) {
		return new Matrix4(
			Matrix.ROW_MAJOR,
			a.xx - b.xx, a.xy - b.xy, a.xz - b.xz, a.xw - b.xw,
			a.yx - b.yx, a.yy - b.yy, a.yz - b.yz, a.yw - b.yw,
			a.zx - b.zx, a.zy - b.zy, a.zz - b.zz, a.zw - b.zw,
			a.wx - b.wx, a.wy - b.wy, a.wz - b.wz, a.ww - b.ww
		);
	}
	
	/* IMMUTABLE MULTIPLICATION */
	public static Matrix2 mul(Matrix2 a, Matrix2 b) {
		return new Matrix2(
			Matrix.ROW_MAJOR,
			Vector.dot(a.xx, a.xy, b.xx, b.yx), Vector.dot(a.xx, a.xy, b.xy, b.yy),
			Vector.dot(a.yx, a.yy, b.xx, b.yx), Vector.dot(a.yx, a.yy, b.xy, b.yy)
		);
	}
	
	public static Matrix3 mul(Matrix3 a, Matrix3 b) {
		return new Matrix3(
			Matrix.ROW_MAJOR,
			Vector.dot(a.xx, a.xy, a.xz, b.xx, b.yx, b.zx), Vector.dot(a.xx, a.xy, a.xz, b.xy, b.yy, b.zy), Vector.dot(a.xx, a.xy, a.xz, b.xz, b.yz, b.zz),
			Vector.dot(a.yx, a.yy, a.yz, b.xx, b.yx, b.zx), Vector.dot(a.yx, a.yy, a.yz, b.xy, b.yy, b.zy), Vector.dot(a.yx, a.yy, a.yz, b.xz, b.yz, b.zz),
			Vector.dot(a.zx, a.zy, a.zz, b.xx, b.yx, b.zx), Vector.dot(a.zx, a.zy, a.zz, b.xy, b.yy, b.zy), Vector.dot(a.zx, a.zy, a.zz, b.xz, b.yz, b.zz)
		);
	}
	
	public static Matrix4 mul(Matrix4 a, Matrix4 b) {
		return new Matrix4(
			Matrix.ROW_MAJOR,
			Vector.dot(a.xx, a.xy, a.xz, a.xw, b.xx, b.yx, b.zx, b.wx), Vector.dot(a.xx, a.xy, a.xz, a.xw, b.xy, b.yy, b.zy, b.wy), Vector.dot(a.xx, a.xy, a.xz, a.xw, b.xz, b.yz, b.zz, b.wz), Vector.dot(a.xx, a.xy, a.xz, a.xw, b.xw, b.yw, b.zw, b.ww),
			Vector.dot(a.yx, a.yy, a.yz, a.yw, b.xx, b.yx, b.zx, b.wx), Vector.dot(a.yx, a.yy, a.yz, a.yw, b.xy, b.yy, b.zy, b.wy), Vector.dot(a.yx, a.yy, a.yz, a.yw, b.xz, b.yz, b.zz, b.wz), Vector.dot(a.yx, a.yy, a.yz, a.yw, b.xw, b.yw, b.zw, b.ww),
			Vector.dot(a.zx, a.zy, a.zz, a.zw, b.xx, b.yx, b.zx, b.wx), Vector.dot(a.zx, a.zy, a.zz, a.zw, b.xy, b.yy, b.zy, b.wy), Vector.dot(a.zx, a.zy, a.zz, a.zw, b.xz, b.yz, b.zz, b.wz), Vector.dot(a.zx, a.zy, a.zz, a.zw, b.xw, b.yw, b.zw, b.ww),
			Vector.dot(a.wx, a.wy, a.wz, a.ww, b.xx, b.yx, b.zx, b.wx), Vector.dot(a.wx, a.wy, a.wz, a.ww, b.xy, b.yy, b.zy, b.wy), Vector.dot(a.wx, a.wy, a.wz, a.ww, b.xz, b.yz, b.zz, b.wz), Vector.dot(a.wx, a.wy, a.wz, a.ww, b.xw, b.yw, b.zw, b.ww)
		);
	}
	
	/* MUTABLE ADDITION */
	public static Matrix2.Mutable m_add(Matrix2.Mutable a, Matrix2 b) {
		return a.setRowMajor(
			a.xx + b.xx, a.xy + b.xy,
			a.yx + b.yx, a.yy + b.yy
		);
	}
	
	public static Matrix3.Mutable m_add(Matrix3.Mutable a, Matrix3 b) {
		return a.setRowMajor(
			a.xx + b.xx, a.xy + b.xy, a.xz + b.xz,
			a.yx + b.yx, a.yy + b.yy, a.yz + b.yz,
			a.zx + b.zx, a.zy + b.zy, a.zz + b.zz
		);
	}
	
	public static Matrix4.Mutable m_add(Matrix4.Mutable a, Matrix4 b) {
		return a.setRowMajor(
			a.xx + b.xx, a.xy + b.xy, a.xz + b.xz, a.xw + b.xw,
			a.yx + b.yx, a.yy + b.yy, a.yz + b.yz, a.yw + b.yw,
			a.zx + b.zx, a.zy + b.zy, a.zz + b.zz, a.zw + b.zw,
			a.wx + b.wx, a.wy + b.wy, a.wz + b.wz, a.ww + b.ww
		);
	}
	
	/* MUTABLE SUBTRACTION */
	public static Matrix2.Mutable m_sub(Matrix2.Mutable a, Matrix2 b) {
		return a.setRowMajor(
			a.xx - b.xx, a.xy - b.xy,
			a.yx - b.yx, a.yy - b.yy
		);
	}
	
	public static Matrix3.Mutable m_sub(Matrix3.Mutable a, Matrix3 b) {
		return a.setRowMajor(
			a.xx - b.xx, a.xy - b.xy, a.xz - b.xz,
			a.yx - b.yx, a.yy - b.yy, a.yz - b.yz,
			a.zx - b.zx, a.zy - b.zy, a.zz - b.zz
		);
	}
	
	public static Matrix4.Mutable m_sub(Matrix4.Mutable a, Matrix4 b) {
		return a.setRowMajor(
			a.xx - b.xx, a.xy - b.xy, a.xz - b.xz, a.xw - b.xw,
			a.yx - b.yx, a.yy - b.yy, a.yz - b.yz, a.yw - b.yw,
			a.zx - b.zx, a.zy - b.zy, a.zz - b.zz, a.zw - b.zw,
			a.wx - b.wx, a.wy - b.wy, a.wz - b.wz, a.ww - b.ww
		);
	}
	
	/* MUTABLE MULTIPLICATION */
	public static Matrix2.Mutable m_mul(Matrix2.Mutable a, Matrix2 b) {
		return a.setRowMajor(
			Vector.dot(a.xx, a.xy, b.xx, b.yx), Vector.dot(a.xx, a.xy, b.xy, b.yy),
			Vector.dot(a.yx, a.yy, b.xx, b.yx), Vector.dot(a.yx, a.yy, b.xy, b.yy)
		);
	}
	
	public static Matrix3.Mutable m_mul(Matrix3.Mutable a, Matrix3 b) {
		return a.setRowMajor(
			Vector.dot(a.xx, a.xy, a.xz, b.xx, b.yx, b.zx), Vector.dot(a.xx, a.xy, a.xz, b.xy, b.yy, b.zy), Vector.dot(a.xx, a.xy, a.xz, b.xz, b.yz, b.zz),
			Vector.dot(a.yx, a.yy, a.yz, b.xx, b.yx, b.zx), Vector.dot(a.yx, a.yy, a.yz, b.xy, b.yy, b.zy), Vector.dot(a.yx, a.yy, a.yz, b.xz, b.yz, b.zz),
			Vector.dot(a.zx, a.zy, a.zz, b.xx, b.yx, b.zx), Vector.dot(a.zx, a.zy, a.zz, b.xy, b.yy, b.zy), Vector.dot(a.zx, a.zy, a.zz, b.xz, b.yz, b.zz)
		);
	}
	
	public static Matrix4.Mutable m_mul(Matrix4.Mutable a, Matrix4 b) {
		return a.setRowMajor(
			Vector.dot(a.xx, a.xy, a.xz, a.xw, b.xx, b.yx, b.zx, b.wx), Vector.dot(a.xx, a.xy, a.xz, a.xw, b.xy, b.yy, b.zy, b.wy), Vector.dot(a.xx, a.xy, a.xz, a.xw, b.xz, b.yz, b.zz, b.wz), Vector.dot(a.xx, a.xy, a.xz, a.xw, b.xw, b.yw, b.zw, b.ww),
			Vector.dot(a.yx, a.yy, a.yz, a.yw, b.xx, b.yx, b.zx, b.wx), Vector.dot(a.yx, a.yy, a.yz, a.yw, b.xy, b.yy, b.zy, b.wy), Vector.dot(a.yx, a.yy, a.yz, a.yw, b.xz, b.yz, b.zz, b.wz), Vector.dot(a.yx, a.yy, a.yz, a.yw, b.xw, b.yw, b.zw, b.ww),
			Vector.dot(a.zx, a.zy, a.zz, a.zw, b.xx, b.yx, b.zx, b.wx), Vector.dot(a.zx, a.zy, a.zz, a.zw, b.xy, b.yy, b.zy, b.wy), Vector.dot(a.zx, a.zy, a.zz, a.zw, b.xz, b.yz, b.zz, b.wz), Vector.dot(a.zx, a.zy, a.zz, a.zw, b.xw, b.yw, b.zw, b.ww),
			Vector.dot(a.wx, a.wy, a.wz, a.ww, b.xx, b.yx, b.zx, b.wx), Vector.dot(a.wx, a.wy, a.wz, a.ww, b.xy, b.yy, b.zy, b.wy), Vector.dot(a.wx, a.wy, a.wz, a.ww, b.xz, b.yz, b.zz, b.wz), Vector.dot(a.wx, a.wy, a.wz, a.ww, b.xw, b.yw, b.zw, b.ww)
		);
	}	
}
