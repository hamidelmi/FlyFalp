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
	private int x, y;
	private String username;

	public GameController() {
		gameView = new GameView(this);
		playersScore = new HashMap<String, Integer>();
		username = gameView.showLoginDialog();

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
			gameView.showFly(x, y);
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
		if (playersScore.containsKey(playerName)) {
			if (newPoints == -1)
				playersScore.remove(playerName);
			else
				playersScore.replace(playerName, newPoints);
		} else
			playersScore.put(playerName, newPoints);

		if (guiInitiated)
			gameView.updateScores(playersScore);
	}

	@Override
	public void receiveFlyPosition(int x, int y) {
		this.x = x;
		this.y = y;
		if (guiInitiated)
			gameView.showFly(x, y);
	}

	public void flyHunted() {
		try {
			gameClient.huntFly(username);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logout() {
		try {
			gameClient.logout(username);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
