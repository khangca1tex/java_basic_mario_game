package model.level;

import java.awt.Graphics2D;

import controller.ControllerGame;
import controller.ControllerLevel;
import model.other.HUD;
import model.other.Observable;
import model.other.Picture;
import model.other.PlayMusic;

public class Level12 extends Level {

	public Level12(ControllerLevel control) {

		super(control);

		map = new ControllerGame("/Map/Level1.map", "/Map/Level1Item.txt", control);
		setMap();
		bg = new Picture("/Background/m.png");

		map.setLocationOfMario(20, 200);

		setEnemies();

		hud = new HUD(map, control);
		hud.scoreSum = control.score;

		music = new PlayMusic(control);
		ob = new Observable(map);
		configObserve();
	}

	@Override
	public void setMap() {

		map.setInfo(control.lives, control.coins);
		setCheckPoint();
		map.setXCheckPoint(CheckPointx);
		map.setXWin(xWinGame);

	}

	@Override
	public void update() {

		map.update();

		hud.update();

		ob.notifier();

	}

	public void setEnemies() {
		map.addGoomba(200, 200);
		map.addKoopa(1800, 400);
	}

	@Override
	public void render(Graphics2D g) {

		bg.render(g);
		map.render(g);

		hud.render(g);

	}

	@Override
	public void keyPressed(int e) {
		map.keyPressofPlayer(e);
	}

	@Override
	public void keyReleased(int e) {
		map.keyRealsedofPlayer(e);
	}

	@Override
	public void restart() {
		map.loadmap("/Map/Level1.map");
		map.loadQuestionBlock("/Map/Level1Item.txt");
		if (!map.isCheckPoint()) {
			map.setLocationOfMario(20, 300);
		} else
			map.setLocationOfMario(CheckPointx, 300);

		map.listExe.add("continue");

		setEnemies();

	}

	@Override
	public void winLevel() {

		control.setCurrentLevel(null, "LEVEL 1-2");

	}

	@Override
	public void setCheckPoint() {
		CheckPointx = 3000;
		xWinGame = 7900;

	}

}
