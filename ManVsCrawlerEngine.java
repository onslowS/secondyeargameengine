

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controlUnit.ControlUnit;

public class ManVsCrawlerEngine extends gameEngine {

	public crawlerPlayer crawler1;
	
	public ManVsCrawlerEngine(Crawler opponent) {
		super(true);
		
		crawler1 = new crawlerPlayer(this, "Crawler2", 300, 300);
		
		crawler2 = opponent;

		crawler2.setGame(this);
		
		initGame();
	

		addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				crawler1.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				crawler1.keyPressed(e);
			}
		});
		setFocusable(true);

		
		
		// TODO Auto-generated constructor stub
	}
	
	

	public void initGame() {
		
		gameInit = false;
		
		time = 180;
		timeCounter = 0;
			
		int randomNum = 0;
		this.tileMap = new tile[NUM_ROWS][NUM_COLS];
		for (int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++){
				randomNum = rand.nextInt(5);
				this.tileMap[i][j] = new tile(randomNum);
			}
		}

		gameInit = true;
		
		revalidate();
		repaint();
		
	}
	
	
	public void refreshDisplay() {

		int sleep = 10;
		
		while(runThread) {
			
			crawler1.movePlayer();
			move();
			revalidate();
			repaint();
			
			if(gameOver()){
				initGame();
			}
			
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public float getC1ShotAccuracy() {
		return (float)(100 * crawler1.getShotsOnTarget()) / crawler1.getShotsFired();
	}

	public float getC2ShotAccuracy() {
		return (float)(100 * crawler2.getShotsOnTarget()) / crawler2.getShotsFired();		
	}

	
	
	public boolean gameOver() {
		if (crawler1.getHealth() <= 0 || 
				crawler2.getHealth() <= 0)
			return true;
		else
			return false;
		
	}

	public void move(){
		crawler2.evaluateInputs();
		controller.move();
	}	
	
}
