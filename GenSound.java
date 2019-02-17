/**
 *
 * Class name: GenSound
 *
 *
**/

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;
import java.io.InputStream;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.audio.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.graphics.Image;

public class GenSound extends Actor {
	private Music sound;
		
	public GenSound (String file) {
		sound = new Music();
		try {sound.openFromFile(Paths.get(file));}
		catch (IOException e) {System.out.println("Couldn't open soundtrack.");}
	}
	
	public void playSound() {
		sound.play();
	}
	
	public void stopSound() {
		sound.stop();
	}
	
	public void loop(boolean status) {
		sound.setLoop(status);
	}
}