package main.java.me.avankziar.cill.spigot.objects;

import java.util.LinkedHashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Costs
{
	private double money;
	private int expLevel;
	private LinkedHashMap<ItemStack, Integer> itemStack;
	
	public Costs(double money, int expLevel, LinkedHashMap<ItemStack, Integer> itemStack)
	{
		setMoney(money);
		setExpLevel(expLevel);
		setItemStack(itemStack);
	}

	public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
	}

	public int getExpLevel()
	{
		return expLevel;
	}

	public void setExpLevel(int expLevel)
	{
		this.expLevel = expLevel;
	}

	public LinkedHashMap<ItemStack, Integer> getItemStack()
	{
		return itemStack;
	}

	public void setItemStack(LinkedHashMap<ItemStack, Integer> itemStack)
	{
		this.itemStack = itemStack;
	}

	public boolean hasAllCost(Player player)
	{
		//ADDME
		return true;
	}
	
	public boolean withDraw(Player player)
	{
		//ADDME
		return true;
	}
}
