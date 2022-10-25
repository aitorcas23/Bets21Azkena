package test.gureTest;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.Calendar;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Bezeroa;
import domain.Errepikapena;
import domain.Event;
import domain.Pronostikoa;
import exceptions.PronosticAlreadyExist;

import org.junit.BeforeClass;

public class ApustuaEginDABTest {
	private static DataAccess da;
	static Bezeroa bezero1;
	static Bezeroa bezero2;
	static Bezeroa bezero21;
	//
	static Event event1;
	
	@BeforeClass
	public static void setDataBase() {
		da = new DataAccess(true);
		da.initializeDB();
		da.open(false);
		
		bezero1 = da.getBezeroa("Tarek12301");
		da.diruaSartu(100, bezero1);
		
		bezero2 = da.getBezeroa("PelloJoxepe");
		da.diruaSartu(100, bezero2);
		bezero21 = da.getBezeroa("Na1ara");
		da.diruaSartu(100, bezero21);
		Errepikapena e= new Errepikapena(bezero21,bezero2, 1,40,0);
		da.errepikatu(e);
		
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		event1 = da.getEvents(UtilDate.newDate(year, month, 17)).get(0);
		event1.getQuestions().get(0).setBetMinimum(5);
		try {
			da.createPronostic(event1.getQuestions().get(0), "no se que poner", 2);
		} catch (PronosticAlreadyExist e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(event1.getQuestions().size());
		da.close();
	}
	
	@Test
	public void test1() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		
		bezero1 = da.getBezeroa(bezero1.getErabiltzaileIzena());
		System.out.println(event1.getQuestions().get(0).getPronostics().size());
		pronostikoak.add(event1.getQuestions().get(0).getPronostics().get(0));
		double dirua1 = bezero1.getDirua();
		assertEquals(da.apustuaEgin(pronostikoak, 10, bezero1), bezero1);
		assertTrue(bezero1.getDirua() == dirua1 - 10);
		da.close();
		
		//Dirua kentzen du apustua egiten duelako
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
		assertTrue(bezero2.getErrepikatzaileak().get(0).getNork().getDirua() == dirua2-10);
		da.close();
		
		//Dirua kentzen du apustua egiten duelako, eta errepikatzaileari ere
	}
	
	
	
	//Ezin da test3 egin ez dagoelako apustua bukatua egoteko modurik
	
	
	
	@Test(expected = Exception.class)
	public void test4() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		
		bezero2 = da.getBezeroa(bezero2.getErabiltzaileIzena());
		da.apustuaEgin(pronostikoak, 10, bezero2);
		da.close();
		
		//Testa ez du ondo funtzionatzen ez dalako errorerik sortzen, apustua ondo egiten da.
	}
	
	@Test(expected = Exception.class)
	public void test5() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		
		bezero2 = da.getBezeroa(bezero2.getErabiltzaileIzena());
		da.apustuaEgin(pronostikoak, 4, bezero2);
		da.close();
		
		//Testa ez du ondo funtzionatzen ez dalako errorerik sortzen, apustua ondo egiten da.
	}
	
	@Test(expected = Exception.class)
	public void test6() {
		ArrayList<Pronostikoa> pronostikoak = new ArrayList<Pronostikoa>();
		da.open(false);
		
		bezero2 = da.getBezeroa(bezero2.getErabiltzaileIzena());
		da.apustuaEgin(pronostikoak, 101, bezero2);
		da.close();
		
		//Testa ez du ondo funtzionatzen ez dalako errorerik sortzen, apustua ondo egiten da.
	}
}
