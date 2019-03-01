import org.jsfml.graphics.*;
public class Yeti extends NPC
{
	/**
	* Constructor of Yeti
	* @param xPixelsPerSecond The number of pixels the yeti should move per second in the x direction
	* @param yPixelsPerSecond The number of pixels the yeti should move per second in the y direction
	* @param r The rotation of the yeti
	* @param diffculty The diffculty of the game
	* @param background The background the yeti will be displayed on
	*/
	public Yeti(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/Death/enemy3.png",background);
		ID = "Yeti";
		type = new String[]{"electric","cryo"};
		defaultType = new String[]{"electric","cryo"};
		dropCount = 4;
		//Difficulty adjusments (diffculty paramter)		
		if(diffculty.equals("easy"))
		{
			health = 300;
			armour = 400;
		}
		else if(diffculty.equals("intermediate"))
		{
			health = 450;
			armour = 600;
		}
		else if(diffculty.equals("hard"))
		{
			health = 700;
			armour = 900;
		}
	}
}
