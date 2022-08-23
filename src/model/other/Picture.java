package model.other;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.View;

public class Picture {
	private BufferedImage image;
	private int xstart, ystart, width, height;

	public Picture(int xstart, int ystart, int width, int height, String path) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
			this.xstart = xstart;
			this.ystart = ystart;
			this.width = width;
			this.height = height;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Picture(String path) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
			
			width= View.WIDTH;
			height =View.HEIGHT;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(image, xstart, ystart, width, height, null);
	}

	public int getXstart() {
		return xstart;
	}

	public void setXstart(int xstart) {
		this.xstart = xstart;
	}

	public int getYstart() {
		return ystart;
	}

	public void setYstart(int ystart) {
		this.ystart = ystart;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
