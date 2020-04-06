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
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Vector && Vector.compare(this, (Vector)o) == 0;
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

	/*IMMUTABLE ADDITION*/
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
	public static Vector2 add(Vector2 a, float x, float y) {
		return new Vector2(
				a.x + x,
				a.y + y
				);
	}
	public static Vector3 add(Vector3 a, float x, float y, float z) {
		return new Vector3(
				a.x + x,
				a.y + y,
				a.z + z
				);
	}	
	public static Vector4 add(Vector4 a, float x, float y, float z, float w) {
		return new Vector4(
				a.x + x,
				a.y + y,
				a.z + z,
				a.w + w
				);
	}
	
	/*IMMUTABLE SUBTRACTION*/
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
	public static Vector2 sub(Vector2 a, float x, float y) {
		return new Vector2(
				a.x - x,
				a.y - y
				);
	}
	public static Vector3 sub(Vector3 a, float x, float y, float z) {
		return new Vector3(
				a.x - x,
				a.y - y,
				a.z - z
				);
	}	
	public static Vector4 sub(Vector4 a, float x, float y, float z, float w) {
		return new Vector4(
				a.x - x,
				a.y - y,
				a.z - z,
				a.w - w
				);
	}
	
	/*IMMUTABLE MULTIPLICATION*/
	public static Vector2 mul(Vector2 a, Vector2 b) {
		return new Vector2(
				a.x * b.x,
				a.y * b.y
				);
	}
	public static Vector3 mul(Vector3 a, Vector3 b) {
		return new Vector3(
				a.x * b.x,
				a.y * b.y,
				a.z * b.z
				);
	}	
	public static Vector4 mul(Vector4 a, Vector4 b) {
		return new Vector4(
				a.x * b.x,
				a.y * b.y,
				a.z * b.z,
				a.w * b.w
				);
	}
	public static Vector2 mul(Vector2 a, float b) {
		return new Vector2(
				a.x * b,
				a.y * b
				);
	}
	public static Vector3 mul(Vector3 a, float b) {
		return new Vector3(
				a.x * b,
				a.y * b,
				a.z * b
				);
	}
	public static Vector4 mul(Vector4 a, float b) {
		return new Vector4(
				a.x * b,
				a.y * b,
				a.z * b,
				a.w * b
				);
	}
	public static Vector2 mul(Vector2 a, float x, float y) {
		return new Vector2(
				a.x * x,
				a.y * y
				);
	}
	public static Vector3 mul(Vector3 a, float x, float y, float z) {
		return new Vector3(
				a.x * x,
				a.y * y,
				a.z * z
				);
	}	
	public static Vector4 mul(Vector4 a, float x, float y, float z, float w) {
		return new Vector4(
				a.x * x,
				a.y * y,
				a.z * z,
				a.w * w
				);
	}
	public static Vector2 mul(Vector2 a, Matrix2 b) {
		return new Vector2(
				Vector.dot(a, b.xx, b.yx),
				Vector.dot(a, b.xy, b.yy)
				);
	}	
	public static Vector2 mul(Vector2 a, Matrix3 b) {
		Vector3 h = Vector.hom(a);
		return new Vector2(
				Vector.dot(h, b.xx, b.yx, b.zx),
				Vector.dot(h, b.xy, b.yy, b.zy)
				);
	}
	public static Vector3 mul(Vector3 a, Matrix3 b) {
		return new Vector3(
				Vector.dot(a, b.xx, b.yx, b.zx),
				Vector.dot(a, b.xy, b.yy, b.zy),
				Vector.dot(a, b.xz, b.yz, b.zz)
				);
	}	
	public static Vector3 mul(Vector3 a, Matrix4 b) {
		Vector4 h = Vector.hom(a);
		return new Vector3(
				Vector.dot(h, b.xx, b.yx, b.zx, b.wx),
				Vector.dot(h, b.xy, b.yy, b.zy, b.wy),
				Vector.dot(h, b.xz, b.yz, b.zz, b.wz)
				);
	}
	public static Vector4 mul(Vector4 a, Matrix4 b) {
		return new Vector4(
				Vector.dot(a, b.xx, b.yx, b.zx, b.wx),
				Vector.dot(a, b.xy, b.yy, b.zy, b.wy),
				Vector.dot(a, b.xz, b.yz, b.zz, b.wz),
				Vector.dot(a, b.xw, b.yw, b.zw, b.ww)
				);
	}
	
	/*IMMUTABLE DIVISION*/
	public static Vector2 div(Vector2 a, Vector2 b) {
		return new Vector2(
				a.x / b.x,
				a.y / b.y
				);
	}
	public static Vector3 div(Vector3 a, Vector3 b) {
		return new Vector3(
				a.x / b.x,
				a.y / b.y,
				a.z / b.z
				);
	}
	public static Vector4 div(Vector4 a, Vector4 b) {
		return new Vector4(
				a.x / b.x,
				a.y / b.y,
				a.z / b.z,
				a.w / b.w
				);
	}	
	public static Vector2 div(Vector2 a, float b) {
		return new Vector2(
				a.x / b,
				a.y / b
				);
	}
	public static Vector3 div(Vector3 a, float b) {
		return new Vector3(
				a.x / b,
				a.y / b,
				a.z / b
				);
	}
	public static Vector4 div(Vector4 a, float b) {
		return new Vector4(
				a.x / b,
				a.y / b,
				a.z / b,
				a.w / b
				);
	}
	public static Vector2 div(Vector2 a, float x, float y) {
		return new Vector2(
				a.x / x,
				a.y / y
				);
	}
	public static Vector3 div(Vector3 a, float x, float y, float z) {
		return new Vector3(
				a.x / x,
				a.y / y,
				a.z / z
				);
	}	
	public static Vector4 div(Vector4 a, float x, float y, float z, float w) {
		return new Vector4(
				a.x / x,
				a.y / y,
				a.z / z,
				a.w / w
				);
	}
	
	/*IMMUTABLE NORMALIZATION*/
	public static Vector2 normal(Vector2 a) {
		return div(a, mag(a));
	}
	public static Vector3 normal(Vector3 a) {
		return div(a, mag(a));
	}
	public static Vector4 normal(Vector4 a) {
		return div(a, mag(a));
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
	public static float dot(Vector2 a, float x, float y) {
		return a.x * x + a.y * y;
	}
	public static float dot(Vector3 a, float x, float y, float z) {
		return a.x * x + a.y * y + a.z * z;
	}
	public static float dot(Vector4 a, float x, float y, float z, float w) {
		return a.x * x + a.y * y + a.z * z + a.w * w;
	}
	public static float dot(float x1, float y1, float x2, float y2) {
		return x1 * x2 + y1 * y2;
	}
	public static float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
		return x1 * x2 + y1 * y2 + z1 * z2;
	}
	public static float dot(float x1, float y1, float z1, float w1, float x2, float y2, float z2, float w2) {
		return x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;
	}
	
	public static float mag(Vector2 a) {
		return (float)Math.sqrt(dot(a));
	}
	public static float mag(Vector3 a) {
		return (float)Math.sqrt(dot(a));
	}
	public static float mag(Vector4 a) {
		return (float)Math.sqrt(dot(a));
	}
	
	public static float distance(Vector2 a, Vector2 b) {
		return mag(sub(a, b));
	}
	public static float distance(Vector3 a, Vector3 b) {
		return mag(sub(a, b));
	}
	public static float distance(Vector4 a, Vector4 b) {
		return mag(sub(a, b));
	}
	public static float distance(Vector2 a, float x, float y) {
		return mag(sub(a, x, y));
	}
	public static float distance(Vector3 a, float x, float y, float z) {
		return mag(sub(a, x, y, z));
	}
	public static float distance(Vector4 a, float x, float y, float z, float w) {
		return mag(sub(a, x, y, z, w));
	}
	
	/*IMMUTABLE PROJECTION*/
	public static Vector2 v_project(Vector2 a, Vector2 b) {
		return Vector.mul(b, s_project(a, b));
	}
	public static Vector3 v_project(Vector3 a, Vector3 b) {
		return Vector.mul(b, s_project(a, b));
	}
	public static Vector4 v_project(Vector4 a, Vector4 b) {
		return Vector.mul(b, s_project(a, b));
	}
	public static float s_project(Vector2 a, Vector2 b) {
		return dot(a, b) / dot(b, b);
	}
	public static float s_project(Vector3 a, Vector3 b) {
		return dot(a, b) / dot(b, b);
	}
	public static float s_project(Vector4 a, Vector4 b) {
		return dot(a, b) / dot(b, b);
	}
	
	/*IMMUTABLE TRANSFORM*/
	public static Vector2 transform(Vector2 a, Transform2 t) {
		return Vector.mul(a, t.m3);
	}	
	public static Vector3 transform(Vector3 a, Transform3 t) {
		return Vector.mul(a, t.m4);
	}	
	
	public static java.awt.Color toColor3i(Vector v) {
		return new java.awt.Color((int)v.x(), (int)v.y(), (int)v.z(), 255);
	}	
	public static java.awt.Color toColor3f(Vector v) {
		return new java.awt.Color(v.x(), v.y(), v.z(), 1f);
	}	
	public static java.awt.Color toColor4i(Vector v) {
		return new java.awt.Color((int)v.x(), (int)v.y(), (int)v.z(), (int)v.w());
	}	
	public static java.awt.Color toColor4f(Vector v) {
		return new java.awt.Color(v.x(), v.y(), v.z(), v.w());
	}
	
	public static Vector3 fromColor3i(java.awt.Color color) {
		int argb = color.getRGB();
		return new Vector3(
				(argb >> 16) & 0xFF,
				(argb >>  8) & 0xFF,
				(argb >>  0) & 0xFF
				);
	}	
	public static Vector3 fromColor3f(java.awt.Color color) {
		float[] rgba = color.getComponents(null);
		return new Vector3(
				rgba[0],
				rgba[1],
				rgba[2]
				);
	}
	public static Vector4 fromColor4i(java.awt.Color color) {
		int argb = color.getRGB();
		return new Vector4(
				(argb >> 16) & 0xFF,
				(argb >>  8) & 0xFF,
				(argb >>  0) & 0xFF,
				(argb >> 24) & 0xFF		
				);
	}
	public static Vector4 fromColor4f(java.awt.Color color) {
		float[] rgba = color.getComponents(null);
		return new Vector4(
				rgba[0],
				rgba[1],
				rgba[2],
				rgba[3]
				);
	}
	
	/*MUTABLE ADDITION*/
	public static Vector2.Mutable m_add(Vector2.Mutable a, Vector2 b) {
		return a.set(
				a.x + b.x,
				a.y + b.y
				);
	}
	public static Vector3.Mutable m_add(Vector3.Mutable a, Vector3 b) {
		return a.set(
				a.x + b.x,
				a.y + b.y,
				a.z + b.z
				);
	}	
	public static Vector4.Mutable m_add(Vector4.Mutable a, Vector4 b) {
		return a.set(
				a.x + b.x,
				a.y + b.y,
				a.z + b.z,
				a.w + b.w
				);
	}	
	public static Vector2.Mutable m_add(Vector2.Mutable a, float x, float y) {
		return a.set(
				a.x + x,
				a.y + y
				);
	}
	public static Vector3.Mutable m_add(Vector3.Mutable a, float x, float y, float z) {
		return a.set(
				a.x + x,
				a.y + y,
				a.z + z
				);
	}	
	public static Vector4.Mutable m_add(Vector4.Mutable a, float x, float y, float z, float w) {
		return a.set(
				a.x + x,
				a.y + y,
				a.z + z,
				a.w + w
				);
	}
	
	/*MUTABLE SUBTRACTION*/
	public static Vector2.Mutable m_sub(Vector2.Mutable a, Vector2 b) {
		return a.set(
				a.x - b.x,
				a.y - b.y
				);
	}
	public static Vector3.Mutable m_sub(Vector3.Mutable a, Vector3 b) {
		return a.set(
				a.x - b.x,
				a.y - b.y,
				a.z - b.z
				);
	}	
	public static Vector4.Mutable m_sub(Vector4.Mutable a, Vector4 b) {
		return a.set(
				a.x - b.x,
				a.y - b.y,
				a.z - b.z,
				a.w - b.w
				);
	}	
	public static Vector2.Mutable m_sub(Vector2.Mutable a, float x, float y) {
		return a.set(
				a.x - x,
				a.y - y
				);
	}
	public static Vector3.Mutable m_sub(Vector3.Mutable a, float x, float y, float z) {
		return a.set(
				a.x - x,
				a.y - y,
				a.z - z
				);
	}	
	public static Vector4.Mutable m_sub(Vector4.Mutable a, float x, float y, float z, float w) {
		return a.set(
				a.x - x,
				a.y - y,
				a.z - z,
				a.w - w
				);
	}
	
	/*MUTABLE MULTIPLICATION*/
	public static Vector2.Mutable m_mul(Vector2.Mutable a, Vector2 b) {
		return a.set(
				a.x * b.x,
				a.y * b.y
				);
	}
	public static Vector3.Mutable m_mul(Vector3.Mutable a, Vector3 b) {
		return a.set(
				a.x * b.x,
				a.y * b.y,
				a.z * b.z
				);
	}	
	public static Vector4.Mutable m_mul(Vector4.Mutable a, Vector4 b) {
		return a.set(
				a.x * b.x,
				a.y * b.y,
				a.z * b.z,
				a.w * b.w
				);
	}

	public static Vector2.Mutable mul(Vector2.Mutable a, float b) {
		return a.set(
				a.x * b,
				a.y * b
				);
	}
	public static Vector3.Mutable mul(Vector3.Mutable a, float b) {
		return a.set(
				a.x * b,
				a.y * b,
				a.z * b
				);
	}
	public static Vector4.Mutable mul(Vector4.Mutable a, float b) {
		return a.set(
				a.x * b,
				a.y * b,
				a.z * b,
				a.w * b
				);
	}
	public static Vector2.Mutable m_mul(Vector2.Mutable a, float x, float y) {
		return a.set(
				a.x * x,
				a.y * y
				);
	}
	public static Vector3.Mutable m_mul(Vector3.Mutable a, float x, float y, float z) {
		return a.set(
				a.x * x,
				a.y * y,
				a.z * z
				);
	}	
	public static Vector4.Mutable m_mul(Vector4.Mutable a, float x, float y, float z, float w) {
		return a.set(
				a.x * x,
				a.y * y,
				a.z * z,
				a.w * w
				);
	}
	public static Vector2.Mutable m_mul(Vector2.Mutable a, Matrix2 b) {
		return a.set(
				Vector.dot(a, b.xx, b.yx),
				Vector.dot(a, b.xy, b.yy)
				);
	}	
	public static Vector2.Mutable m_mul(Vector2.Mutable a, Matrix3 b) {
		Vector3 h = Vector.hom(a);
		return a.set(
				Vector.dot(h, b.xx, b.yx, b.zx),
				Vector.dot(h, b.xy, b.yy, b.zy)
				);
	}
	public static Vector3.Mutable m_mul(Vector3.Mutable a, Matrix3 b) {
		return a.set(
				Vector.dot(a, b.xx, b.yx, b.zx),
				Vector.dot(a, b.xy, b.yy, b.zy),
				Vector.dot(a, b.xz, b.yz, b.zz)
				);
	}	
	public static Vector3.Mutable m_mul(Vector3.Mutable a, Matrix4 b) {
		Vector4 h = Vector.hom(a);
		return a.set(
				Vector.dot(h, b.xx, b.yx, b.zx, b.wx),
				Vector.dot(h, b.xy, b.yy, b.zy, b.wy),
				Vector.dot(h, b.xz, b.yz, b.zz, b.wz)
				);
	}
	public static Vector4.Mutable m_mul(Vector4.Mutable a, Matrix4 b) {
		return a.set(
				Vector.dot(a, b.xx, b.yx, b.zx, b.wx),
				Vector.dot(a, b.xy, b.yy, b.zy, b.wy),
				Vector.dot(a, b.xz, b.yz, b.zz, b.wz),
				Vector.dot(a, b.xw, b.yw, b.zw, b.ww)
				);
	}
	
	/*MUTABLE DIVISION*/
	public static Vector2.Mutable m_div(Vector2.Mutable a, Vector2 b) {
		return a.set(
				a.x / b.x,
				a.y / b.y
				);
	}
	public static Vector3.Mutable m_div(Vector3.Mutable a, Vector3 b) {
		return a.set(
				a.x / b.x,
				a.y / b.y,
				a.z / b.z
				);
	}	
	public static Vector4.Mutable m_div(Vector4.Mutable a, Vector4 b) {
		return a.set(
				a.x / b.x,
				a.y / b.y,
				a.z / b.z,
				a.w / b.w
				);
	}	
	public static Vector2.Mutable m_div(Vector2.Mutable a, float x, float y) {
		return a.set(
				a.x / x,
				a.y / y
				);
	}
	public static Vector3.Mutable m_div(Vector3.Mutable a, float x, float y, float z) {
		return a.set(
				a.x / x,
				a.y / y,
				a.z / z
				);
	}	
	public static Vector4.Mutable m_div(Vector4.Mutable a, float x, float y, float z, float w) {
		return a.set(
				a.x / x,
				a.y / y,
				a.z / z,
				a.w / w
				);
	}
	public static Vector2.Mutable m_div(Vector2.Mutable a, float b) {
		return a.set(
				a.x / b,
				a.y / b
				);
	}
	public static Vector3.Mutable m_div(Vector3.Mutable a, float b) {
		return a.set(
				a.x / b,
				a.y / b,
				a.z / b
				);
	}
	public static Vector4.Mutable m_div(Vector4.Mutable a, float b) {
		return a.set(
				a.x / b,
				a.y / b,
				a.z / b,
				a.w / b
				);
	}
	
	/*MUTABLE NORMALIZATION*/
	public static Vector2.Mutable m_normal(Vector2.Mutable a) {
		return m_div(a, mag(a));
	}
	public static Vector3.Mutable m_normal(Vector3.Mutable a) {
		return m_div(a, mag(a));
	}
	public static Vector4.Mutable m_normal(Vector4.Mutable a) {
		return m_div(a, mag(a));
	}

	/*MUTABLE PROJECTION*/
	public static Vector2.Mutable m_project(Vector2.Mutable a, Vector2 b) {
		return a.set(v_project(a, b));
	}
	public static Vector3.Mutable m_project(Vector3.Mutable a, Vector3 b) {
		return a.set(v_project(a, b));
	}
	public static Vector4.Mutable m_project(Vector4.Mutable a, Vector4 b) {
		return a.set(v_project(a, b));
	}
	
	/*MUTABLE TRANSFORM*/
	public static Vector2.Mutable m_transform(Vector2.Mutable a, Transform2 t) {
		return Vector.m_mul(a, t.m3);
	}	
	public static Vector3.Mutable m_transform(Vector3.Mutable a, Transform3 t) {
		return Vector.m_mul(a, t.m4);
	}	
}
