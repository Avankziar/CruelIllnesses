package main.java.me.avankziar.cill.spigot.guiapi;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import main.java.me.avankziar.cill.spigot.CruelIllnesses;

public class InventoryClickListener implements Listener
{
	
	@EventHandler
	public void onClickListener(InventoryClickEvent event)
	{
		if(event.isCancelled())
		{
			return;
		}
		if(event.getResult() == Result.DENY)
		{
			return;
		}
		if(event.getClickedInventory() == null)
		{
			return;
		}
		if(event.getClickedInventory().getType() != InventoryType.CHEST)
		{
			return;
		}
		ItemStack i = event.getCurrentItem().clone();
		CruelIllnesses plugin = CruelIllnesses.getPlugin();
		NamespacedKey npluginName = new NamespacedKey(plugin, GUIApi.PLUGINNAME);
		NamespacedKey ninventoryIdentifier = new NamespacedKey(plugin, GUIApi.INVENTORYIDENTIFIER);
		NamespacedKey nclickEventCancel = new NamespacedKey(plugin, GUIApi.CLICKEVENTCANCEL);
		NamespacedKey nfunction = new NamespacedKey(plugin, GUIApi.FUNCTION);
		PersistentDataContainer pdc = i.getItemMeta().getPersistentDataContainer();
		if(!pdc.has(npluginName, PersistentDataType.STRING)
				|| !pdc.has(ninventoryIdentifier, PersistentDataType.STRING)
				|| !pdc.has(nclickEventCancel, PersistentDataType.STRING)
				|| !pdc.has(nfunction, PersistentDataType.STRING))
		{
			return;
		}
		boolean clickEventCancel = Boolean.parseBoolean(pdc.get(nclickEventCancel, PersistentDataType.STRING));
		if(!clickEventCancel)
		{
			event.setCancelled(true);
			event.setResult(Result.DENY);
		}
		GuiClickEvent gce = new GuiClickEvent(
				event, npluginName.getKey(), ninventoryIdentifier.getKey(), nfunction.getKey());
		for(NamespacedKey key : pdc.getKeys())
		{
			if(!key.getKey().contains(":::"))
			{
				continue;
			}
			String[] split = key.getKey().split(":::");
			String purekey = split[0];
			GUIApi.Type type = GUIApi.Type.valueOf(split[1]);
			switch(type)
			{
			case BYTE:
				if(pdc.has(key, PersistentDataType.BYTE))
				{
					gce.getValuesByte().put(purekey, pdc.get(key, PersistentDataType.BYTE));
				}
				continue;
			case BYTE_ARRAY:
				if(pdc.has(key, PersistentDataType.BYTE_ARRAY))
				{
					gce.getValuesByteArray().put(purekey, pdc.get(key, PersistentDataType.BYTE_ARRAY));
				}
				continue;
			case DOUBLE:
				if(pdc.has(key, PersistentDataType.DOUBLE))
				{
					gce.getValuesDouble().put(purekey, pdc.get(key, PersistentDataType.DOUBLE));
				}
				continue;
			case FLOAT:
				if(pdc.has(key, PersistentDataType.FLOAT))
				{
					gce.getValuesFloat().put(purekey, pdc.get(key, PersistentDataType.FLOAT));
				}
				continue;
			case INTEGER:
				if(pdc.has(key, PersistentDataType.INTEGER))
				{
					gce.getValuesInteger().put(purekey, pdc.get(key, PersistentDataType.INTEGER));
				}
				continue;
			case INTEGER_ARRAY:
				if(pdc.has(key, PersistentDataType.INTEGER_ARRAY))
				{
					gce.getValuesIntegerArray().put(purekey, pdc.get(key, PersistentDataType.INTEGER_ARRAY));
				}
				continue;
			case LONG:
				if(pdc.has(key, PersistentDataType.LONG))
				{
					gce.getValuesLong().put(purekey, pdc.get(key, PersistentDataType.LONG));
				}
				continue;
			case LONG_ARRAY:
				if(pdc.has(key, PersistentDataType.LONG_ARRAY))
				{
					gce.getValuesLongArray().put(purekey, pdc.get(key, PersistentDataType.LONG_ARRAY));
				}
				continue;
			case SHORT:
				if(pdc.has(key, PersistentDataType.SHORT))
				{
					gce.getValuesShort().put(purekey, pdc.get(key, PersistentDataType.SHORT));
				}
				continue;
			case STRING:
				if(pdc.has(key, PersistentDataType.STRING))
				{
					gce.getValuesString().put(purekey, pdc.get(key, PersistentDataType.STRING));
				}
				continue;
			}
		}
		Bukkit.getPluginManager().callEvent(gce);
		return;
	}
	
	public String getPureKey(String key)
	{
		if(!key.contains(":::"))
		{
			return key;
		}
		String[] split = key.split(":::");
		return split[0];
	}
}
