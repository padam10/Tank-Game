import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
// This class creates bomb when they are destroyed
// It also manages their speed and where they appear


public class Bomb {

	private double x;
	private double y;

	Random r = new Random();

	private BufferedImage bomb;
	
	private Game game;
	private Controller c;// need to import this to get access the remove method
	public Tank tank;

	public Bomb(double x, double y, Controller c, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.c = c;

		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		bomb = ss.grabImage(3, 1, 30, 30);
	}

	public void update() {
		y += (r.nextInt(5)+ 1); // 5 is for 5 diff speed and it will 1 to make it faster
// this will make the bomb to drop from different place everytime
		if (y > Game.height) {// 
			y = 0;// When the bomb get to the bottom they will start from top again
			x = r.nextInt(Game.width);// This will make sure they start from diff position
		}
		if (cDetect.Collison(this, game.buList)) {
			c.removeBomb(this); // it will remove the bomb from the linked list
			// it will add more bomb after they were destroyed
			game.setBomb_destroyed(game.getBomb_destroyed() + 1);
			Game.score += 20;
		}
		
		if (cDetect.Collison(this, game.getTank())) {
			c.removeBomb(this);
			// it will add more bomb when they are destroyed
			game.setBomb_destroyed(game.getBomb_destroyed() + 1);
			Game.score -= 50;
			
		}

	}

	public void paint(Graphics g) {
		g.drawImage(bomb, (int) x, (int) y, null);
	}

	public double getY() {
		return y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
	
//	public BufferedImage grabImage(int col, int row, int width, int height){
//	
//	BufferedImage img = image.getSubimage((col*32)-32,(row * 32)-32, width, height);
//	return img;
//}
	

}