package model.level;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import controller.ControllerLevel;
import model.other.PlayerSave;
import model.other.ReadFile;
import view.View;

public class LoadLevel implements ILevel {

	public ControllerLevel control;

	public static int timeSet = 2;
	public int timeLeft = timeSet;
	private int countFrame = 0;

	public LoadLevel(ControllerLevel control) {
		this.control = control;
	}

	@Override
	public void update() {

		if (timeLeft == 0) {
			control.isLoad = false;
			timeLeft = timeSet;
			if (!control.lost)
				control.inGame = true;
			else {
				control.inGame = false;
				control.lost = false;

				String name = JOptionPane.showInputDialog("Enter your name");

				PlayerSave p = new PlayerSave(name, control.score);
				ReadFile.luuFile("source/Save/save.txt", p);

				control.lives = 3;
				control.coins = 0;
				control.score = 0;
			}
			countFrame = 0;
		}
		countFrame++;
		timeLeft = timeSet - ((int) (countFrame / View.FPS));

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, View.WIDTH, View.HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString(control.currentLevel, 320, 300);
	}

	@Override
	public void keyPressed(int e) {

	}

	@Override
	public void keyReleased(int e) {

	}

}
