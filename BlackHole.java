public class BlackHole extends Tower
{
	/**
	* Constructor of BlackHole
	* @param x The x position of the black hole
	* @param y The y position of the black hole
	* @param r The rotation of the black hole
	* @param Background The background black hole will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public BlackHole(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-13.png",background,placed);
		ID = "Black Hole";
		trapHealth =  275;
		baseDamage = 200;
		cost = 650;
		upgradeCost = 0;
		damage = baseDamage;
		baseCooldown = 25;
		cooldown = baseCooldown;
		range = 0;
		type = "galaxy";
		isTrap = true;
	}
	
	void upgrade()
	{

	}
}