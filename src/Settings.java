public class Settings {
	
	private int volume;
	private String diff;
	
	public Settings(){
		
		volume = 5; 
		diff = new String("med");
		
		Level l = new Level(0, 0);
	}
	
	public void setDiff(String diff){
	
		this.diff = diff;
	}
	
	public void setVol(int volume){
		
		this.volume = volume;
	}
	
	public String getDiff(){
		
		return diff;
	}
	
	public int getVol(){
		
		return volume;
	}
	
	
}
