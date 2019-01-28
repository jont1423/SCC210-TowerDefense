public class Level {
	
	private int ID;
	private int rounds;
	private int currRound;
	
	public Level (int ID, int currRound){
		
		this.ID = ID;
		this.currRound = currRound;
	}
	
	public void advLvl(int adv){
		
		if(currRound < rounds){
			currRound = currRound + adv;
		}else{
			roundFinish();
		}
	}
	
	public void roundFinish(){
	
	}
}
