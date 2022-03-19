package br.com.finalcraft.finalconsolefilter.commands;

import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.fancytext.FancyFormatter;
import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.evernifecore.locale.FCLocale;
import br.com.finalcraft.evernifecore.locale.LocaleMessage;
import br.com.finalcraft.evernifecore.locale.LocaleType;
import br.com.finalcraft.evernifecore.util.FCMessageUtil;
import br.com.finalcraft.evernifecore.util.FCTextUtil;
import br.com.finalcraft.finalconsolefilter.FinalConsoleFilter;
import br.com.finalcraft.finalconsolefilter.PermissionNodes;
import br.com.finalcraft.finalconsolefilter.config.CFSettings;
import br.com.finalcraft.finalconsolefilter.config.ConfigManager;
import org.bukkit.command.CommandSender;

import java.util.regex.Pattern;

@FinalCMD(
        aliases = {"finalconsolefilter", "consolefilter"}
)
public class CMDCoreCommand {

    @FCLocale(lang = LocaleType.EN_US, text = "§2§l ▶ §aConsole Filter Regex §6[§e%regex%§6]§a added!")
    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aConsole Filter Regex §6[§e%regex%§6]§a adicionada!")
    private static LocaleMessage REGEX_ADDED;

    @FinalCMD.SubCMD(
            subcmd = {"add"},
            permission = PermissionNodes.COMMAND_ADD
    )
    public void add(CommandSender sender, MultiArgumentos argumentos, @Arg(name = "<Regex>") String regex) {
        regex = argumentos.joinStringArgs(1);

        Pattern pattern = Pattern.compile(regex);

        REGEX_ADDED
                .addPlaceholder("%regex%",regex)
                .send(sender);

        if (!CFSettings.HIDE_LIST.stream().filter(pattern1 -> pattern1.pattern().equals(pattern.pattern())).findAny().isPresent()){ //Check if this pattern already exist
            CFSettings.HIDE_LIST.add(pattern);
            CFSettings.save();
        }
    }

    @FCLocale(lang = LocaleType.EN_US, text = "§4§l ▶ §cNo Console Filter §6[§e%regex%§6]§c found to be removed!")
    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aNenhum filtro §6[§e%regex%§6]§a encontrado para ser removido!")
    private static LocaleMessage REGEX_NOT_FOUND;

    @FCLocale(lang = LocaleType.EN_US, text = "§2§l ▶ §aConsole Filter Regex §6[§e%regex%§6]§a was removed!")
    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aConsole Filter Regex §6[§e%regex%§6]§a foi removida!")
    private static LocaleMessage REGEX_REMOVED;

    @FinalCMD.SubCMD(
            subcmd = {"remove"},
            permission = PermissionNodes.COMMAND_REMOVE
    )
    public void remove(CommandSender sender, MultiArgumentos argumentos, @Arg(name = "<Regex>") String regex) {
        regex = argumentos.joinStringArgs(1);

        Pattern toRemove = null;
        for (Pattern pattern : CFSettings.HIDE_LIST) {
            if (pattern.pattern().equalsIgnoreCase(regex)){
                toRemove = pattern;
                break;
            }
        }

        if (toRemove == null){
            REGEX_NOT_FOUND
                    .addPlaceholder("%regex%", regex)
                    .send(sender);
            return;
        }

        REGEX_REMOVED
                .addPlaceholder("%regex%", regex)
                .send(sender);

        CFSettings.HIDE_LIST.remove(toRemove);
        CFSettings.save();
    }

    @FinalCMD.SubCMD(
            subcmd = {"list"},
            permission = PermissionNodes.COMMAND_LIST
    )
    public void list(CommandSender sender, String label, MultiArgumentos argumentos) {

        String straightLine = FCTextUtil.straightLineOf("§a§m-§r");
        FancyFormatter formatter = FancyFormatter.of(straightLine);

        int contador = 1;
        for (Pattern pattern : CFSettings.HIDE_LIST) {
            formatter.append(
                    FancyText.of("\n§7  ♦  §eRegex" + (contador++) + " §c§d( ✖ ClickToRemove ✖ )")
                            .setHoverText("\n§cClique here to remove the Regex: \n\n§e" + pattern.pattern() + "\n")
                            .setRunCommandAction("/" + label + " remove " + pattern.pattern())
            );
        }

        formatter.append("\n");
        formatter.send(sender);
    }

    @FinalCMD.SubCMD(
            subcmd = {"reload"},
            permission = PermissionNodes.COMMAND_RELOAD
    )
    public void reload(CommandSender sender) {
        ConfigManager.initialize(FinalConsoleFilter.instance);
        FCMessageUtil.pluginHasBeenReloaded(sender, FinalConsoleFilter.instance.getName());
    }

}
