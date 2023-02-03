package br.com.finalcraft.finalconsolefilter;

import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import br.com.finalcraft.finalconsolefilter.commands.CommandRegisterer;
import br.com.finalcraft.finalconsolefilter.config.ConfigManager;
import br.com.finalcraft.finalconsolefilter.consolefilter.ConsoleFilter;
import org.bukkit.plugin.java.JavaPlugin;

@ECPlugin(
        bstatsID = "14682",
        spigotID = "100839"
)
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
    }

}
