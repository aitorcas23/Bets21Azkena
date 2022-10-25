package test.gureTest;
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
	 static DataAccess da;

	@BeforeClass
	public static void setUpClass() {
		//sut= new BLFacadeImplementation();
		
		// you can parametrize the DataAccess used by BLFacadeImplementation
		//DataAccess da= new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		da= new DataAccess(true);

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
			sut.removePertsona("Ulabak");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		try {
			Admin admin1 = new Admin("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
			

			Admin admin2 =(Admin)sut.register(admin1, "admin");
			assertEquals(admin1,admin2);
			sut.removePertsona("Ulabak");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test3() {
		try {
			Langilea langile1 = new Langilea("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));

			Langilea langile2 =(Langilea)sut.register(langile1, "langilea");
			assertEquals(langile1,langile2);
			sut.removePertsona("Ulabak");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test4() {
		try {
			Bezeroa bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
			sut.register(bezero1,"bezeroa");
			
			
			bezero1 =(Bezeroa)sut.register(bezero1, "bezeroa");

			//Programa honera ezin du iritsi
			fail();
		}catch(UserAlreadyExist e) {
			assertTrue(true);
			sut.removePertsona("Ulabak");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test5() {
		try {
			Bezeroa bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
			bezero1 =(Bezeroa)sut.register(bezero1, "bezeroa");

		}catch(NullPointerException e) {
		assertTrue(true);
		sut.removePertsona("Ulabak");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}


				
	
			