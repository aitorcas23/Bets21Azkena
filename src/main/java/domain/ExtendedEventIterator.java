package domain;

import java.util.Iterator;
import java.util.List;

public class ExtendedEventIterator<Event> implements Iterator<Event> {
    private int pos;
    private List<Event> array;
    
    public ExtendedEventIterator(List<Event> array) {
    	this.pos = 0;
    	this.array = array;
    }
	
	@Override
	public boolean hasNext() {
		if(pos >array.size()-1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Event next() {
		try {
			return array.get(pos+1);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public boolean hasPrevious() {
		if (pos-1>0) {
			return true;
		} 
		else {
			return false;
		}
	}

	public Event previous() {
		try {
			return array.get(pos-1);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public void goFirst() {
		pos = 0;
	}
	
	public void goLast() {
		pos = array.size()-1;
	}
}
