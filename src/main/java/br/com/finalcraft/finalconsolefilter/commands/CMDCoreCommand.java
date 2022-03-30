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

    @FCLocale(lang = LocaleType.EN_US, text = "§2§l ▶ §aThe rule §6[§e%rule%§6]§a already exists!")
    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aA regra §6[§e%rule%§6]§a já existe!")
    private static LocaleMessage RULE_ALREADY_EXISTS;

    @FCLocale(lang = LocaleType.EN_US, text = "§2§l ▶ §aConsole Filter LineContainsRule §6[§e%rule%§6]§a added!")
    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aConsole Filter LineContainsRule §6[§e%rule%§6]§a adicionado!")
    private static LocaleMessage LINECONTAINS_RULE_ADDED;

    @FinalCMD.SubCMD(
            subcmd = {"add"},
            permission = PermissionNodes.COMMAND_ADD,
            locales = {
                    @FCLocale(lang = LocaleType.EN_US, text = "§2§l ▶ §aAdd a Rule to exclude any message that CONTAINS this text!"),
                    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aAdiciona uma regra para excluir qualquer mensagem que CONTENHA esse texto!")
            }
    )
    public void add(CommandSender sender, MultiArgumentos argumentos, @Arg(name = "<Text>") String text) {
        String theText = argumentos.joinStringArgs(1);

        if (CFSettings.CONTAINS_LIST.stream().filter(text1 -> text1.equalsIgnoreCase(theText)).findAny().isPresent()){
            RULE_ALREADY_EXISTS
                    .addPlaceholder("%rule%",text)
                    .send(sender);
            return;
        }

        LINECONTAINS_RULE_ADDED
                .addPlaceholder("%rule%",text)
                .send(sender);

        CFSettings.CONTAINS_LIST.add(theText);
        CFSettings.save();
    }

    @FCLocale(lang = LocaleType.EN_US, text = "§2§l ▶ §aConsole Filter RegexRule §6[§e%rule%§6]§a added!")
    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aConsole Filter RegexRule §6[§e%rule%§6]§a adicionada!")
    private static LocaleMessage REGEX_RULE_ADDED;

    @FinalCMD.SubCMD(
            subcmd = {"addregex"},
            permission = PermissionNodes.COMMAND_ADDREGEX,
            locales = {
                    @FCLocale(lang = LocaleType.EN_US, text = "§2§l ▶ §aAdd a Rule to exclude any message that MATCHES this regex!"),
                    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aAdiciona uma regra para excluir qualquer mensagem que de MATCH nesse regex!")
            }
    )
    public void addregex(CommandSender sender, MultiArgumentos argumentos, @Arg(name = "<Regex>") String regex) {
        regex = argumentos.joinStringArgs(1);

        Pattern pattern = Pattern.compile(regex);

        if (CFSettings.REGEX_LIST.stream().filter(pattern1 -> pattern1.pattern().equals(pattern.pattern())).findAny().isPresent()){
            RULE_ALREADY_EXISTS
                    .addPlaceholder("%rule%",regex)
                    .send(sender);
            return;
        }

        REGEX_RULE_ADDED
                .addPlaceholder("%rule%",regex)
                .send(sender);

        CFSettings.REGEX_LIST.add(pattern);
        CFSettings.save();
    }

    @FCLocale(lang = LocaleType.EN_US, text = "§4§l ▶ §cNo Console Filter §6[§e%rule%§6]§c found to be removed!")
    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aNenhum filtro §6[§e%rule%§6]§a encontrado para ser removido!")
    private static LocaleMessage RULE_NOT_FOUND;

    @FCLocale(lang = LocaleType.EN_US, text = "§2§l ▶ §aConsole Filter Rule §6[§e%rule%§6]§a was removed!")
    @FCLocale(lang = LocaleType.PT_BR, text = "§2§l ▶ §aConsole Filter Rule §6[§e%rule%§6]§a foi removida!")
    private static LocaleMessage RULE_REMOVED;

    @FinalCMD.SubCMD(
            subcmd = {"remove"},
            permission = PermissionNodes.COMMAND_REMOVE
    )
    public void remove(CommandSender sender, MultiArgumentos argumentos, @Arg(name = "<Regex|Text>") String textOrRegex) {
        textOrRegex = argumentos.joinStringArgs(1);

        for (int i = 0; i < CFSettings.REGEX_LIST.size(); i++) {
            Pattern pattern = CFSettings.REGEX_LIST.get(i);
            if (pattern.pattern().equalsIgnoreCase(textOrRegex)){
                CFSettings.REGEX_LIST.remove(i);
                CFSettings.save();
                RULE_REMOVED
                        .addPlaceholder("%rule%", textOrRegex)
                        .send(sender);
                return;
            }
        }

        for (int i = 0; i < CFSettings.CONTAINS_LIST.size(); i++) {
            String text = CFSettings.CONTAINS_LIST.get(i);
            if (text.equalsIgnoreCase(textOrRegex)){
                CFSettings.CONTAINS_LIST.remove(i);
                CFSettings.save();
                RULE_REMOVED
                        .addPlaceholder("%rule%", textOrRegex)
                        .send(sender);
                return;
            }
        }

        RULE_NOT_FOUND
                .addPlaceholder("%rule%", textOrRegex)
                .send(sender);
    }

    @FinalCMD.SubCMD(
            subcmd = {"list"},
            permission = PermissionNodes.COMMAND_LIST
    )
    public void list(CommandSender sender, String label, MultiArgumentos argumentos) {

        String straightLine = FCTextUtil.straightLineOf("§a§m-§r");
        FancyFormatter formatter = FancyFormatter.of(straightLine);

        int contador = 1;
        for (String contains : CFSettings.CONTAINS_LIST) {
            formatter.append(
                    FancyText.of("\n§7  ♦  §eRule" + (contador++) + " §c§d( ✖ ClickToRemove ✖ )")
                            .setHoverText("\n§cClique here to remove the Regex: \n\n§e" + contains + "\n")
                            .setRunCommandAction("/" + label + " remove " + contains)
            );
        }
        for (Pattern pattern : CFSettings.REGEX_LIST) {
            formatter.append(
                    FancyText.of("\n§7  ♦  §eRule" + (contador++) + " §c§d( ✖ ClickToRemove ✖ )")
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
