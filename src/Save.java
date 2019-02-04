import java.io.*;
import java.util.*;

public class Save {
	
	private String diff;
	private String saveFile = "save.txt";
	private int currLvl, currRound;
	
	public Save(){
		
	}
	
	public void setDiff(String diff){
	
		this.diff = diff;
	}
	
	public void newGame(String diff){
		
		currLvl = 1;
		currRound = 1;
		
		this.diff = diff;
		
		Level l = new Level(currLvl, currRound, diff);
		
	}
	
	public void contGame(){
		
		readFile();
		
		Level l = new Level(currLvl, currRound, diff);
		
	}
	
	public void nextLvl(int currLvl, int currRound, String diff){
	
		try{
			
			PrintWriter pW = new PrintWriter(saveFile);
			
			pW.println(currLvl);
			pW.println(currRound);
			pW.println(diff);
			
			pW.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Error opening save file: " + saveFile);
		}
	
	}
	
	private void readFile(){
	
		try{
			
			Scanner sc = new Scanner(new File(saveFile));
			
			currLvl = Integer.parseInt(sc.nextLine());
			currRound = Integer.parseInt(sc.nextLine());
			diff = sc.nextLine();
			
		}
		catch(FileNotFoundException e){
			
			System.out.println("Error opening save file: " + saveFile);
		}
	
	}
}
