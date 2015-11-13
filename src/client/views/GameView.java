package client.views;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import client.controller.*;

/**
 * @author hamidelmi
 * 
 *         View(GUI) of the game
 */
@SuppressWarnings("serial")
public class GameView extends JFrame {
	private GameController controller;
	private LoginView loginDialog;
	private JLabel fly, scores;
	private JPanel container, scorePanel;

	public GameView(GameController gc) {
		this.controller = gc;
	}

	/**
	 * Shows a login dialog (modal) and asks for a name
	 * 
	 * @return player name
	 */
	public String showLoginDialog() {
		Window win = SwingUtilities.getWindowAncestor(this);
		loginDialog = new LoginView(win, "Login",
				ModalityType.APPLICATION_MODAL);
		loginDialog.setVisible(true);

		return loginDialog.getUsername();
	}

	/**
	 * Initialize the GUI elements
	 * 
	 * @param username
	 */
	public void startGame(String username) {
		// I don't know why it is not working!
		setTitle(username);

		container = new JPanel(null);
		// container.setSize(140, 480);
		container.setBackground(Color.WHITE);

		try {
			fly = new JLabel(new ImageIcon(ImageIO.read(new File("fly.png"))));
		} catch (Exception e) {
		}

		fly.setLocation(10, 10);
		fly.setSize(32, 32);
		fly.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.flyHunted();
			}
		});

		container.add(fly);

		JPanel p = new JPanel(new GridLayout(1, 2));
		p.setBackground(Color.BLUE);
		p.add(container);

		scorePanel = new JPanel();
		scorePanel.setBackground(Color.CYAN);
		JLabel lbl = new JLabel("Scores:");
		scorePanel.add(lbl);

		scores = new JLabel("<html>[Empty]</html>");
		scorePanel.add(scores);

		p.add(scorePanel);
		add(p);
	}

	/**
	 * Show players scores on the left side of the screen
	 * 
	 * @param scores
	 */
	public void updateScores(HashMap<String, Integer> scores) {
		if (this.scores != null) {
			StringBuilder sb = new StringBuilder("<html>");
			for (Map.Entry<String, Integer> entry : scores.entrySet()) {
				String player = entry.getKey();
				Integer score = entry.getValue();
				sb.append(player + ":" + score + "<br/>");
			}
			sb.append("</html>");
			this.scores.setText(sb.toString());
		}
	}

	/**
	 * Change the position of the fly
	 * 
	 * @param x
	 * @param y
	 */
	public void showFly(int x, int y) {
		if (fly != null) 
			fly.setLocation(x * (container.getWidth() - fly.getWidth()) / 100,
					y * (container.getHeight() - fly.getHeight()) / 100);
	}
}
