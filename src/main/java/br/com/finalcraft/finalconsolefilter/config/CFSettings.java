package br.com.finalcraft.finalconsolefilter.config;

import br.com.finalcraft.finalconsolefilter.FinalConsoleFilter;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CFSettings {

    public static List<Pattern> HIDE_LIST = new ArrayList<>();

    public static void initialize(){

        synchronized (HIDE_LIST){
            HIDE_LIST.clear();
        }

        List<Pattern> NEW_PATTERN_LIST = new ArrayList<>();
        List<String> REGEX_LIST = ConfigManager.getMainConfig().getStringList("HidePatterns.RegexList");
        if (REGEX_LIST.isEmpty()){
            REGEX_LIST = new ArrayList<>();
            REGEX_LIST.add(".*(?i)abra CaDaBra(?-i).*");
            ConfigManager.getMainConfig().setValue("HidePatterns.RegexList", REGEX_LIST);
            ConfigManager.getMainConfig().save();
        }

        for (String regex : REGEX_LIST) {
            try {
                Validate.isTrue(!regex.isEmpty(), "Regex cannot be Empty");
                Pattern pattern = Pattern.compile(regex);
                FinalConsoleFilter.info("Adding Console Filter Regex [" + pattern.pattern() + "]");
                NEW_PATTERN_LIST.add(pattern);
            }catch (Throwable e){
                FinalConsoleFilter.warning("Failed to load regex [" + regex + "]");
                e.printStackTrace();
            }
        }

        synchronized (HIDE_LIST){
            HIDE_LIST = NEW_PATTERN_LIST;
        }
    }

    public static void save(){
        ConfigManager.getMainConfig().setValue("HidePatterns.RegexList", HIDE_LIST.stream().map(Pattern::pattern).collect(Collectors.toList()));
        ConfigManager.getMainConfig().save();
    }

    public static boolean shouldBlockMessage(String message){
        for (Pattern pattern : HIDE_LIST) {
            if (pattern.matcher(message).matches()){
                return true;
            }
        }
        return false;
    }

}
