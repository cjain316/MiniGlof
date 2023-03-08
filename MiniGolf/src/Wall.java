import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	private int width, height;
	private int x,y;
	private boolean horizontal;
	private Rectangle hitbox;
	private boolean moving;
	private int movementAmount;
	private int movementSpeed;
	private boolean movinghorizontal;
	private boolean alternate = false;
	private int timer;
	private int counter;
	
	public Wall(int px, int py, int pwidth, int pheight) {
		x = px;
		y = py;
		width = pwidth;
		height = pheight;
		if (width > height) {horizontal = true;}
		else {horizontal = false;}
		hitbox = new Rectangle(x,y,width,height);
	}
	
	public void paint(Graphics g) {
		if (timer > 0) {timer--;}
		if (counter == 10) {counter = 0; alternate = !alternate;}
		if (moving) {
			if (movinghorizontal && timer == 0) {
				counter++;
				timer = movementSpeed;
				if (alternate) {x+=movementAmount/10;}
				else {x-=movementAmount/10;}
			}
			if (!movinghorizontal && timer == 0) {
				counter++;
				timer = movementSpeed;
				if (alternate) {y += movementAmount/10;}
				else {y -= movementAmount/10;}
			}
		}
		hitbox.setLocation(x,y);
		g.setColor(new Color(255,255,255));
		g.fillRect(x,y,width,height);
		//g.setColor(new Color(200,200,200));
		g.fillRect(x,y+height-10,width,10);
		//g.setColor(new Color(180,180,180));
		g.fillRect(x+width-7,y,7,height);
	}
	
	public void setMoving(boolean moving, int amount, int speed, boolean horizontal) {
		this.moving = moving;
		movinghorizontal = horizontal;
		movementAmount = amount;
		movementSpeed = speed;
		if (horizontal) {x-=amount/2;}
		else {y -= amount/2;}
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	public boolean getHorizontal() {
		return horizontal;
	}
}
