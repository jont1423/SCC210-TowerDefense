//
//	SCC210 Example code
//
//		Andrew Scott, 2015
//
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class Game {
	private static int screenWidth  = 1024;
	private static int screenHeight = 768;

	//
	// The Java install comes with a set of fonts but these will
	// be on different filesystem paths depending on the version
	// of Java and whether the JDK or JRE version is being used.
	//
	private static String JavaVersion = 
		Runtime.class.getPackage( ).getImplementationVersion( );
	private static String JdkFontPath =
		"C:\\Program Files\\Java\\jdk" + JavaVersion +
		"\\jre\\lib\\fonts\\";
	private static String JreFontPath =
		"C:\\Program Files\\Java\\jre" + JavaVersion +
		"\\lib\\fonts\\";

	private static int fontSize     = 48;
	private static String FontFile  = "LucidaSansRegular.ttf";
	private static String ImageFile = "";

	private static String Title   = "Hello SCC210!";
	private static String Message = "Round and round...";

	private String FontPath;	// Where fonts were found

	private ArrayList<Actor> actors = new ArrayList<Actor>( );
	private boolean deployed = false;

	private abstract class Actor {
		Drawable obj;
		IntConsumer rotate;
		BiConsumer<Float, Float> setPosition;

		int x  = 0;	// Current X-coordinate
		int y  = 0;	// Current Y-coordinate

		int r  = 0;	// Change in rotation per cycle
		int dx = 5;	// Change in X-coordinate per cycle
		int dy = 5;	// Change in Y-coordinate per cycle

		//
		// Is point x, y within area occupied by this object?
		//
		// This should really be done with bounding boxes not points
		//
		boolean within (int x, int y) {
			// Should check object bounds here
			// -- we'd normally assume a simple rectangle
			//    ...and override as necessary
			return false;
		}

		//
		// Work out where object should be for next frame
		//
		void calcMove(int minx, int miny, int maxx, int maxy) {
			//
			// Add deltas to x and y position
			//
			x += dx;
			y += dy;

			//
			// Check we've not hit screen bounds
			//
			if (x <= minx || x >= maxx) { dx *= -1; x += dx; }
			if (y <= miny || y >= maxy) { dy *= -1; y += dy; }

			//
			// Check we've not collided with any other actor
			//
			for (Actor a : actors) {
				if (a.obj != obj && a.within(x, y)) {
					dx *= -1; x += dx;
					dy *= -1; y += dy;
				}
			}
		}

		//
		// Reposition the object
		//
		void performMove( ) {
			rotate.accept(r);
			setPosition.accept((float)x, (float)y);
		}

		//
		// Render the object at its new position
		//
		void draw(RenderWindow w) {
			w.draw(obj);
		}
	}

	private class Message extends Actor {
		private Text text;

		public Message(int x, int y, int r, String message, Color c) {
			//
			// Load the font
			//
			Font sansRegular = new Font( );
			try {
				sansRegular.loadFromFile(
						Paths.get(FontPath+FontFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}

			text = new Text (message, sansRegular, fontSize);
			text.setColor(c);
			text.setStyle(Text.BOLD | Text.UNDERLINED);

			FloatRect textBounds = text.getLocalBounds( );
			// Find middle and set as origin/ reference point
			text.setOrigin(textBounds.width / 2,
					textBounds.height / 2);

			this.x = x;
			this.y = y;
			this.r = r;

			//
			// Store references to object and key methods
			//
			obj = text;
			rotate = text::rotate;
			setPosition = text::setPosition;
		}
	}
	
	private class Image extends Actor {
		private Sprite img;

		public Image(int x, int y, int r, String textureFile) {
			//
			// Load image/ texture
			//
			Texture imgTexture = new Texture( );
			try {
				imgTexture.loadFromFile(Paths.get(textureFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}
			imgTexture.setSmooth(true);

			img = new Sprite(imgTexture);
			img.setOrigin(Vector2f.div(
					new Vector2f(imgTexture.getSize()), 2));

			this.x = x;
			this.y = y;
			this.r = r;

			//
			// Store references to object and key methods
			//
			obj = img;
			rotate = img::rotate;
			setPosition = img::setPosition;
		}
	}
	
	private class Rectangle extends Actor {
		private RectangleShape rectangle;


		public Rectangle(float x, float y, Color c,
				int transparency) {
			rectangle = new RectangleShape(new Vector2f(x,y));
			rectangle.setFillColor(new Color(c, transparency));
			rectangle.setOrigin(screenWidth, screenHeight);
			this.x = (int) x;
			this.y = (int) y;

			//
			// Store references to object and key methods
			//
			obj = rectangle;
			rotate = rectangle::rotate;
			setPosition = rectangle::setPosition;
		}

		//
		// Default method typically assumes a rectangle,
		// so do something a little different
		//
		@Override
		public boolean within (int px, int py) {
		// Check we've not collided with any other actor
		//
		//if(cursorPosition + selectedShapeSize/2 > a.x &&  cursorPosition - selectedShapeSize < a.x 
			for (Actor a : actors) 
			{
				if(a.equals(this)) continue; //Dont compare with self
				/*System.out.println("CursorX:"+ px + "CursorY" + py);
				System.out.println("Cursor+radius:"+ (px+radius) + "Cursor-radius" + (py-radius));
				System.out.println("ActorX:"+ a.x + "ActorY" + a.y);*/
				//Square collision
				if (px >= a.x && px <= a.x &&
						py  >= a.y && py <= a.y) {
					
					return true;//Red - cant place here
				}
			}
				return false;
		}
		
		@Override
		public void calcMove(int minx, int miny, int maxx, int maxy) {
		x = x;
		y = y;
		}
		
		public void setColor(Color c, int transparency) {
			rectangle.setFillColor(new Color(c, transparency));
		}
		
		public void calcMoveCursor(int newX, int newY) {
		x = newX;
		y = newY;
		}
	}
	
	private class Bubble extends Actor {
		private CircleShape circle;

		private int radius;

		public Bubble(int x, int y, int radius, Color c,
				int transparency) {
			circle = new CircleShape(radius);
			circle.setFillColor(new Color(c, transparency));
			circle.setOrigin(radius, radius);

			this.x = x;
			this.y = y;
			this.radius = radius;

			//
			// Store references to object and key methods
			//
			obj = circle;
			rotate = circle::rotate;
			setPosition = circle::setPosition;
		}

		//
		// Default method typically assumes a rectangle,
		// so do something a little different
		//
		@Override
		public boolean within (int px, int py) {
		// Check we've not collided with any other actor
		//
		//if(cursorPosition + selectedShapeSize/2 > a.x &&  cursorPosition - selectedShapeSize < a.x 
			for (Actor a : actors) 
			{
				if(a.equals(this)) continue; //Dont compare with self
				/*System.out.println("CursorX:"+ px + "CursorY" + py);
				System.out.println("Cursor+radius:"+ (px+radius) + "Cursor-radius" + (py-radius));
				System.out.println("ActorX:"+ a.x + "ActorY" + a.y);*/
				//Square collision
				if (px + radius >= a.x - radius && px - radius <= a.x + radius &&
						py + radius >= a.y - radius && py - radius <= a.y + radius) {
					
					return true;//Red - cant place here
				}
			}
				return false;
		}
		
		@Override
		public void calcMove(int minx, int miny, int maxx, int maxy) {
		x = x;
		y = y;
		}
		
		public void setColor(Color c, int transparency) {
			circle.setFillColor(new Color(c, transparency));
		}
		
		public void calcMoveCursor(int newX, int newY) {
		x = newX;
		y = newY;
		}
	}
	
	public void run ( ) {

		//
		// Check whether we're running from a JDK or JRE install
		// ...and set FontPath appropriately.
		//
		if ((new File(JreFontPath)).exists( )) FontPath = JreFontPath;
		else FontPath = JdkFontPath;

		//
		// Create a window
		//
		RenderWindow window = new RenderWindow( );
		ContextSettings settings = new ContextSettings(8);
		window.create(new VideoMode(screenWidth, screenHeight),
				"Testing",
				WindowStyle.DEFAULT,
				settings);
		boolean collision = true;
		

		window.setFramerateLimit(60); // Avoid excessive updates
		//
		// Create some actors
		//
		actors.add(new Bubble(300,300, 20, Color.RED,   128));
		Bubble b = new Bubble(300,300, 20, Color.BLACK,   50); //This will probably change as the cursor wont always have a circle selected
		b.circle.setOutlineThickness(2.0f);
		actors.add(b);
		actors.add(new Bubble(300,200, 20, Color.CYAN,   128));
		Rectangle r = new Rectangle(screenWidth,screenHeight*0.9f, Color.TRANSPARENT,   0); //Could just change to a line much simpler
		actors.add(r);
		r.rectangle.setOutlineThickness(1.5f);
		r.rectangle.setOutlineColor(Color.BLACK);

		//
		// Main loop
		//
		while (window.isOpen( )) {
			// Clear the screen
		window.clear(Color.WHITE);

			// Move all the actors around
			for (Actor actor : actors) {
				actor.calcMove(0, 0, screenWidth, screenHeight);
				actor.performMove( );
				actor.draw(window);						
			}
			
			// Update the display with any changes
			window.display( );

			// Handle any events
			for (Event event : window.pollEvents( )) {
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close( );
				}
				//if(!r.within(event.asMouseEvent().position.x,event.asMouseEvent().position.y)) //System.out.println("HELLO"); //<----THIS
				if (event.type == Event.Type.MOUSE_MOVED) {
				
					//Mapping is no longer necessary as its 1:1
					b.calcMoveCursor(event.asMouseEvent().position.x, event.asMouseEvent().position.y);
					
					if(b.within(b.x,b.y)) //This needs to be removed
					{
						b.circle.setOutlineColor(Color.RED);
						collision = true;
					}
					else
					{
						b.circle.setOutlineColor(Color.GREEN);
						collision = false;
					}
				}
				//Draws a ball at the cursors position
			
				if(event.type == Event.Type.MOUSE_BUTTON_RELEASED){
					if(event.asMouseButtonEvent().button == Mouse.Button.LEFT)
					{

						if(actors.indexOf(b) !=-1 && !collision)  actors.add(new Bubble((int) event.asMouseEvent().position.x, (int) event.asMouseEvent().position.y, 20, Color.YELLOW,  128));
						//if(!collision) actors.remove(b);
						if(b.within(b.x,b.y)) //If cursor is not holding anything, check if the cursor selects any actors????
						{
							b.circle.setOutlineColor(Color.RED);
							collision = true;
						}
						else
						{
							b.circle.setOutlineColor(Color.GREEN);
							collision = false;
						}
					}
					else if(event.asMouseButtonEvent().button == Mouse.Button.RIGHT)
					{
						if(actors.indexOf(b) ==-1) 
						{
							actors.add(b);
							b.calcMoveCursor((int) event.asMouseEvent().position.x, (int) event.asMouseEvent().position.y);
						}
					}

				}
			}

		}
		
	}
	
	

	public static void main (String args[ ]) {
		Game g = new Game( );
		g.run( );
	}
}	
