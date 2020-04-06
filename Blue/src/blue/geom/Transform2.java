package blue.geom;

import java.util.LinkedList;

public class Transform2 extends Transform<Matrix3> {
	protected final LinkedList<Matrix3>
		stack = new LinkedList<Matrix3>();
	public final Matrix3.Mutable
		m3 = Matrix3.Mutable.identity();
	
	public Transform2() {
		//do nothing
	}	
	
	public Transform2 translate(Vector2 t) {		
		Matrix.m_mul(m3, Matrix.translate(t));
		return this;
	}
	
	public Transform2 translate(float tx, float ty) {		
		Matrix.m_mul(m3, Matrix.translate(tx, ty));
		return this;
	}
	
	public Transform2 scale(Vector2 s) {		
		Matrix.m_mul(m3, Matrix.scale(s));
		return this;
	}
	
	public Transform2 scale(float sx, float sy) {	
		Matrix.m_mul(m3, Matrix.scale(sx, sy));
		return this;
	}
	
	public Transform2 push() {
		stack.push(m3.copy());
		return this;
	}	
	public Transform2 pop() {
		m3.set(stack.pop());
		return this;
	}
	
	@Override
	public String toString() {
		return Matrix3.toString(m3, "%16.3f");
	}
}
