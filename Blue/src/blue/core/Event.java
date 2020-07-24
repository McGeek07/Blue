package blue.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public interface Event extends Serializable {
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> typeOf(T t) {
		return (Class<T>)t.getClass();
	}
	
	public interface Listener<T> {
		public void handle(T event);
		
		public static class Group<T> implements Iterable<Listener<T>> {
			protected final HashSet<Listener<T>>
				listeners = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public void attach(Listener<T> listener) {
				attach.add(listener);
			}
			
			public void detach(Listener<T> listener) {
				detach.add(listener);
			}		
			
			public boolean onAttach(Listener<T> listener) {
				return listeners.add(listener);
			}
			
			public boolean onDetach(Listener<T> listener) {
				return listeners.remove(listener);
			}
			
			public void attachPending() {
				if(attach.size() > 0) {
					for(Listener<T> listener: attach)
						onAttach(listener);
					attach.clear();
				}
			}
			
			public void detachPending() {
				if(detach.size() > 0) {
					for(Listener<T> listener: detach)
						onDetach(listener);
					detach.clear();
				}
			}		
			
			public void flush(T event) {
				for(Listener<T> listener : listeners)
					listener.handle(event);		
			}

			@Override
			public Iterator<Listener<T>> iterator() {
				return listeners.iterator();
			}
		}
	}
	
	public static class Handle {
		protected final HashMap<Class<?>, Listener.Group<?>>
			listeners = new HashMap<>();
		
		@SuppressWarnings("unchecked")
		public <T> Listener.Group<T> getListeners(Class<T> type) {
			Listener.Group<T> listeners = (Listener.Group<T>)this.listeners.get(type);
			if(listeners == null) {
				listeners = new Listener.Group<T>();
				this.listeners.put(type, listeners);
			}
			return listeners;
		}
		
		public <T> void attach(Class<T> type, Listener<T> listener) {
			getListeners(type).attach(listener);
		}
		
		public <T> void detach(Class<T> type, Listener<T> listener) {
			getListeners(type).detach(listener);
		}
		
		public <T> boolean onAttach(Class<T> type, Listener<T> listener) {
			return getListeners(type).onAttach(listener);
		}
		
		public <T> boolean onDetach(Class<T> type, Listener<T> listener) {
			return getListeners(type).onDetach(listener);
		}
		
		public void attachPending() {
			this.listeners.forEach((type, listeners) -> {
				listeners.attachPending();
			});
		}
		
		public void detachPending() {
			this.listeners.forEach((type, listeners) -> {
				listeners.detachPending();
			});
		}
		
		public <T> void flush(T event) {
			getListeners(typeOf(event)).flush(event);
		}
		
		public static class Group implements Iterable<Handle> {
			protected final HashSet<Handle>
				handles = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public void attach(Handle handle) {
				attach.add(handle);
			}
			
			public void detach(Handle handle) {
				detach.add(handle);
			}
			
			public boolean onAttach(Handle handle) {
				return handles.add(handle);
			}
			
			public boolean onDetach(Handle handle) {
				return handles.remove(handle);
			}
			
			public void attachPending() {
				if(attach.size() > 0) {
					for(Handle handle: attach)
						onAttach(handle);
					attach.clear();
				}
				if(handles.size() > 0) {
					for(Handle handle: handles)
						handle.attachPending();
				}
			}
			
			public void detachPending() {
				if(handles.size() > 0) {
					for(Handle handle: handles)
						handle.detachPending();
				}
				if(detach.size() > 0) {
					for(Handle handle: detach)
						onDetach(handle);
					detach.clear();
				}
			}
			
			public <T> void flush(T event) {
				for(Handle handle: handles)
					handle.flush(event);
			}

			@Override
			public Iterator<Handle> iterator() {
				return handles.iterator();
			}
		}
	}
	
	public static class Broker {
		protected final Handle.Group
			handles = new Handle.Group();
		protected final Broker.Group
			brokers = new Broker.Group();
		
		protected LinkedList<Object>
			events1 = new LinkedList<>(),
			events2 = new LinkedList<>();
		
		public void attach(Handle handle) {
			handles.attach(handle);
		}
		
		public void detach(Handle handle) {
			handles.detach(handle);
		}
		
		public boolean onAttach(Handle handle) {
			return handles.onAttach(handle);
		}
		
		public boolean onDetach(Handle handle) {
			return handles.onDetach(handle);
		}
		
		public void attach(Broker broker) {
			brokers.attach(broker);
		}
		
		public void detach(Broker broker) {
			brokers.detach(broker);
		}
		
		public boolean onAttach(Broker broker) {
			return brokers.onAttach(broker);
		}
		
		public boolean onDetach(Broker broker) {
			return brokers.onDetach(broker);
		}
		
		public void attachPending() {
			handles.attachPending();
			brokers.attachPending();
		}
		
		public void detachPending() {
			handles.detachPending();
			brokers.detachPending();
		}				
		
		public <T> void queue(T event) {
			events1.add(event);
		}
		
		public <T> void flush(T event) {
			handles.flush(event);
			brokers.flush(event);
		}
		
		public synchronized void flushPending() {
			if(events1.size() > 0) {
				LinkedList<Object> events3 = events1;
				events1 = events2;
				events2 = events3;
				
				for(Object event: events2)
					flush(event);
				events2.clear();
			}
			for(Broker broker: brokers)
				broker.flushPending();
		}
		
		public void poll() {
			attachPending();
			detachPending();
			flushPending();
		}
		
		public static class Group implements Iterable<Broker> {
			protected final HashSet<Broker>
				brokers = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public boolean onAttach(Broker broker) {
				return brokers.add(broker);
			}
			
			public boolean onDetach(Broker broker) {
				return brokers.remove(broker);
			}
			
			public void attach(Broker broker) {
				attach.add(broker);
			}
			
			public void detach(Broker broker) {
				detach.add(broker);
			}
			
			public void attachPending() {
				if(attach.size() > 0) {
					for(Broker broker: attach)
						onAttach(broker);
					attach.clear();
				}
				if(brokers.size() > 0) {
					for(Broker broker: brokers)
						broker.attachPending();
				}
			}
			
			public void detachPending() {
				if(brokers.size() > 0) {
					for(Broker broker: brokers)
						broker.detachPending();
				}
				if(detach.size() > 0) {
					for(Broker broker: detach)
						onDetach(broker);
					detach.clear();
				}
			}
			
			public <T> void flush(T event) {
				for(Broker broker: brokers)
					broker.flush(event);
			}

			@Override
			public Iterator<Broker> iterator() {
				return brokers.iterator();
			}
		}
	}
}
