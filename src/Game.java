

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int width = 640; // This constant
	public static final int height = 470; // Constant
	public final String title = "Tank Game";
	
	Font font = new Font("sans-serif",Font.BOLD,16);
	Font font1 = new Font("Courier",Font.BOLD,14);

	private boolean running = false;
	private Thread thread;// initialize and

	private BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private boolean is_shooting = false;
	public boolean isOver = false;
	
	private int bomb_count = 5;
	private int bomb_destroyed = 0;
	
	public static int score;

	private Tank t;
	private Controller c;
	
	public LinkedList<Bullet> buList;
	public LinkedList<Bomb> bomList;
	
	public void init() {
		
		requestFocus();
		score=0;
		BufferedImageLoader loader = new BufferedImageLoader();

		try {
			spriteSheet = loader.loadImage("sprite_sheet.png");
			background = loader.loadImage("background.png");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addKeyListener(this);
		
		t = new Tank(280, 440, this);
		c = new Controller(this);
		
		buList = c.getBulletList();
		bomList = c.getBombList();
		
		c.addBomb(bomb_count);

	}

	private synchronized void start() {
		// if it is already running do not run again just get out of the loop
		// return will make it get out of the loop
		if (running)
			return;
		running = true;
		// if it is running create a thread and start it
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		// if it is not running join and kill them
		while (!running)
			return;

		running = false;
		try {
			thread.join();// joins all thread wait to die
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}

	@Override
	public void run() {
		init();

		while (running) {
			update();

			paint();

		}
		stop(); // when finish the loop stop it

	}

	private void update() {
		if(!isOver){
		
		t.update();
		c.update();
		
		//if all the bombs are destroyed it will create one extra then it was created the first time
		if(bomb_destroyed >= bomb_count){
			bomb_count += 1;
			bomb_destroyed = 0;
			c.addBomb(bomb_count);
		}
		if(score < 0){
			isOver = true;
			}
		}
	}

	private void paint() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		// this draws out our buffer
		Graphics g = bs.getDrawGraphics();
		// Drawing all the images here 
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(background, 0, 0, null);
		t.paint(g);
		c.paint(g);
		// to draw the negative score
		g.setColor(new Color(13,222,13));
		g.fillRect(10, 5, 200, 20);
		g.setColor(new Color(255,255,255));
		g.setFont(font1);
		g.drawString("-ve Score Ends the Game", 13,20);
		// draws the score block
		g.setColor(new Color(13,222,13));
		g.fillRect(10, 35, 100, 24);
		g.setColor(new Color(255,255,255));
		g.setFont(font);
		g.drawString("Score: " + score, 16, 52);
		// condition to end the game, and drop the stop
		if(isOver){
			g.setColor(Color.RED);
			g.fillRect(240, 230, 105, 30);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Game over", 250, 250);
			c.removeBomb(bomList);
		}
		// get rid of everything
		g.dispose();
		bs.show();

	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT)       {    t.setVelX(5);
		} else if (key == KeyEvent.VK_LEFT) {    t.setVelX(-5);
		} else if (key == KeyEvent.VK_DOWN) {	 t.setVelY(5);
		} else if (key == KeyEvent.VK_UP)   {    t.setVelY(-5);
			}
		// this make it to release the space bar to shoot
		if(key == KeyEvent.VK_SPACE && !is_shooting){
			is_shooting = true;
			// bullet go off where the tank is
			c.addBullet(new Bullet(t.getX(), t.getY(),  this));
		}
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT)       {	t.setVelX(0);
		} else if (key == KeyEvent.VK_LEFT) {	t.setVelX(0);
		} else if (key == KeyEvent.VK_DOWN) {	t.setVelY(0);
		} else if (key == KeyEvent.VK_UP)   {	t.setVelY(0);
		} else if(key == KeyEvent.VK_SPACE) {	is_shooting = false;}
	}

	public static void main(String args[]) {

		Game game = new Game();
		// class to encapsulate width and height
		game.setPreferredSize(new Dimension(width, height));
		game.setMaximumSize(new Dimension(width, height));
		game.setMinimumSize(new Dimension(width, height));

		JFrame frame = new JFrame(game.title);
		frame.add(game);
		frame.pack(); // it will resize the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allows x button
																// to wrok
		frame.setResizable(false); // can not resize
		frame.setLocationRelativeTo(null); // sets the function to null
		frame.setVisible(true);

		game.start(); // while running is true start the game
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	
	public int getBomb_count() {
		return bomb_count;
	}

	public void setBomb_count(int bomb_count) {
		this.bomb_count = bomb_count;
	}

	public int getBomb_destroyed() {
		return bomb_destroyed;
	}

	public void setBomb_destroyed(int bomb_destroyed) {
		this.bomb_destroyed = bomb_destroyed;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public Tank getTank() {
		return t;
	}

	public void setTank(Tank t) {
		this.t = t;
	}

}