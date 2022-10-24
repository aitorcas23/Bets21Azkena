import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Bezeroa;
import domain.Langilea;
import domain.Pertsona;
import exceptions.UserAlreadyExist;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.runners.MockitoJUnitRunner;
import businesslogic.BLFacadeImplementation;

@RunWith(MockitoJUnitRunner.class)
public class registerMockInt {
	@Mock
	DataAccess mockitoDA;
	@InjectMocks
	BLFacadeImplementation sut;


	//sut.createQuestion:  The event has one question with a queryText. 


	@Test
	//sut.createQuestion:  The event has NOT a question with a queryText.
	public void test1() {
		try {
			Bezeroa bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));

			Mockito.doReturn(bezero1).when(mockitoDA).register(Mockito.any(Pertsona.class),Mockito.anyString());


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

			Mockito.doReturn(admin1).when(mockitoDA).register(Mockito.any(Pertsona.class),Mockito.anyString());


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

			Mockito.doReturn(langile1).when(mockitoDA).register(Mockito.any(Pertsona.class),Mockito.anyString());


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

			Mockito.doThrow(new UserAlreadyExist()).when(mockitoDA).register(Mockito.any(Pertsona.class),Mockito.anyString());
			
			Bezeroa bezero2 =(Bezeroa)sut.register(bezero1, "bezero");

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
			Bezeroa bezero1 = null;

			Mockito.doReturn(null).when(mockitoDA).register(Mockito.any(Pertsona.class),Mockito.anyString());
			Bezeroa bezero11 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));

			Bezeroa bezero2 =(Bezeroa)sut.register(bezero11, "bezero");

			assertEquals(bezero2,bezero1);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}


				
	