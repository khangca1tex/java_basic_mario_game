package model.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.ControllerGame;
import model.other.Animation;
import view.View;

public class Player extends ModelGame {

	// state of mario
	private boolean isLarge;
	private boolean isDead;
	private boolean isHavePower;
	public boolean isShocking;

	// action of mario
	private boolean jump;
	private boolean run;

	// speed
	private double jumpHeight = 60;
	private double maxgravity = 20;
	private double stopjumpspeed = 20;
	private double runSpeed = 16;
	private double stopSpeed = 0.8;
	private int countTimeShocking;

	// info
	public int coins;
	public int life;
	private int timeLeft;
	private long countFrame;
	public int timeSet = 200;
	private ArrayList<Fire> listFire;

	// animation
	private final int STAND = 0;
	private final int MOVE = 1;
	private final int JUMP = 2;
	private final int DEAD = 3;
	private int currentAction;

	// run
	private ArrayList<BufferedImage> runsmall;
	private ArrayList<BufferedImage> runlarge;
	private ArrayList<BufferedImage> runlargeflower;
	// stand

	private ArrayList<BufferedImage> standsmall;
	private ArrayList<BufferedImage> standlarge;
	private ArrayList<BufferedImage> standlargeflower;
	// jump
	private ArrayList<BufferedImage> jumpsmall;
	private ArrayList<BufferedImage> jumplarge;
	private ArrayList<BufferedImage> jumplargeflower;
	// jump
	private ArrayList<BufferedImage> die;

	public Player(ControllerGame map) {

		super(map);
		addAnimation();
		isLarge = false;
		moveRight = true;
		isHavePower = false;
		listFire = new ArrayList<>();

		isDead = false;
		speed = 5;
		maxSpeed = 8;
		width = 40;
		height = 40;
		life = 3;
		coins = 0;
		timeLeft = timeSet;
		currentAction = STAND;

	}

	private void addAnimation() {

		cutSprite();
		ani = new Animation(standsmall.toArray(new BufferedImage[standsmall.size()]), 1);

	}

	private void nextPosition() {
		if (left) {
			dx -= speed;
			moveRight = false;
			double max = 0;
			if (!run) {
				max = maxSpeed;
			} else
				max = runSpeed;
			if (dx < max)
				dx = -max;
		} else if (right) {
			dx += speed;
			moveRight = true;
			double max = 0;
			if (!run) {
				max = maxSpeed;
			} else
				max = runSpeed;
			if (dx > max)
				dx = max;
		} else if (dx > 0) {
			dx -= stopSpeed;
			if (dx < 0) {
				dx = 0;
			}
		} else if (dx < 0) {
			dx += stopSpeed;
			if (dx > 0) {
				dx = 0;
			}
		}

		if (jump && !falling) {
			dy = -jumpHeight;
			falling = true;
			map.listExe.add("jump");

		}
		if (falling) {
			dy += gravity;
			if (dy < 0 && !jump)
				dy += stopjumpspeed;
			if (dy > maxgravity)
				dy = maxgravity;
		}

	}

	public void checkAnimationofBrick() {
		if (upleft || upright) {
			getCurrentRowCol();
			int row = currRow - 1;
			int col = currCol;
			if (map.getblock(row, col) == null) {
				col = currCol + 1;
			}
			map.touchBlock(row, col, isLarge);
		}
	}

	public void turnIntoLarge() {
		width = 40;
		height = 80;
		isLarge = true;
		y = y - ControllerGame.tileSize;
		setAnimation(STAND);
	}

	@Override
	public void update() {
		if (!isDead && !map.isWinGame()) {
			countFrame++;
			timeLeft = timeSet - ((int) (countFrame / View.FPS));

			if (isShocking) {
				countTimeShocking++;

				if (countTimeShocking > 150) {
					isShocking = false;
					countTimeShocking = 0;
				}
			}
			
			if(x<0){
				x=0;
			}

			if (y > View.HEIGHT ||timeLeft <=0) {
				isDead = true;
				map.listExe.add("death");
			}

			nextPosition();
			checkMapCollision();
			checkAnimationofBrick();
			changeAnimation();
			updateFire();
			removeFire();
		} else if (isDead)
			playAniOfDeath();

	}

	private void playAniOfDeath() {
		dx = 0;
		dy += 5;
		y += dy;
	}

	private void updateFire() {
		for (int i = 0; i < listFire.size(); i++) {
			listFire.get(i).update();
		}
	}

	private void changeAnimation() {
		if (dy != 0) {
			if (currentAction != JUMP)
				setAnimation(JUMP);
		} else if (left || right) {
			if (currentAction != MOVE)
				setAnimation(MOVE);

		} else if (isDead) {
			if (currentAction != DEAD) {
				setAnimation(DEAD);
			}
		} else {
			if (currentAction != STAND)
				setAnimation(STAND);
		}
	}

	private void setAnimation(int i) {
		currentAction = i;

		ani.setDelay(2);
		switch (currentAction) {
		case STAND:
			if (!isLarge) {
				ani.changeAnimation(standsmall.toArray(new BufferedImage[standsmall.size()]));
			} else {
				if (isHavePower) {
					ani.changeAnimation(standlargeflower.toArray(new BufferedImage[standlargeflower.size()]));
				} else {
					ani.changeAnimation(standlarge.toArray(new BufferedImage[standlarge.size()]));
				}
			}

			break;
		case MOVE:

			if (!isLarge) {
				ani.changeAnimation(runsmall.toArray(new BufferedImage[runsmall.size()]));
			} else {
				if (isHavePower) {
					ani.changeAnimation(runlargeflower.toArray(new BufferedImage[runlargeflower.size()]));
				} else
					ani.changeAnimation(runlarge.toArray(new BufferedImage[runlarge.size()]));
			}
			break;
		case JUMP:
			if (!isLarge) {
				ani.changeAnimation(jumpsmall.toArray(new BufferedImage[jumpsmall.size()]));
			} else {
				if (isHavePower) {
					ani.changeAnimation(jumplargeflower.toArray(new BufferedImage[jumplargeflower.size()]));
				} else
					ani.changeAnimation(jumplarge.toArray(new BufferedImage[jumplarge.size()]));
			}
			break;
		case DEAD:
			ani.changeAnimation(die.toArray(new BufferedImage[die.size()]));
			break;

		default:
			break;
		}
	}

	@Override
	public void render(Graphics2D g) {

		// drawFire
		for (int i = 0; i < listFire.size(); i++) {
			listFire.get(i).render(g);
		}

		if (isShocking) {
			float r = (float) Math.random();
			if (r > 0.5)
				return;
		}

		super.render(g);
	}

	public void restart() {
		right = left = false;
		isLarge = false;
		isHavePower = false;
		isDead = false;
		height = 40;
		moveRight = true;
		listFire.clear();
		timeLeft = timeSet = 200;
		countFrame = 0;
		dy=0;

	}

	private void cutSprite() {
		BufferedImage imagesmall = null;
		BufferedImage imagelarge = null;

		runsmall = new ArrayList<>();
		runlarge = new ArrayList<>();
		runlargeflower = new ArrayList<>();
		// stand
		standsmall = new ArrayList<>();
		standlarge = new ArrayList<>();
		standlargeflower = new ArrayList<>();
		// jump
		jumpsmall = new ArrayList<>();
		jumplarge = new ArrayList<>();
		jumplargeflower = new ArrayList<>();
		// die
		die = new ArrayList<>();

		try {
			imagesmall = ImageIO.read(getClass().getResourceAsStream("/Player/smallMario.png"));

			imagelarge = ImageIO.read(getClass().getResourceAsStream("/Player/largeMario.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 9; i++) {
			BufferedImage small = imagesmall.getSubimage(i * 16, 0, 16, 16);
			BufferedImage large = imagelarge.getSubimage(i * 16, 0, 16, 32);
			BufferedImage largeflower = imagelarge.getSubimage(i * 16, 32, 16, 32);

			switch (i) {
			case 0:
				standlarge.add(large);
				standsmall.add(small);
				standlargeflower.add(largeflower);
				break;
			case 1:
			case 2:
			case 3:
				runlarge.add(large);
				runsmall.add(small);
				runlargeflower.add(largeflower);
				break;
			case 5:
				jumplarge.add(large);
				jumpsmall.add(small);
				jumplargeflower.add(largeflower);
				break;
			case 6:
				die.add(small);
				break;
			}
		}

	}

	private void removeFire() {
		for (int i = 0; i < listFire.size(); i++) {
			if (listFire.get(i).getRemove())
				listFire.remove(i);
		}
	}

	public void setPlayerisHitted() {
		if (isLarge) {
			isLarge = false;
			isHavePower = false;
			isShocking = true;
			height = 40;
			y = y + ControllerGame.tileSize;

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			setAnimation(DEAD);
			isDead = true;
			map.listExe.add("death");
		}
	}

	public void setHavePower() {
		if (isLarge) {
			isHavePower = true;
			setAnimation(currentAction);
		}
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setJump(boolean jump) {
		this.jump = jump;

	}

	public int getCoins() {
		return coins;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public int getLives() {
		return this.life;
	}

	public void setFire() {
		if (isHavePower) {
			listFire.add(new Fire(map, this));
			map.listExe.add("shot");
		}
	}

	public void increaseCoin() {
		coins++;
		if (coins == 100) {
			coins = 0;
			life++;
		}
	}

	public boolean isDeath() {
		return isDead;
	}

}
