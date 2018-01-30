
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
public class crawlerPlayer extends Crawler{
	
	private int	shotsFired;
	private int shotsOnTarget;
	private int collisions;
	private int tilesExplored; 
	
    double x = 100, y = 330;
    int currentAngle = 0, health = 100;
    private gameEngine game;
    Color tileColour = new Color(153,102,0);
    private boolean leftRotate = false, rightRotate = false, forwardMove = false,
    backwardMove = false;
    private boolean[][] BoolGrid;
    private int[][] IntGrid;
    private tile[][] VisibleGrid;
    public crawlerPlayer(gameEngine game, String type, int x, int y){
    	super(game, type, x, y);
        BoolGrid = new boolean[10][10];
        IntGrid = new int[10][10];
        VisibleGrid = new tile[10][10];
        for (int i = 0; i < 10; i++){
        	for(int j = 0; j < 10; j++){
        		BoolGrid[i][j] = false;
        		IntGrid[i][j] = 0;
        		VisibleGrid[i][j] = new tile(2);
        		VisibleGrid[i][j].setDetails(i * 50, j * 50, 50, 50);
        	}
        }
    }
    public void paint(Graphics2D g) {
    	reset();
    	determineVisibility();
    	for(int i = 0; i < 10; i ++){
    		for(int j = 0; j < 10; j++){
    			g.setColor(VisibleGrid[i][j].getColour());
    			VisibleGrid[i][j].paint(g);
    		}
    		if (sight[i].getType().equals("Crawler")){
    			game.crawler1.paint(g);
    		}
    	}
    	super.paint(g);
    }
    public void reset(){
    	for(int i = 0; i < 10; i++){
    		for(int j = 0; j < 10; j++){
    			VisibleGrid[i][j].setColour(new Color(0,0,0));
    		}
    	}
    }
    public void determineVisibility(){
    	for (int i = 0;  i < 10; i++){
    		for(int j = 0; j < 10; j++){
    			for(int k = 0; k < 10; k++){
    				if(sight[i].getVisibility(VisibleGrid[j][k]) == true){
    					VisibleGrid[j][k].setColour(game.tileMap[j][k].getColour());
    				}
    			}
    		}
    	}
    }
    public void keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == KeyEvent.VK_LEFT)
            leftRotate = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            rightRotate = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        	backwardMove = false;
        if (e.getKeyCode() == KeyEvent.VK_UP)
        	forwardMove = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            game.controller.addBullet(new bullet((int) x + 15,(int) y, currentAngle, type, 0.6f));
        }
    }
 
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            leftRotate = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            rightRotate = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        	backwardMove = true;
        if (e.getKeyCode() == KeyEvent.VK_UP)
        	forwardMove = true;
    }
    
    public void movePlayer(){
    	if (leftRotate == true){
    		rotateLeft();
    	}
    	if (rightRotate == true){
    		rotateRight();
    	}
    	if (backwardMove == true){
    		moveBackward();
    	}
    	if (forwardMove == true){
    		moveForward();
    	}
    }
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 30, 30);
    }
    public Rectangle getPredictedBounds(int x, int y){
        return new Rectangle(x, y, 30, 30);
    }
    public double getTopY(){
        return y;
    }
    public boolean collision(double x, double y){
        boolean collisionDetect = false;
        for (int i = 0; i < gameEngine.NUM_ROWS; i++) {
            for (int j = 0; j < gameEngine.NUM_COLS; j++) {
                if(game.tileMap[i][j].getBounds().intersects(getPredictedBounds((int) x, (int) y)) && game.tileMap[i][j].getColour().equals(tileColour)){
                    collisionDetect = true;
                }
            }
        }
        return collisionDetect;
    }
    public int getHealth(){
    	return health;
    }
    public void resetHealth(){
    	health = 100;
    }
    
    public int getShotsFired() {
		return shotsFired;
	}
	
	public int getShotsOnTarget() {
		return shotsOnTarget;
	}	
	
	public int getCollisions() {
		return collisions;
	}	
	

	public int getTilesExplored() {
		return	tilesExplored;
	}	

	
	
	
	public void resetCounters() {
		health = 100;
		shotsFired = 0;
		shotsOnTarget = 0;
		collisions = 0;
		tilesExplored = 0; 		
	}
    
    
}
