package model.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.ControllerGame;
import model.other.Animation;

public class Goompa extends Enemy {

	private BufferedImage[] die;

	public Goompa(ControllerGame map, Player mario) {
		super(map, mario);
	}

	@Override
	public void addAnimation() {
		BufferedImage image[] = new BufferedImage[2];

		BufferedImage im = null;
		try {
			im = ImageIO.read(getClass().getResourceAsStream("/Enemies/gompa.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 2; i++) {
			image[i] = im.getSubimage(16 * i, 0, 16, 16);

		}
		die = new BufferedImage[1];
		die[0] = im.getSubimage(16 * 2, 0, 16, 16);
		ani = new Animation(image, 3);

	}

	private void checkCollision() {

		if (topCollision) {
			setDie(true);
		} else if (collision && !isDie) {
			mario.setPlayerisHitted();
		}

	}

	@Override
	public void update() {
		if (!mario.isDeath()) {
			super.update();

			checkCollision();
		}

	}
}
