import org.jsfml.graphics.*;
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
