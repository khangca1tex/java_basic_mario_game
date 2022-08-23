package model.entity;

import java.awt.Rectangle;

import controller.ControllerGame;
import view.View;

public abstract class Enemy extends ModelGame {

	private boolean start;
	protected Player mario;
	protected boolean isDie, knockDown;
	protected boolean topCollision, collision;
	private int score;

	// vi lay hinh nguoc nen cai moveright phai bi doi

	public Enemy(ControllerGame map, Player mario) {
		super(map);
		this.mario = mario;
		start = false;
		width = 40;
		height = 40;
		moveRight = false;
		speed = 2;
		maxSpeed = 6;
		score = 200;
		addAnimation();

	}

	public abstract void addAnimation();

	private void nextPosition() {
		if (isLeft()) {
			dx -= speed;
			if (dx < -maxSpeed)
				dx = -maxSpeed;

		} else if (isRight()) {
			dx += speed;
			if (dx > maxSpeed)
				dx = maxSpeed;

		}

		if (falling) {
			dy += gravity;

		}
	}

	protected void checkCollisionWithPlayer() {
		Rectangle eRec = this.getRectangle();
		Rectangle pRec = mario.getRectangle();
		if (eRec.intersects(pRec) &&!mario.isShocking) {
			collision = true;

			if ((pRec.getY() + pRec.getHeight() >= eRec.getY()) && mario.getDy() > 0) {
				topCollision = true;
			} else {
				topCollision = false;
			}
		} else {
			
				topCollision = false;
				collision = false;
		}

	}

	protected void intersecWithFire(Fire fire) {
		if (fire.intersecs(this)) {
			isDie = true;
		}
	}

	protected void setMoveLeft() {

		setLeft(true);
		setRight(false);
		moveRight = false;

	}

	protected void setMoveRight() {
		setLeft(false);
		setRight(true);
		moveRight = true;

	}

	@Override
	public void update() {

		// cho con thu lay hinh ng nen cai moveright bi nguoc
		if (!start) {
			dx = 0;

			if (mario.getX() + View.WIDTH > this.getX()) {
				start = true;
				setMoveLeft();
			}
		} else {

			nextPosition();
			checkMapCollision();
			checkCollisionWithPlayer();

			if (this.isLeft() && dx == 0) {
				setMoveRight();
			} else if (this.isRight() && dx == 0) {
				setMoveLeft();
			}

		}
	}

	public void setDie(boolean die) {
		map.listExe.add("enemydie");
		this.isDie = die;
	}

	public int getScore() {
		return this.score;
	}

	public boolean getIsDie() {

		return this.isDie;
	}
}
