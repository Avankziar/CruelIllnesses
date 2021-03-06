package main.java.me.avankziar.cill.spigot.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import main.java.me.avankziar.cill.spigot.CruelIllnesses;
import main.java.me.avankziar.cill.spigot.database.Language.ISO639_2B;

public class YamlHandler
{
	private CruelIllnesses plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();
	
	private File commands = null;
	private YamlConfiguration com = new YamlConfiguration();
	
	private String languages;
	private File language = null;
	private YamlConfiguration lang = new YamlConfiguration();

	public YamlHandler(CruelIllnesses plugin)
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public YamlConfiguration getConfig()
	{
		return cfg;
	}
	
	public YamlConfiguration getCommands()
	{
		return com;
	}
	
	public YamlConfiguration getLang()
	{
		return lang;
	}
	
	private boolean loadYamlTask(File file, YamlConfiguration yaml)
	{
		try 
		{
			yaml.load(file);
		} catch (IOException | InvalidConfigurationException e) 
		{
			CruelIllnesses.log.severe(
					"Could not load the %file% file! You need to regenerate the %file%! Error: ".replace("%file%", file.getName())
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean writeFile(File file, YamlConfiguration yml, LinkedHashMap<String, Language> keyMap)
	{
		yml.options().header("For more explanation see \n Your pluginsite");
		for(String key : keyMap.keySet())
		{
			Language languageObject = keyMap.get(key);
			if(languageObject.languageValues.containsKey(plugin.getYamlManager().getLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInput(yml, keyMap, key, plugin.getYamlManager().getLanguageType());
			} else if(languageObject.languageValues.containsKey(plugin.getYamlManager().getDefaultLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInput(yml, keyMap, key, plugin.getYamlManager().getDefaultLanguageType());
			}
		}
		try
		{
			yml.save(file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean loadYamlHandler()
	{
		/*
		 * Init all path from all yamls files
		 */
		plugin.setYamlManager(new YamlManager());
		/*
		 * Load all files, which are unique, for examples config.yml, commands.yml etc.
		 */
		if(!mkdirStaticFiles())
		{
			return false;
		}
		/*
		 * Load all files, which exist mutiple versions. Languages etc.
		 */
		if(!mkdirDynamicFiles())
		{
			return false;
		}
		return true;
	}
	
	public boolean mkdirStaticFiles()
	{
		/*
		 * Create the plugin general directory
		 */
		File directory = new File(plugin.getDataFolder()+"");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		/*
		 * Init config.yml
		 */
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			CruelIllnesses.log.info("Create config.yml...");
			try
			{
				/*
				 * If config.yml dont exist in the main directory, than create config.yml as empty file
				 */
				FileUtils.copyToFile(plugin.getResource("default.yml"), config);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		/*
		 * Load the config.yml
		 */
		if(!loadYamlTask(config, cfg))
		{
			return false;
		}
		/*
		 * Write all path for the configfile
		 * Make sure, you use the right linkedHashmap from the YamlManager
		 */
		writeFile(config, cfg, plugin.getYamlManager().getConfigKey());
		/*
		 * Define the language
		 */
		languages = cfg.getString("Language", "ENG").toUpperCase();
		/*
		 * Repeat for all other single flatfiles.
		 */
		commands = new File(plugin.getDataFolder(), "commands.yml");
		if(!commands.exists()) 
		{
			CruelIllnesses.log.info("Create commands.yml...");
			try
			{
				//Erstellung einer "leere" config.yml
				FileUtils.copyToFile(plugin.getResource("default.yml"), commands);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		if(!loadYamlTask(commands, com))
		{
			return false;
		}
		writeFile(commands, com, plugin.getYamlManager().getCommandsKey());
		return true;
	}
	
	private boolean mkdirDynamicFiles()
	{
		//Compare the using languages and set this as standard
		List<Language.ISO639_2B> types = new ArrayList<Language.ISO639_2B>(EnumSet.allOf(Language.ISO639_2B.class));
		ISO639_2B languageType = ISO639_2B.ENG;
		for(ISO639_2B type : types)
		{
			if(type.toString().equals(languages))
			{
				languageType = type;
				break;
			}
		}
		/*
		 * Set the standard languages
		 */
		plugin.getYamlManager().setLanguageType(languageType);
		/*
		 * Start to make the specific languagefile.
		 * Attention! Only one file will be created.
		 */
		if(!mkdirLanguage())
		{
			return false;
		}
		/*
		 * Here a example to create multiple flatfile for one purpose.
		 * This example is been using for inventory-guis.
		 */
		if(!mkdirGUIs())
		{
			return false;
		}
		return true;
	}
	
	private boolean mkdirLanguage()
	{
		/*
		 * Making the "prefix" thing for the filename.
		 */
		String languageString = plugin.getYamlManager().getLanguageType().toString().toLowerCase();
		/*
		 * Adding a new directory
		 */
		File directory = new File(plugin.getDataFolder()+"/Languages/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		/*
		 * The rest is equals part from the config.yml
		 */
		language = new File(directory.getPath(), languageString+".yml");
		if(!language.exists()) 
		{
			CruelIllnesses.log.info("Create %lang%.yml...".replace("%lang%", languageString));
			try
			{
				FileUtils.copyToFile(plugin.getResource("default.yml"), language);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if(!loadYamlTask(language, lang))
		{
			return false;
		}
		writeFile(language, lang, plugin.getYamlManager().getLanguageKey());
		return true;
	}
	
	private boolean mkdirGUIs()
	{
		
		return true;
	}
}