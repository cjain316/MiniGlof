import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Frame extends JPanel implements KeyListener, ActionListener{
    private Image Sprite = null;
    private int tempvel = 0;
    private AffineTransform tx;
    private Level level1 = new Level(960,700,980,230);
    /* paint is getting called roughly 144x per second */
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g.setColor(new Color(207, 255, 189));
        g.fillRect(0, 0, 1920, 1080);
        
        level1.paint(g);
        g.fillRect(30,800,tempvel*10,50);
	}

    
    
    public static void main(String[] arg) {
        Frame f = new Frame();
        
    }    
    
    @Override
    public void keyPressed(KeyEvent arg) {
    	System.out.println(arg.getExtendedKeyCode());
    	if (arg.getExtendedKeyCode() == 87) {
    		tempvel += 1;
    	}
    	if (arg.getExtendedKeyCode() == 83) {
    		tempvel -= 1;
    	}
    	if (arg.getExtendedKeyCode() == 65) {
    		level1.getBall().setAngle(level1.getBall().getAngle()-10);
    	}
    	if (arg.getExtendedKeyCode() == 68) {
    		level1.getBall().setAngle(level1.getBall().getAngle()+10);
    	}
    	if (arg.getExtendedKeyCode() == 32) {
    		level1.getBall().setVelocity(tempvel);
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
    	level1.addWall(new Wall(1200,200,100,700));
    	level1.addWall(new Wall(720,200,100,700));
    	level1.addWall(new Wall(720,100,580,100));
    	level1.addWall(new Wall(720,900,580,100));
    	
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
    
    
}

class Background {
	
}