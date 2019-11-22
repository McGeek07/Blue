package blue.geom;

import java.util.LinkedList;

public class Transform2 extends Transform<Matrix3> {
	protected final LinkedList<Matrix3>
		stack = new LinkedList<Matrix3>();
	public final Matrix3.Mutable
		xform = new Matrix3.Mutable(
				Matrix.ROW_MAJOR,
				1, 0, 0,
				0, 1, 0,
				0, 0, 1
				);
	
	public Transform2() {
		//do nothing
	}	
	
	public Transform2 push() {
		stack.push(xform.copy());
		return this;
	}	
	public Transform2 pop() {
		xform.set(stack.pop());
		return this;
	}	
	
	public Transform2 mov(Vector2 t) {
		return mov(t.x, t.y);
	}	
	public Transform2 mov(float tx, float ty) {
		Matrix.mul(xform, Transform.mov2(tx, ty));
		return this;
	}
	
	public Transform2 sca(Vector2 s) {
		return mov(s.x, s.y);
	}	
	public Transform2 sca(float sx, float sy) {
		Matrix.mul(xform, Transform.sca2(sx, sy));
		return this;
	}	
	
	@Override
	public String toString() {
		return Matrix3.toString(xform, "%16.3f");
	}
}
