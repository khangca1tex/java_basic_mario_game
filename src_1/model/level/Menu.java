package model.level;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import controller.ControllerLevel;
import model.other.Picture;
import model.other.PlayerSave;
import model.other.ReadFile;
import view.View;

public class Menu implements ILevel {

	private String[] menu = { "Play", "Cretis", "Option", "How to play", "High Score", "Exit" };
	// de biet dang dinh chon o dau
	private byte current;

	// biet dc so chon de hien ra cho phu hop
	private byte choose;

	private final byte CRETIS = 1;
	private final byte OPTION = 2;
	private final byte HOWTOPLAY = 3;
	private final byte HIGHSCORE = 4;

	// hinh anh
	Picture bg, title, mr;

	// controller
	private ControllerLevel control;

	public Menu(ControllerLevel control) {
		current = 0;
		choose = 0;

		this.control = control;

		// truyen hinh anh
		bg = new Picture(0, 0, View.WIDTH, View.HEIGHT, "/Background/bg.jpg");
		title = new Picture(50, 80, 400, 200, "/title/NES.png");
		mr = new Picture("/Mushroom/mr.png");
		mr.setWidth(30);
		mr.setHeight(30);

	}

	@Override
	public void update() {

	}

	@Override
	public void render(Graphics2D g) {

		// draw Image
		bg.render(g);
		title.render(g);

		switch (choose) {
		case CRETIS:
			// code o day
			drawCretis(g);

			break;
		case OPTION:
			drawOption(g);

			break;
		case HOWTOPLAY:
			drawHowtoPlay(g);

			break;

		case HIGHSCORE:
			drawHighScore(g);
			break;

		default:
			drawAll(g);
		}
	}

	// in tren man hinh
	private void drawAll(Graphics2D g) {

		int x = 270;
		int y = 350;

		for (byte i = 0; i < menu.length; i++) {

			if (i == current) {
				mr.setXstart(x - 35);
				mr.setYstart(y + i * 30 - 30);
				mr.render(g);

			}
			g.drawString(menu[i], x, y + i * 30);
		}

	}

	// in diem cao
	private void drawHighScore(Graphics2D g) {
		int x = 270;
		int y = 350;
		ArrayList<PlayerSave> list = ReadFile.docFile("source/Save/save.txt");
		for (int i = 0; i < list.size(); i++) {
			g.drawString(i + 1 + "  " + list.get(i).getName() + "   " + list.get(i).getPoint(), x, y + i * 33);

			if (i == 5) {
				break;
			}
		}
	}

	// in thong tin

	private void drawCretis(Graphics2D g) {
		int x = 150;
		int y = 350;

		String[] name = { "Pham Quang Tuan - 16130646", "Bui Minh Hieu - 16130375", "Ngo Van Nhan - 16130495", "Phu Van Son - 16130552",
				"Nguyen Le Khang - 16130419" };

		for (int i = 0; i < name.length; i++) {
			g.drawString(name[i], x, y + i * 33);

		}

	}

	// hien Option
	private void drawOption(Graphics2D g) {
		int x = 270;
		int y = 450;

		mr.setXstart(x - 35);
		mr.setYstart(y);
		mr.render(g);
		String s;
		if (control.isTurnOnMusic) {
			s = " MUSIC: ON";
		} else
			s = " MUSIC: OFF";

		g.drawString(s, x, y + 30);

	}

	// how toplay
	private void drawHowtoPlay(Graphics2D g) {
		int x = 150;
		int y = 350;

		String[] name = { "Press CTRL to RUN", "PRESS SPACE TO JUMP", "PRESS ARROW LEFT OR RIGHT", "TO MOVE MORIO",
				"PRESS F TO FIRE", "PRESS P TO PAUSE" };

		for (int i = 0; i < name.length; i++) {
			g.drawString(name[i], x, y + i * 33);

		}
	}

	@Override
	public void keyPressed(int e) {
		if (e == KeyEvent.VK_DOWN) {

			if (current < menu.length - 1) {
				current++;
			} else
				current = 0;
		}

		if (e == KeyEvent.VK_LEFT || e == KeyEvent.VK_RIGHT) {
			if (choose == OPTION) {
				control.onOffMusic();
			}
		}

		if (e == KeyEvent.VK_UP) {
			
			if (current > 0)
				current--;
			else
				current = (byte) (menu.length - 1);
		}
		
		if (e == KeyEvent.VK_ESCAPE) {
			choose = 0;
		}

		if (e == KeyEvent.VK_ENTER) {

			switch (current) {
			case 0:
				control.setCurrentLevel(new Level11(control), "LEVEL 1-1");
				control.isLoad = true;
				control.inGame = true;
				LoadLevel.timeSet = 2;

				break;

			case 1:
			case 2:
			case 3:
			case 4:
				choose = current;

				break;
			case 5:
				System.exit(0);
				break;

			}

		}
	}

	@Override
	public void keyReleased(int e) {

	}

}
