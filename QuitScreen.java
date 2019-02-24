

public class QuitScreen{
	private static String ImageFile = "quitScreen.png";
	private static ImageAct quitScreenWindow;
	private static GenButton rects[];
	
	public QuitScreen() {
		buttonBox = new GenButton[2];
		
		for (int i=0; i<2; i++)
			buttonBox[i] = new GenButton(90, 30, Color.RED, 500);

		buttonBox[0].setLocation((float) 140, (float) 140);
		buttonBox[1].setLocation((float) 288, (float) 140);
	}
}
