import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Level {
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Portal> portals = new ArrayList<Portal>();
	private ArrayList<Ramp> ramps = new ArrayList<Ramp>();
	private int startingx,startingy;
	private int holex,holey;
	private boolean complete;
	Ball ball;
	Hole hole;
	private int timer = 0;
	
	private void rampFunction(Ramp ramp) {
		ball.setVelocity(ball.getVelocity()+0.5);
		ball.setVy(ball.getVy()+0.07);
	}

	public void paint(Graphics g) {
		//Timer decrement
		if (timer > 0) {timer--;}
		
		//painting walls
		for (int i = 0; i < walls.size();i++) {walls.get(i).paint(g);}
		for (int i = 0; i < portals.size();i++) {portals.get(i).paint(g);}
		for (int i = 0; i < ramps.size();i++) {ramps.get(i).paint(g);}
		hole.paint(g);
		
		//Collision detection
		Rectangle xtest;
		Rectangle ytest;
		//System.out.println(ball.getVx());
		for (int i = 0; i < portals.size();i++) {
			if (ball.getHitbox().intersects(portals.get(i).getHitbox())) {
				ball.setX(portals.get(i).x2);
				ball.setY(portals.get(i).y2);
				ball.setVelocity(ball.getVelocity() + 1);
				if (portals.get(i).directionChange == 90) {
					ball.setVy(0);
					ball.setVx(1);
				}
			}
		}
		boolean goofyahh = false;
		for (int i = 0; i < ramps.size();i++) {
			if (ball.getHitbox().intersects(ramps.get(i).getHitbox())) {
				goofyahh = true;
				rampFunction(ramps.get(i));
			}
		}
		if (!goofyahh) {
			//if (ball.getVelocity() > 0) {
				//ball.setVelocity(ball.getVelocity()*-1);
				//ball.recalculate(ball.getVx(),ball.getVy());
			//}
		}
		
		for (int i = 0; i < walls.size();i++) {
			xtest = new Rectangle(0,0,30,30);
			ytest = new Rectangle(0,0,30,30);
			xtest.setLocation((int)(ball.getX()+(ball.getVx()*ball.getVelocity())),ball.getY());
			ytest.setLocation(ball.getX(),(int)(ball.getY()+(ball.getVy()*ball.getVelocity())));
			//g.setColor(new Color(255,0,0));
			//g.fillRect((int)xtest.getX(),(int)xtest.getY(),30,30);
			//g.setColor(new Color(0,255,0));
			//g.fillRect((int)ytest.getX(),(int)ytest.getY(),30,30);
			if (xtest.intersects(walls.get(i).getHitbox())) {
				ball.setVx(ball.getVx()*-1);
			}
			else if (ytest.intersects(walls.get(i).getHitbox())) {		
				ball.setVy(ball.getVy()*-1);	
			}
			if (colliding(ball,walls.get(i))) {
				ball.setVelocity(ball.getVelocity()+1);
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
	
	public void removeWall() {
		walls.remove(walls.size()-1);
	}
	
	public void setMoving(boolean moving, int amount, int speed, boolean horizontal) {
		walls.get(walls.size()-1).setMoving(moving, amount, speed, horizontal);
	}
	
	public void addWall(Wall w) {
		walls.add(w);
	}
	public void addRamp(Ramp r) {
		ramps.add(r);
	}
	
	public void addPortal(Portal p) {
		portals.add(p);
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
    
    private void clearArray(ArrayList<Integer> a) {
    for (int i = 0; i < a.size(); i++) {a.remove(i);}}
    
    private int getIndexOf(ArrayList<Integer> a, int value) {
    	for (int i = 0; i < a.size(); i++) {
    		if (a.get(i) == value) {return i;}
    	}
    	return -1;
    }

    //private int getRicochet(Wall w, Ball b) {
    	
    //}
	
}
