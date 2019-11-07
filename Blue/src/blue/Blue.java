package blue;

import blue.core.Scene;
import blue.geom.Matrix3;
import blue.geom.Vector;
import blue.geom.Vector2;
import blue.util.Version;

public class Blue extends Scene {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 13);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Matrix3 m3 = new Matrix3(
				1, 0, 10,
				0, 1, 10,
				0, 0, 1
				);
		
		System.out.println(Vector.mul(m3, new Vector2(10, 10)));
		
	}
}
