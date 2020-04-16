package blue.util.event;

import java.util.HashSet;
import java.util.Iterator;

public class MonoHandle<T> extends Listener.Group<T> {
	
	public static class Group<T> implements Iterable<MonoHandle<T>> {
		protected final HashSet<MonoHandle<T>>
			handles = new HashSet<>(),
			attach = new HashSet<>(),
			detach = new HashSet<>();
		
		public boolean add(MonoHandle<T> handle) {
			return handles.add(handle);
		}
		
		public boolean del(MonoHandle<T> handle) {
			return handles.remove(handle);
		}
		
		public void attach(MonoHandle<T> handle) {
			attach.add(handle);
		}
		
		public void detach(MonoHandle<T> handle) {
			detach.add(handle);
		}
		
		public void attach() {
			if(attach.size() > 0) {
				for(MonoHandle<T> handle: attach)
					add(handle);	
				attach.clear();
			}
			if(handles.size() > 0) {
				for(MonoHandle<T> handle: handles)
					handle.attach();
			}
		}
		
		public void detach() {
			if(handles.size() > 0) {
				for(MonoHandle<T> handle: handles)
					handle.detach();
			}
			if(detach.size() > 0) {
				for(MonoHandle<T> handle: detach)
					del(handle);
				detach.clear();
			}
		}
		
		public void flush(T event) {
			for(MonoHandle<T> handle: handles)
				handle.flush(event);
		}

		@Override
		public Iterator<MonoHandle<T>> iterator() {
			return handles.iterator();
		}
	}
}