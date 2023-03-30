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
		if (direction == 270) {
			int[] xvals = {(length/2)-30 + x,(length/2)+30 + x,(length/2) + x};
			int[] yvals = {(height/2)-30 + y,(height/2)-30 + y,(height/2)+30 + y};
			g.fillPolygon(xvals,yvals,3);
		}
		if (direction == 0) {
			int[] xvals = {(length/2)-30 + x,(length/2)+30 + x,(length/2)-30 + x};
			int[] yvals = {(height/2)-30 + y,(height/2) + y,(height/2)+30 + y};
			g.fillPolygon(xvals,yvals,3);
		}
		if (direction == 90) {
			int[] xvals = {(length/2)-30 + x,(length/2)+30 + x,(length/2) + x};
			int[] yvals = {(height/2)+30 + y,(height/2)+30 + y,(height/2)-30 + y};
			g.fillPolygon(xvals,yvals,3);
		}
		if (direction == 180) {
			int[] xvals = {(length/2)+30 + x,(length/2)-30 + x,(length/2)+30 + x};
			int[] yvals = {(height/2)-30 + y,(height/2) + y,(height/2)+30 + y};
			g.fillPolygon(xvals,yvals,3);
		}
	}
	
	public Rectangle getHitbox() {return hitbox;}
	public int getDirection() {return direction;}
}
