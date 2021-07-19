package main.java.me.avankziar.cill.spigot.objects.illness;

import org.bukkit.block.Biome;

public class Biomes
{
	private Biome biome;
	/*
	 * the time between the next action.
	 * In the backgroundtask run a task, and if this time smaller as last time
	 * so roll the dice.
	 */
	private long timeBetweenNextAction;
	/**
	 * Probility can be negative (for a positive Effect on the player)
	 * or positive (for a negative Effect on the player)
	 */
	private double probability;
	
	public Biomes(Biome biome, long timeBetweenNextAction, double probability)
	{
		setBiome(biome);
		setTimeBetweenNextAction(timeBetweenNextAction);
		setProbability(probability);
	}

	public Biome getBiome()
	{
		return biome;
	}

	public void setBiome(Biome biome)
	{
		this.biome = biome;
	}

	public long getTimeBetweenNextAction()
	{
		return timeBetweenNextAction;
	}

	public void setTimeBetweenNextAction(long timeBetweenNextAction)
	{
		this.timeBetweenNextAction = timeBetweenNextAction;
	}

	public double getProbability()
	{
		return probability;
	}

	public void setProbability(double probability)
	{
		this.probability = probability;
	}

}
