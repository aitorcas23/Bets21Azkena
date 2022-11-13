package domain;

import java.util.Iterator;
import java.util.Vector;

public class ExtendedEventIterator implements ExtendedIterator<Event> {
    private int pos;
    private Vector<Event> array;
    
    public ExtendedEventIterator() {
    	this.pos =0;
    	this.array = new Vector<Event>();
    }
    
    public ExtendedEventIterator(Iterable<Event> array) {
    	this.pos = 0;
    	this.array = new Vector<Event>();
    	for(Event e : array) {
    		this.array.add(e);
    	}
    }
	
	@Override
	public boolean hasNext() {
		return (pos >= 0 && pos < array.size());
		
		/*
		if(pos >array.size()-1) {
			return true;
		} else {
			return false;
		}*/
	}

	@Override
	public Event next() {
		Event e = array.get(pos);
		pos++;
		return e;
	}
	
	@Override
	public boolean hasPrevious() {
		return (pos >= 0 && pos < array.size());
	}

	@Override
	public Event previous() {
		Event e = array.get(pos);
		pos--;
		return e;
	}
	
	@Override
	public void goFirst() {
		pos = 0;
	}
	
	@Override
	public void goLast() {
		pos = array.size()-1;
	}

	@Override
	public void add(Event ev) {
		array.add(ev);
	}

	@Override
	public Iterator iterator() {
		return this;
	}
	
	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	@Override
	public int size() {
		return array.size();
	}
}
