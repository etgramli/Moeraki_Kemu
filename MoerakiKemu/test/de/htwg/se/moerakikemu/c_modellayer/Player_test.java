package de.htwg.se.moerakikemu.c_modellayer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.moerakikemu.modellayer.modellayerimpl.Player;


public class Player_test {
	private Player player;

	@Before
	public void setUp(){
		player = new Player();
	}
	
	@Test
	public void test_getName_returnNotNullString(){
		final String playerName = "alpha";
		player.setName(playerName);
		assertEquals(playerName, player.getName());
	}
	
	@Test
	public void test_getPoints_returnTwo(){
		player.addPoints(2);
		assertEquals(2, player.getPoints());
	}
	
	@Test
	public void test_addPoints_negativeValueDoesNotLowerValue() {
		player.addPoints(-2);
		assertEquals(0, player.getPoints());
	}
	
	@Test
	public void test_tryToSetNameToNull_staysTheSame() {
		String oldPlayerName = player.getName();
		player.setName(null);
		assertEquals(oldPlayerName, player.getName());
	}
	
	@Test
	public void test_refreshPoints_pointsAreZero() {
		player.refreshPoints();
		assertEquals(0, player.getPoints());

		player.addPoints(20);
		assertEquals(20, player.getPoints());

		player.refreshPoints();
		assertEquals(0, player.getPoints());
	}
}

