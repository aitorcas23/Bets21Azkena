import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;
import dataAccess.DataAccess;
import domain.ArretaElkarrizketa;
import domain.ArretaMezua;
import domain.Bezeroa;
import domain.BezeroartekoMezua;
import domain.Langilea;
import exceptions.UserAlreadyExist;
import test.dataAccess.TestDataAccess;

/**
 * 
 */

public class removeMezuaDAWTest {

	static TestDataAccess testDA=new TestDataAccess();
	static DataAccess sut=new DataAccess(true);
	private Bezeroa b1 = new Bezeroa("b1",  "b1", "b1", "b1", "p1", "666666661", "b1@email.com", new Date());
	private Bezeroa b2 = new Bezeroa("b2",  "b2", "b2", "b2", "p2", "666666662", "b2@email.com", new Date());
	private Langilea lan = new Langilea("lan",  "lan", "lan", "lan", "pasl", "000000000", "lan@email.com", new Date());
	@Test
	public void test1() {
		try {
			testDA.open();
			testDA.register(b1, "bezeroa");
			testDA.close();
		}catch (Exception e) {
			System.out.println("bezeroa jada exixtitzen da");
		}
		try {
			testDA.open();
			testDA.register(b2, "bezeroa");
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

		//invoke System Under Test (sut)  
		try {
			boolean obtained = sut.removeMezua(m);
			assertTrue(!sut.getMezuak(b1).contains(m));
			assertTrue(obtained);
		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removePertsona(b1);
			testDA.removePertsona(b2);
			testDA.close();
		}
	} 

	//mezua ez da instanceof BezeroartekoMezua eta elkarrizketa amaituta dago
	@Test
	public void test2() {
		String gaia = "gaia";
		String mezua = "mezua";
		boolean obtained = true;
		try {
			testDA.open();
			testDA.register(b1, "bezeroa");
			testDA.close();
		}
		catch (UserAlreadyExist e) {
			System.out.println("User exists");
		}
		try {
			testDA.open();
			testDA.register(lan, "langilea");
			testDA.close();
		}catch (Exception e) {
			System.out.println("bezeroa jada exixtitzen da");
		}
		testDA.open();
		testDA.arretaElkarrizketaSortu(testDA.getBezeroa(b1.getErabiltzaileIzena()), gaia, mezua);
		ArretaElkarrizketa e = testDA.bezeroaEsleitu(testDA.getLangilea(lan.getErabiltzaileIzena()));
		testDA.arretaMezuaBidali(e, mezua, false);
		
		testDA.amaituElkarrizketa(e);
		e = testDA.getArretaElkarrizketa(e.getIdentifikadorea());
		ArretaMezua m = e.getMezuak().lastElement();
		sut.removeMezua(m);
		assertTrue(!sut.getMezuak(b1).contains(m));
		assertTrue(obtained);
	
		testDA.removePertsona(b1);
		testDA.removePertsona(lan);
		testDA.close();
	}

	//mezua ez da instanceof BezeroartekoMezua eta elkarrizketa amaituta dago eta elkarrizketa hutsik dago
	@Test
	public void test3() {
		String gaia = "gaia";
		String mezua = "mezua";
		boolean expected = true;
		try {
			testDA.open();
			testDA.register(b2, "bezeroa");
			testDA.close();
		}
		catch (UserAlreadyExist e) {
			System.out.println("User exists");
		}
		try {
			testDA.open();
			testDA.register(lan, "langilea");
			testDA.close();
		}
		catch (UserAlreadyExist e) {
			System.out.println("User exists");
		}
		testDA.open();
		testDA.arretaElkarrizketaSortu(testDA.getBezeroa(b2.getErabiltzaileIzena()), gaia, mezua);
		ArretaElkarrizketa e = testDA.bezeroaEsleitu(testDA.getLangilea(lan.getErabiltzaileIzena()));
		testDA.arretaMezuaBidali(e, mezua, false);
		System.out.println(e.getBezeroakBidalitakoak().size() + " " + e.getLangileakBidalitakoak().size());
		ArretaMezua m = e.getMezuak().get(0);
		testDA.removeMezua(e.getMezuak().lastElement());
		boolean obtained = sut.removeMezua(e.getMezuak().lastElement());
		e = testDA.bezeroaEsleitu(testDA.getLangilea(lan.getErabiltzaileIzena()));
		assertEquals(expected, obtained);
		assertTrue(!testDA.getMezuak(b2).contains(m));
		assertFalse(b2.getArretaElkarrizketak().contains(e));
		testDA.removePertsona(b2);
		testDA.removePertsona(lan);
		testDA.close();
	}
	//mezua ez da instanceof BezeroartekoMezua eta elkarrizketa ez dago amaituta
	@Test
	public void test4() {
			String gaia = "gaia";
			String mezua = "mezua";
			boolean expected = true;
			try {
				testDA.open();
				testDA.register(b1, "bezeroa");
				testDA.close();
			}
			catch (UserAlreadyExist e) {
				System.out.println("User exists");
			}
			try {
				testDA.open();
				testDA.register(lan, "langilea");
				testDA.close();
			}
			catch (UserAlreadyExist e) {
				System.out.println("User exists");
			}
			testDA.open();
			testDA.arretaElkarrizketaSortu(testDA.getBezeroa(b1.getErabiltzaileIzena()), gaia, mezua);
			ArretaElkarrizketa e = testDA.bezeroaEsleitu(testDA.getLangilea(lan.getErabiltzaileIzena()));
			testDA.arretaMezuaBidali(e, mezua, false);
			testDA.amaituElkarrizketa(e);
			e = testDA.getArretaElkarrizketa(e.getIdentifikadorea());
			ArretaMezua m = e.getMezuak().lastElement();
			boolean obtained = sut.removeMezua(m);
			assertTrue(testDA.getArretaElkarrizketa(e.getIdentifikadorea()).getMezuak().contains(m));
			assertEquals(obtained, expected);
			
			
			testDA.close();
	}


}
