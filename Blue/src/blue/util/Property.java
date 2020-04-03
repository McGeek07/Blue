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
	
	public Property(Property<T> p) {
		this.t = p.get();
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
		
		public Mutable(Property<T> p) {
			super(p);
		}
		
		public Property.Mutable<T> set(T t) {
			this.t = t;
			return this;
		}		
	}
}
