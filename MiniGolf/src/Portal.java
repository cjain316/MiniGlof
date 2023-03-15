import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Portal {
	private int x,y;
	private Rectangle hitbox;
	public int x2,y2;
	private int width, height;
	private Color color;
	
	public Portal(int x, int y, int width, int height, int x2,int y2) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.x2 = x2;
		this.y2 = y2;
		hitbox = new Rectangle(x,y,width,height);
	}
	
	public void paint(Graphics g) {
		g.setColor(new Color(66, 135, 245));
		g.fillRect(x,y,width,height);
		hitbox.setLocation(x,y);
		g.setColor(new Color(252, 204, 58));
		g.fillRect(x2-(width/2),y2-(height/2),width,height);
	}
	
	public Rectangle getHitbox() {return hitbox;}
}
