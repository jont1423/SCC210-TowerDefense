import java.io.*;
import java.util.*;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class Save {
	
	private String diff;
	private String saveFile = "save.txt";
	private int currLvl, currRound;
	private RenderWindow r;
	
	public Save(){
			readFile();
		
	}
	
	public int getCurrLvl()
	{
		return currLvl;
	}
	
	public int getCurrRound()
	{
		return currRound;
	}
	
	public String getDiff()
	{
		return diff;
	}
	
	public void setDiff(String diff){
	
		this.diff = diff;
		saveFile(currLvl, currRound, diff);
	}
	
	public void newGame(String diff){
		
		currLvl = 5;
		currRound = 1;
		
		setDiff("easy");
				
	}
	
	//Load the save and continue the game from the previous Level and round
	public void contGame(){
		
		if ((currLvl < 1) || (currRound < 1) || (diff.isEmpty())){
			System.out.println("Corrupt save file - new save reset");
			currLvl = 1;
			currRound = 1;
			diff.equals("easy");
		}
			
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
