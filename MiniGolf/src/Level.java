import java.awt.Graphics;
import java.util.ArrayList;

public class Level {
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private int startingx,startingy;
	private int holex,holey;
	private boolean complete;
	Ball ball;
	Hole hole;
	private int timer = 0;

	public void paint(Graphics g) {
		if (timer > 0) {timer--;}
		for (int i = 0; i < walls.size();i++) {
			walls.get(i).paint(g);
		}
		hole.paint(g);
		for (int i = 0; i < walls.size();i++) {
			if (timer == 0) {
				if (colliding(ball,walls.get(i))) {
					ball.setVelocity(ball.getVelocity()+1);
					if (walls.get(i).getHorizontal()) {
						ball.setAngle(ball.getAngle()*-1);
						timer = 3;
					}
					if (timer == 0) {
						if (!walls.get(i).getHorizontal()) {
							if (Math.cos(ball.getAngle()) > -90) {ball.setAngle(ball.getAngle()-(180-Math.abs(ball.getAngle()*2)));}
							if (Math.cos(ball.getAngle()) < -90) {ball.setAngle(ball.getAngle()+(180-Math.abs(ball.getAngle()*2)));}
							timer = 3;
						}
					}
				}
				
			}
			
		}
		hole.paint(g);
		if (!colliding(ball,hole)) {
			ball.paint(g);
			g.drawLine(ball.getX()+15,ball.getY()+15,15+(int)ball.getX() + (int)(ball.getVx()*200),15+(int)ball.getY() + (int)(ball.getVy()*200));
			g.fillRect(30,30,(int)ball.getVelocity()*10,50);
		} else {
			complete = true;
		}
		
		
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
    
    public Ball getBall() {return ball;}
    public boolean getCompleted() {return complete;}
	
}
