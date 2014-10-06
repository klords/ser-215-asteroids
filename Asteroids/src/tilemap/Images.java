package tilemap;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Images {
	private BufferedImage image;
	
	private double x,y,xDir,yDir,moveScale;

	private Double movescale;
	
	public Images(String name){
		try{
			image = ImageIO.read(
			getClass().getResourceAsStream(name)); //retrieve image in constructor
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public Images(String name,Double ms){//for movescale objects
		try{
			image = ImageIO.read(
			getClass().getResourceAsStream(name)); //retrieve image in constructor
		}
		catch(Exception e){
			e.printStackTrace();
		}
		movescale = ms;
	}
	public void setPosition(double x,double y){
		this.x = x;				//set position on screen
		this.y = y;
	}
	public void setX(double x){
		this.x+=x;
	}
	public void setY(double y){
		this.y+=y;
	}
	
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	public void setAutoScroll(double xDir,double yDir){
		this.xDir=xDir;
		this.yDir=yDir;
	}
	public void update(){
		x+=xDir;
		y+=yDir;
	}
	public void draw(Graphics2D g){
		g.drawImage(image, (int)x, (int)y, null);	//draw to screen
	}
}
