package client.controller;

import impl.IGameClient;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import client.model.GameClient;
import client.views.GameView;

public class GameController implements IGameClient {
	private GameClient gameClient;
	private GameView gameView;
	private HashMap<String, Integer> playersScore;
	private boolean guiInitiated = false;

	public GameController() {
		gameView = new GameView();
		playersScore = new HashMap<String, Integer>();
		String username = gameView.showLoginDialog();

		System.out.println("Try to connet '" + username + "' to the server");

		try {
			gameClient = new GameClient(this);
			gameClient.login(username);

			System.out.println("Connected to the server\r\nReady to play");

			JFrame frame = gameView;
			frame.setTitle("Fly Flap!");
			frame.setBackground(Color.BLACK);
			frame.setSize(640, 480);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			gameView.startGame(username);
			frame.setVisible(true);

			gameView.updateScores(playersScore);
			guiInitiated = true;
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

	@Override
	public void receiveFlyHunted(String playerName, int newPoints) {
		if (playersScore.containsKey(playerName))
			playersScore.replace(playerName, newPoints);
		else
			playersScore.put(playerName, newPoints);

		if (guiInitiated)
			gameView.updateScores(playersScore);
	}

	@Override
	public void receiveFlyPosition(int x, int y) {
		gameView.showFly(x, y);
	}
}
