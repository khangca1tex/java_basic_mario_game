package model.level;

import java.awt.Font;

import controller.ControllerGame;
import controller.ControllerLevel;
import model.other.HUD;
import model.other.Observable;
import model.other.Picture;
import model.other.PlayMusic;

public abstract class Level implements ILevel {

	protected ControllerLevel control;
	protected Font font;
	public int CheckPointx;
	protected int xWinGame;

	protected HUD hud;
	protected PlayMusic music;
	protected Observable ob;
	
	// Map
	protected ControllerGame map;

	// BackGround
	protected Picture bg;

	public Level(ControllerLevel controllerLevel) {
		this.control = controllerLevel;
	}
	
	public abstract void setMap();

	public abstract void setEnemies();

	public abstract void restart();

	public abstract void winLevel();

	public abstract void setCheckPoint();
	
	public void saveScore() {
		control.coins = hud.coins;
		control.lives = hud.life;
		control.score = hud.scoreSum;
	}
	

	public void configObserve(){
		if(control.isTurnOnMusic){
			ob.attach(hud);
			ob.attach(music);
		}else
			ob.attach(hud);
	}
}
