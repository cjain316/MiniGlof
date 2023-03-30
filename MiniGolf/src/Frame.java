import java.awt.Color;
import java.awt.Font;
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
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;



public class Frame extends JPanel implements KeyListener, ActionListener, MouseListener{
    private Image Sprite = null;
    private int tempvel = 0;
    private AffineTransform tx;
    private int level = 13;
    private Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 32);
    private ArrayList<Integer> controlbuffer = new ArrayList<Integer>();
    private Level[] levels = {
    		new Level(1000,700,980,230),
    		new Level(420,700,1400,430),
    		new Level(320,300,1450,300),
    		new Level(385,535,1485,525),
    		new Level(585,535,1485,525),
    		new Level(585,535,1600,250),
    		new Level(700,450,1025,425),
    		new Level(400,600,1500,550),
    		new Level(400,700,1025,475),
    		new Level(400,520,950,250),
    		new Level(400,520,1100,500),
    		new Level(450,700,1450,320),
    		new Level(665,700,1450,275),
    		new Level(1680,830,200,230),
 // }: sad
    };
    private int holestrokes = 0;
    private int strokes = 0;
    
    /* paint is getting called roughly 144x per second */
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        readControlBuffer();
        
        PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        point.setLocation(point.getX()-7,point.getY()-31);
        Ball b = levels[level].getBall();
        
        double x_dist = (b.getX()+b.getWidth()/2 - point.getX());
		double y_dist = (b.getY()+b.getWidth()/2 - point.getY());
		
		if (levels[level].getBall().getVelocity() == 0) {
			tempvel = (int) Math.sqrt(Math.pow(x_dist,2) + Math.pow(y_dist,2)) / 10;
			if (tempvel > 40) {tempvel = 40;}
		}
        
        
		
        if (levels[level].getBall().getVelocity() == 0 && !levels[level].rampcolliding()) {levels[level].getBall().setAngle(calculateAngle(levels[level].getBall(),point));}
        if (levels[level].getCompleted() && !(level+1 >= levels.length)) {level++; strokes += holestrokes; holestrokes = 0;}
        g.setColor(new Color(97, 186, 121));
        g.fillRect(0, 0, 1920, 1080);
        levels[level].paint(g);
        g.setColor(new Color(tempvel*4,240-tempvel*6,0));
        g.fillRect(30,30,tempvel*10,50);
        
        if (level == 7) {
        	g.setColor(new Color(97, 186, 121));
        	g.fillRect(500, 0, 1920, 200);
        	g.fillRect(0, 950, 1920, 950);
        }
        
        tx = AffineTransform.getTranslateInstance(30, 30);
		Sprite = getImage("resources\\power bar.png");
		g2.drawImage(Sprite, tx, null);
		
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Strokes: " + holestrokes, 1550, 50);
		g.drawString("Total Strokes: " + strokes, 1550, 80);
		g.drawString("Hole " + ((int)level+1), 1700, 1040);
		
	}
    
    public void readControlBuffer() {
    	if (controlbuffer.size() > 0) {
    		while (controlbuffer.size() > 0) {
    			//Setting controls
    			
    			if (controlbuffer.get(0) == 100) { // mouse clicked
    				if ((int)levels[level].getBall().getVelocity() == 0) {
    					levels[level].getBall().setVelocity(tempvel);
    					tempvel = 0;
    					holestrokes++;
    				}
    				controlbuffer.remove(0);
    			}
    		}
    	}
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
    	
    	levels[3].addWall(new Wall(200,200,1500,100));
    	levels[3].addWall(new Wall(200,200,100,600));
    	levels[3].addWall(new Wall(200,800,1500,100));
    	levels[3].addWall(new Wall(1600,200,100,600));
    	levels[3].addWall(new Wall(900,800,50,300));
    	levels[3].setMoving(true,400,2,false);
    	
    	levels[4].addWall(new Wall(400, 200, 100, 600));
    	levels[4].addWall(new Wall(400, 200, 1300, 100));
    	levels[4].addWall(new Wall(1600, 200, 100, 600));
    	levels[4].addWall(new Wall(400, 800, 1300, 100));
    	levels[4].addWall(new Wall(700, 450, 200, 200));
    	levels[4].addWall(new Wall(1200, 450, 200, 200));
    	
    	levels[5].addWall(new Wall(500, 100, 1300, 100));
    	levels[5].addWall(new Wall(1700, 100, 100, 700));
    	levels[5].addWall(new Wall(400, 100, 100, 700));
    	levels[5].addWall(new Wall(400, 800, 1400, 100));
    	levels[5].addWall(new Wall(1400, 100, 100, 200));
    	levels[5].addWall(new Wall(1600, 400, 200, 100));
    	levels[5].addWall(new Wall(1100, 400, 200, 200));
    	
    	levels[6].addWall(new Wall(450, 200, 100, 550));
    	levels[6].addWall(new Wall(550, 700, 1000, 50));
    	levels[6].addWall(new Wall(1500, 200, 100, 550));
    	levels[6].addWall(new Wall(550, 200, 1000, 50));
    	levels[6].addWall(new Wall(950, 350, 50, 250));
    	levels[6].addWall(new Wall(1100, 350, 50, 250));
    	
    	levels[7].addWall(new Wall(150, 200, 100, 750));
    	levels[7].addWall(new Wall(150, 200, 1550, 50));
    	levels[7].addWall(new Wall(1650, 200, 100, 750));
    	levels[7].addWall(new Wall(150, 900, 1600, 50));
    	levels[7].addWall(new Wall(700, 300, 50, 250));
    	levels[7].setMoving(true,200,5,false);
    	levels[7].addWall(new Wall(700, 700, 50, 550));
    	levels[7].setMoving(true,200,5,false);
    	levels[7].addWall(new Wall(1050, 350, 50, 350));
    	levels[7].setMoving(true,200,5,false);
    	levels[7].addWall(new Wall(1050, 850, 50, 400));
    	levels[7].setMoving(true,200,5,false);
    	
    	levels[8].addWall(new Wall(300, 150, 1500, 50));
    	levels[8].addWall(new Wall(300, 200, 50, 650));
    	levels[8].addWall(new Wall(300, 800, 1500, 50));
    	levels[8].addWall(new Wall(1750, 150, 50, 700));
    	levels[8].addWall(new Wall(850, 300, 150, 50));
    	levels[8].addWall(new Wall(850, 300, 50, 150));
    	levels[8].addWall(new Wall(1100, 300, 150, 50));
    	levels[8].addWall(new Wall(1200, 300, 50, 150));
    	levels[8].addWall(new Wall(1200, 550, 50, 150));
    	levels[8].addWall(new Wall(1100, 650, 150, 50));
    	levels[8].addWall(new Wall(850, 650, 150, 50));
    	levels[8].addWall(new Wall(850, 550, 50, 100));
    	
    	levels[9].addWall(new Wall(300, 150, 1350, 50));
    	levels[9].addWall(new Wall(300, 200, 50, 650));
    	levels[9].addWall(new Wall(300, 800, 1350, 50));
    	levels[9].addWall(new Wall(1600, 150, 50, 700));
    	levels[9].addWall(new Wall(600, 200, 50, 250));
    	levels[9].addWall(new Wall(600, 550, 50, 250));
    	levels[9].addWall(new Wall(850, 200, 50, 150));
    	levels[9].addWall(new Wall(850, 450, 50, 350));
    	levels[9].addWall(new Wall(1050, 200, 50, 350));
    	levels[9].addWall(new Wall(1050, 650, 50, 200));
    	levels[9].addWall(new Wall(1300, 150, 50, 350));
    	levels[9].addWall(new Wall(1300, 600, 50, 250));
    	
    	levels[10].addWall(new Wall(150, 100, 1600, 50));
    	levels[10].addWall(new Wall(1700, 100, 50, 850));
    	levels[10].addWall(new Wall(150, 900, 1600, 50));
    	levels[10].addWall(new Wall(150, 100, 50, 850));
    	levels[10].addWall(new Wall(700, 600, 50, 100));
    	levels[10].addWall(new Wall(750, 550, 50, 50));
    	levels[10].addWall(new Wall(800, 500, 50, 50));
    	levels[10].addWall(new Wall(850, 550, 50, 50));
    	levels[10].addWall(new Wall(900, 600, 50, 100));
    	levels[10].addWall(new Wall(950, 550, 50, 50));
    	levels[10].addWall(new Wall(1000, 500, 50, 50));
    	levels[10].addWall(new Wall(1050, 550, 50, 50));
    	levels[10].addWall(new Wall(1100, 600, 50, 100));
    	levels[10].addWall(new Wall(700, 450, 450, 50));
    	levels[10].addWall(new Wall(700, 400, 100, 50));
    	levels[10].addWall(new Wall(750, 350, 50, 50));
    	levels[10].addWall(new Wall(900, 400, 50, 50));
    	levels[10].addWall(new Wall(1050, 400, 100, 50));
    	levels[10].addWall(new Wall(1050, 350, 50, 50));
    	levels[10].addWall(new Wall(800, 350, 250, 50));
    	levels[10].addWall(new Wall(800, 300, 250, 50));
    	
    	levels[11].addWall(new Wall(200, 150, 100, 800));
    	levels[11].addWall(new Wall(200, 850, 1500, 100));
    	levels[11].addWall(new Wall(1600, 100, 100, 850));
    	levels[11].addWall(new Wall(200, 100, 1500, 100));
    	levels[11].addWall(new Wall(600, 200, 50, 700));
    	levels[11].addWall(new Wall(950, 150, 50, 750));
    	levels[11].addWall(new Wall(1300, 150, 50, 750));
    	levels[11].addPortal(new Portal(350,250,200,50,800,750,0));
    	levels[11].addPortal(new Portal(700,250,200,50,1150,750,0));
    	levels[11].addPortal(new Portal(1050,250,200,50,1475,750,0));
    	
    	levels[12].addWall(new Wall(250, 150, 50, 800));
    	levels[12].addWall(new Wall(250, 900, 1400, 50));
    	levels[12].addWall(new Wall(1600, 150, 50, 800));
    	levels[12].addWall(new Wall(250, 150, 1400, 50));
    	levels[12].addWall(new Wall(450, 200, 50, 200));
    	levels[12].addWall(new Wall(650, 200, 50, 200));
    	levels[12].addWall(new Wall(850, 200, 50, 200));
    	levels[12].addWall(new Wall(1050, 200, 50, 750));
    	levels[12].addWall(new Wall(1100, 400, 150, 50));
    	levels[12].addWall(new Wall(1050, 650, 200, 50));
    	levels[12].addPortal(new Portal(325,225,100,50,1125,800,90));
    	levels[12].addPortal(new Portal(525,225,100,50,1125,300,90));
    	levels[12].addPortal(new Portal(725,225,100,50,1125,550,90));
    	levels[12].addPortal(new Portal(925,225,100,50,675,850,0));
    	
    	levels[13].addWall(new Wall(1800, 100, 50, 900));
    	levels[13].addWall(new Wall(50, 950, 1750, 50));
    	levels[13].addWall(new Wall(50, 100, 50, 900));
    	levels[13].addWall(new Wall(50, 100, 1800, 50));
    	levels[13].addWall(new Wall(1550, 350, 50, 400));
    	levels[13].addWall(new Wall(300, 350, 1300, 50));
    	levels[13].addWall(new Wall(300, 350, 50, 400));
    	levels[13].addWall(new Wall(300, 700, 1300, 50));
    	levels[13].addRamp(new Ramp(1600,450,200,200,90));
    	levels[13].addRamp(new Ramp(1350,750,200,200,180));
    	levels[13].addRamp(new Ramp(100,450,200,200,90));
    	levels[13].addRamp(new Ramp(350,750,200,200,180));
    	levels[13].addRamp(new Ramp(1350,150,200,200,180));
    	levels[13].addRamp(new Ramp(350,150,200,200,180));
    	
    	
    	
        JFrame f = new JFrame("A+ Project");
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
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		controlbuffer.add(100);
		repaint();
	}
    
    
}

class Background {
	
}