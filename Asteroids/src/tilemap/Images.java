package tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Images {
	private BufferedImage image;
	
	private double x,y,xDir,yDir,moveScale;
	
	public Images(String name){
		try{
			image = ImageIO.read(
			getClass().getResourceAsStream(name));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setPosition(double x,double y){
		this.x = (x*moveScale)%GamePanel.WIDTH;
		this.y = (y*moveScale)%GamePanel.HEIGHT;
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
		g.drawImage(image, (int)x, (int)y, null);
	}
}
