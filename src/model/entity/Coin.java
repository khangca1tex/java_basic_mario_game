package model.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

public class Coin extends Item {

	public Coin(ControllerGame map, Player mario) {
		super(map, mario);
		dy = -1;
	}

	@Override
	protected void addAnimation() {
		BufferedImage[] images = { image[11],image[12],image[13],image[14] };
		ani = new Animation(images, 3);

	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
	}

	public void update() {

		dy -= 1;
		y += dy;

		if (dy <= -5) {
			dy = 0;
			remove = true;
		}
	}

}
