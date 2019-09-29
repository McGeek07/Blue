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
	
	public static Vector2f add(Vector2f a, Vector2f b) {
		return new Vector2f(
				a.x + b.x,
				a.y + b.y
				);
	}
	public static Vector3f add(Vector3f a, Vector3f b) {
		return new Vector3f(
				a.x + b.x,
				a.y + b.y,
				a.z + b.z
				);
	}
	public static Vector4f add(Vector4f a, Vector4f b) {
		return new Vector4f(
				a.x + b.x,
				a.y + b.y,
				a.z + b.z,
				a.w + b.w
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
	}
}
