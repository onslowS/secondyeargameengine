import java.awt.*;
import java.awt.geom.Line2D;

public class ray {
	private int angle;
	private double endX, endY, x, y, shortestTile = -1, result;
	private String type;
	Line2D shape = new Line2D.Double();
	gameEngine game;
	Crawler host;
	private String detectType = "NULL";
	public ray(int angle, Crawler host, gameEngine game, String type) {
		this.angle = angle;
		this.game = game;
		this.host = host;
		this.type = type;
		x = host.getX();
		y = host.getY();
		endX = x + 707.1 * Math.sin(Math.toRadians(angle));
		endY = y + 707.1 * Math.cos(Math.toRadians(angle));
	}
	public void paint(Graphics2D g){
		if (getCollision() != -1){
			g.setColor(Color.red);
		}
		else{
			g.setColor(Color.blue);
		}
		g.draw(shape);
	}
	public void rotateRight(){
		angle -= 1;
	}
	public void rotateLeft(){
		angle += 1;
	}
	public void setLine(){
		x = host.getX() + 15;
		y = host.getY() + 15;
		endX = x + 707.1 * Math.sin(Math.toRadians(angle));
		endY = y + 707.1 * Math.cos(Math.toRadians(angle));
		shape.setLine(x, y, endX, endY); 
	}
	public boolean getVisibility(tile tile){
		double visibleResult;
		if(shape.intersects(tile.getBounds())){
			visibleResult = Math.sqrt(Math.pow(tile.getBounds().getX() - x, 2) + (Math.pow(tile.getBounds().getY() - y, 2)));
			if(visibleResult <= shortestTile || shortestTile == -1){
				return true;
			}
		}
		return false;
	}
	public double getCollision(){
		shortestTile = -1;
		for (int i = 0; i < 10; i++){  
			for (int j = 0; j < 10; j++){
				if(shape.intersects(game.tileMap[i][j].getBounds()) &&  game.tileMap[i][j].getType().equals("Barricade")){
					result = Math.sqrt(Math.pow(game.tileMap[i][j].getBounds().getX() - x, 2) + (Math.pow(game.tileMap[i][j].getBounds().getY() - y, 2)));
					if(result < shortestTile || shortestTile == -1){
						detectType = "Barricade";
						shortestTile = result;
					}
				}
			}
		}
		if(type.equals("crawler1")){
			if(shape.intersects(game.crawler2.getBounds())){
				result = Math.sqrt(Math.pow(game.crawler2.getBounds().getX() - x, 2) + (Math.pow(game.crawler2.getBounds().getY() - y, 2)));
				if(result < shortestTile || shortestTile == -1){
					detectType = "Crawler";
					shortestTile = result;
				}
			}
		}
		else if(type.equals("crawler2")){
			if(shape.intersects(game.crawler1.getBounds())){
				result = Math.sqrt(Math.pow(game.crawler1.getBounds().getX() - x, 2) + (Math.pow(game.crawler1.getBounds().getY() - y, 2)));
				if(result < shortestTile || shortestTile == -1){
					detectType = "Crawler";
					shortestTile = result;
				}
			}
		}
		if (shortestTile == -1){
			detectType = "NULL";
			System.out.println(shortestTile);
		}
		else{
			System.out.println(shortestTile/707.1);
		}
		return shortestTile;
	}
	public double getShortestTile(){
		return shortestTile;
	}
	public String getType(){
		return detectType;
	}
}
