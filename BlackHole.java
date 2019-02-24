public class BlackHole extends Tower
{
	public BlackHole(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-13.png",background,placed);
		ID = "Black Hole";
		trapHealth =  50;
		baseDamage = 50;
		damage = baseDamage;
		baseCooldown = 150;
		cooldown = baseCooldown;
		range = 0;
		type = "galaxy";
		isTrap = true;
	}
	
	void upgrade()
	{

	}
}