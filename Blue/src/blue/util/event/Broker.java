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
	
	public boolean add(Handle handle) {
		return handles.add(handle);
	}
	
	public boolean del(Handle handle) {
		return handles.del(handle);
	}
	
	public void attach(Handle handle) {
		handles.attach(handle);
	}
	
	public void detach(Handle handle) {
		handles.detach(handle);
	}
	
	public boolean add(Broker broker) {
		return brokers.add(broker);
	}
	
	public boolean del(Broker broker) {
		return brokers.del(broker);
	}
	
	public void attach(Broker broker) {
		brokers.attach(broker);
	}
	
	public void detach(Broker broker) {
		brokers.detach(broker);
	}
	
	public void attach() {
		handles.attach();
		brokers.attach();
	}
	
	public void detach() {
		handles.detach();
		brokers.detach();
	}				
	
	public <T> void queue(T event) {
		events1.add(event);
	}
	
	public <T> void flush(T event) {
		handles.flush(event);
		brokers.flush(event);
	}	
	
	public void flush() {
		if(events1.size() > 0) {
			LinkedList<Object> events3 = events1;
			events1 = events2;
			events2 = events3;
			
			for(Object event: events2)
				flush(event);
			events2.clear();
		}
		for(Broker broker: brokers)
			broker.flush();
	}
	
	public void poll() {
		attach();
		detach();
		flush();
	}
	
	public static class Group implements Iterable<Broker> {
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
		
		public void attach() {
			if(attach.size() > 0) {
				for(Broker broker: attach)
					add(broker);
				attach.clear();
			}
			if(brokers.size() > 0) {
				for(Broker broker: brokers)
					broker.attach();
			}
		}
		
		public void detach() {
			if(brokers.size() > 0) {
				for(Broker broker: brokers)
					broker.detach();
			}
			if(detach.size() > 0) {
				for(Broker broker: detach)
					del(broker);
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