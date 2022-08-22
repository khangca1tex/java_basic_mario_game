package controller;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import model.block.BrickBlock;
import model.block.DefaultBlock;
import model.block.GroundBlock;
import model.block.ModelBlock;
import model.block.PipeBlock;
import model.block.PipeTop;
import model.block.QuestionBlock;
import model.entity.Coin;
import model.entity.Enemy;
import model.entity.Flower;
import model.entity.Goompa;
import model.entity.Item;
import model.entity.Koopa;
import model.entity.Mushroom;
import model.entity.Player;
import view.View;

public class ControllerGame {
	// info of map
	private int numRow;
	private int numCol;
	private int numColtoDraw;
	private int width;
	private int height;
	public String path;
	public String pathQue;

	// camera of map
	private int x;
	private int y;
	private int colStart;
	private int xCheckPoint;
	private int xWin;
	private boolean checkPoint;
	private boolean win;

	// position
	private int xmax;
	private int xmin;
	private int ymax;
	private int ymin;

	// tile
	private ModelBlock[][] blocks;
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Enemy> listEnemy = new ArrayList<>();

	// Player
	private Player player;

	// time
	private int tickTime;
	private int timeLeft;
	private int timeLoad = 5;
	private ControllerLevel control;

	public Queue<String> listExe = new LinkedList<>();

	public static final int tileSize = 40;

	public ControllerGame(String path, String pathQue, ControllerLevel control) {

		loadmap(path);
		loadQuestionBlock(pathQue);
		numColtoDraw = View.WIDTH / tileSize + 2;
		this.path = path;
		this.pathQue = pathQue;

		win = false;
		checkPoint = false;
		player = new Player(this);
		this.control = control;
		timeLeft = timeLoad;
	}

	public void loadQuestionBlock(String path) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));

			String line = br.readLine();
			while (line != null) {
				String[] token = line.split(",");
				int[] tokens = new int[3];

				for (int i = 0; i < 3; i++) {
					tokens[i] = Integer.parseInt(token[i]);
				}

				blocks[tokens[0]][tokens[1]].setSpecialType(tokens[2]);

				line = br.readLine();

			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadmap(String path) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));

			numCol = Integer.parseInt(br.readLine());
			numRow = Integer.parseInt(br.readLine());

			width = numCol * tileSize;
			height = numRow * tileSize;

			xmin = View.WIDTH - width;
			xmax = 0;
			ymin = View.HEIGHT - height;
			ymax = 0;

			blocks = new ModelBlock[numRow][numCol];

			for (int row = 0; row < numRow; row++) {
				String line = br.readLine();
				String[] token = line.split("");
				for (int col = 0; col < numCol; col++) {

					int type = Integer.parseInt(token[col]);

					switch (type) {
					case 1:
						blocks[row][col] = new GroundBlock(this, type, row, col);
						break;
					case 2:
						blocks[row][col] = new BrickBlock(this, type, row, col);
						break;
					case 3:
						blocks[row][col] = new DefaultBlock(this, type, row, col);
						break;
					case 4:
						blocks[row][col] = new QuestionBlock(this, type, row, col);
						break;
					case 5:
					case 6:
						blocks[row][col] = new PipeTop(this, type, row, col);
						break;
					case 7:
					case 8:
						blocks[row][col] = new PipeBlock(this, type, row, col);
						break;
					default:
						blocks[row][col] = null;
						break;

					}
				}

			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// neu nho la van con thieu phan row start ( sau nay them vao)
	public void updateCamera(int x, int y) {

		this.x = x;
		this.y = y;

		fixMap();

		// System.out.println(this.x);
		colStart = -this.x / tileSize;

	}

	private void fixMap() {
		if (x < xmin) {
			x = xmin;
		}
		if (y < ymin) {
			y = ymin;
		}
		if (x > xmax) {
			x = xmax;
		}
		if (y > ymax) {
			y = ymax;
		}
	}

	public void update() {

		// player
		player.update();

		updateCamera(-player.getX() + View.WIDTH / 2, -player.getY() + View.HEIGHT / 2);

		// map
		for (int row = 0; row < numRow; row++) {

			if (row >= numRow)
				break;

			for (int col = colStart; col < colStart + numColtoDraw; col++) {

				if (col >= numCol)
					break;

				if (blocks[row][col] == null)
					continue;

				// after render brick, then remove it
				if (blocks[row][col].isBreak() == true && blocks[row][col].getType() != 4) {
					blocks[row][col] = null;
				} else
					blocks[row][col].update();

			}
		}

		// update item
		removeItem();

		// update enmy
		updateEnemies();

		// update checkpoint
		checkCheckPoint();

		// checkWin
		checkWinGame();

	}

	private void checkCheckPoint() {
		if (player.getX() > xCheckPoint) {
			checkPoint = true;
		}

		if (player.isDeath()) {

			tickTime++;
			timeLeft = timeLoad - ((int) (tickTime / View.FPS));

			if (timeLeft < 1) {

				listEnemy.clear();
				items.clear();
				tickTime = 0;
				player.life -= 1;
				timeLeft = timeLoad;

				if (player.life > 0) {
					player.restart();
					control.resetLevel();
				} else {
					control.lost = true;
					control.gameOver();
					listExe.add("gameover");
					
				}
			}
		}

	}

	private void checkWinGame() {
		if ((player.getX() > xWin) && !win) {
			win = true;
			listExe.add("win");

		}

		if (win) {
			tickTime++;
			timeLeft = timeLoad - ((int) (tickTime / View.FPS));
			System.out.println(timeLeft);

			if (timeLeft < 1) {
				win = false;
				listEnemy.clear();
				items.clear();
				tickTime = 0;
				timeLeft = timeLoad;
				control.winLevel();

			}
		}
	}

	public void render(Graphics2D g) {

		// draw Player
		player.render(g);

		// drawMap
		for (int row = 0; row < numRow; row++) {

			if (row >= numRow)
				break;

			for (int col = colStart; col < colStart + numColtoDraw; col++) {

				if (col >= numCol)
					break;

				if (blocks[row][col] == null)
					continue;

				g.drawImage(blocks[row][col].getImage(), blocks[row][col].getX() + x, blocks[row][col].getY() + y,
						tileSize, tileSize, null);

			}
		}
		// drawItem
		for (int i = 0; i < items.size(); i++) {

			items.get(i).render(g);
		}

		// draw Ennemy
		for (int i = 0; i < listEnemy.size(); i++) {
			listEnemy.get(i).render(g);
		}

	}

	private void updateEnemies() {
		for (int i = 0; i < listEnemy.size(); i++) {
			if (listEnemy.get(i).getIsDie()) {
				listEnemy.remove(i);
			} else
				listEnemy.get(i).update();
		}

	}

	private void removeItem() {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isRemove())
				items.remove(i);
			else
				items.get(i).update();
		}

	}

	public void touchBlock(int row, int col, boolean isLarge) {
		ModelBlock b = blocks[row][col];
		if (b != null) {
			if (!b.isBreak()) {
				if (!b.rise) {
					Item i = null;
					switch (b.getSpeicalType()) {
					case 1:
						i = new Coin(this, player);
						i.setLocation(b.getX(), b.getY() - ControllerGame.tileSize);
						items.add(i);
						player.increaseCoin();
						listExe.add("coin");// add music

						break;

					case 10:
						listExe.add("mushroomappear");
						if (!isLarge) {
							i = new Mushroom(this, player);
							i.setLocation(b.getX(), b.getY());
							items.add(i);
						} else {
							i = new Flower(this, player);
							i.setLocation(b.getX(), b.getY());
							items.add(i);
						}
						break;
					}
				}
				b.isTouch(isLarge);

			}
		} else
			return;

	}

	public void keyPressofPlayer(int e) {
		if (e == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		if (e == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		if (e == KeyEvent.VK_SPACE) {
			player.setJump(true);
		}
		if (e == KeyEvent.VK_CONTROL) {
			player.setRun(true);
		}

		if (e == KeyEvent.VK_F) {
			player.setFire();
		}

	}

	public void keyRealsedofPlayer(int e) {
		if (e == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if (e == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if (e == KeyEvent.VK_SPACE) {
			player.setJump(false);
		}
		if (e == KeyEvent.VK_CONTROL) {
			player.setRun(false);
		}

	}

	public void setLocationOfMario(int x, int y) {
		this.player.setLocation(x, y);
	}

	public void addGoomba(int x, int y) {
		Enemy e = new Goompa(this, player);
		e.setLocation(x, y);
		listEnemy.add(e);
	}

	public void addKoopa(int x, int y) {
		Enemy e = new Koopa(this, player);
		e.setLocation(x, y);
		listEnemy.add(e);
	}

	public int getNumRow() {
		return numRow;
	}

	public int getNumCol() {
		return numCol;
	}

	public ModelBlock getblock(int row, int col) {
		return blocks[row][col];
	}

	public int getX() {
		return x;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public ArrayList<Enemy> getEnemies() {
		return listEnemy;
	}

	public void addMusic(String exe) {
		listExe.add(exe);
	}

	public int getTimeLeft() {
		return player.getTimeLeft();
	}

	public int getCoins() {
		return player.getCoins();
	}

	public int getLife() {
		return player.getLives();
	}

	public void setXCheckPoint(int x) {
		this.xCheckPoint = x;
	}

	public boolean isCheckPoint() {
		return checkPoint;
	}

	public void setXWin(int x) {
		this.xWin = x;
	}

	public boolean isWinGame() {
		return this.win;
	}

	public void setInfo(int livesLeft, int coins) {
		player.life = livesLeft;
		player.coins = coins;

	}
}
