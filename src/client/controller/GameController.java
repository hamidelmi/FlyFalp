package client.controller;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import client.model.GameClient;
import client.views.GameView;

public class GameController {
	private GameClient gameClient;
	private GameView gameView;

	public GameController() {
		gameView = new GameView();
		String username = gameView.showLoginDialog();

		System.out.println("Try to connet '" + username + "' to the server");

		try {
			gameClient = new GameClient();
			gameClient.login(username);

			System.out.println("Connected to the server\r\nReady to play");

			JFrame frame = gameView;
			frame.setTitle("Fly Flap!");
			frame.setBackground(Color.BLACK);
			frame.setSize(640, 480);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			gameView.startGame(username);
			frame.setVisible(true);
		} catch (Exception ex) {
			System.out.println("Connection failed");
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameController();

			}
		});
	}
}
