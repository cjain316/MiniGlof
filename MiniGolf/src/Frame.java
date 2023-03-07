import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;



public class Frame extends JPanel implements KeyListener, ActionListener, MouseListener{
    private Image Sprite = null;
    private int tempvel = 0;
    private AffineTransform tx;
    private int level = 0;
    private Level[] levels = {
    		new Level(1000,700,980,230),
    		new Level(420,700,1400,430),
    		new Level(320,300,1450,300),
    };
    /* paint is getting called roughly 144x per second */
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        point.setLocation(point.getX()-7,point.getY()-31);
        Ball b = levels[level].getBall();
        
        double x_dist = (b.getX()+b.getWidth()/2 - point.getX());
		double y_dist = (b.getY()+b.getWidth()/2 - point.getY());
		
		if (levels[level].getBall().getVelocity() == 0) {
			tempvel = (int) Math.sqrt(Math.pow(x_dist,2) + Math.pow(y_dist,2)) / 15;
			if (tempvel > 40) {tempvel = 40;}
		}
        
        
        if (levels[level].getBall().getVelocity() == 0) {levels[level].getBall().setAngle(calculateAngle(levels[level].getBall(),point));}
        if (levels[level].getCompleted() && !(level+1 >= levels.length)) {level++;}
        g.setColor(new Color(207, 255, 189));
        g.fillRect(0, 0, 1920, 1080);
        levels[level].paint(g);
        g.setColor(new Color(tempvel*4,240-tempvel*6,0));
        g.fillRect(30,30,tempvel*10,50);
        
        tx = AffineTransform.getTranslateInstance(30, 30);
		Sprite = getImage("resources\\power bar.png");
		g2.drawImage(Sprite, tx, null);
	}
    
    
    
    public static void main(String[] arg) {
        Frame f = new Frame();
        
    }    
    
    
    @Override
    public void keyPressed(KeyEvent arg) {
    	//System.out.println(arg.getExtendedKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent arg) {
    	
    }
    
    

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        repaint();
    }
    
    public boolean randBool() {if (Math.random() > 0.5) {return true;} else {return false;}}
    
    Timer t;
    
    public Frame() {
    	levels[0].addWall(new Wall(1200,200,100,700));
    	levels[0].addWall(new Wall(720,200,100,700));
    	levels[0].addWall(new Wall(720,100,580,100));
    	levels[0].addWall(new Wall(720,900,580,100));
    	
    	levels[1].addWall(new Wall(200,200,1300,100));
    	levels[1].addWall(new Wall(200,200,100,800));
    	levels[1].addWall(new Wall(200,900,500,100));
    	levels[1].addWall(new Wall(600,600,100,300));
    	levels[1].addWall(new Wall(600,599,900,100));
    	levels[1].addWall(new Wall(1500,200,100,500));
    	
    	levels[2].addWall(new Wall(100,200,100,700));
    	levels[2].addWall(new Wall(1600,200,100,700));
    	levels[2].addWall(new Wall(100,100,1600,100));
    	levels[2].addWall(new Wall(100,900,1600,100));
    	levels[2].addWall(new Wall(860,400,100,500));
    	levels[2].addWall(new Wall(460,200,100,500));
    	levels[2].addWall(new Wall(1260,200,100,500));
    	
        JFrame f = new JFrame("Mini Golf");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.addKeyListener(this);
        f.addMouseListener(this);
        
        f.setResizable(false);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        t = new Timer(7, this);
        t.start();
        f.setVisible(true);
        
       
        
    }
    
    protected Image getImage(String path) {

        Image tempImage = null;
        try {
            URL imageURL = Background.class.getResource(path);
            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {e.printStackTrace();}
        return tempImage;
    }



	



	



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	public int calculateAngle(Ball b, Point cursor) {
		double x_dist = (b.getX()+b.getWidth()/2 - cursor.getX());
		double y_dist = (b.getY()+b.getWidth()/2 - cursor.getY());
		
		if (x_dist == 0) {
			return 0;
		}
		double result = Math.atan(Math.abs(y_dist/x_dist));
		//System.out.println("x_dist: " + x_dist + " y_dist: " + y_dist + " Result: " + result);
		
		if (x_dist < 0) {
			if (y_dist < 0) return (int)(Math.toDegrees(result)); // Quadrant 1
			else return 360-(int)(Math.toDegrees(result)); // Quadrant 4
		} else {
			if (y_dist < 0) return 180-(int)(Math.toDegrees(result)); // Quadrant 2
			else return 180 + (int)(Math.toDegrees(result)); // Quadrant 3
		}
		
	}
	



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		repaint();
		if (levels[level].getBall().getVelocity() == 0) {
			levels[level].getBall().setVelocity(tempvel);
			tempvel = 0;
		}
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    
    
}

class Background {
	
}