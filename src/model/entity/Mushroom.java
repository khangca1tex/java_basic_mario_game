package model.entity;

import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

public class Mushroom extends Item {

	private int preY;
	private boolean rise;

	public Mushroom(ControllerGame map, Player mario) {
		super(map, mario);
		speed = 1;
		maxSpeed = 6;
		moveRight = true;
		right = true;
		rise = true;
	}

	@Override
	protected void addAnimation() {
		BufferedImage[] images = { image[0] };
		ani = new Animation(images, -1);

	}

	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		preY = y;
	}

	private void nextPosition() {
		if (left) {
			dx -= speed;
			if (dx < -maxSpeed)
				dx = -maxSpeed;

		} else if (right) {
			dx += speed;
			if (dx > maxSpeed)
				dx = maxSpeed;

		}

		if (falling) {
			dy = gravity;

		}
	}

	public void update() {

		// cho con thu lay hinh ng nen cai moveright bi nguoc
		if (rise) {
			dy = -8;
			if (this.y <= preY - ControllerGame.tileSize) {
				dy = 0;
				rise = false;
			}
			this.x += dx;
			this.y += dy;

		} else {
			nextPosition();
			checkMapCollision();
			if (right && dx == 0) {
				right = false;
				left = true;

			} else if (left && dx == 0) {
				right = true;
				left = false;
			}

			super.update();

			if (this.intersecs(mario)) {
				remove=true;
				map.listExe.add("mushroomeat");
				mario.turnIntoLarge();
			}
		}

	}

}
