package main.java.me.avankziar.cill.spigot.database.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.me.avankziar.cill.spigot.CruelIllnesses;
import main.java.me.avankziar.cill.spigot.objects.PluginUser;

public interface TableI
{
	
	default boolean existI(CruelIllnesses plugin, String whereColumn, Object... object) 
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tablePluginUser 
						+ "` WHERE "+whereColumn+" LIMIT 1";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : object)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        
		        result = preparedStatement.executeQuery();
		        while (result.next()) 
		        {
		        	return true;
		        }
		    } catch (SQLException e) 
			{
				  CruelIllnesses.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return false;
	}
	
	default boolean createI(CruelIllnesses plugin, Object object) 
	{
		if(!(object instanceof PluginUser))
		{
			return false;
		}
		PluginUser ep = (PluginUser) object;
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) {
			try 
			{
				String sql = "INSERT INTO `" + plugin.getMysqlHandler().tablePluginUser 
						+ "`(`player_uuid`, `player_name`, `balance`, `bankaccountlist`,"
						+ " `moneyplayerflow`, `moneybankflow`, `generalmessage`, `pendinginvite`, `frozen`) " 
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
				preparedStatement = conn.prepareStatement(sql);
		        preparedStatement.setString(1, ep.getUUID());
		        preparedStatement.setString(2, ep.getName());
		        preparedStatement.setDouble(3, ep.getBalance());
		        preparedStatement.setString(4, String.join(";", ep.getBankAccountNumber()));
		        preparedStatement.setBoolean(5, ep.isMoneyBankFlow());
		        preparedStatement.setBoolean(6, ep.isMoneyPlayerFlow());
		        preparedStatement.setBoolean(7, ep.isGeneralMessage());
		        preparedStatement.setString(8, ep.getPendingInvite());
		        preparedStatement.setBoolean(9, ep.isFrozen());
		        
		        preparedStatement.executeUpdate();
		        return true;
		    } catch (SQLException e) 
			{
				  CruelIllnesses.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return false;
	}
	
	default boolean updateDataI(CruelIllnesses plugin, Object object, String whereColumn, Object... whereObject) 
	{
		if(!(object instanceof PluginUser))
		{
			return false;
		}
		if(whereObject == null)
		{
			return false;
		}
		PluginUser ep = (PluginUser) object;
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{
				String data = "UPDATE `" + plugin.getMysqlHandler().tablePluginUser
						+ "` SET `player_uuid` = ?, `player_name` = ?, `balance` = ?,"
						+ " `bankaccountlist` = ?, `moneyplayerflow` = ?, `moneybankflow` = ?, `generalmessage` = ?,"
						+ " `pendinginvite` = ?, `frozen` = ?" 
						+ " WHERE "+whereColumn;
				preparedStatement = conn.prepareStatement(data);
				preparedStatement.setString(1, ep.getUUID());
		        preparedStatement.setString(2, ep.getName());
		        preparedStatement.setDouble(3, ep.getBalance());
		        preparedStatement.setString(4, String.join(";", ep.getBankAccountNumber()));
		        preparedStatement.setBoolean(5, ep.isMoneyPlayerFlow());
		        preparedStatement.setBoolean(6, ep.isMoneyBankFlow());
		        preparedStatement.setBoolean(7, ep.isGeneralMessage());
		        preparedStatement.setString(8, ep.getPendingInvite());
		        preparedStatement.setBoolean(9, ep.isFrozen());
		        int i = 10;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
				
				preparedStatement.executeUpdate();
				return true;
			} catch (SQLException e) {
				CruelIllnesses.log.warning("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null) 
					{
						preparedStatement.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
        return false;
	}
	
	default Object getDataI(CruelIllnesses plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tablePluginUser 
						+ "` WHERE "+whereColumn+" LIMIT 1";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        
		        result = preparedStatement.executeQuery();
		        while (result.next()) 
		        {
		        	String bankacc = result.getString("bankaccountlist");
		        	List<String> lists = new ArrayList<>();
		        	if(bankacc != null)
		        	{
		        		lists = Arrays.asList(result.getString("bankaccountlist").split(";"));
		        	}
		        	return new PluginUser(result.getInt("id"),
		        			result.getString("player_uuid"),
		        			result.getString("player_name"),
		        			result.getDouble("balance"),
		        			lists,
		        			result.getBoolean("moneyplayerflow"),
		        			result.getBoolean("moneybankflow"),
		        			result.getBoolean("generalmessage"),
		        			result.getString("pendinginvite"),
		        			result.getBoolean("frozen"));
		        }
		    } catch (SQLException e) 
			{
				  CruelIllnesses.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
	
	default int getCountI(CruelIllnesses plugin, String orderByColumn, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{
				String sql = " SELECT count(*) FROM `"+plugin.getMysqlHandler().tablePluginUser
						+"` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" DESC";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        
		        result = preparedStatement.executeQuery();
		        while (result.next()) 
		        {
		        	return result.getInt(1);
		        }
		    } catch (SQLException e) 
			{
				  CruelIllnesses.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return 0;
	}
	
	default int getCountAllI(CruelIllnesses plugin)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{
				String sql = " SELECT count(*) FROM `"+plugin.getMysqlHandler().tablePluginUser+"`";
		        preparedStatement = conn.prepareStatement(sql);
		        
		        result = preparedStatement.executeQuery();
		        while (result.next()) 
		        {
		        	return result.getInt(1);
		        }
		    } catch (SQLException e) 
			{
				  CruelIllnesses.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return 0;
	}
	
	default int getTopListPlaceI(CruelIllnesses plugin, String orderByColumn, String whereColumn, Object... whereObject)
	{
		//whereColumn: for example: `afktime` > xxx
		return getCountI(plugin, orderByColumn, whereColumn, whereObject) + 1;
	}
	
	default boolean deleteDataI(CruelIllnesses plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		try 
		{
			String sql = "DELETE FROM `" + plugin.getMysqlHandler().tablePluginUser + "` WHERE "+whereColumn;
			preparedStatement = conn.prepareStatement(sql);
			int i = 1;
	        for(Object o : whereObject)
	        {
	        	preparedStatement.setObject(i, o);
	        	i++;
	        }
			preparedStatement.execute();
			return true;
		} catch (Exception e) 
		{
			e.printStackTrace();
		} finally 
		{
			try {
				if (preparedStatement != null) 
				{
					preparedStatement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	default int lastIDI(CruelIllnesses plugin)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tablePluginUser + "` ORDER BY `id` DESC LIMIT 1";
		        preparedStatement = conn.prepareStatement(sql);
		        
		        result = preparedStatement.executeQuery();
		        while(result.next())
		        {
		        	return result.getInt("id");
		        }
		    } catch (SQLException e) 
			{
		    	e.printStackTrace();
		    	return 0;
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return 0;
	}
	
	default int countWhereIDI(CruelIllnesses plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tablePluginUser
						+ "` WHERE "+whereColumn
						+ " ORDER BY `id` DESC";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        int count = 0;
		        while(result.next())
		        {
		        	count++;
		        }
		        return count;
		    } catch (SQLException e) 
			{
		    	e.printStackTrace();
		    	return 0;
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return 0;
	}
	
	default ArrayList<PluginUser> getListI(CruelIllnesses plugin, String orderByColumn,
			boolean desc, int start, int end, String whereColumn, Object...whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tablePluginUser
						+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" DESC LIMIT "+start+", "+end;
				if(desc)
				{
					sql = "SELECT * FROM `" + plugin.getMysqlHandler().tablePluginUser
							+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" DESC LIMIT "+start+", "+end;
				} else
				{
					sql = "SELECT * FROM `" + plugin.getMysqlHandler().tablePluginUser
							+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" ASC LIMIT "+start+", "+end;
				}
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        ArrayList<PluginUser> list = new ArrayList<PluginUser>();
		        while (result.next()) 
		        {
		        	String bankacc = result.getString("bankaccountlist");
		        	List<String> lists = new ArrayList<>();
		        	if(bankacc != null)
		        	{
		        		lists = Arrays.asList(result.getString("bankaccountlist").split(";"));
		        	}
		        	PluginUser ep = new PluginUser(result.getInt("id"),
		        			result.getString("player_uuid"),
		        			result.getString("player_name"),
		        			result.getDouble("balance"),
		        			lists,
		        			result.getBoolean("moneyplayerflow"),
		        			result.getBoolean("moneybankflow"),
		        			result.getBoolean("generalmessage"),
		        			result.getString("pendinginvite"),
		        			result.getBoolean("frozen"));
		        	list.add(ep);
		        }
		        return list;
		    } catch (SQLException e) 
			{
				  CruelIllnesses.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
	
	default ArrayList<PluginUser> getTopI(CruelIllnesses plugin, String orderByColumn, boolean desc, int start, int end)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "";
				if(desc)
				{
					sql = "SELECT * FROM `" + plugin.getMysqlHandler().tablePluginUser 
							+ "` ORDER BY "+orderByColumn+" DESC LIMIT "+start+", "+end;
				} else
				{
					sql = "SELECT * FROM `" + plugin.getMysqlHandler().tablePluginUser 
							+ "` ORDER BY "+orderByColumn+" ASC LIMIT "+start+", "+end;
				}
		        preparedStatement = conn.prepareStatement(sql);
		        
		        result = preparedStatement.executeQuery();
		        ArrayList<PluginUser> list = new ArrayList<PluginUser>();
		        while (result.next()) 
		        {
		        	String bankacc = result.getString("bankaccountlist");
		        	List<String> lists = new ArrayList<>();
		        	if(bankacc != null)
		        	{
		        		lists = Arrays.asList(result.getString("bankaccountlist").split(";"));
		        	}
		        	PluginUser ep = new PluginUser(result.getInt("id"),
		        			result.getString("player_uuid"),
		        			result.getString("player_name"),
		        			result.getDouble("balance"),
		        			lists,
		        			result.getBoolean("moneyplayerflow"),
		        			result.getBoolean("moneybankflow"),
		        			result.getBoolean("generalmessage"),
		        			result.getString("pendinginvite"),
		        			result.getBoolean("frozen"));
		        	list.add(ep);
		        }
		        return list;
		    } catch (SQLException e) 
			{
				  CruelIllnesses.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
	
	default ArrayList<PluginUser> getAllListAtI(CruelIllnesses plugin, String orderByColumn,
			boolean desc, String whereColumn, Object...whereObject) throws IOException
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "";
				if(desc)
				{
					sql = "SELECT * FROM `" + plugin.getMysqlHandler().tablePluginUser
							+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" DESC";
				} else
				{
					sql = "SELECT * FROM `" + plugin.getMysqlHandler().tablePluginUser
							+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" ASC";
				}
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        ArrayList<PluginUser> list = new ArrayList<PluginUser>();
		        while (result.next()) 
		        {
		        	String bankacc = result.getString("bankaccountlist");
		        	List<String> lists = new ArrayList<>();
		        	if(bankacc != null)
		        	{
		        		lists = Arrays.asList(result.getString("bankaccountlist").split(";"));
		        	}
		        	PluginUser ep = new PluginUser(result.getInt("id"),
		        			result.getString("player_uuid"),
		        			result.getString("player_name"),
		        			result.getDouble("balance"),
		        			lists,
		        			result.getBoolean("moneyplayerflow"),
		        			result.getBoolean("moneybankflow"),
		        			result.getBoolean("generalmessage"),
		        			result.getString("pendinginvite"),
		        			result.getBoolean("frozen"));
		        	list.add(ep);
		        }
		        return list;
		    } catch (SQLException e) 
			{
				  CruelIllnesses.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
}