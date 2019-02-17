/**
 *
 * Class name: Actor
 *
 *
**/

//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.ArrayList;
import java.util.function.*;
//import java.io.InputStream;

//import org.jsfml.system.*;
//import org.jsfml.window.*;
//import org.jsfml.audio.*;
//import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
//import org.jsfml.graphics.Image;

public abstract class Actor {
	Drawable item;
	BiConsumer<Float, Float> setPosition;
	float xPosition;
	float yPosition;
	
	public void setLocation (Float x, Float y) {
		xPosition = x;
		yPosition = y;
		setPosition.accept((float) xPosition, (float) yPosition);
	}
	public void draw(RenderWindow w) {
		w.draw(item);
	}		
}