package blue.geom;

public abstract class Transform<T extends Matrix> {
	
	
	public static Matrix3 mov2(Vector2 t) {
		return mov2(t.x, t.y);
	}	
	public static Matrix3 mov2(float tx, float ty) {
		return new Matrix3(
				Matrix.ROW_MAJOR,
				1 , 0 , 0,
				0 , 1 , 0,
				tx, ty, 1
				);
	}
	
	public static Matrix3 sca2(Vector2 s) {
		return sca2(s.x, s.y);
	}	
	public static Matrix3 sca2(float sx, float sy) {
		return new Matrix3(
				Matrix.ROW_MAJOR,
				sx, 0 , 0,
				0 , sy, 0,
				0 , 0 , 1
				);
	}
}
