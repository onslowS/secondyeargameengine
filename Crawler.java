
import java.util.*;

import ANN.ANN;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.lang.Math;
import java.awt.Rectangle;

import controlUnit.ControlUnit;



public class Crawler {
	
	public final static int INPUTS = 10;
	public final static int OUTPUTS = 3;

	
	private int generation;
	private int id;
	private ANN ann;
	private double rating;
	
	private float performanceRating;
	
	private int health;
	private int	shotsFired;
	private int shotsOnTarget;
	private int collisions;
	private int tilesExplored; 

	
	protected int fieldOfView = 90, currentAngle = 0;
    private gameEngine game;
    protected double x = 100, y = 100;
    protected Color tileColour = new Color(153,102,0);
    protected ray[] sight = new ray[10];
    protected String type;
    private boolean[][] BoolGrid;
    private int[][] IntGrid;
    private tile[][] VisibleGrid;
    public Crawler(gameEngine game, String type, int x, int y) {
        this.game = game;
        this.type = type;
        this.x = x;
        this.y = y;
        for (int i = 0; i < 10; i++){
        	sight[i] = new ray(currentAngle - fieldOfView/2 + ((fieldOfView/10) * i), this, game, type);
        }
    }
    
    public void getGame() {
    	System.out.println(this.game.getHeight());
    }
    
    
    public void setGame(gameEngine game) {
    	this.game = game;
    	for (int i = 0; i < 10; i++){
        	sight[i] = new ray(currentAngle - fieldOfView/2 + ((fieldOfView/10) * i), this, game, type);
        }
    }
    
    public int getNumHiddenLayers() {
    	return ann.getHiddenLayers();
    }
    
    public int getNPL() {
    	return ann.getNPL();
    }
    
    public int getInputs() {
    	return ann.getInputs();
    }

    public int getOutputs() {
    	return ann.getOutputs();
    }    
    
/* For man opperated crawler */
	
	public Crawler() {
		resetCounters();
	}
		
	public Crawler(int generation, int id, double rating, ANN ann, String type, int x, int y) {
		this.x = x;
		this.y = y;
		
		this.generation = generation;
		this.id = id;
		this.rating = rating;
		this.ann = ann;
		resetCounters();		
	}

	public Crawler(ANN ann) {
		this.ann = ann;
		resetCounters();
			
	}
    
    public void evaluateInputs() {
    	
    	double[] inputs = new double[INPUTS];
    	
    	int inputCount = 0;
    	
    	for(int i = 0; i < 10; i++)
    	
    		inputs[inputCount++] = (sight[i].getShortestTile() == -1) ? -1 : sight[i].getShortestTile() / 707.1D;
    		
    	double[] outputs = ann.evaluateInputs(inputs);
    	
    	if(outputs[0] < 0.5D) moveBackward();
    	else moveForward();
    	
    	if(outputs[1] < 0.5D) rotateRight();
    	else rotateLeft();

    	if(outputs[2] > 0.5D) fire();
    	
    	//moveForward();
    }
    
    public void moveForward() {
		    	
    	if (getCollision((x + Math.sin(Math.toRadians(currentAngle))), y + Math.cos(Math.toRadians(currentAngle))) == false){
            x = x + Math.sin(Math.toRadians(currentAngle));
    		y = y + Math.cos(Math.toRadians(currentAngle));
    		
    	}
    	else {
    		
    		collisions++;
    	}
    }
    
    public void moveBackward() {
    	if (getCollision((x - Math.sin(Math.toRadians(currentAngle))), y - Math.cos(Math.toRadians(currentAngle))) == false){
    		x = x - Math.sin(Math.toRadians(currentAngle));
    		y = y - Math.cos(Math.toRadians(currentAngle));
    	}
    	else {
    		
    		collisions++;
    	}
    }
    
    public void rotateRight(){
    	currentAngle -= 1;
    	for (int i = 0; i < 10; i++){
    		sight[i].rotateRight();
    	}
    }
    
    public void fire(){
    	game.controller.addBullet(new bullet((int)x, (int)y, currentAngle, type, 0.6f));
    	shotsFired++;
    }
    
    public void rotateLeft(){
    	currentAngle += 1;
    	for (int i = 0; i < 10; i++){
    		sight[i].rotateLeft();
    	}
    }
    
    public void paint(Graphics2D g) {
    	g.setColor(Color.blue);
        g.fillOval((int)x, (int)y, 30, 30);
        for (int i = 0; i < 10; i++){
        	sight[i].setLine();
        	sight[i].paint(g);
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 30, 30);
    }
    private boolean getCollision(double x, double y){
    	System.out.println(x + " " + y);
    	for (int i = 0; i < gameEngine.NUM_ROWS; i++) {
            for (int j = 0; j < gameEngine.NUM_COLS; j++) {
                if(game.tileMap[i][j].getBounds().intersects(new Rectangle((int)x, (int)y, 30, 30)) && game.tileMap[i][j].getType().equals("Barricade")){
                    return true;
                }
            }
        }
    	if (x >= 470 || x <= 0 || y >= 470 || y <= 0){
    		return true;
    	}
    	if (type.equals("crawler1")){
    		if(game.crawler2.getBounds().intersects(new Rectangle((int)x, (int)y, 30, 30))){
    			return true;
    		}
    	}
    	else if (type.equals("crawler2")){
    		if(game.crawler1.getBounds().intersects(new Rectangle((int)x, (int)y, 30, 30))){
    			return true;
    		}
    	}
    	return false;
    }
    
    public void tileCounter(){
    	for(int i = 0; i < 10; i++){
    		for(int j = 0; j < 10; j++){
    			if (game.tileMap[i][j].getType().equals("Floor") && getBounds().intersects(game.tileMap[i][j].getBounds())){
    				if(BoolGrid[i][j] == false){
    					IntGrid[i][j] += 1;
    					BoolGrid[i][j] = true;
    				}
    			}
    			else if (game.tileMap[i][j].getType().equals("Floor") && getBounds().intersects(game.tileMap[i][j].getBounds()) == false){
    				BoolGrid[i][j] = false;
    			}
    		}
    	}
    }
    
    public double getX(){
    	return x;
    }
    
    public double getY(){
    	return y;
    }
    
    public void removeHealth(int damage){
    	health -= damage;
    }
	
	
	
	public int getHealth() {
		return health;
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
	
	public float getPerformanceRating() {
		return performanceRating;
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
	
	
	public Crawler(int generation, int id, ANN ann) {
		
		this.generation = generation;
		this.id = id;
		this.ann = ann;
		resetCounters();		
	}
	
	
	
	public int getID() {
		return id;
	}
	
	public void setID(int agent_id) {
		id = agent_id;
	}
	
	public int getGeneration() {
		return generation;
	}
	
	public void setGeneration(int gen) {
		generation = gen;
	}
	
	public ANN getANN() {
		return ann;
	}
	
	public void setANN(ANN a) {
		ann = a;
	}
	
	public double getRating() {
		return rating;
	}
	
	public void setRating(double r) {
		rating = r;
	}
		
	
	


}