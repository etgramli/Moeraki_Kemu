package de.htwg.se.moerakikemu.view.viewimpl;

import static org.junit.Assert.*;

import org.junit.Test;

public class WebInterfaceTest {

	@Test
	public void test() {
		final String coordinates = "0-0";
		
		int []ij = WebInterface.splitXY(coordinates);
		
		assertEquals(0, ij[0]);
		assertEquals(0, ij[1]);
	}

}
