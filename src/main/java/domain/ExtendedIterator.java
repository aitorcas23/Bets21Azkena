package domain;

import java.util.Collection;
import java.util.Iterator;

public interface ExtendedIterator<Event> extends Iterator<Event>, Iterable{
	//uneko elementua itzultzen du eta aurrekora pasatzen da
	public Event previous();
	//true aurreko elementua existitzen bada.
	public boolean hasPrevious();
	//Lehendabiziko elementuan kokatzen da.
	public void goFirst();
	//Azkeneko elementuan kokatzen da.
	public void goLast();
	
	void add(Event ev);
	
	public boolean isEmpty();
	
	public int size();
}
