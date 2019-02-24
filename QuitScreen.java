

public class QuitScreen{
	private static String ImageFile = "quitScreen.png";
	private static ImageAct quitScreenWindow;
	private static GenButton buttons[];
	
	public QuitScreen() {
		quitScreenWindow = new ImageAct(ImageFile);
		buttons = new GenButton[2];
		
		for (GenButton button: buttons)
			buttons[i] = new GenButton(90, 30, Color.RED, 500);

		quitScreenWindow.setLocation((float) 256, (float) 288);
		buttons[0].setLocation((float) 140, (float) 140);
		buttons[1].setLocation((float) 288, (float) 140);
	}
	
	public void display(RenderWindow window) {
		quitScreenWindow.draw(window);
		for (GenButton button: buttons)
			button.draw(window);
	}
	
	public boolean yesEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (buttons[0].detectPos(buttons[0].getRectPosition(), buttons[0].getRectDimensions(), mouseLoc)) {
			detected = true;
			buttons[0].setRectColor(Color.TRANSPARENT, 0);
		}
		else buttons[0].setRectColor(Color.GREEN, 500);

		return detected;
	}
	
	public boolean noEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (buttons[1].detectPos(buttons[1].getRectPosition(), buttons[1].getRectDimensions(), mouseLoc)) {
			detected = true;
			buttons[1].setRectColor(Color.TRANSPARENT, 0);
		}
		else buttons[1].setRectColor(Color.RED, 500);
		
		return detected;
	}
}
