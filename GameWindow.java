//
//	GameWindow Class
//
//	Author: Jordan Young
//

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

public class GameWindow {
	private static int screenWidth = 1024;
	private static int screenHeight = 768;

	//
	// The Java install comes with a set of fonts but these will
	// be on different filesystem paths depending on the version
	// of Java and whether the JDK or JRE version is being used.
	//
	private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
				
	private static String Title = "Tower Defense";
	private static String ImageFile = "Wallpaper.png";
	private static String SoundFile = "BGM.wav";
	private static String ButtonFile[] = {"playButton.png", "levelButton.png", "exitButton.png"};
	private static ImageAct wallpaperIMG, ButtonsIMG[];
	private static GenSound BGM, sound1;
	private float buttonX = 50, buttonY = 0, adjustX, adjustY, mouseLocX, mouseLocY;
	private static GenButton rect0, rect1, rect2;
	private static Mouse mouseMov;
	
	public abstract class Actor {
		Drawable item;
		BiConsumer<Float, Float> setPosition;
		float xPosition;
		float yPosition;
	
		void setLocation (Float x, Float y) {
			xPosition = x;
			yPosition = y;
			setPosition.accept((float) xPosition, (float) yPosition);
		}
		void draw(RenderWindow w) {
			w.draw(item);
		}
		
	}
	
	public class ImageAct extends Actor {
		private Sprite image;
		public ImageAct(String textureFile) {
			Texture imgTexture = new Texture();
			try {imgTexture.loadFromFile(Paths.get(textureFile));}
			catch (IOException e) {System.out.println("Couldn't read image.");}
			
			imgTexture.setSmooth(true);
			image = new Sprite(imgTexture);

			//this.xPosition = x;
			//this.yPosition = y;
			
			item = image;
			setPosition = image :: setPosition;
			//setPosition.accept(x, y);
		}
	}
	
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
		
		public boolean checkClick(Vector2f position, Vector2f dimension) {
			boolean onButton = false;
			if (mouseLocX >= position.x && mouseLocX <= (position.x + dimension.x) && mouseLocY >= position.y && mouseLocY <= (position.y + dimension.y))
				onButton = true;
			return onButton;
		}
	}
	
	public class GenSound extends Actor {
		private Music sound;
		
		public GenSound (String file) {
			sound = new Music();
			try {sound.openFromFile(Paths.get(file));}
			catch (IOException e) {System.out.println("Couldn't open soundtrack.");}
		}
		void playSound() {
			sound.play();
		}
		void stopSound() {
			sound.stop();
		}
		void loop(boolean status) {
			sound.setLoop(status);
		}
	}
	
	public void run () {
		System.out.println("ButtonFile array length: " + ButtonFile.length);
		
		// Create a window
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(screenWidth, screenHeight), Title, WindowStyle.CLOSE);

		window.setFramerateLimit(30); // Avoid excessive updates

		// Create some actors
		BGM = new GenSound(SoundFile);
		wallpaperIMG = new ImageAct(ImageFile);
		ButtonsIMG = new ImageAct[ButtonFile.length];
		for (int i=0; i<ButtonFile.length; i++)
			ButtonsIMG[i] = new ImageAct(ButtonFile[i]);
		
		ButtonsIMG[0].setLocation(buttonX, buttonY);
		adjustX = 20; adjustY = -80;
		ButtonsIMG[1].setLocation(buttonX - adjustX, buttonY + adjustY);
		adjustX = 0; adjustY = 100;
		ButtonsIMG[2].setLocation(buttonX - adjustX, buttonY + adjustY);
		
		rect0 = new GenButton(199, 80, Color.TRANSPARENT, 0);
		rect1 = new GenButton(215, 63, Color.TRANSPARENT, 0);
		rect2 = new GenButton(140, 50, Color.TRANSPARENT, 0);
		rect0.setLocation((float) 445, (float) 432);
		rect1.setLocation((float) 440, (float) 540);
		rect2.setLocation((float) 475, (float) 632);
		
		BGM.loop(true);
		BGM.playSound();

		// Main loop
		while (window.isOpen()) {
			// Clear the screen
			window.clear(Color.WHITE);
			
			wallpaperIMG.draw(window);
			for (ImageAct buttonsIMG: ButtonsIMG)
				buttonsIMG.draw(window);
			
			rect0.draw(window);
			rect1.draw(window);
			rect2.draw(window);
			
			// Update the display with any changes
			window.display();
		
			mouseLocX = mouseMov.getPosition(window).x;
			mouseLocY = mouseMov.getPosition(window).y;
		
			//System.out.println(mouseMov.getPosition(window));
			//System.out.println(rect0.getRectDimensions().x);
			//System.out.println(rect0.getRectPosition().x);

			// Handle any events
			for (Event event : window.pollEvents()) {
				
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close();
				}
				if (rect0.checkClick(rect0.getRectPosition(), rect0.getRectDimensions()) == true)
					rect0.setRectColor(Color.MAGENTA, 40);
				else {rect0.setRectColor(Color.TRANSPARENT, 0);}
				
				//float mouseLocX = mouseMov.getPosition(window).x;
				//float mouseLocY = mouseMov.getPosition(window).y;
				//if (mouseMov.getPosition(window).x >= 445 && mouseMov.getPosition(window).x <= 644 && mouseMov.getPosition(window).y >= 432 && mouseMov.getPosition(window).y <= 512) {
				
				//if (mouseMov.isButtonPressed(Mouse.Button.LEFT) && mouseLocX >= 445 && mouseLocX <= 644 && mouseLocY >= 432 && mouseLocY <= 512) {
					//System.out.println("On playButton!");
					//rect0.setRectColor(Color.MAGENTA, 40);
				//}
				//else {rect0.setRectColor(Color.TRANSPARENT, 0);}
				
			}
		}
	}

	public static void main (String args[]) {
		GameWindow g = new GameWindow();
		g.run();
	}
}	
