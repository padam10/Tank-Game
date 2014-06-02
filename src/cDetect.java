

import java.util.LinkedList;


// going to handle if we have the collision
public class cDetect {

	// checks if bomb and bullet collide
	public static boolean Collison(Bomb bomb, LinkedList<Bullet> ea) {
		for (int i = 0; i < ea.size(); i++) {
			// using built in intersect method from Rectangle object
			if (bomb.getBounds().intersects(ea.get(i).getBounds())) {
				return true;
			}
		}

		return false;
	}


//  this checks if the bomb hits the tank
	public static boolean Collison(Bomb bomb, Tank t) {
		if (bomb.getBounds().intersects(t.getBounds())) {
			return true;
		}
		return false;
	}

}
