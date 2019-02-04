public class Level extends Save{
	
	private int ID;
	private int rounds;
	private int currRound;
	private String diff;
	
	public Level (int ID, int currRound, String diff){
		
		this.ID = ID;
		this.currRound = currRound;
		this.diff = diff;
		
		System.out.println(ID+" "+currRound);
		nextLvl(ID, currRound, diff);
	}
	
	public void advRound(int adv){
		
		if(currRound < rounds){
			currRound = currRound + adv;
		}else{
			roundFinish();
		}
	}
	
	public void roundFinish(){
	
	}
}
