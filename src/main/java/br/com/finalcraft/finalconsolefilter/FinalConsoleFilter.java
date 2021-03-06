package br.com.finalcraft.finalconsolefilter;

import br.com.finalcraft.evernifecore.autoupdater.SpigotUpdateChecker;
import br.com.finalcraft.evernifecore.metrics.Metrics;
import br.com.finalcraft.finalconsolefilter.commands.CommandRegisterer;
import br.com.finalcraft.finalconsolefilter.config.ConfigManager;
import br.com.finalcraft.finalconsolefilter.consolefilter.ConsoleFilter;
import org.bukkit.plugin.java.JavaPlugin;

public class FinalConsoleFilter extends JavaPlugin{

    public static FinalConsoleFilter instance;

    public static void info(String msg){
        instance.getLogger().info("[Info] " + msg);
    }

    public static void debug(String msg){
        instance.getLogger().info("[Debug] " + msg);
    }

    public static void warning(String msg){
        instance.getLogger().info("[Warning] " + msg);
    }

    @Override
    public void onEnable() {
        instance = this;

        info("§aLoading Configuration...");
        ConfigManager.initialize(this);

        info("§aRegistering Commands...");
        CommandRegisterer.registerCommands(this);

        info("§aApplying Filters...");
        ConsoleFilter.initialize();

        SpigotUpdateChecker.checkForUpdates(
                this,
                "100839", //FinalConsoleFilter SpigotID: 100839
                ConfigManager.getMainConfig()
        );

        new Metrics(this, 14682); //14682 FinalConsoleFilter BStats
    }

}
