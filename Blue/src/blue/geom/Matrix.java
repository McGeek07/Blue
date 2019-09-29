package blue.geom;

import java.io.Serializable;

import blue.util.Copyable;

public abstract class Matrix<T extends Vector> implements Serializable, Copyable<Matrix<T>> {
	private static final long 
		serialVersionUID = 1L;
	
	public abstract float get(int i, int j);
	public abstract T row(int i);
	public abstract T col(int j);
	public int m() { return 0; }
	public int n() { return 0; }
}
