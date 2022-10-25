package test.gureTest;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import configuration.UtilDate;
import domain.Pronostikoa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import businesslogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Bezeroa;

@RunWith(MockitoJUnitRunner.class)
public class apustuaEginMockINTTest {
	@Mock
	DataAccess mockitoDA;
	@InjectMocks
	BLFacadeImplementation sut;
	
	@Test
	public void test1() {
		Bezeroa b1 = new Bezeroa("b1",  "b1", "b1", "b1", "p1", "666666661", "b1@email.com", UtilDate.newDate(2002, 9, 11));
		ArrayList<Pronostikoa> alp;
		alp = new ArrayList<Pronostikoa>();
		Mockito.doReturn(b1).when(mockitoDA).apustuaEgin(Mockito.any(ArrayList.class), Mockito.anyDouble(), Mockito.any(Bezeroa.class));
		Bezeroa obtained = sut.apustuaEgin(alp, 0, b1);
		assertEquals(obtained, b1);
	}
}
