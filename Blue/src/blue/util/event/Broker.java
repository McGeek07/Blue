package blue.util.event;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Broker {
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