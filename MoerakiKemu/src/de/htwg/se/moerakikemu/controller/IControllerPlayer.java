package de.htwg.se.moerakikemu.controller;

/**
 * Methods to manage the values of the two participating players.
 */
public interface IControllerPlayer {

	/**
	 * set the Names of the two players
	 * @param player1name Name of player 1.
	 * @param player2name Name of player 2.
	 */
	void setName(String player1name, String player2name);
	
	/**
	 * set the points of the player to zero
	 */
	void newGame();
	
	/**
	 * looks if the start Dot is set
	 */
	boolean startDotSet();
	
	/**
	 * Returns the name of player1.
	 *
	 * @return A String, not null.
	 */
	String getPlayer1Name();
	
	/**
	 * Returns the name of player2.
	 *
	 * @return A String, not null.
	 */
	String getPlayer2Name();
	
	/**
	 * Returns the points of the first player.
	 *
	 * @return Amount of points, 0 or more.
	 */
	int getPlayer1Points();
	
	/**
	 * Returns the points of the second player.
	 *
	 * @return Amount of points, 0 or more.
	 */
	int getPlayer2Points();
	
	/**
	 * adds a Point to Player 1
	 */
	void addAPointPlayer1();
	
	/**
	 * adds a Point to Player 2
	 */
	void addAPointPlayer2();
	
	/**
	 * Return the current Player
	 * @return A String, not null
	 */
	String getCurrentPlayerName();
	
	/**
	 * Determines the player that has the next turn.
	 * If no one is the current player then player1 begins.
	 * Else player1 and player2 are altering the next player.
	 */
	void selectNextPlayer();
	
	
}
