package server;

public class Player {

	public Player(String name) {
		this.name = name;
		this.score = 0;
		this.removeMark = false;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int score;

	public int getScore() {
		return score;
	}

	public void increaseScore() {
		this.score++;
	}

	private boolean removeMark;

	public boolean getRemoveMark() {
		return removeMark;
	}

	public void markRemove() {
		removeMark = true;
	}
}
