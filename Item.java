import java.lang.Math;
public class Item extends ImageActor
{
	private static String itemFile[] = {"Items/Scrap.png","Items/Damage.png","Items/Firerate.png","Items/Piercing.png","Items/Fortune.png"};
	private int effect;
	/**
	* Intitals location and effect of the itemFile
	* @param x The x position of the item
	* @param y The y position of the item
	* @param i The number for the effect of the item
	*/
	Item(float x, float y, int i)
	{
		super(x,y,0,itemFile[i]);
		this.effect = i;
	}
	
	/**
	* Returns the value of the effect
	* @return effect The value of the effect
	*/
	int getEffect()
	{
			return effect;
	}
	
	void calcMove(int minx, int miny, int maxx, int maxy, float time)
	{
		
	}
	
}