package blue.util;

import java.io.Serializable;
import java.util.Objects;

public class Property<T> implements Serializable {
	private static final long 
		serialVersionUID = 1L;
	protected T
		t;
	
	public Property(T t) {
		this.t = t;
	}
	
	public T get() {
		return t;
	}
	
	@Override
	public String toString() {
		return Objects.toString(t);
	}
	
	public static class Mutable<T> extends Property<T> {
		private static final long 
			serialVersionUID = 1L;

		public Mutable(T t) {
			super(t);
		}
		
		public T set(T t) {
			return this.t = t;
		}		
	}
}
