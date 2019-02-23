import org.jsfml.graphics.*;
public class SpeedDemon extends NPC
{
	public SpeedDemon(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/enemy2.png",background);
		ID = "SpeedDemon";
		type = new int[]{0,0,0,0,0};
		dropCount = 2; //Number of drops when killed
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