package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import controller.ControllerLevel;

public class View extends JPanel implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	// GameStateManager
	private ControllerLevel controlLevel;

	// Thread and draw image
	private Thread thread;
	private BufferedImage backbuffedimage;
	private Graphics2D backbuffered;
	private boolean isRunning;

	// FPS

	public static final byte FPS = 30;
	public int targettime = 1000 / FPS ;

	public View() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);

		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		// addKey
		addKeyListener(this);
	}

	public void main() {
		isRunning = true;
		backbuffedimage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		backbuffered = (Graphics2D) backbuffedimage.getGraphics();

		// game state manager
		controlLevel = new ControllerLevel();
	}

	@Override
	public void run() {
		// first
		main();

		// time run fps
		long timestart;
		long timework;
		long timewait;

		while (isRunning) {
			timestart = System.nanoTime();

			update();
			render();

			timework = (System.nanoTime() - timestart) /1000000;
			timewait = targettime - timework;
			if (timewait < 0)
				timewait = targettime;

			try {
				Thread.sleep(timewait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void update() {
		controlLevel.update();
	}

	private void render() {
		controlLevel.render(backbuffered);

		// dispose graphic
		Graphics g2 = getGraphics();
		g2.drawImage(backbuffedimage, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controlLevel.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		controlLevel.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
