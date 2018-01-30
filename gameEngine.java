

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.SimulationModel;
import controlUnit.ControlUnit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class gameEngine extends JPanel implements Runnable  {

	public Crawler crawler1;
	
	public Crawler crawler2;
	
	bulletController controller = new bulletController(this);
	public static final int NUM_COLS = 10;
	public static final int NUM_ROWS = 10;
	public static final int PREFERRED_GRID_SIZE_PIXELS = 50;
	public tile[][] tileMap;
	Random rand = new Random();
	
	int randomo;
	private SimulationModel sm;
	
	private boolean playerMode;
	protected boolean gameInit = false;
	protected boolean runThread = true;
	protected int timeCounter;
	protected int time;

	private int c1Wins = 0;
	private int c2Wins = 0;
	private int draws = 0;
	int[][] mapArray1 ={{1,1,1,1,1,1,1,1,1,1},
			   {1,0,0,0,0,0,0,0,0,1},
			   {1,0,0,0,0,0,0,0,0,1},
			   {1,0,0,0,1,1,1,0,0,1},
			   {1,0,0,0,0,0,0,0,0,1},
			   {1,0,0,0,0,0,0,0,0,1},
			   {1,0,0,0,0,0,0,0,0,1},
			   {1,0,0,0,0,0,0,0,0,1},
			   {1,0,0,0,0,0,0,0,0,1},
			   {1,1,1,1,1,1,1,1,1,1}};

	int[][] mapArray2 ={{0,0,0,0,0,0,0,0,0,0},
	   		   {0,0,0,0,0,0,0,0,0,0},
	           {0,0,0,0,0,0,0,0,0,0},
	           {0,0,0,0,0,0,0,0,0,0},
	           {0,0,0,0,0,0,0,0,0,0},
	           {0,0,0,0,0,0,0,0,0,0},
	           {0,0,0,0,0,0,0,0,0,0},
	           {0,0,0,0,0,0,0,0,0,0},
	           {0,0,0,0,0,0,0,0,0,0},
	           {0,0,0,0,0,0,0,0,0,0}};

	public void run() {
		
		revalidate();
		repaint();
		initGame();
		refreshDisplay();
		
	}
	
	public gameEngine(SimulationModel sm) {

		this.sm = sm;
		
		playerMode = false;		

		this.setPreferredSize(new Dimension(NUM_COLS * PREFERRED_GRID_SIZE_PIXELS, NUM_ROWS * PREFERRED_GRID_SIZE_PIXELS));
		
		setTiles();
	
		setFocusable(true);
		/*if (Math.min(crawler1.getPerformanceEvalue(), crawler2.getPerformanceEvalue()) >= certainValue){
		for (int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++){
				x = i * rectWidth;
				y = j * rectHeight;
				this.tileMap[i][j] = new tile(mapArray1[i][j]);
				this.tileMap[i][j].setDetails(x, y, rectHeight, rectWidth);
			}
		}
		}	
		else if (Math.min(crawler1.getPerformanceEvalue(), crawler2.getPerformanceEvalue()) < certainValue){
			for (int i = 0; i < NUM_ROWS; i++){
				for (int j = 0; j < NUM_COLS; j++){
					x = i * rectWidth;
					y = j * rectHeight;
					this.tileMap[i][j] = new tile(mapArray2[i][j]);
					this.tileMap[i][j].setDetails(x, y, rectHeight, rectWidth);
				}
			}
		}*/
	}

	
	gameEngine(boolean pm) {
		playerMode = pm;
		this.setPreferredSize(new Dimension(NUM_COLS * PREFERRED_GRID_SIZE_PIXELS, NUM_ROWS * PREFERRED_GRID_SIZE_PIXELS));
	}	
	
	public boolean isInitialised() {
		return gameInit;		
	}
	
	public void endThread() {
		runThread = false;
	}
	
	public void initGame() {
		gameInit = false;
		
		time = 180;
		timeCounter = 0;
		ControlUnit cu = new ControlUnit();	
		Crawler[] crawlers = cu.createAgents(2, Crawler.INPUTS, Crawler.OUTPUTS, sm.getNHL(), sm.getNPL());
		crawler1 = crawlers[0];
		crawler2 = crawlers[1];
		crawler1.setGame(this);
		crawler2.setGame(this);
		
		int randomNum = 0;
		this.tileMap = new tile[NUM_ROWS][NUM_COLS];
		for (int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++){
				randomNum = rand.nextInt(5);
				this.tileMap[i][j] = new tile(randomNum);
				tileMap[i][j].setDetails(i * 50, j * 50, 50, 50);
			}
		}

		gameInit = true;
		
		revalidate();
		repaint();
		
	}
	


	

	
	
	
	public void reset() {
		time = 180;
		timeCounter = 0;
						
	}
	
	public int getTime() {
		return time;
	}
	
	
	/*public void setTiles() {
		
		int randomNum = 0;
		this.tileMap = new tile[NUM_ROWS][NUM_COLS];
//		int rectWidth = getWidth() / NUM_COLS;
//        int rectHeight = getHeight() / NUM_ROWS;
		for (int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++){
//                int x = i * rectWidth;
//                int y = j * rectHeight;				
				randomNum = rand.nextInt(5);
				this.tileMap[i][j] = new tile(randomNum);
	//			tileMap[i][j].setDetails(x, y, rectHeight, rectWidth);
			}
		}

		
	}*/
	

	
	public boolean gameOver() {
		if(crawler1.getHealth() <= 0 || 
				crawler2.getHealth() <= 0 || time <= 0)
			return true;
		else
			return false;
		
	}
	


	public void refreshDisplay() {

		int sleep = 10;
		
		while(runThread) {
		
			if(gameInit) {
			
				if(timeCounter == 100){
					updateTimer();
					timeCounter = 0;
				}
				else {
					timeCounter += sleep;
				}
			
				if(gameOver()){
					ControlUnit cu = new ControlUnit();
					cu.submitGameResult(this);
					initGame();
				}
						
				move();
			
			}	
				
			revalidate();
			repaint();
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void updateTimer() {
		
		if(time != 0) {
		
			time--;
		}
	}
		
	
	public void move(){
		crawler1.evaluateInputs();
		crawler2.evaluateInputs();
		controller.move();
	}
	

	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int rectWidth = getWidth() / NUM_COLS;
        int rectHeight = getHeight() / NUM_ROWS;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                g.setColor(tileMap[i][j].getColour());
                tileMap[i][j].paint(g2d);
            }
        }
        g.setColor(Color.blue);
        
        if(gameInit == true) {
        	g.setColor(Color.blue);
        	crawler1.paint(g2d);
        	g.setColor(Color.blue);
        	crawler2.paint(g2d);
        	g.setColor(Color.black);
        	controller.paint(g2d);
        }	
	}
	
	public float getC1ShotAccuracy() {
		return (float)(100 * crawler1.getShotsOnTarget()) / crawler1.getShotsFired();
	}

	public float getC2ShotAccuracy() {
		return (float)(100 * crawler2.getShotsOnTarget()) / crawler2.getShotsFired();		
	}


	public int getC1Wins() {
		return c1Wins;
	}
	
	public int getC2Wins() {
		return c2Wins;
	}

	public int getDraws() {
		return draws;
	}
	
	public void incrementC1Wins() {
		c1Wins++;
	}

	public void incrementC2Wins() {
		c2Wins++;
	}


	public void incrementDraws() {
		draws++;
	}
	
}



