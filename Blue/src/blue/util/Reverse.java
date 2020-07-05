package blue.util;

import java.util.Collection;

public interface Reverse<T> {
	
	public Reverse.Iterable<T> reverse();
	
	public static class Iterable<T> implements java.lang.Iterable<T> {
		private final Object[]
			list;
		
		public Iterable(Collection<T> list) {
			this.list = list.toArray();
		}
		
		public Iterable(T[] list) {
			this.list = list;
		}

		@Override
		public Reverse.Iterator<T> iterator() {
			return new Reverse.Iterator<T>(this);
		}
	}
	
	
	public static class Iterator<T> implements java.util.Iterator<T> {		
		private Object[]
			list;
		private int
			a,
			b;		
		
		public Iterator(Reverse.Iterable<T> reverse) {
			init(reverse.list);
		}		
		
		public Iterator(Collection<T> list) {
			init(list.toArray());
		}
		
		public Iterator(T[] list) {
			init(list);
		}
		
		private void init(Object[] list) {
			this.list = list;
			
			this.a =           0;
			this.b = list.length;
		}
	
		@Override
		public boolean hasNext() {
			return a < b;
		}
	
		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			return (T)list[-- b];
		}	
	}
}