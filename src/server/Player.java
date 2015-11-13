package server;

/**
 * @author hamidelmi
 * 
 *         Abstract representation of a player
 *
 */
public class Player {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.score = 0;
	}

	private String name;

	/**
	 * @return player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set player's name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	private int score;

	/**
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * increase player's score
	 */
	public void increaseScore() {
		this.score++;
	}
}
