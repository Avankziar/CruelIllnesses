package main.java.me.avankziar.cill.spigot.objects.illness;

import org.bukkit.entity.Player;

public class PlayerInfection
{
	private Player player;
	
	public PlayerInfection(Player player)
	{
		setPlayer(player);
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public boolean biomeInfection(Biomes biomes)
	{
		//ADDME
		return true;
	}
	
	public boolean illnessInfection(Illness illness, double otherOuterAdditionalProbability)
	{
		//ADDME
		return true;
	}
	
	public boolean vaccinate(Vaccination vaccination, double otherOuterAdditionalProbability)
	{
		//ADDME
		return true;
	}
}
