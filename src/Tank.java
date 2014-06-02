import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;

import java.awt.image.BufferedImage;

public class Tank extends KeyAdapter {

	private double x;
	private double y;
	// this will speed up the tanks movement 
	private double velX = 0;
	private double velY = 0;
	
	private BufferedImage tank;

	public Tank(double x, double y, Game game) {

		this.x = x;
		this.y = y; 
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		tank = ss.grabImage(1, 1, 32, 32);
	}
	
	public void update(){
		// this just adds the velocity to the movement. 
		x = x + velX;
		y = y + velY;
		// this will make sure it won't go out of bounds
		if (x <= 1)
			x = 1;
		if (x >= 607)
			x = 607;
		if (y <=1)
			y = 1;
		if(y >= 437)
			y = 437;
				
	}

	public void paint(Graphics g){
		g.drawImage(tank,(int) x, (int)y, null);
	}
	
	
	public double getX(){
		return  x;
	}
	
	public double getY(){
		return  y;
	}
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	// this will set the speed to fast
	public void setVelX(double velX){
		this.velX = velX;
	}
	
	public void setVelY(double velY){
		this.velY = velY;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
	
}
