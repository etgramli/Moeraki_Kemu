package de.htwg.se.moerakikemu.controller.controllerimpl;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.State;
import de.htwg.se.moerakikemu.modellayer.IField;
import de.htwg.se.moerakikemu.modellayer.modellayerimpl.Field;
import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.util.observer.IObserverSubject;
import de.htwg.se.util.observer.ObserverObserver;
import de.htwg.se.util.observer.ObserverSubject;

public class Controller extends ObserverSubject implements IController, IObserverSubject {
	
	private IField gameField;
	private int fieldLength;

	private ControllerHelper helper;
	private IControllerPlayer playerController;
	
	private int xCoordinateStartDot, yCoordinateStartDot;
	
	private String playerWin;
	private boolean quitGame;
	private boolean winner;
	
	public Controller(int fieldLength, IControllerPlayer playerCon) {
		super();
		gameField = new Field(fieldLength);
		this.fieldLength = fieldLength;
		this.playerController = playerCon;
		quitGame = false;
		playerWin = "";
		xCoordinateStartDot = 0;
		yCoordinateStartDot = 0;
		notifyObservers();
	}
	
	public String getIsOccupiedByPlayer(int x, int y) {
		return gameField.getIsOccupiedFrom(x, y);
	}
	
	public int getEdgeLength() {
		return fieldLength;
	}
	
	public int occupy(int x, int y) {
		printInfoAllUIs(x, y);
		
		if(gameField.getIsOccupiedFrom(x, y) != ""){
			return -1;
		} if(!playerController.startDotSet()){
			if(!setStartDot(x, y)){
				return -1;
			}
		}
		gameField.occupy(x, y, playerController.getCurrentPlayerName());
		helper = new ControllerHelper(x, y, fieldLength - 1);
		helper.testSquare();
		testListOfSquares();
		if(playerController.getCurrentPlayerName() != "StartDot"){
			testAllInLine(x, y);
		}
		helper.resetSquareTest();
		playerController.selectNextPlayer();

		if(gameField.isFilled()){
			setEnd(true);
		}

		notifyObservers();
		
		return 0;
	}
	
	public boolean setStartDot(int xCoordinate, int yCoordinate){
		int radiusLow = (fieldLength/2)-1;
		int radiusUp = (fieldLength/2)+1;
		if(xCoordinate >= radiusLow && xCoordinate <= radiusUp){
			if(yCoordinate >= radiusLow && yCoordinate <= radiusUp){
				xCoordinateStartDot = xCoordinate;
				yCoordinateStartDot = yCoordinate;
				return true;
			}
		}
		return false;

	}

	private void testListOfSquares(){
		int[] squareArray = new int[17];
		squareArray = helper.getSquareArray();
		if(squareArray[0] == 1){
			testSquare(squareArray[1], squareArray[2], squareArray[3],squareArray[4]);
		} else if(squareArray[0] == 2){
			testSquare(squareArray[1], squareArray[2], squareArray[3],squareArray[4]);
			testSquare(squareArray[5], squareArray[6], squareArray[7],squareArray[8]);
		} else if(squareArray[0] == 4){
			for(int i = 1; i < 17; i+=4){
				testSquare(squareArray[i], squareArray[i+1], squareArray[i+2], squareArray[i+3]);
			}
		}
	}
	
	private void testSquare(int xMin, int yMin, int xMax, int yMax){
		int []counterForPlayers = {0, 0};
		
		int index;
		index = checkOccupationReturnPlayerGettingPoint(xMin, yMin);
		if (index != -1) {
			counterForPlayers[index]++;
		}
		index = checkOccupationReturnPlayerGettingPoint(xMin, yMax);
		if (index != -1) {
			counterForPlayers[index]++;
		}
		index = checkOccupationReturnPlayerGettingPoint(xMax, yMin);
		if (index != -1) {
			counterForPlayers[index]++;
		}
		index = checkOccupationReturnPlayerGettingPoint(xMax, yMax);
		if (index != -1) {
			counterForPlayers[index]++;
		}

		setPointsOfPlayer(counterForPlayers[0], counterForPlayers[1]);
	}

	private int checkOccupationReturnPlayerGettingPoint(final int x, final int y) {
		if(!"".equals(gameField.getIsOccupiedFrom(x, y))){
			if(gameField.getIsOccupiedFrom(x, y).equals(playerController.getPlayer1Name())) {
				return 0;
			} else if(gameField.getIsOccupiedFrom(x, y).equals(playerController.getPlayer2Name())) {
				return 1;
			}
		}
		return -1;
	}

	private void setPointsOfPlayer(int counter1, int counter2){
		if(counter1 == 3  && counter2 == 1){
			playerController.addAPointPlayer1();
			printInfoALLUIs(playerController.getPlayer1Name());
			System.out.println(playerController.getPlayer1Points());
		}
		if(counter1 == 4){ 
			playerController.addAPointPlayer1();
			playerWin = playerController.getPlayer1Name();
			printInfoALLUIs(playerController.getPlayer1Name());
			setWinner(true);
		}
		if(counter2 == 3 && counter1 == 1){
			playerController.addAPointPlayer2();
			printInfoALLUIs(playerController.getPlayer2Name());
		} 
		if(counter2 == 4){
			playerController.addAPointPlayer2();
			playerWin = playerController.getPlayer2Name();
			printInfoALLUIs(playerController.getPlayer2Name());
			setWinner(true);
		}
	}
	
	private void testAllInLine(int x, int y){
		if(!testIfNearStartDot(x, y)){
			return;
		}
		int distanceTop = yCoordinateStartDot;
		int distanceBot = fieldLength - (yCoordinateStartDot+1);
		int distanceRight = fieldLength - (xCoordinateStartDot+1);
		int distanceLeft = xCoordinateStartDot;
		if(x == xCoordinateStartDot && y < yCoordinateStartDot){
			testDistanceOccupys(false, false, x, y, distanceLeft);
		} else if(x == xCoordinateStartDot && y < yCoordinateStartDot){
			testDistanceOccupys(false, true, x, y, distanceRight);
		} else if(x < xCoordinateStartDot && y == yCoordinateStartDot){
			testDistanceOccupys(true, false, x, y, distanceTop);
		} else if(x > xCoordinateStartDot && y == yCoordinateStartDot){
			testDistanceOccupys(true, true, x, y, distanceBot);
		}
	}

	private boolean testIfNearStartDot(int x, int y){
		if(xCoordinateStartDot == x || yCoordinateStartDot == y){
			return true;
		}
		return false;
	}
	
	private void testDistanceOccupys(boolean itsX, boolean Addition, int x, int y, int distance){
		System.out.println(distance);
		int counter = 0;
		int adder = 0;
		if(Addition){
			adder = 1;
		} else {
			adder = -1;
		}
		
		if(itsX){
			for(int i = 1; i < distance; i++){
				System.out.println("Xadder" + (x+(adder*i)));
				if(gameField.getIsOccupiedFrom(x+adder*i, y).equals(playerController.getCurrentPlayerName())){
					counter++;
					System.out.println("x");
				}
			}
		} else {
			for(int i = 1; i < distance; i++){
				if(gameField.getIsOccupiedFrom(x, y+(adder*i)).equals(playerController.getCurrentPlayerName())){
					System.out.println("Yadder" + (y+(adder*i)));
					counter++;
					System.out.println("y");
				}
			}
		}
		System.out.println(counter);
		if(counter == distance){
			setWinner(true);
		}
	}
	
	public String getWinner(){
		if("".equals(playerWin)){
			if(playerController.getPlayer1Points() > playerController.getPlayer2Points()){
				playerWin = playerController.getPlayer1Name();
			} else if(playerController.getPlayer1Points() < playerController.getPlayer2Points()){
				playerWin = playerController.getPlayer2Name();
			}
		}
		return playerWin;
	}

	public boolean testIfWinnerExists() {
		return winner;
	}
	
	private void setWinner(boolean win){
		winner = win;
	}
	
	public void setEnd(boolean end) {
		quitGame = end;
		printInfoALLUIs();
	}
	
	public boolean testIfEnd(){
		return quitGame;
	}
	
	public void newGame(){
		gameField = new Field(fieldLength);
		for (ObserverObserver ui : observers) {
			((UserInterface) ui).printMessage("");
		}
		for (ObserverObserver ui : observers) {
			((UserInterface) ui).addPoints(0, 0);;
		}
		playerController.newGame();
		playerWin = "";
		quitGame = false;
		winner = false;
		
		notifyObservers();
	}
	
	private void printInfoALLUIs(){
		String pointString = "Das Spiel endet";
		for (ObserverObserver ui : observers) {
			((UserInterface) ui).printMessage(pointString);
		}
	}
	
	private void printInfoALLUIs(String player){
		String pointString = "Ein Punkt fuer " + player;
		for (ObserverObserver ui : observers) {
			((UserInterface) ui).addPoints(playerController.getPlayer1Points(), playerController.getPlayer2Points());;
		}
		for (ObserverObserver ui : observers) {
			((UserInterface) ui).printMessage(pointString);
		}
	}

	private void printInfoAllUIs(int x, int y) {
		int a = x + 1;
		int b = y + 1;
		String xValue = String.valueOf(a);
		String yValue = String.valueOf(b);
		String pointString = "Gewaehlter Punkt: " + xValue + "/" +yValue;
		for (ObserverObserver ui : observers) {
			((UserInterface) ui).printMessage(pointString);
		}
	}

	@Override
	public State getState() {
		if ("".equals(playerController.getPlayer1Name()) || "".equals(playerController.getPlayer2Name())) {
			return State.query_player_name;
		} else if (quitGame) {
			return State.game_finished;
		} else if (winner) {
			return State.player_won;
		} else {
			return State.player_occupied;
		}
	}
	
	
}
