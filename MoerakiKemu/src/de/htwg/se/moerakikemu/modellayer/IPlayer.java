package de.htwg.se.moerakikemu.modellayer;

public interface IPlayer {

	/**
	 * Returns the number of points of the player.
	 * @return Int >= 0;
	 */
	int getPoints();
	
	/**
	 * Returns the name of the player.
	 * @return String or null.
	 */
	String getName();
	
	/**
	 * Adds an amount of points to the current amount of points.
	 * @param amount Must be greater than 0, or it has no effect.
	 */
	void addPoints(int amount);
	
	/**
	 * Sets the name of a player.
	 * @param name Name as string, should not be null.
	 */
	void setName (String name);
	
	/**
	 * Sets the points to zero.
	 */
	void refreshPoints();

}
