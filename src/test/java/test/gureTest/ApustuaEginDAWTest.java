package test.gureTest;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Bezeroa;
import domain.Errepikapena;
import domain.Event;
import domain.Pronostikoa;
import domain.Question;

public class ApustuaEginDAWTest {
	
	private static DataAccess da;
	static Bezeroa bezero1;
	static Bezeroa bezero2;
	static Bezeroa bezero21;
	static Bezeroa bezero3;
	static Bezeroa bezero31;
	static Bezeroa bezero4;
	static Bezeroa bezero41;
	
	static Event event1;
	@BeforeClass
	public static void setDataBase() {
		da = new DataAccess(true);
		da.initializeDB();
		da.open(false);
		
		bezero1 = da.getBezeroa("Tarek12301");
		da.diruaSartu(100, bezero1);
		
		bezero2 = da.getBezeroa("Saioo99");
		da.diruaSartu(104, bezero2);
		bezero21 = da.getBezeroa("Na1ara");
		da.diruaSartu(100, bezero21);
		Errepikapena e= new Errepikapena(bezero21,bezero2, 1,40,0);
		da.errepikatu(e);
		
		bezero3 = da.getBezeroa("JoseRamon");
		bezero31 = da.getBezeroa("Ontsalo");
		da.diruaSartu(100, bezero31);
		Errepikapena f= new Errepikapena(bezero31,bezero3, 1,0,0);
		da.errepikatu(f);
		
		bezero4 = da.getBezeroa("Josueeee");
		bezero41 = da.getBezeroa("PelloJoxepe");
		Errepikapena g= new Errepikapena(bezero41,bezero4, 1,3,0);
		da.errepikatu(g);
		
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		event1 = da.getEvents(UtilDate.newDate(year, month, 1)).get(0);
		//System.out.println(event1.getDescription() + event1.getQuestions().get(0).getPronostics().size() + " only in ohio");
		event1.getQuestions().get(0).setBetMinimum(5);
		da.close();
	}
	
	@Test
	public void test1() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		
		bezero2 = da.getBezeroa(bezero2.getErabiltzaileIzena());
		double dirua1 = bezero2.getDirua();
		double dirua2 = bezero2.getErrepikatzaileak().get(0).getNork().getDirua();
		assertEquals(da.apustuaEgin(pronostikoak, 10, bezero2), bezero2);
		assertTrue(bezero2.getDirua() == dirua1 - 10);
		assertTrue(bezero2.getErrepikatzaileak().get(0).getNork().getDirua() == dirua2-10);
		da.close();
		
		//Dirua kentzen du apustua egiten duelako, eta errepikatzaileari ere
	}
	
	@Test
	public void test2() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		bezero2 = da.getBezeroa(bezero2.getErabiltzaileIzena());
		pronostikoak.add(event1.getQuestions().get(0).getPronostics().get(0));
		double dirua1 = bezero2.getDirua();
		double dirua2 = bezero2.getErrepikatzaileak().get(0).getNork().getDirua();
		assertEquals(da.apustuaEgin(pronostikoak, 10, bezero2), bezero2);
		assertTrue(bezero2.getDirua() == dirua1 - 10);
		assertTrue(bezero2.getErrepikatzaileak().get(0).getNork().getDirua() == dirua2 - 10);
		da.close();
		
		//Dirua kentzen du apustua egiten duelako, eta errepikatzaileari ere
	}
	
	@Test
	public void test3() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		bezero1 = da.getBezeroa(bezero1.getErabiltzaileIzena());
		pronostikoak.add(event1.getQuestions().get(0).getPronostics().get(0));
		double dirua1 = bezero1.getDirua();
		assertEquals(da.apustuaEgin(pronostikoak, 10, bezero1), bezero1);
		assertTrue(bezero1.getDirua() == dirua1 - 10);
		da.close();
		
		//Ez dauka errepikatzailerik
	}
	
	@Test
	public void test4() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		bezero3 = da.getBezeroa(bezero3.getErabiltzaileIzena());
		pronostikoak.add(event1.getQuestions().get(0).getPronostics().get(0));
		double dirua1 = bezero3.getDirua();
		double dirua2 = bezero3.getErrepikatzaileak().get(0).getNork().getDirua();
		assertEquals(da.apustuaEgin(pronostikoak, 10, bezero3), bezero3);
		assertTrue(bezero3.getDirua() == dirua1 - 10);
		assertTrue(bezero3.getErrepikatzaileak().get(0).getNork().getDirua() == dirua2);
		da.close();
		
		//Errepikatzailearen dirua ez da aldatzen ez daukalako hilabetean diru nahikorik apustua egiteko
	}
	
	@Test
	public void test5() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		bezero2 = da.getBezeroa(bezero3.getErabiltzaileIzena());
		pronostikoak.add(event1.getQuestions().get(0).getPronostics().get(0));
		double dirua1 = bezero2.getDirua();
		double dirua2 = bezero2.getErrepikatzaileak().get(0).getNork().getDirua();
		assertEquals(da.apustuaEgin(pronostikoak, 50, bezero2), bezero2);
		assertTrue(bezero2.getDirua() == dirua1 - 50);
		assertTrue(bezero2.getErrepikatzaileak().get(0).getNork().getDirua() == dirua2);
		da.close();
		
		//Errepikatzailearen dirua ez da aldatzen ez daukalako hilabetean diru nahikorik apustua egiteko
	}
	
	
	@Test
	public void test6() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		bezero4 = da.getBezeroa(bezero4.getErabiltzaileIzena());
		pronostikoak.add(event1.getQuestions().get(0).getPronostics().get(0));
		double dirua1 = bezero4.getDirua();
		double dirua2 = bezero4.getErrepikatzaileak().get(0).getNork().getDirua();
		assertEquals(da.apustuaEgin(pronostikoak, 10, bezero4), bezero4);
		assertTrue(bezero4.getDirua() == dirua1 - 10);
		assertTrue(bezero4.getErrepikatzaileak().get(0).getNork().getDirua() == dirua2);
		da.close();
		
		//Errepikatzailearen dirua ez da aldatzen ez daukalako hilabetean diru nahikorik apustua egiteko
	}
}
