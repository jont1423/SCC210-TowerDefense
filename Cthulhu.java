import org.jsfml.graphics.*;
/**
* Constructor of Cthulhu
* @param xPixelsPerSecond The number of pixels the cthulhu should move per second in the x direction
* @param yPixelsPerSecond The number of pixels the cthulhu should move per second in the y direction
* @param r The rotation of the cthulhu
* @param diffculty The diffculty of the game
* @param background The background the cthulhu will be displayed on
*/
public class Cthulhu extends NPC
{
	public Cthulhu(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/Death/enemy6.png",background);
		ID = "Cthulhu";
		type = new String[]{"electric","cryo","laser"};
		defaultType = new String[]{"electric","cryo","laser"};
		dropCount = 5;
		//Difficulty adjusments (diffculty paramter)		
		if(diffculty.equals("easy"))
		{
			health = 650;
			armour = 650;
		}
		else if(diffculty.equals("intermediate"))
		{
			health = 1250;
			armour = 1250;
		}
		else if(diffculty.equals("hard"))
		{
			health = 2000;
			armour = 2000;
		}
	}
}
