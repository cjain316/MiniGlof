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



public class LevelCreator extends JPanel implements KeyListener, ActionListener, MouseListener{
    private Image Sprite = null;
    private int tempvel = 0;
    private AffineTransform tx;
    private int level = 0;
    private Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 32);
    private ArrayList<String> controlbuffer = new ArrayList<String>();
    private Point pos1,pos2;
    private int levelnumber = 8;
    private Level[] levels = {
    		new Level(100,100,200,200),
 // }: sad
    };
    private ArrayList<String> levelCode = new ArrayList<String>();
    private int holestrokes = 0;
    private int strokes = 0;
    
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
        if (levels[level].getCompleted() && !(level+1 >= levels.length)) {level++; strokes += holestrokes; holestrokes = 0;}
        g.setColor(new Color(97, 186, 121));
        g.fillRect(0, 0, 1920, 1080);
        levels[level].paint(g);
        g.setColor(new Color(tempvel*4,240-tempvel*6,0));
        g.fillRect(30,30,tempvel*10,50);
        
        tx = AffineTransform.getTranslateInstance(30, 30);
		Sprite = getImage("resources\\power bar.png");
		g2.drawImage(Sprite, tx, null);
		
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Hole Strokes: " + holestrokes, 1550, 50);
		g.drawString("Total Strokes: " + strokes, 1550, 80);
		g.drawString("Hole " + ((int)level+1), 1700, 1040);
		
		g.drawString("X: " + point.getX() + " Y: " + point.getY(), 100, 1000);
		
		
		g.setColor(Color.BLACK);
        for (int i = 0; i < 100; i ++) {
        	g.drawLine(50*i,0,50*i,1920);
        	g.drawLine(0,50*i,1920,50*i);
        }
	}
    
    
    
    public static void main(String[] arg) {
        LevelCreator f = new LevelCreator();
        
    }    
    
    
    @Override
    public void keyPressed(KeyEvent arg) {
    	//System.out.println(arg.getExtendedKeyCode());
    	if (arg.getExtendedKeyCode() == 90) {
    		levels[0].removeWall();
    		levelCode.remove(levelCode.size()-1);
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
    
    public LevelCreator() {
    	
        JFrame f = new JFrame("Mr David give me an A pls");
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



	public void createnew() {
		if (pos1 != null && pos2 != null) {
			levels[0].addWall(new Wall((int)pos1.getX(),(int)pos1.getY(),(int)pos2.getX()-(int)pos1.getX(),(int)pos2.getY()-(int)pos1.getY()));
			levelCode.add( ("levels[0].addWall(new Wall(" + (int)pos1.getX() + ", " + (int)pos1.getY() + ", " + ((int)pos2.getX()-(int)pos1.getX()) + ", " + ((int)pos2.getY()-(int)pos1.getY())+ "));" + "\n") );
			pos1 = null;
			pos2 = null;
			for (int i = 0; i < 100; i++) {
				System.out.println("\n");
			}
			System.out.println(arr2string(levelCode));
		}
		//
	}

	public String arr2string(ArrayList<String> s) {
		String output = "";
		for (int i = 0; i < s.size(); i++) {
			output += (s.get(i));
		}
		return output;
	}

	



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("mousereleased");
		PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        point.setLocation(point.getX()-7,point.getY()-31);
        pos2 = new Point((int)point.getX(),(int)point.getY());
        
        createnew();
		
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
		//System.out.println("mouseclicked");
		PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        point.setLocation(point.getX()-7,point.getY()-31);
        pos1 = new Point((int)point.getX(),(int)point.getY());
		repaint();
	}
    
    
}
