import org.jsfml.graphics.*;
/**
* Constructor of SpeedDemon
* @param xPixelsPerSecond The number of pixels the speed demon should move per second in the x direction
* @param yPixelsPerSecond The number of pixels the speed demon should move per second in the y direction
* @param r The rotation of the speed demon
* @param diffculty The diffculty of the game
* @param background The background the speed demon will be displayed on
*/
public class SpeedDemon extends NPC
{
	public SpeedDemon(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/Death/enemy2.png",background);
		ID = "SpeedDemon";
		type = new String[]{"warp"};
		defaultType = new String[]{"warp"};
		dropCount = 2;
		//Difficulty adjusments (diffculty paramter)
		if(diffculty.equals("easy"))
		{
			health = 75;
			armour = 50;
		}
		else if(diffculty.equals("intermediate"))
		{
			health = 100;
			armour = 150;
		}
		else if(diffculty.equals("hard"))
		{
			health = 150;
			armour = 215;
		}
	}
}
