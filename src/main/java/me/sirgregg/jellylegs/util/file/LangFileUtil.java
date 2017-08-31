package me.sirgregg.jellylegs.util.file;

import me.sirgregg.jellylegs.JellyLegs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class LangFileUtil extends YamlConfiguration {
	private static LangFileUtil lang;
	private final File file;

	public static LangFileUtil getLang() {
		if (lang == null) {
			lang = new LangFileUtil();
		}
		return lang;
	}
	private LangFileUtil() {
		file = new File(JellyLegs.getPlugin(JellyLegs.class).getDataFolder(), "lang.yml");
		loadLang();
	}

	public void loadLang() {
		if (!file.exists()) {
			saveDefaultLang();
		}
		loadConfiguration(file);
		try {
			load(file);
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load lang.yml", e);
		}
	}

	public void saveLang() {
		try {
			save(file);
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Cannot save lang.yml", e);
		}
	}

	public void reloadLang() {
		loadLang();
	}

	public void saveDefaultLang() {
		if (!file.exists()) {
			JellyLegs.getPlugin(JellyLegs.class).saveResource("lang.yml", false);
		}
	}
}
