import java.awt.Graphics;
import java.util.ArrayList;

public class Level {
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private int startingx,startingy;
	private int holex,holey;
	Ball ball;
	Hole hole;

	public void paint(Graphics g) {
		for (int i = 0; i < walls.size();i++) {
			walls.get(i).paint(g);
		}
		ball.paint(g);
		hole.paint(g);
		for (int i = 0; i < walls.size();i++) {
			if (colliding(ball,walls.get(i))) {if (walls.get(i).getHorizontal()) {ball.setVy(ball.getVy()*-1);}}
			else {ball.setVx(ball.getVx()*-1);}
		}
		ball.paint(g);
		hole.paint(g);
		
		
	}
	
	public void addWall(Wall w) {
		walls.add(w);
	}
	
	public Level(int psx,int psy,int phx,int phy) {
		startingx = psx;
		startingy = psy;
		holex = phx;
		holey = phy;
		
		ball = new Ball(startingx,startingy,30);
		hole = new Hole(phx,phy);
		
	}
	
	public boolean colliding(Ball b, Hole h) {
    	return b.getHitbox().intersects(h.getHitbox());
    }
    public boolean colliding(Ball b, Wall w) {
    	return b.getHitbox().intersects(w.getHitbox());
    }
	
}
