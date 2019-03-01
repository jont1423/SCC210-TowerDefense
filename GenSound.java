/**
 *
 * Class name: GenSound
 *
 *
**/

import java.io.IOException;
import java.nio.file.*;
import org.jsfml.audio.*;

public class GenSound{
	private Music sound;
		
	/**
	 *
	 * @param	file the String variable that pass the path of file
	 * Constructor to initiate the Music instance with the sound file
	 *
	**/
	public GenSound (String file) {
		sound = new Music();
		try {sound.openFromFile(Paths.get(file));}
		catch (IOException e) {System.out.println("Couldn't open soundtrack.");}
	}
	
	/**
	 *
	 *	Play sound method
	 *
	**/
	public void playSound() {
		sound.play();
	}
	
	/**
	 *
	 *	Stop sound method
	 *
	**/
	public void stopSound() {
		sound.stop();
	}
	
	/**
	 *
	 *	@param 	status the boolean variable to set looping to true/false
	 *	loop sound method
	 *
	**/
	public void loop(boolean status) {
		sound.setLoop(status);
	}
}