package controller;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import model.level.ILevel;
import model.level.Level;
import model.level.LoadLevel;
import model.level.Menu;

public class ControllerLevel {
	private Level level;
	private ILevel menu;
	private ILevel load;
	public String currentLevel;

	private Font font;

	public boolean paused;
	public boolean inGame;
	public boolean isLoad;
	public boolean isTurnOnMusic;
	public boolean lost;

	public int lives = 3;
	public int score;
	public int coins;

	public ControllerLevel() {

		paused = false;

		menu = new Menu(this);

		load = new LoadLevel(this);

		setFont();

		inGame = false;
		isLoad = false;

		isTurnOnMusic = true;

	}

	public void setPaused(boolean b) {
		paused = b;
	}

	public void update() {
		if (paused) {
			return;
		}

		if (isLoad) {
			load.update();
		}
		if (!inGame) {
			menu.update();
		}
		if (inGame && !isLoad)
			level.update();

	}

	public void render(Graphics2D g) {
		g.setFont(font);
		if (paused) {

			g.drawString("PAUSED", 350, 300);
			return;
		}

		if (isLoad) {
			load.render(g);
		}

		if (!inGame) {
			menu.render(g);
		}
		if (inGame && !isLoad) {
			level.render(g);
		}

	}

	public void keyPressed(int e) {

		if (!inGame) {
			menu.keyPressed(e);
		} else if (level != null) {
			level.keyPressed(e);

			if (e == KeyEvent.VK_P) {
				if (!paused)
					paused = true;
				else
					paused = false;
			}
		}
	}

	public void keyReleased(int e) {
		if (level != null)
			level.keyReleased(e);
	}

	private void setFont() {
		InputStream stream = getClass().getResourceAsStream("/Font/font.otf");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(28f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setCurrentLevel(Level level, String info) {
		if (level == null) {
			gameOver();
			lost = true;
			return;
		}
		this.currentLevel = info;
		this.level = level;
	}

	public void resetLevel() {
		isLoad = true;
		level.restart();
	}

	public void gameOver() {
		isLoad = true;
		level.saveScore();
		LoadLevel.timeSet = 5;
		this.currentLevel = "GAME OVER";
	}

	public void winLevel() {
		isLoad = true;
		level.winLevel();
	}

	public void onOffMusic() {
		if (!isTurnOnMusic) {
			isTurnOnMusic = true;
		} else
			isTurnOnMusic = false;
	}

}
