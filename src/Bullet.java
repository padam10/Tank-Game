
import java.awt.Graphics;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// This class handles the speed and direction of bomb

public class Bullet {
	private double x;
	private double y;

	BufferedImage image;

	public Bullet(double x, double y, Game game) {
		this.x = x;
		this.y = y;

		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		image = ss.grabImage(2, 1, 32, 32);

	}

	public void update() {
		y -= 20;

	}

	public void paint(Graphics g) {
		g.drawImage(image, (int) x, (int) y, null);
	}
	
	public double getY(){
		return y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
