package model.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import controller.ControllerGame;
import model.other.Animation;

public abstract class ModelGame {

	protected int x;
	protected int y;
	protected double dx;
	protected double dy;

	public int width;
	public int height;
	protected boolean falling;
	protected boolean left;
	protected boolean right;

	// speed
	public double speed;
	public double maxSpeed;
	public double gravity = 8;
	// animation
	protected boolean moveRight;
	public Animation ani;

	// collision
	protected boolean upleft;
	protected boolean upright;
	protected boolean downleft;
	protected boolean downright;
	protected boolean checksideup;
	protected boolean checksidedown;
	protected int currCol;
	protected int currRow;
	// map
	protected int xMap;
	protected ControllerGame map;

	public ModelGame(ControllerGame map) {
		this.map = map;
	}

	public void checkMapCollision() {

		getCurrentRowCol();

		double xcheck = x + dx;
		double ycheck = y + dy;

		calculateCorners(xcheck, y);
		if (checksideup || checksidedown) {

			dx = 0;
			if (dx < 0) {

				x = currCol * ControllerGame.tileSize;
			}
			if (dx > 0) {
				x = (currCol + 1) * ControllerGame.tileSize;

			}
		} else {
			x += dx;
		}

		// calculateCorners(xcheck, y);
		// if (dx < 0) {
		// if (upleft || downleft) {
		// dx = 0;
		// x = currCol * Map.tileSize;
		// } else {
		// x += dx;
		// }
		// }
		// if (dx > 0) {
		// if (upright || downright) {
		// dx = 0;
		// x = (currCol + 1) * Map.tileSize;
		// } else {
		// x += dx;
		// }
		// }

		calculateCorners(x, ycheck);
		if (dy < 0) {
			if (upleft || upright) {
				dy = 0;
				if (y % ControllerGame.tileSize != 0) {
					y = (currRow) * ControllerGame.tileSize;
				} else {
					y = (currRow - 1) * ControllerGame.tileSize;
				}
			} else {
				y += dy;
			}
		}
		if (dy > 0) {
			if (downleft || downright) {

				dy = 0;
				falling = false;
				if (y % ControllerGame.tileSize != 0) {
					y = (currRow + 1) * ControllerGame.tileSize;
				} else {
					y = (currRow) * ControllerGame.tileSize;
				}
			} else {
				y += dy;
			}
		}

		if (!falling) {
			calculateCorners(x, ycheck + 1);
			if (!downleft && !downright) {
				falling = true;
			}
		}

	}

	private void calculateCorners(double x, double y) {
		int tileTop = (int) (y) / ControllerGame.tileSize;
		int tileBot = (int) (y + height - 1) / ControllerGame.tileSize;
		int tileLeft = (int) (x) / ControllerGame.tileSize;
		int tileRight = (int) (x + width - 1) / ControllerGame.tileSize;

		if (tileTop < 0 || tileBot >= map.getNumRow() || tileLeft < 0 || tileRight >= map.getNumCol()) {
			downleft = downright = checksideup = checksidedown = upleft = upright = false;
			return;
		}

		downleft = map.getblock(tileBot, tileLeft) != null;
		downright = map.getblock(tileBot, tileRight) != null;

		upright = map.getblock(tileTop, tileRight) != null;
		upleft = map.getblock(tileTop, tileLeft) != null;

		if (moveRight) {
			checksideup = map.getblock(tileTop, tileRight) != null;
			checksidedown = map.getblock(tileBot, tileRight) != null;
		} else {
			checksideup = map.getblock(tileTop, tileLeft) != null;
			checksidedown = map.getblock(tileBot, tileLeft) != null;
		}
	}

	public boolean intersecs(ModelGame o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();

		return r1.intersects(r2);
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

	public void render(Graphics2D g) {

		ani.update();
		setMapLoc();
		if (moveRight) {
			g.drawImage(ani.getImage(), x + xMap, y, width, height, null);
		} else {
			g.drawImage(ani.getImage(), x + width + xMap, y, -width, height, null);
		}

	}

	public boolean isFalling() {
		return falling;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	protected void setMapLoc() {
		this.xMap = map.getX();
	}

	public abstract void update();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	protected void getCurrentRowCol() {
		currCol = x / ControllerGame.tileSize;
		currRow = y / ControllerGame.tileSize;
	}

	public boolean isMoveRight() {
		return moveRight;
	}
	
	

}
