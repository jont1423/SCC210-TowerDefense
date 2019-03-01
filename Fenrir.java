import org.jsfml.graphics.*;
/**
* Constructor of Fenrir
* @param xPixelsPerSecond The number of pixels the fenrir should move per second in the x direction
* @param yPixelsPerSecond The number of pixels the fenrir should move per second in the y direction
* @param r The rotation of the fenrir
* @param diffculty The diffculty of the game
* @param background The background the fenrir will be displayed on
*/
public class Fenrir extends NPC
{
	public Fenrir(float xPixelsPerSecond, float yPixelsPerSecond,int r,String diffculty, Background background)
	{
		super(xPixelsPerSecond, yPixelsPerSecond, r, "Enemies/Death/enemy4.png",background);
		ID = "Fenrir";
		type = new String[]{"laser"};
		defaultType = new String[]{"laser"};
		dropCount = 3;
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
