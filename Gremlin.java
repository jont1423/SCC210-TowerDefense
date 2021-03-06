import org.jsfml.graphics.*;
public class Gremlin extends NPC
{
	public Gremlin(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/Death/enemy1.png",background);
		ID = "Gremlin";
		type = new String[]{"electric"};
		defaultType = new String[]{"electric"};
		dropCount = 1;
		//Difficulty adjusments (diffculty paramter)		
		if(diffculty.equals("easy"))
		{
			health = 100;
			armour = 0;
		}
		else if(diffculty.equals("intermediate"))
		{
			health = 150;
			armour = 50;
		}
		else if(diffculty.equals("hard"))
		{
			health = 200;
			armour = 100;
		}
	}
}
