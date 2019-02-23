import org.jsfml.graphics.*;
public class Yeti extends NPC
{
	public Yeti(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/enemy3.png",background);
		ID = "Yeti";
		type = new int[]{0,0,0,0,0};
		dropCount = 1;
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