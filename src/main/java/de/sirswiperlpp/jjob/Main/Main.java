package de.sirswiperlpp.jjob.Main;

import de.sirswiperlpp.jjob.Commands.JobCommand;
import de.sirswiperlpp.jjob.Lang.Language;
import de.sirswiperlpp.jjob.Listener.MinerListener;
import de.sirswiperlpp.jjob.Listener.PlayerListener;
import de.sirswiperlpp.jjob.Provider.JobPROV;
import de.sirswiperlpp.jjob.SQL.JobSQL;
import de.sirswiperlpp.jjob.SQL.MySQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static FileConfiguration config;
    public static Language language;

    public void loadConfiguration() {
        File datafolder = this.getDataFolder();
        File configFile = new File(datafolder + File.separator + "config.yml");

        if (!configFile.exists()) {
            this.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public Main() {
        instance = this;
        language = new Language(new File(getDataFolder(), "lang.ini"));
    }

    public static Main getInstance() {
        return instance;
    }

    private void checkAndCreateLanguageFile() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File languageFile = new File(getDataFolder(), "lang.ini");
        if (!languageFile.exists()) {
            getLogger().info("language.ini not found. Creating...");

            saveResource("lang.ini", true);
        }
    }

    @Override
    public void onEnable() {
        loadConfiguration();
        checkAndCreateLanguageFile();
        MySQL.connect("JAL");
        JobSQL.connect("JAL_JOB");
        try {
            JobPROV.createJobTable();
            JobPROV.createXPTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new MinerListener(), this);

        getCommand("job").setExecutor(new JobCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
