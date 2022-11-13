package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import businesslogic.BLFacade;
import businesslogic.BLFacadeFactory;
import businesslogic.BLFacadeImplementation;
import businesslogic.BusinessLogicServer;
import configuration.ConfigXML;

public class IteratorProba {
	public static void main(String[] args) {
		ConfigXML c=ConfigXML.getInstance();
		Locale.setDefault(new Locale(c.getLocale()));
		boolean isLocal=true;
		BLFacadeFactory blFacade = new BLFacadeFactory();
		Date date = parseDate("2022-11-14"); 
		BusinessLogicServer facade= new BusinessLogicServer();
		ExtendedEventIterator<Event> i=facade.getEventIterator(date);
		try {
			blFacade.createBLFacade(c);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		Event ev;
		i.goLast();
		while (i.hasPrevious()){
			ev=i.previous();
			System.out.println(ev.toString());
		}
		//Nahiz eta suposatu hasierara ailegatu garela, eragiketa egiten dugu.
		i.goFirst();
		while (i.hasNext()){
			ev=i.next();
			System.out.println(ev.toString());
		}
	}
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
}