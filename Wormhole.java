public class Wormhole extends Tower
{
	public Wormhole(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-12.png",background,placed);
		ID = "Wormhole";
		cost = 350;
		upgradeCost = 0;
		trapHealth =  50;
		baseDamage = 50;
		damage = baseDamage;
		baseCooldown = 150;
		cooldown = baseCooldown;
		range = 0;
		type = "warp";
		isTrap = true;
	}
	
	void upgrade()
	{

	}
}