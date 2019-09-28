package blue.geom;

import java.io.Serializable;

import blue.util.Copyable;

public abstract class Vector implements Serializable, Copyable<Vector> {
	private static final long 
		serialVersionUID = 1L;
	public static final int
		X = 0,
		Y = 1,
		Z = 2;
	
	public float x() { return 0f; }
	public float y() { return 0f; }
	public float z() { return 0f; }	
	
	public abstract float get(int j);
	
	public static int compare(Vector2f a, Vector2f b) {
		float tmp;
		if((tmp = a.x - b.x) != 0) return tmp > 0 ? 1 : -1;
		if((tmp = a.y - b.y) != 0) return tmp > 0 ? 1 : -1;
		return 0;
	}
	
	public static int compare(Vector3f a, Vector3f b) {
		float tmp;
		if((tmp = a.x - b.x) != 0) return tmp > 0 ? 1 : -1;
		if((tmp = a.y - b.y) != 0) return tmp > 0 ? 1 : -1;
		if((tmp = a.z - b.z) != 0) return tmp > 0 ? 1 : -1;
		return 0;
	}
	
	public static int compare(Vector a, Vector b) {
		float tmp;
		if((tmp = a.x() - b.x()) != 0) return tmp > 0 ? 1 : -1;
		if((tmp = a.y() - b.y()) != 0) return tmp > 0 ? 1 : -1;
		if((tmp = a.z() - b.z()) != 0) return tmp > 0 ? 1 : -1;
		return 0;
	}	
}
