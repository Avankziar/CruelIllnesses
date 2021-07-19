package main.java.me.avankziar.cill.spigot.objects.illness;

import java.util.LinkedHashMap;

import org.bukkit.entity.Player;

import main.java.me.avankziar.cill.spigot.objects.Costs;

public class Healer
{
	private String npcName;
	/*
	 * Name of the illness, and the maximum of Level which can it cure it.
	 */
	private LinkedHashMap<String, Integer> illnessList;
	/*
	 * Name of the Illness, and the costs
	 */
	private LinkedHashMap<String, Costs> costsList;
	
	public Healer(String npcName, LinkedHashMap<String, Integer> illnessList)
	{
		setNpcName(npcName);
		setIllnessList(illnessList);
	}

	public String getNpcName()
	{
		return npcName;
	}

	public void setNpcName(String npcName)
	{
		this.npcName = npcName;
	}

	public LinkedHashMap<String, Integer> getIllnessList()
	{
		return illnessList;
	}

	public void setIllnessList(LinkedHashMap<String, Integer> illnessList)
	{
		this.illnessList = illnessList;
	}

	public LinkedHashMap<String, Costs> getCostsList()
	{
		return costsList;
	}

	public void setCostsList(LinkedHashMap<String, Costs> costsList)
	{
		this.costsList = costsList;
	}
	
	public boolean cure(Player player, String illness)
	{
		//ADDME, call CurePlayerEvent(Player player, Illness illness
		return true;
	}

}
