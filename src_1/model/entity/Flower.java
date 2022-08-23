package model.entity;

import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

public class Flower extends Item {

	private int preY;

	public Flower(ControllerGame map, Player mario) {
		super(map, mario);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addAnimation() {
		BufferedImage[] images = { image[3] };
		ani = new Animation(images, -1);

	}

	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		preY = y;
	}

	public void update() {
		dy = -6;
		if (this.y <= preY - ControllerGame.tileSize) {
			dy = 0;
		} else
			this.y += dy;

		if (this.intersecs(mario)) {
			remove=true;
			map.listExe.add("mushroomeat");
			mario.setHavePower();
		}
		super.update();
	}
}
