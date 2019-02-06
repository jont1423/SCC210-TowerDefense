public class Level extends Save{
	
	private int ID;	//The current level ID 
	private int rounds;	//The total rounds of the current level
	private int currRound;	//Current round
	private String diff;	//Difficulty
	
	public Level (int ID, int currRound, String diff){
		
		this.ID = ID;
		this.currRound = currRound;
		this.diff = diff;
		
		//Determining the amount of rounds depending on the difficulty * the current level
		
		if (diff.equals ("easy")){			//Easy Difficulty 
			rounds = ID *2;
			
		}else if (diff.equals ("medium")){	//Medium Difficulty 
			rounds = ID *3;
			
		}else {								//Hard Difficulty
			rounds = ID *4;
		}
		
		//advRound(1);//method call when round is completed
	}
	
	//Advance to the next round, int adv is the amount to advance by
	public void advRound(int adv){
		
		if(currRound < rounds){
			currRound = currRound + adv;
			saveFile(ID, currRound, diff);
		}else{
			levelFinish();
		}
	}
	
	//Method called at the end of the level to save the game, terminate the current instance of Level after this is called
	public void levelFinish(){
		saveFile(ID+1, 1, diff);
	}
	
	//Player loses, restart level from round 1
	public void gameOver(){
		saveFile(ID, 1, diff);
	}
}
