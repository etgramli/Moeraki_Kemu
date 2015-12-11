package de.htwg.se.moerakikemu.modellayer;

public interface IField {

	boolean occupy(int x, int y, String getCurrentPlayerName);
	boolean getIsOccupied(int x, int y);
	String getIsOccupiedFrom(int x, int y);
	
	boolean isFilled();
	int getEdgeLength();
}
