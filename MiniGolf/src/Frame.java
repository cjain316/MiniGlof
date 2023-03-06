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
    };
    /* paint is getting called roughly 144x per second */
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        point.setLocation(point.getX()-7,point.getY()-31);
        
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
    	if (arg.getExtendedKeyCode() == 87) {
    		if (tempvel < 40) {
    			tempvel += 1;
    		}
    	}
    	if (arg.getExtendedKeyCode() == 83) {
    		if (tempvel > 0) {
    			tempvel -= 1;
    		}
    	}
    	if (arg.getExtendedKeyCode() == 65) {
    		levels[level].getBall().setAngle(levels[level].getBall().getAngle()-10);
    	}
    	if (arg.getExtendedKeyCode() == 68) {
    		if (levels[level].getBall().getAngle() == 0) {levels[level].getBall().setAngle(-350);}
    		else {levels[level].getBall().setAngle(levels[level].getBall().getAngle()+10);}
    	}
    	if (arg.getExtendedKeyCode() == 32) {
    		levels[level].getBall().setVelocity(tempvel);
    		tempvel = 0;
    	}
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
    	
        JFrame f = new JFrame("Mini Golf");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.addKeyListener(this);
        
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		
	}
    
    
}

class Background {
	
}