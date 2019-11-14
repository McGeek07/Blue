package blue.geom;

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
}
