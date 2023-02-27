import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Hole {
	private int x,y;
	private Rectangle hitbox;
	
	public Hole(int px,int py) {
		x = px;
		y = py;
		
		hitbox = new Rectangle(x+5,y+5,40,40);
		
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval(x,y+3,50,50);
		g.setColor(Color.WHITE);
		g.fillOval(x,y,50,50);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
}
