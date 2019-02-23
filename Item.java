import java.lang.Math;
public class Item extends ImageActor
{
	//Currency, damage*1.5 to all towers, firate * 15%, Piercing - Ignore enemy types, Fortune - double scrap
	private static String itemFile[] = {"Items/Scrap.png","Items/Damage.png","Items/Firerate.png","Items/Piercing.png","Items/Fortune.png"};
	private int effect;
	Item(float x, float y, int i)
	{
		super(x,y,0,itemFile[i]);
		this.effect = i;
	}
	

	int getEffect()
	{
			return effect;
	}
	
	void calcMove(int minx, int miny, int maxx, int maxy, float time)
	{
		
	}
	
}