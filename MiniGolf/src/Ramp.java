import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ramp {
	private int x,y;
	private int length,height;
	private int direction;
	private Rectangle hitbox;
	
	public Ramp(int x, int y, int l, int h, int d) {
		length = l;
		height = h;
		direction = d;
		this.x = x;
		this.y = y;
		hitbox = new Rectangle(x,y,l,h);
	}
	
	public void paint(Graphics g) {
		g.setColor(new Color(51, 143, 76));
		g.fillRect(x,y,length,height);
		g.setColor(new Color(57, 168, 87));
		if (direction == 0) {
			//g.fillPolygon({},{},3);
		}
	}
	
	public Rectangle getHitbox() {return hitbox;}
	public int getDirection() {return direction;}
}
