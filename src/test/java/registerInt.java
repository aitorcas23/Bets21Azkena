import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import org.junit.BeforeClass;
import org.junit.Test;


import businesslogic.BLFacadeImplementation;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Bezeroa;
import domain.Langilea;
import exceptions.UserAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

public class registerInt {
	 static BLFacadeImplementation sut;
	 static TestFacadeImplementation testBL;

	@BeforeClass
	public static void setUpClass() {
		//sut= new BLFacadeImplementation();
		
		// you can parametrize the DataAccess used by BLFacadeImplementation
		//DataAccess da= new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		DataAccess da= new DataAccess();

		sut=new BLFacadeImplementation(da);
		
		testBL= new TestFacadeImplementation();
	}
	
	@Test
	//sut.createQuestion:  The event has one question with a queryText. 
	public void test1() {
		try {

			Bezeroa bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));

			Bezeroa bezero2 =(Bezeroa)sut.register(bezero1, "bezeroa");
			assertEquals(bezero1,bezero2);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		try {
			Admin admin1 = new Admin("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
			

			Admin admin2 =(Admin)sut.register(admin1, "admina");
			assertEquals(admin1,admin2);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test3() {
		try {
			Langilea langile1 = new Langilea("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));

			Langilea langile2 =(Langilea)sut.register(langile1, "langile");
			assertEquals(langile1,langile2);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test4() {
		try {
			Bezeroa bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
			sut.register(bezero1,"bezeroa");
			
			
			bezero1 =(Bezeroa)sut.register(bezero1, "bezero");

			//Programa honera ezin du iritsi
			fail();
		}catch(UserAlreadyExist e) {
			assertTrue(true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test5() {
		try {
			Bezeroa bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
			bezero1 =(Bezeroa)sut.register(bezero1, "bezero");

		}catch(NullPointerException e) {
		assertTrue(true);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}


				
	
			