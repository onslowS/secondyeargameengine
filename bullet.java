import java.awt.*;
public class bullet {
	private double x, y;
	private int angle;
	private String type;
	private float size;
	public bullet(int x, int y, int angle, String type, float size){
		this.x = x + 15;
		this.y = y + 15;
		this.angle = angle;
		this.type = type;
	}
	public void move(){
		x = x + 5 * Math.sin(Math.toRadians(angle));
		y = y + 5 * Math.cos(Math.toRadians(angle));
	}
	public void paint(Graphics g){
		g.fillOval((int)x, (int)y, 5, 5);
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 5, 5);
	}
	public double getY(){
		return y;
	}
	public double getX(){
		return x;
	}
	public String getType(){
		return type;
	}
	public float getSize(){
		return size;
	}
}
