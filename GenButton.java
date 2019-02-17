/**
 *
 * Class name: GenButton
 *
 *
**/

//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.function.*;
//import java.io.InputStream;

import org.jsfml.system.*;
//import org.jsfml.window.*;
//import org.jsfml.audio.*;
//import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
//import org.jsfml.graphics.Image;

//GenButton class allow generating rectangular and circle buttons
public class GenButton extends Actor {
	private CircleShape circle;
	private RectangleShape rectangle;
	private int radius;
		
	//Constructor for Circle Shape
	public GenButton(int radius, Color c, int opacity) {
		circle = new CircleShape(radius);
		circle.setFillColor(new Color(c, opacity));
		circle.setOrigin(radius, radius);
			
		item = circle;
		setPosition = circle :: setPosition;
	}
		
	//Constructor for Rectangle Shape
	public GenButton(int x, int y, Color c, int opacity) {
		rectangle = new RectangleShape(new Vector2f(x, y));
		rectangle.setFillColor(new Color(c, opacity));
		rectangle.setOrigin(0, 0);
			
		item = rectangle;
		setPosition = rectangle :: setPosition;
	}
		
	public void setCirColor(Color c, int opacity) {
		circle.setFillColor(new Color(c, opacity));
	}
		
	public void setRectColor(Color c, int opacity) {
		rectangle.setFillColor(new Color(c, opacity));
	}
		
	public Transform getCirTransformedDimension() {
		Transform value = circle.getInverseTransform();
		return value;
	}
		
	public Transform getRectTransformedDimension() {
		Transform value = rectangle.getInverseTransform();
		return value;
	}
		
	public float getCirRadius() {
		float value = circle.getRadius();
		return value;
	}
		
	public Vector2f getRectDimensions() {
		Vector2f value = rectangle.getSize();
		return value;
	}
		
	public Vector2f getCirPosition() {
		Vector2f value = circle.getPosition();
		return value;
	}
		
	public Vector2f getRectPosition() {
		Vector2f value = rectangle.getPosition();
		return value;
	}
		
	public boolean detectPos(Vector2f position, Vector2f dimension, Vector2i mousePos) {
		boolean onButton = false;
		if (mousePos.x >= position.x && mousePos.x <= (position.x + dimension.x) && mousePos.y >= position.y && mousePos.y <= (position.y + dimension.y))
			onButton = true;
		return onButton;
	}
}