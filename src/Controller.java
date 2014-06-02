import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

// This class lists the bullets and bombs in a linked list. 
// It also 

public class Controller {
	// 
	private LinkedList<Bullet> buList = new LinkedList<Bullet>();
	private LinkedList<Bomb> bomList = new LinkedList<Bomb>();

	Random r = new Random();

	Bullet bullet;
	Bomb bomb;

	Game game;

	public Controller(Game game) {
		this.game = game;

	}
// creates bomb
	public void addBomb(int bomb_count) {
		for (int i = 0; i < bomb_count; i++) {
			addBomb(new Bomb(r.nextInt(630), 0, this, game));
		}

	}
// This method gets the bullets from the linked list 
// and makes it to go upward with update method
	public void update() {
		//gets the bullet from linked list
		for (int i = 0; i < buList.size(); i++) {
			bullet = buList.get(i);
			// This will make bullet disappear after it gets out of the screen
			if (bullet.getY() < 0)
				removeBullet(bullet);
			// move the bullet to upward directions
			bullet.update();
		}
		// gets the bomb list and have them go down
		for (int i = 0; i < bomList.size(); i++) {
			bomb = bomList.get(i);
			// makes it go down
			bomb.update();
		}
	}
	// draws the bullets 
	public void paint(Graphics g) {
		for (int i = 0; i < buList.size(); i++) {
			bullet = buList.get(i);
			bullet.paint(g);
		}
		// draws the bombs 
		for (int i = 0; i < bomList.size(); i++) {
			bomb = bomList.get(i);
			bomb.paint(g);
		}

	}

	public void addBullet(Bullet bullet) {
		buList.add(bullet);
	}

	public void removeBullet(Bullet bullet) {
		buList.remove(bullet);
	}

	public void addBomb(Bomb bomb) {
		bomList.add(bomb);
	}

	public void removeBomb(Bomb bomb) {
		bomList.remove(bomb);
	}

	public LinkedList<Bullet> getBulletList() {
		return buList;
	}

	public LinkedList<Bomb> getBombList() {
		return bomList;
	}

	public void removeBomb(LinkedList<Bomb> bomList2) {
		 bomList2.remove(bomList2);
		
	}

}
