package model.other;

import java.awt.Graphics2D;

import controller.ControllerGame;
import controller.ControllerLevel;

public class HUD implements IObserve {

	int score = 0;
	public int scoreSum = 0;
	private boolean stop = false;

	public int coins = 0;
	public int times = 0;
	public int life = 0;

	private ControllerGame control;

	public HUD(ControllerGame control, ControllerLevel controlLevel) {
		this.control = control;
		times = control.getTimeLeft();
		coins = control.getCoins();
		life = control.getLife();
		scoreSum = controlLevel.score;
		scoreSum = 0;

	}

	public void update() {
		if (!stop) {
			times = control.getTimeLeft();
			coins = control.getCoins();
			life = control.getLife();
		}

	}

	public void render(Graphics2D g) {
		if (score != 0) {

			g.drawString("+" + score, 50, 110);
			score = 0;
		}
		g.drawString("MARIO", 50, 40);
		g.drawString("" + scoreSum, 50, 80);
		g.drawString("x" + coins, 300, 40);
		g.drawString("LIVES: " + life, 450, 40);
		g.drawString("Time", 700, 40);
		g.drawString("" + times, 700, 80);

	}

	public int list(String s) {

		int score = 0;
		switch (s) {
		case "coin":
			score = 200;
			break;
		case "mushroomeat":
			score = 1000;
			break;

		case "stomp":
			score = 200;
			break;

		case "enemydie":
			score = 200;
			break;

		case "win":
			score = times *100 +5000;
			break;

		}
		return score;
	}

	@Override
	public void updateObserve(String s) {
		int score = list(s);
		scoreSum += score;

		if (s.equals("death") || s.equals("win"))
			stop = true;
		else if (s.equals("continue")) {
			stop = false;
		}
	}
}
