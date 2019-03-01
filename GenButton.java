/**
 *
 * Class name: GenButton
 *
 * Author: Jordan Young
**/

import org.jsfml.system.*;
import org.jsfml.graphics.*;

//GenButton class allow generating rectangular and circle buttons
public class GenButton extends Actor {
	private CircleShape circle;
	private RectangleShape rectangle;
	private int radius;
		
	/**
	 *
	 *@param	radius as the integer variable for setting shape radius
	 *@param	c the color instance variable from jsfml library
	 *@param	opacity the integer variable for transparency
	 *
	**/
	//Constructor for Circle Shape
	public GenButton(int radius, Color c, int opacity) {
		circle = new CircleShape(radius);
		circle.setFillColor(new Color(c, opacity));
		circle.setOrigin(radius, radius);
			
		obj = circle;
		setPosition = circle :: setPosition;
	}
		
	/**
	 *
	 *@param	x as the integer variable for shape length
	 *@param	y as the integer variable for shape height
	 *@param	c the color instance variable from jsfml library
	 *@param	opacity the integer variable for transparency
	 *
	**/
	//Constructor for Rectangle Shape
	public GenButton(int x, int y, Color c, int opacity) {
		rectangle = new RectangleShape(new Vector2f(x, y));
		rectangle.setFillColor(new Color(c, opacity));
		rectangle.setOrigin(0, 0);
			
		obj = rectangle;
		setPosition = rectangle :: setPosition;
	}
	
	void calcMove(int minx, int miny, int maxx, int maxy, float time) {}
	
	/**
	 *
	 *@param	c the color instance variable from jsfml library
	 *@param	opacity the integer variable for transparency
	 *
	**/
	public void setCirColor(Color c, int opacity) {
		circle.setFillColor(new Color(c, opacity));
	}
		
	/**
	 *
	 *@param	c the color instance variable from jsfml library
	 *@param	opacity the integer variable for transparency
	 *
	**/
	public void setRectColor(Color c, int opacity) {
		rectangle.setFillColor(new Color(c, opacity));
	}
	
	/**
	 *
	 *@return	Return a Transform instance value 
	 *
	**/
	public Transform getCirTransformedDimension() {
		Transform value = circle.getInverseTransform();
		return value;
	}
		
	/**
	 *
	 *@return	Return a Transform instance value 
	 *
	**/
	public Transform getRectTransformedDimension() {
		Transform value = rectangle.getInverseTransform();
		return value;
	}
		
	/**
	 *
	 *@return	Return a float value of radius
	 *
	**/
	public float getCirRadius() {
		float value = circle.getRadius();
		return value;
	}
		
	/**
	 *
	 *@return	Return a Vector2f instance value for shape dimensions x and y
	 *
	**/
	public Vector2f getRectDimensions() {
		Vector2f value = rectangle.getSize();
		return value;
	}
	
	/**
	 *
	 *@return	Return a Vector2f instance value for shape position
	 *
	**/	
	public Vector2f getCirPosition() {
		Vector2f value = circle.getPosition();
		return value;
	}
		
	/**
	 *
	 *@return	Return a Vector2f instance value for shape position
	 *
	**/
	public Vector2f getRectPosition() {
		Vector2f value = rectangle.getPosition();
		return value;
	}
		
	/**
	 *
	 *@param 	Vector2f variable for shape position
	 *@param 	Vector2f variable for shape dimension
	 *@param 	Vector2i variable for cursor real time coordinates
	 *@return	Return a boolean value for checking cursor on object true/false
	 *
	**/
	public boolean detectPos(Vector2f position, Vector2f dimension, Vector2i mousePos) {
		boolean onButton = false;
		if (mousePos.x >= position.x && mousePos.x <= (position.x + dimension.x) && mousePos.y >= position.y && mousePos.y <= (position.y + dimension.y))
			onButton = true;
		return onButton;
	}
}