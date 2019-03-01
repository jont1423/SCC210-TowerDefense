import org.jsfml.graphics.*;
/**
* Constructor of Phoenix
* @param xPixelsPerSecond The number of pixels the phoenix should move per second in the x direction
* @param yPixelsPerSecond The number of pixels the phoenix should move per second in the y direction
* @param r The rotation of the phoenix
* @param diffculty The diffculty of the game
* @param background The background the phoenix will be displayed on
*/
public class Phoenix extends NPC
{
	public Phoenix(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/Death/enemy5.png",background);
		ID = "Phoenix";
		type = new String[]{"star","warp"};
		defaultType = new String[]{"star","warp"};
		dropCount = 3;
		//Difficulty adjusments (diffculty paramter)		
		if(diffculty.equals("easy"))
		{
			health = 300;
			armour = 100;
		}
		else if(diffculty.equals("intermediate"))
		{
			health = 500;
			armour = 225;
		}
		else if(diffculty.equals("hard"))
		{
			health = 750;
			armour = 450;
		}
	}
}
