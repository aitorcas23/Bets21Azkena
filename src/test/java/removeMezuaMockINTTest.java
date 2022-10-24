import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.Mockito;
import org.mockito.junit.*;
import businesslogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.ArretaMezua;
import domain.BezeroartekoMezua;

@RunWith(MockitoJUnitRunner.class)
public class removeMezuaMockINTTest {

	@Mock
	DataAccess mockitoDA;
	@InjectMocks
	BLFacadeImplementation sut;
	
	@Test
	public void test1() {
		Mockito.doReturn(true).when(mockitoDA).removeMezua(Mockito.any(BezeroartekoMezua.class));
		BezeroartekoMezua m = new BezeroartekoMezua();
		boolean obtained = sut.removeMezua(m);
		boolean expected = true;
		assertEquals(obtained, expected);
	}
	@Test
	public void test2() {
		Mockito.doReturn(false).when(mockitoDA).removeMezua(Mockito.any(ArretaMezua.class));
		ArretaMezua m = new ArretaMezua();
		boolean obtained = sut.removeMezua(m);
		boolean expected = false;
		assertEquals(obtained, expected);
	}
	
}
