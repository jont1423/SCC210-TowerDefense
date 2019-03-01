import org.jsfml.graphics.*;
/**
* Constructor of Gremlin
* @param xPixelsPerSecond The number of pixels the gremlin should move per second in the x direction
* @param yPixelsPerSecond The number of pixels the gremlin should move per second in the y direction
* @param r The rotation of the gremlin
* @param diffculty The diffculty of the game
* @param background The background the gremlin will be displayed on
*/
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
			health = 175;
			armour = 75;
		}
	}
}
