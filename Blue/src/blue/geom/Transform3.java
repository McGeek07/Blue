package blue.geom;

import java.util.LinkedList;

public class Transform3 {
	protected final LinkedList<Matrix4>
		stack = new LinkedList<Matrix4>();
	public final Matrix4.Mutable
		m4 = Matrix4.Mutable.identity();
	
	public Transform3() {
		//do nothing
	}	
	
	public Transform3 translate(Vector3 t) {		
		Matrix.m_mul(m4, Matrix.translate(t));
		return this;
	}
	
	public Transform3 translate(float tx, float ty, float tz) {		
		Matrix.m_mul(m4, Matrix.translate(tx, ty, tz));
		return this;
	}
	
	public Transform3 scale(Vector3 s) {		
		Matrix.m_mul(m4, Matrix.scale(s));
		return this;
	}
	
	public Transform3 scale(float sx, float sy, float sz) {	
		Matrix.m_mul(m4, Matrix.scale(sx, sy, sz));
		return this;
	}
	
	public Transform3 push() {
		stack.push(m4.copy());
		return this;
	}	
	public Transform3 pop() {
		m4.set(stack.pop());
		return this;
	}
	
	@Override
	public String toString() {
		return Matrix4.toString(m4, "%16.3f");
	}
}
