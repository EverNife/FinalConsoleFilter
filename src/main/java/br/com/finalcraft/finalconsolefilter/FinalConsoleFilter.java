package br.com.finalcraft.finalconsolefilter;

import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import br.com.finalcraft.evernifecore.logger.ECLogger;
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

    private final ECLogger ecLogger = new ECLogger(this);

    public static ECLogger getLog() {
        return instance.ecLogger;
    }

    @Override
    public void onEnable() {
        instance = this;

        getLog().info("§aLoading Configuration...");
        ConfigManager.initialize(this);

        getLog().info("§aRegistering Commands...");
        CommandRegisterer.registerCommands(this);

        getLog().info("§aApplying Filters...");
        ConsoleFilter.initialize();
    }

}
