public class Level extends Save{
	
	private int ID;
	private int rounds;
	private int currRound;
	private String diff;
	
	public Level (int ID, int currRound, String diff){
		
		this.ID = ID;
		this.currRound = currRound;
		this.diff = diff;
		
		
		advRound(1);
	}
	
	public void advRound(int adv){
		
		if(currRound < rounds){
			currRound = currRound + adv;
			saveFile(ID, currRound, diff);
		}else{
			roundFinish();
		}
	}
	
	public void roundFinish(){
		saveFile(ID+1, 1, diff);
	}
	
	public void gameOver(){
		
	}
}
