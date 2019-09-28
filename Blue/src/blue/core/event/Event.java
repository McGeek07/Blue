package blue.core.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public interface Event {
	public static interface Listener<T extends Event> {
		public void handle(T event);
		
		public static class Group<T extends Event> {
			protected final HashSet<Listener<T>>
				listeners = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public boolean add(Listener<T> listener) {
				return listeners.add(listener);
			}
			
			public boolean del(Listener<T> listener) {
				return listeners.remove(listener);
			}
			
			public void attach(Listener<T> listener) {
				attach.add(listener);
			}
			
			public void detach(Listener<T> listener) {
				detach.add(listener);
			}
			
			public void attachPending() {
				for(Listener<T> listener: attach)
					add(listener);
				attach.clear();
			}			
			
			public void detachPending() {
				for(Listener<T> listener: detach)
					del(listener);
				detach.clear();
			}
			
			public void handle(T event) {
				for(Listener<T> listener: listeners)
					listener.handle(event);
			}
		}
	}
	
	public static class Handle {
		protected final HashMap<Class<?>, Listener.Group<?>>
			listeners = new HashMap<>();
		
		@SuppressWarnings("unchecked")
		public <T extends Event> Listener.Group<T> getListeners(Class<T> type) {
			Listener.Group<T> group = (Listener.Group<T>)listeners.get(type);
			if(group == null) {
				group = new Listener.Group<T>();
				listeners.put(type, group);
			}
			return group;
		}
		
		public <T extends Event> boolean add(Class<T> type, Listener<T> listener) {
			return getListeners(type).add(listener);
		}
		
		public <T extends Event> boolean del(Class<T> type, Listener<T> listener) {
			return getListeners(type).del(listener);
		}	
		
		public <T extends Event> void attach(Class<T> type, Listener<T> listener) {
			getListeners(type).attach(listener);
		}
		
		public <T extends Event> void detach(Class<T> type, Listener<T> listener) {
			getListeners(type).detach(listener);
		}
		
		public void attachPending() {
			listeners.forEach((type, group) -> {
				group.attachPending();
			});
		}
		
		public void detachPending() {
			listeners.forEach((type, group) -> {
				group.detachPending();
			});
		}
		
		public <T extends Event> void flush(T event) {
			getListeners(typeOf(event)).handle(event);
		}
		
		public static class Group {
			protected final HashSet<Handle>
				handles = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public boolean add(Handle handle) {
				return handles.add(handle);
			}
			
			public boolean del(Handle handle) {
				return handles.remove(handle);
			}
			
			public void attach(Handle handle) {
				attach.add(handle);
			}
			
			public void detach(Handle handle) {
				detach.add(handle);
			}
			
			public void attachPending() {
				for(Handle handle: attach)
					add(handle);
				for(Handle handle: handles)
					handle.attachPending();
				attach.clear();
			}
			
			public void detachPending() {
				for(Handle handle: handles)
					handle.detachPending();
				for(Handle handle: detach)
					del(handle);
				detach.clear();
			}
			
			public <T extends Event> void flush(T event) {
				for(Handle handle: handles)
					handle.flush(event);
			}
		}
	}
	
	public static class Broker {
		protected final Handle.Group
			handles = new Handle.Group();
		protected final Broker.Group
			brokers = new Broker.Group();
		protected LinkedList<Event>
			events1 = new LinkedList<>(),
			events2 = new LinkedList<>();
		
		public boolean add(Handle handle) {
			return handles.add(handle);
		}
		
		public boolean del(Handle handle) {
			return handles.del(handle);
		}
		
		public boolean add(Broker broker) {
			return brokers.add(broker);
		}
		
		public boolean del(Broker broker) {
			return brokers.del(broker);
		}
		
		public void attach(Handle handle) {
			handles.attach(handle);
		}
		
		public void detach(Handle handle) {
			handles.detach(handle);
		}
		
		public void attach(Broker broker) {
			brokers.attach(broker);
		}
		
		public void detach(Broker broker) {
			brokers.detach(broker);
		}
		
		public <T extends Event> void queue(T event) {
			events1.add(event);
		}
		
		public <T extends Event> void flush(T event) {
			handles.flush(event);
			brokers.flush(event);
		}
		
		public void flush() {
			LinkedList<Event> events3 = events1;
			events1 = events2;
			events2 = events3;
			
			for(Event event: events2)
				flush(event);
			events2.clear();
		}	
		
		public void attachPending() {
			handles.attachPending();
			brokers.attachPending();
		}
		
		public void detachPending() {
			handles.detachPending();
			brokers.detachPending();
		}
		
		public void flushPending() {
			attachPending();
			detachPending();
			flush();
		}
		
		public static class Group {
			protected final HashSet<Broker>
				brokers = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public boolean add(Broker broker) {
				return brokers.add(broker);
			}
			
			public boolean del(Broker broker) {
				return brokers.remove(broker);
			}
			
			public void attach(Broker broker) {
				attach.add(broker);
			}
			
			public void detach(Broker broker) {
				detach.add(broker);
			}
			
			public void attachPending() {
				for(Broker broker: attach)
					add(broker);
				for(Broker broker: brokers)
					broker.attachPending();
				attach.clear();
			}
			
			public void detachPending() {
				for(Broker broker: brokers)
					broker.detachPending();
				for(Broker broker: detach)
					del(broker);
				detach.clear();
			}
			
			public <T extends Event> void flush(T event) {
				for(Broker broker: brokers)
					broker.flush(event);
			}
		}
	}
	
	public static class Dispatcher {
		protected Handle
			handle;
		protected Broker
			broker;
		
		public Dispatcher(Handle handle) {
			this.setHandle(handle);
		}
		
		public Dispatcher(Broker broker) {
			this.setBroker(broker);
		}
		
		public Dispatcher(Handle handle, Broker broker) {
			this.setHandle(handle);
			this.setBroker(broker);
		}
		
		public Dispatcher(Broker broker, Handle handle) {
			this.setBroker(broker);
			this.setHandle(handle);
		}
		
		public void setHandle(Handle handle) {
			if(broker != null) {
				if(this.handle != null)
					broker.detach(this.handle);
				this.handle = handle;
				if(this.handle != null)
					broker.attach(this.handle);
			} else
				this.handle = handle;
		}
		
		public void setBroker(Broker broker) {
			if(handle != null) {
				if(this.broker != null)
					this.broker.detach(handle);
				this.broker = broker;
				if(this.broker != null)
					this.broker.attach(handle);
			} else
				this.broker = broker;
		}
		
		public <T extends Event> boolean add(Class<T> type, Listener<T> listener) {
			return handle != null && handle.add(type, listener);
		}
		
		public <T extends Event> boolean del(Class<T> type, Listener<T> listener) {
			return handle != null && handle.del(type, listener);
		}
		
		public <T extends Event> void attach(Class<T> type, Listener<T> listener) {
			if(handle != null) handle.attach(type, listener);
		}
		
		public <T extends Event> void detach(Class<T> type, Listener<T> listener) {
			if(handle != null) handle.detach(type, listener);
		}
		
		public <T extends Event> void queue(T event) {
			if(broker != null) broker.queue(event);
			else dispatch(event);
		}
		
		public <T extends Event> void flush(T event) {
			if(broker != null) broker.flush(event);
			else dispatch(event);
		}
		
		public <T extends Event> void dispatch(T event) {
			if(handle != null) handle.flush(event);			
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Event> Class<T> typeOf(T event) {
		return (Class<T>)event.getClass();
	}
}
