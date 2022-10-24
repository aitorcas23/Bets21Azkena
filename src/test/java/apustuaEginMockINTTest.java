import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Date;
import domain.Pronostikoa;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import businesslogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Bezeroa;
@RunWith(MockitoJUnitRunner.class)
class apustuaEginMockINTTest {
	
	@Mock
	DataAccess mockitoDA = Mockito.mock(DataAccess.class);
	@InjectMocks
	BLFacadeImplementation sut = new BLFacadeImplementation(mockitoDA);
	private Bezeroa b1 = new Bezeroa("b1",  "b1", "b1", "b1", "p1", "666666661", "b1@email.com", new Date());
	private ArrayList<Pronostikoa> alp;
	
	@Test
	void test1() {
		
		alp = new ArrayList<Pronostikoa>();
		Mockito.doReturn(b1).when(mockitoDA).apustuaEgin(Mockito.any(ArrayList.class), Mockito.anyDouble(), Mockito.any(Bezeroa.class));
		Bezeroa obtained = sut.apustuaEgin(alp, 0, b1);
		assertEquals(obtained, b1);
	}

}
