import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	private int width, height;
	private int x,y;
	private boolean horizontal;
	private Rectangle hitbox;
	
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
		g.setColor(new Color(255,255,255));
		g.fillRect(x,y,width,height);
		//g.setColor(new Color(200,200,200));
		g.fillRect(x,y+height-10,width,10);
		//g.setColor(new Color(180,180,180));
		g.fillRect(x+width-7,y,7,height);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	public boolean getHorizontal() {
		return horizontal;
	}
}
