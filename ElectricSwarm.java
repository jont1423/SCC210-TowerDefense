import org.jsfml.graphics.*;
public class ElectricSwarm extends Tower
{
	/**
	* Constructor of ElectricSwarm
	* @param x The x position of the electric swarm
	* @param y The y position of the electric swarm
	* @param r The rotation of the electric swarm
	* @param Background The background electric swarm will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public ElectricSwarm(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-11.png",background,placed);
		ID = "Electric Swarm";
		cost = 25;
		upgradeCost = 0;
		trapHealth =  10;
		baseDamage = 50;
		damage = baseDamage;
		baseCooldown = 150;
		cooldown = baseCooldown;
		range = 0;

		type = "electric";
		isTrap = true;
	}
	
	void upgrade()
	{

	}
}