package network;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class NetworkTest extends TestCase {
	
	Network testObject;
	
	@Before
	public void startup() {
		this.testObject = new Network(1,2,4,5);
	}
	
	@Test
	public void constructorTest() {
		assertTrue(true);
	}

}
