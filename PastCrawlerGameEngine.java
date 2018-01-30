


public class PastCrawlerGameEngine extends gameEngine {

	
	public PastCrawlerGameEngine(Crawler c1, Crawler c2) {

		super(false);		
		
		crawler1 = c1;
		
		crawler2 = c2;
	
		initGame();	

		setFocusable(true);
				
	}
	
	
	public boolean gameOver() {
		if(crawler1.getHealth() <= 0 || 
				crawler2.getHealth() <= 0 || time <= 0) {
			
			if(crawler1.getHealth() > crawler2.getHealth())
			
				incrementC1Wins();
				
			else if(crawler1.getHealth() > crawler2.getHealth())
				
				incrementC2Wins();
			
			else if(crawler1.getHealth() == crawler2.getHealth())
			
				incrementDraws();
				
			return true;
		}
			
		else
			return false;
		
	}
	
	
	public void initGame() {
		
		gameInit = false;
		
		time = 180;
		timeCounter = 0;
				
		crawler1.setGame(this);
		crawler2.setGame(this);
		
		crawler1.resetCounters();
		crawler2.resetCounters();
		
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
	
}
