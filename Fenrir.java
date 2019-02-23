import org.jsfml.graphics.*;
public class Fenrir extends NPC
{
	public Fenrir(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/enemy4.png",background);
		ID = "Fenrir";
		type = new int[]{0,0,0,0,0};
		dropCount = 1;
		//Difficulty adjusments (diffculty paramter)		
		if(diffculty.equals("easy"))
		{
			health = 125;
			armour = 50;
		}
		else if(diffculty.equals("intermediate"))
		{
			health = 200;
			armour = 125;
		}
		else if(diffculty.equals("hard"))
		{
			health = 300;
			armour = 200;
		}
	}
}