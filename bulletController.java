
import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.Color;
public class bulletController {
    private LinkedList<bullet> b = new LinkedList<bullet>();
    private bullet tempBullet;
    gameEngine Crawler;
    Color tileColour = new Color(153,102,0);
    public bulletController(gameEngine crawler){
        this.Crawler = crawler;
    }
    public void move(){
        boolean remove = false;
        for(int i = 0; i < b.size(); i++){
            tempBullet = b.get(i);
            // If the bullet hits the border it is destroyed and removed from the linked list of bullets
            if(tempBullet.getY() < 0 || tempBullet.getY() > 500 || tempBullet.getX() < 0 ||  tempBullet.getX() > 500){
                remove = true;
                removeBullet(tempBullet);
            }
            if(tempBullet.getType().equals("crawler1")){
            	if(tempBullet.getBounds().intersects(Crawler.crawler2.getBounds())){
            		remove = true;
            		removeBullet(tempBullet);
            		Crawler.crawler2.removeHealth((int)(10 * tempBullet.getSize()));
            	}
            }
            else if(tempBullet.getType().equals("crawler2")){
            	if(tempBullet.getBounds().intersects(Crawler.crawler1.getBounds())){
            		remove = true;
            		removeBullet(tempBullet);
            		Crawler.crawler1.removeHealth((int)(10 * tempBullet.getSize()));
            	}
            }
        // For loop to determine whether the bullet has hit a barricade
        for (int k = 0; k < gameEngine.NUM_ROWS; k++){
            for (int j = 0; j < gameEngine.NUM_COLS; j++){
            	// If the bullet hits the crawler it is destroyed and removed from the linked list of bullets
            	 if(Crawler.tileMap[k][j].getBounds().intersects(tempBullet.getBounds()) && Crawler.tileMap[k][j].getType().equals("Barricade")){
            		remove = true;
                    removeBullet(tempBullet);
                }
            }
        }
        // If there hasn't been a collision move the bullet in the direction it was heading
        if(remove == false){
            tempBullet.move();
        }
        }
    }
    public void paint(Graphics g){
        for (int i = 0; i < b.size(); i++){
            tempBullet = b.get(i);
            tempBullet.paint(g);
        }
    }
    
    public void addBullet(bullet tBullet){
        b.add(tBullet);
    }
    public void removeBullet(bullet tBullet){
        b.remove(tBullet);
    }
}