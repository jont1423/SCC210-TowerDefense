import java.io.*;
import java.util.*;

public class Save {
	
	private String diff;
	private String saveFile = "save.txt";
	private int currLvl, currRound;
	
	public Save(){
		
	}
	
	//Change the difficulty
	public void setDiff(String diff){
	
		this.diff = diff;
		saveFile(currLvl, currRound, diff);
	}
	
	//Start a new game
	public void newGame(String diff){
		
		currLvl = 1;
		currRound = 1;
		
		this.diff = diff;
		
		Level l = new Level(currLvl, currRound, diff);
		
	}
	
	//Load the save and continue the game from the previous level and round
	public void contGame(){
		
		readFile();
		
		if ((currLvl < 1) || (currRound < 1) || (diff.isEmpty())){
			System.out.println("Corrupt save file - new save reset");
			currLvl = 1;
			currRound = 1;
			diff.equals("easy");
		}
		
		Level l = new Level(currLvl, currRound, diff);
		
	}
	
	//Saves the current values to the .txt file
	public void saveFile(int currLvl, int currRound, String diff){
	
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
	
	//Reads the values from the save.txt file and updates the local variables
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
