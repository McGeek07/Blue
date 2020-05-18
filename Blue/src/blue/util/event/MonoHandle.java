package blue.util.event;

import java.util.HashSet;
import java.util.Iterator;

public class MonoHandle<T> extends Listener.Group<T> {
	
	public static class Group<T> implements Iterable<MonoHandle<T>> {
		protected final HashSet<MonoHandle<T>>
			handles = new HashSet<>(),
			attach = new HashSet<>(),
			detach = new HashSet<>();
		
		public void attach(MonoHandle<T> handle) {
			attach.add(handle);
		}
		
		public void detach(MonoHandle<T> handle) {
			detach.add(handle);
		}
		
		public boolean onAttach(MonoHandle<T> handle) {
			return handles.add(handle);
		}
		
		public boolean onDetach(MonoHandle<T> handle) {
			return handles.remove(handle);
		}
		
		public void attachPending() {
			if(attach.size() > 0) {
				for(MonoHandle<T> handle: attach)
					onAttach(handle);	
				attach.clear();
			}
			if(handles.size() > 0) {
				for(MonoHandle<T> handle: handles)
					handle.attachPending();
			}
		}
		
		public void detachPending() {
			if(handles.size() > 0) {
				for(MonoHandle<T> handle: handles)
					handle.detachPending();
			}
			if(detach.size() > 0) {
				for(MonoHandle<T> handle: detach)
					onDetach(handle);
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