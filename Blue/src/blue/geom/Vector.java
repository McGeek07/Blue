package blue.geom;

import java.io.Serializable;

import blue.util.Copyable;

public abstract class Vector implements Serializable, Copyable<Vector> {
	private static final long 
		serialVersionUID = 1L;
	public static final int
		X = 0,
		Y = 1,
		Z = 2,
		W = 3;
	
	public float x() { return 0f; }
	public float y() { return 0f; }
	public float z() { return 0f; }
	public float w() { return 0f; }
	public int n() { return 0; }
	
	public float get(int j) {
		switch(j) {
			case X: return x();
			case Y: return y();
			case Z: return z();
			case W: return w();
		}
		return 0f;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Vector ? Vector.compare(this, (Vector)o) == 0 : false;
	}
	
	public static int compare(Vector a, Vector b) {	
		float tmp;
		switch(Math.max(a.n(), b.n())) {
			case 4:
				if((tmp = a.x() - b.x()) != 0) return tmp > 0 ? 1 : -1;
				if((tmp = a.y() - b.y()) != 0) return tmp > 0 ? 1 : -1;
				if((tmp = a.z() - b.z()) != 0) return tmp > 0 ? 1 : -1;
				if((tmp = a.w() - b.w()) != 0) return tmp > 0 ? 1 : -1;
				return 0;
			case 3:
				if((tmp = a.x() - b.x()) != 0) return tmp > 0 ? 1 : -1;
				if((tmp = a.y() - b.y()) != 0) return tmp > 0 ? 1 : -1;
				if((tmp = a.z() - b.z()) != 0) return tmp > 0 ? 1 : -1;
				return 0;
			case 2:
				if((tmp = a.x() - b.x()) != 0) return tmp > 0 ? 1 : -1;
				if((tmp = a.y() - b.y()) != 0) return tmp > 0 ? 1 : -1;
				return 0;
		}
		throw new UnsupportedOperationException();
	}
	

	public static Vector2 add(Vector2 a, Vector2 b) {
		return new Vector2(
				a.x + b.x,
				a.y + b.y
				);
	}
	public static Vector3 add(Vector3 a, Vector3 b) {
		return new Vector3(
				a.x + b.x,
				a.y + b.y,
				a.z + b.z
				);
	}	
	public static Vector4 add(Vector4 a, Vector4 b) {
		return new Vector4(
				a.x + b.x,
				a.y + b.y,
				a.z + b.z,
				a.w + b.w
				);
	}	
	public static Vector2.Mutable add(Vector2.Mutable a, Vector2 b) {
		a.x += b.x;
		a.y += b.y;
		return a;
	}
	public static Vector3.Mutable add(Vector3.Mutable a, Vector3 b) {
		a.x += b.x;
		a.y += b.y;
		a.z += b.z;
		return a;
	}
	public static Vector4.Mutable add(Vector4.Mutable a, Vector4 b) {
		a.x += b.x;
		a.y += b.y;
		a.z += b.z;
		a.w += b.w;
		return a;
	}
	
	public static Vector2 sub(Vector2 a, Vector2 b) {
		return new Vector2(
				a.x - b.x,
				a.y - b.y
				);
	}
	public static Vector3 sub(Vector3 a, Vector3 b) {
		return new Vector3(
				a.x - b.x,
				a.y - b.y,
				a.z - b.z
				);
	}
	public static Vector4 sub(Vector4 a, Vector4 b) {
		return new Vector4(
				a.x - b.x,
				a.y - b.y,
				a.z - b.z,
				a.w - b.w
				);
	}
	public static Vector2.Mutable sub(Vector2.Mutable a, Vector2 b) {
		a.x -= b.x;
		a.y -= b.y;
		return a;
	}
	public static Vector3.Mutable sub(Vector3.Mutable a, Vector3 b) {
		a.x -= b.x;
		a.y -= b.y;
		a.z -= b.z;
		return a;
	}
	public static Vector4.Mutable sub(Vector4.Mutable a, Vector4 b) {
		a.x -= b.x;
		a.y -= b.y;
		a.z -= b.z;
		a.w -= b.w;
		return a;
	}
	
	public static Vector3 hom(Vector2 a) {
		return new Vector3(a.x, a.y, 1f);
	}	
	public static Vector4 hom(Vector3 a) {
		return new Vector4(a.x, a.y, a.z, 1f);
	}
	
	public static float dot(Vector2 a) {
		return a.x * a.x + a.y * a.y;
	}	
	public static float dot(Vector3 a) {
		return a.x * a.x + a.y * a.y + a.z * a.z;
	}	
	public static float dot(Vector4 a) {
		return a.x * a.x + a.y * a.y + a.z * a.z + a.w * a.w;
	}	
	public static float dot(Vector2 a, Vector2 b) {
		return a.x * b.x + a.y * b.y;
	}	
	public static float dot(Vector3 a, Vector3 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}	
	public static float dot(Vector4 a, Vector4 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
	}	
	
	public static Vector2 mul(Matrix2 a, Vector2 b) {
		return new Vector2(
				Vector.dot(a.v0, b),
				Vector.dot(a.v1, b)
				);
	}
	public static Vector2 mul(Matrix3 a, Vector2 b) {
		Vector3 h = Vector.hom(b);
		return new Vector2(
				Vector.dot(a.v0, h),
				Vector.dot(a.v1, h)
				);
	}
	public static Vector3 mul(Matrix3 a, Vector3 b) {
		return new Vector3(
				Vector.dot(a.v0, b),
				Vector.dot(a.v1, b),
				Vector.dot(a.v2, b)
				);
	}
	public static Vector3 mul(Matrix4 a, Vector3 b) {
		Vector4 h = Vector.hom(b);
		return new Vector3(
				Vector.dot(a.v0, h),
				Vector.dot(a.v1, h),
				Vector.dot(a.v2, h)
				);
	}
	public static Vector4 mul(Matrix4 a, Vector4 b) {
		return new Vector4(
				Vector.dot(a.v0, b),
				Vector.dot(a.v1, b),
				Vector.dot(a.v2, b),
				Vector.dot(a.v3, b)
				);
	}
	
	public static Vector2.Mutable mul(Matrix2 a, Vector2.Mutable b) {
		return b.set(
				Vector.dot(a.v0, b),
				Vector.dot(a.v1, b)
				);
	}
	public static Vector2.Mutable mul(Matrix3 a, Vector2.Mutable b) {
		Vector3 h = Vector.hom(b);
		return b.set(
				Vector.dot(a.v0, h),
				Vector.dot(a.v1, h)
				);
	}
	public static Vector3.Mutable mul(Matrix3 a, Vector3.Mutable b) {
		return b.set(
				Vector.dot(a.v0, b),
				Vector.dot(a.v1, b),
				Vector.dot(a.v2, b)
				);
	}
	public static Vector3.Mutable mul(Matrix4 a, Vector3.Mutable b) {
		Vector4 h = Vector.hom(b);
		return b.set(
				Vector.dot(a.v0, h),
				Vector.dot(a.v1, h),
				Vector.dot(a.v2, h)
				);
	}
	public static Vector4.Mutable mul(Matrix4 a, Vector4.Mutable b) {
		return b.set(
				Vector.dot(a.v0, b),
				Vector.dot(a.v1, b),
				Vector.dot(a.v2, b),
				Vector.dot(a.v3, b)
				);
	}
	
	public static java.awt.Color toColor3i(Vector v) {
		return new java.awt.Color((int)v.x(), (int)v.y(), (int)v.z(), 1f);
	}	
	public static java.awt.Color toColor3f(Vector v) {
		return new java.awt.Color(v.x(), v.y(), v.z(), 1f);
	}	
	public static java.awt.Color toColor4i(Vector v) {
		return new java.awt.Color((int)v.x(), (int)v.y(), (int)v.z(), (int)v.w());
	}	
	public static java.awt.Color toColor4f(Vector v) {
		return new java.awt.Color(v.x(), v.y(), v.z(), v.w());
	}}
