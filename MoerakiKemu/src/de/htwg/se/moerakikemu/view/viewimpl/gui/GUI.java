package de.htwg.se.moerakikemu.view.viewimpl.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.State;
import de.htwg.se.moerakikemu.view.IViewsObserver;
import de.htwg.se.moerakikemu.view.UserInterface;

public class GUI extends JFrame implements UserInterface, IViewsObserver {
	private static final long serialVersionUID = 2078463309153663728L;

	IController myController;
	IControllerPlayer myPlayerController;
	
	JTextField messageField;
	
	public GUI(IController newController, IControllerPlayer playerController) {
		super("Moeraki Kemu");
		this.myController = newController;
		this.myPlayerController = playerController;
		this.messageField = new JTextField("Spiel-Informationen");

		this.setJMenuBar(new MainMenu());
		
		this.setLayout(new BorderLayout());
		this.add(new MainPanel(myController.getEdgeLength()), BorderLayout.CENTER);
		this.add(messageField, BorderLayout.EAST);

		this.setSize(1024, 768);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/*
	void setSpotColor(final int playerNum, final int x, final  int y) {
		field[x][y].setIcon(playerNum == 0 ? black_icon : red_icon);
	}*/

	@Override
	public void update() {
		State controllerState = myController.returnState();
		
		switch (controllerState) {
		case game_finished:
			myController.setEnd(true);
			break;
		case player_occupied:
			drawCurrentState();
			break;
		case query_player_name:
			queryPlayerName();
			break;
		default:
			break;
		}
	}

	@Override
	public void drawCurrentState() {
		// TODO Auto-generated method stub
		this.repaint();
	}

	@Override
	public void queryPlayerName() {
		String player1Name = "";
		String player2Name = "";

		while ("".equals(player1Name)) {
			player1Name = (String) JOptionPane.showInputDialog("Name für Spieler 1 eigeben:", "Spieler 1");
		}

		while ("".equals(player2Name)) {
			player1Name = (String) JOptionPane.showInputDialog("Name für Spieler 1 eigeben:", "Spieler 1");
		}

		myPlayerController.setName(player1Name, player2Name);
	}

	@Override
	public void printMessage(String msg) {
		// TODO Auto-generated method stub
		messageField.setText(messageField.getText() + "\n" + msg);
	}


}
