import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import businesslogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.ArretaElkarrizketa;
import domain.ArretaMezua;
import domain.Bezeroa;
import domain.BezeroartekoMezua;
import domain.Langilea;
import domain.Mezua;
import exceptions.UserAlreadyExist;
import test.dataAccess.TestDataAccess;


public class removeMezuaINTTest {

	static TestDataAccess testDA=new TestDataAccess();
	static DataAccess sut=new DataAccess();
	private Bezeroa b1 = new Bezeroa("b1",  "b1", "b1", "b1", "p1", "666666661", "b1@email.com", new Date());
	private Bezeroa b2 = new Bezeroa("b2",  "b2", "b2", "b2", "p2", "666666662", "b2@email.com", new Date());
	private Langilea lan = new Langilea("lan",  "lan", "lan", "lan", "pasl", "000000000", "lan@email.com", new Date());
	private Bezeroa bez = new Bezeroa("bez",  "bez", "bez", "bez", "pas", "000000001", "bez@email.com", new Date());
	
	@Test
	public void test1() {
		try {
			testDA.open();
			testDA.register("b1",  "b1", "b1", "b1", "p1", "666666661", "b1@email.com", new Date(), "bezeroa");
			testDA.close();
		}catch (Exception e) {
			System.out.println("bezeroa jada exixtitzen da");
		}
		try {
			testDA.open();
			testDA.register("b2",  "b2", "b2", "b2", "p2", "666666662", "b2@email.com", new Date(), "bezeroa");
			testDA.close();
		}
		catch (Exception e) {
			System.out.println("bezeroa jada exixtitzen da");
		}
		String mezua = "mezua";
		String gaia = "gaia";
		String mota = "mota";
		//configure the state of the system (create object in the dabatase)
		testDA.open();
		BezeroartekoMezua m = testDA.bidaliMezua(b1, b2, mezua, gaia, mota, 0.0, 0.0, 0.0);
		testDA.close();
		
		boolean obtained = sut.removeMezua(m);
		boolean expected = true;
		assertEquals(obtained, expected);
	}
	@Test
	public void test2() {
		String gaia = "gaia";
		String mezua = "mezua";
		try {
			testDA.open();
			testDA.register("b1",  "b1", "b1", "b1", "p1", "666666661", "b1@email.com", new Date(), "bezeroa");
			testDA.register("lan",  "lan", "lan", "lan", "pasl", "000000000", "lan@email.com", new Date(), "langilea");
			testDA.close();
		}
		catch (UserAlreadyExist e) {
			System.out.println("User exists");
		}
		testDA.open();
		testDA.arretaElkarrizketaSortu(testDA.getBezeroa(b1.getErabiltzaileIzena()), gaia, mezua);
		ArretaElkarrizketa e = testDA.bezeroaEsleitu(testDA.getLangilea(lan.getErabiltzaileIzena()));
		testDA.arretaMezuaBidali(e, mezua, true);
		e = testDA.getArretaElkarrizketa(e.getIdentifikadorea());
		ArretaMezua m = e.getMezuak().firstElement();
		boolean obtained = sut.removeMezua(m);
		boolean expected = true;
		assertEquals(obtained, expected);
	}

}

