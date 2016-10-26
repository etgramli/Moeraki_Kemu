package de.htwg.se.moerakikemu.controller;

/**
 * This enum represents the State of the controller.
 */
public enum State {

	PLAYER_OCCUPIED,	// A Player is occupying a Spot
	QUERY_PLAYER_NAME,	// At least the name of one Player is not set
	PLAYER_WON,			// A Player won the game
	GAME_FINISHED		// The game is ended by the user and the program must be aborted
}
