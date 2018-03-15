package warehouse_interface;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import interfaces.Robot;
import lejos.geom.Point;

public class WarehouseInterface extends JFrame implements Runnable{

	private Image bg = new ImageIcon("src/warehouse_interface/FullMap.jpg").getImage();
	private Image robot1 = new ImageIcon("src/warehouse_interface/robotOne.png").getImage();
	private Image robot2 = new ImageIcon("src/warehouse_interface/robotTwo.png").getImage();
	private Image robot3 = new ImageIcon("src/warehouse_interface/robotThree.png").getImage();
	private Image[] arrayOfImages = {robot1, robot2, robot3};
	private Thread thread;
	private int zeroOnRobotXAxis = 60;
	private int zeroOnRobotYAxis = 730;
	private int moveByXAxis = 90;
	private int moveByYAxis = -93;
	private Robot[] robots;
	
	
	//Constructor that takes a list of robots as a parameter
	public WarehouseInterface(Robot[] robots){
		setFrame();		
		this.robots = robots;	
		thread = new Thread(this);
		thread.start();	
	}
	
	//To set a frame	
	public void setFrame(){
		this.setName("WarehouseInterface");
		this.setSize(1200, 850);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//To draw all robots on a map
	public void paint(Graphics g){
		g.drawImage(bg, 0, 0, null);		
		for(int i = 0; i < robots.length; i++){
			//Giving unique image for each robot if number of robots is less than 3 and set current position of each robot on a map
			if(i<arrayOfImages.length)
				g.drawImage(arrayOfImages[i], zeroOnRobotXAxis+Math.round(robots[i].getCurrentPosition().x)*moveByXAxis, zeroOnRobotYAxis+Math.round(robots[i].getCurrentPosition().y)*moveByYAxis, null);
			//In case if there are more than 3 robots, because we have only 3 pictures of robots, we will give green lamborghini to a new robot which is repeating of pictures
			else
				g.drawImage(arrayOfImages[0], zeroOnRobotXAxis, zeroOnRobotYAxis, null);			
		}
		JLabel lblRobot = new JLabel();
			this.add(lblRobot);		
	}
	 
	//A thread to redraw map with updated coordinates
	@Override
	public void run() {		
		while(true){
			//check and update the current position of each robot on a map
			for(int i = 0; i < robots.length; i++){
				robots[i].setCurrentPosition(new Point(robots[i].getCurrentPosition().x, robots[i].getCurrentPosition().y));
			}
			revalidate();
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
	
}



