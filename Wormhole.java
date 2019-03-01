public class Wormhole extends Tower
{
	/**
	* Constructor of Wormhole
	* @param x The x position of the worm hole
	* @param y The y position of the worm hole
	* @param r The rotation of the worm hole
	* @param Background The background worm hole will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public Wormhole(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-12.png",background,placed);
		ID = "Wormhole";
		cost = 400;
		upgradeCost = 0;
		trapHealth =  60;
		baseDamage = 65;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 0;
		type = "warp";
		isTrap = true;
	}
	
	void upgrade()
	{

	}
}