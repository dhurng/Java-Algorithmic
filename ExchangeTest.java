import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ExchangeTest {
	Exchange e;
	Exchange e2;
	Exchange e3;
	Exchange e4;

	@Before
	public void setUp() throws Exception{
		e = new Exchange(3);
		e.setRate(0, 1, 1.5);
		e.setRate(0, 2, 2);
		e.setRate(1, 2, 1.5);
		e.setRate(2, 0, .3);
	}

	@Test
	public void Test(){
		assertEquals(.45 , e.bestExchangeRate(1, 0), .01);
		assertEquals(1.0 , e.bestExchangeRate(1, 1), .01);
		assertEquals(2.25 , e.bestExchangeRate(0, 2), .01);
		assertEquals(false , e.arbitrageExists());
	}
	
	@Before
	public void setUp2() throws Exception{
		e2 = new Exchange(3);
		e2.setRate(0, 1, .5);
		e2.setRate(0, 2, 2);
		e2.setRate(1, 0, 1.9);
		e2.setRate(1, 2, 4);
		e2.setRate(2, 0, .51);
		e2.setRate(2, 1, .22);
	}

	@Test
	public void Test2(){
		assertEquals(true , e2.arbitrageExists());
	}
	
	@Before
	public void setUp3() throws Exception {
		e3 = new Exchange(-1);
	}
}
