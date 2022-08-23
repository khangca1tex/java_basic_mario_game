package model.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.ControllerGame;
import model.other.Animation;

public class Koopa extends Enemy {

	private BufferedImage[] move;
	private BufferedImage[] idle;
	private int countToCountinueMove;
	private boolean isRunning;

	public Koopa(ControllerGame map, Player mario) {
		super(map, mario);
		isRunning = true;
	}

	@Override
	public void addAnimation() {

		BufferedImage im = null;
		move = new BufferedImage[2];
		idle = new BufferedImage[1];
		try {
			im = ImageIO.read(getClass().getResourceAsStream("/Enemies/koopa.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 2; i++) {

			move[i] = im.getSubimage(16 * (i + 2), 16, 16, 16);
		}
		idle[0] = im.getSubimage(0, 16, 16, 16);
		ani = new Animation(move, 3);
	}

	private void koopaRun() {
		if (collision && !topCollision) {// when it knock down and
											// it touch with
			// other object
			if (!isRunning && knockDown) {// mario
				isRunning = true;
				speed = 5;
				maxSpeed = 25;
				if (mario.isLeft()) {
					setMoveLeft();
				} else {
					setMoveRight();
				}
			} else
				mario.setPlayerisHitted();
		} else if (knockDown) {
			checkCollisionWithOtherEnemies();
		}

	}

	private void koopaMoveAgain() {
		if (knockDown && dx == 0) {

			countToCountinueMove++;

			if (countToCountinueMove > 1000) {
				ani.changeAnimation(move);
				countToCountinueMove = 0;
				ani.setDelay(3);
				knockDown = false;
				isRunning = true;
				speed = 1;
				maxSpeed = 5;
			}
		}
	}

	private void hitKoopa() {

		if (topCollision && mario.getDy() > 0) {
			mario.setDy(-100);
			map.listExe.add("enemydie");
			if (!knockDown) {

				isRunning = false;
				knockDown = true;
				dx = 0;
				ani.changeAnimation(idle);
			} else {
				dx = 0;
				isRunning = false;

			}
			topCollision = false;
		}
	}

	private void checkCollisionWithOtherEnemies() {
		ArrayList<Enemy> list = map.getEnemies();
		for (Enemy enemy : list) {
			if (!enemy.equals(this)) {
				if (this.intersecs(enemy))
					enemy.setDie(true);
			}
		}
	}

	public void render(Graphics2D g) {

		ani.update();
		setMapLoc();
		if (!moveRight) {
			g.drawImage(ani.getImage(), x + xMap, y, width, height, null);
		} else {
			g.drawImage(ani.getImage(), x + width + xMap, y, -width, height, null);
		}

	}

	@Override
	public void update() {
		if (isRunning)
			super.update();
		koopaRun();
		hitKoopa();
		koopaMoveAgain();

	}

}
