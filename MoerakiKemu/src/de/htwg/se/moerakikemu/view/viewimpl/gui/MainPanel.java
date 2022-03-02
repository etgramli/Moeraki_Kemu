package de.htwg.se.moerakikemu.view.viewimpl.gui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import java.awt.Color;


public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private IController myController;
	private IControllerPlayer myPlayerController;

	private ImageIcon blackIcon = new ImageIcon();
	private ImageIcon redIcon = new ImageIcon();
	private ImageIcon greenIcon = new ImageIcon();

	private JButton[][] field;

	private void initIconImages() {
		final int iconDimension = 40;

		Image imageIcon = new ImageIcon("Spot_black.png").getImage();
		blackIcon.setImage(imageIcon.getScaledInstance(iconDimension, iconDimension, Image.SCALE_SMOOTH));

		imageIcon = new ImageIcon("Spot_blue.png").getImage();
		redIcon.setImage(imageIcon.getScaledInstance(iconDimension, iconDimension, Image.SCALE_SMOOTH));

		imageIcon = new ImageIcon("Spot_green.png").getImage();
		greenIcon.setImage(imageIcon.getScaledInstance(iconDimension, iconDimension, Image.SCALE_SMOOTH));
	}

	private JButton[][] getJButtonField(final int fieldLength) {
		MouseListener listener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				JButton pressedButton = (JButton) me.getSource();
				int []coordinates = getButtonCoordinates(pressedButton);
				String name = myController.getIsOccupiedByPlayer(coordinates[0]-1, coordinates[1]-1);
				if ("".equals(name)) {
					setSpotColor(pressedButton, myPlayerController.getCurrentPlayerName());
					myController.occupy(coordinates[0]-1, coordinates[1]-1);
				}

			}
		};

		JButton[][] newField = new JButton[fieldLength][fieldLength];
		for (int i = 0; i < fieldLength; i++) {
			for (int j = 0; j < fieldLength; j++) {
				newField[i][j] = new JButton();
				this.add(newField[i][j]);
				newField[i][j].addMouseListener(listener);
				newField[i][j].setToolTipText("(" + (i + 1) + "/" + (j + 1) + ")");
				newField[i][j].setText("+");
				newField[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
				newField[i][j].setVerticalTextPosition(SwingConstants.CENTER);
				newField[i][j].setOpaque(false);
				newField[i][j].setContentAreaFilled(false);
				newField[i][j].setBorderPainted(false);
			}
		}

		return newField;
	}

	public MainPanel(IController controller, IControllerPlayer playerController, final int fieldLength) {
		super();
		this.myController = controller;
		this.myPlayerController = playerController;
		this.setBackground(new Color(200, 120, 40));

		initIconImages();

		this.setLayout(new GridLayout(fieldLength, fieldLength));
		field = getJButtonField(fieldLength);
	}

	
	public void updateField() {
		final int fieldLength = myController.getEdgeLength();
		for (int i = 0; i < fieldLength; i++) {
			for (int j = 0; j < fieldLength; j++) {
				setSpotColor(field[i][j], myController.getIsOccupiedByPlayer(i, j));
			}
		}
		this.repaint();
	}
	
	private int[] getButtonCoordinates(JButton button) {
		int []xyCoordinates = new int[2];
		
		Scanner getNumbers = new Scanner(button.getToolTipText()); 
		getNumbers.useDelimiter("[()/]");
		xyCoordinates[0] = getNumbers.nextInt();
		xyCoordinates[1] = getNumbers.nextInt();
		getNumbers.close();
		
		return xyCoordinates;
	}

	private void setSpotColor(JButton buttonToChange, final String playerNameOnSpot) {
		if (playerNameOnSpot == null || "".equals(playerNameOnSpot)){
			buttonToChange.setText("+");
			buttonToChange.setIcon(null);
		} else if (myPlayerController.getPlayer1Name().equals(playerNameOnSpot)) {
			buttonToChange.setText("");
			buttonToChange.setIcon(blackIcon);
		} else if (myPlayerController.getPlayer2Name().equals(playerNameOnSpot)) {
			buttonToChange.setText("");
			buttonToChange.setIcon(redIcon);
		} else if ("StartDot".equals(playerNameOnSpot)){
			buttonToChange.setText("");
			buttonToChange.setIcon(greenIcon);
		}
	}

}
