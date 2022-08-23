package model.level;

import java.awt.Graphics2D;

public interface ILevel {

	public abstract void update();

	public abstract void render(Graphics2D g);

	public abstract void keyPressed(int e);

	public abstract void keyReleased(int e);
}
