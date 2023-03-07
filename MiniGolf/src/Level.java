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
		boolean done = false;
		//Timer decrement
		if (timer > 0) {timer--;}
		
		//painting walls
		for (int i = 0; i < walls.size();i++) {walls.get(i).paint(g);}
		hole.paint(g);
		
		//Collision detection
		for (int i = 0; i < walls.size();i++) {
			if (colliding(ball,walls.get(i)) && timer == 0) {
				//ricochet
				if (walls.get(i).getHorizontal()) {ball.setVy(ball.getVy()*-1); timer = 2;} 
					//DANIAL RIGHT HERE THIS IS WHERE THE METHOD CALL GOES
					//THE WALLS ARE ALREADY DEFINED AS HORIZONTAL OR VERTICAL
				if (!walls.get(i).getHorizontal()) {ball.setVx(ball.getVx()*-1); timer = 2;}
					//DANIAL RIGHT HERE THIS IS WHERE THE METHOD CALL GOES
					//THE WALLS ARE ALREADY DEFINED AS HORIZONTAL OR VERTICAL

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
