package main.java.me.avankziar.cill.spigot.database;

import java.io.IOException;
import java.util.ArrayList;

import main.java.me.avankziar.cill.spigot.CruelIllnesses;
import main.java.me.avankziar.cill.spigot.database.tables.TableI;

public class MysqlHandler implements TableI
{
	public enum Type
	{
		PLUGINUSER;
	}
	
	private CruelIllnesses plugin;
	public String tablePluginUser;
	
	public MysqlHandler(CruelIllnesses plugin) 
	{
		this.plugin = plugin;
		loadMysqlHandler();
	}
	
	public boolean loadMysqlHandler()
	{
		tablePluginUser = plugin.getYamlHandler().getConfig().getString("Mysql.tablePluginUser");
		if(tablePluginUser == null)
		{
			return false;
		}
		return true;
	}
	
	public boolean exist(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.existI(plugin, whereColumn, whereObject);
		}
		return false;
	}
	
	public boolean create(Type type, Object object)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.createI(plugin, object);
		}
		return false;
	}
	
	public boolean updateData(Type type, Object object, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.updateDataI(plugin, object, whereColumn, whereObject);
		}
		return false;
	}
	
	public Object getCount(Type type, String orderByColumn, String whereColumn, Object... whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getCountI(plugin, orderByColumn, whereColumn, whereObject);
		}
		return null;
	}
	
	public Object getCountAll(Type type) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getCountAllI(plugin);
		}
		return null;
	}
	
	public Object getData(Type type, String orderByColumn, String whereColumn, Object... whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getTopListPlaceI(plugin, orderByColumn, whereColumn, whereObject);
		}
		return null;
	}
	
	public Object getData(Type type, String whereColumn, Object... whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getDataI(plugin, whereColumn, whereObject);
		}
		return null;
	}
	
	public boolean deleteData(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.deleteDataI(plugin, whereColumn, whereObject);
		}
		return false;
	}
	
	public int lastID(Type type)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.lastIDI(plugin);
		}
		return 0;
	}
	
	public int countWhereID(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.countWhereIDI(plugin, whereColumn, whereObject);
		}
		return 0;
	}
	
	public ArrayList<?> getList(Type type, String orderByColumn,
			boolean desc, int start, int quantity, String whereColumn, Object...whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getListI(plugin, orderByColumn, desc, start, quantity, whereColumn, whereObject);
		}
		return null;
	}
	
	public ArrayList<?> getTop(Type type, String orderByColumn, boolean desc, int start, int end) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getTopI(plugin, orderByColumn, desc, start, end);
		}
		return null;
	}
	
	public ArrayList<?> getAllListAt(Type type, String orderByColumn,
			boolean desc, String whereColumn, Object...whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getAllListAtI(plugin, orderByColumn, desc, whereColumn, whereObject);
		}
		return null;
	}
}
