package br.com.finalcraft.finalconsolefilter.config;

import br.com.finalcraft.finalconsolefilter.FinalConsoleFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CFSettings {

    public static List<Pattern> REGEX_LIST = new ArrayList<>();
    public static List<String> CONTAINS_LIST = new ArrayList<>();

    public static void initialize(){

        synchronized (REGEX_LIST){
            REGEX_LIST.clear();
        }
        synchronized (CONTAINS_LIST){
            CONTAINS_LIST.clear();
        }

        ConfigManager.getMainConfig().setDefaultValue("HidePatterns.ContainsList", Arrays.asList("abra cadabra"));
        ConfigManager.getMainConfig().setDefaultValue("HidePatterns.RegexList", new ArrayList<>());

        //Loading ContainsRules
        List<String> NEW_CONTAINS_LIST = new ArrayList<>();
        for (String text : ConfigManager.getMainConfig().getStringList("HidePatterns.ContainsList")) {
            try {
                Validate.isTrue(!text.isEmpty(), "TextContains cannot be Empty");
                FinalConsoleFilter.getLog().info("Adding Console Filter TextContains [" + text + "]");
                NEW_CONTAINS_LIST.add(text.toLowerCase());
            }catch (IllegalArgumentException e){
                FinalConsoleFilter.getLog().warning("Failed to load TextContains [" + text + "]");
                e.printStackTrace();
            }
        }

        //Loading RegexRules
        List<Pattern> NEW_REGEX_LIST = new ArrayList<>();
        for (String regex : ConfigManager.getMainConfig().getStringList("HidePatterns.RegexList")) {
            try {
                Validate.isTrue(!regex.isEmpty(), "Regex cannot be Empty");
                Pattern pattern = Pattern.compile(regex);
                FinalConsoleFilter.getLog().info("Adding Console Filter Regex [" + pattern.pattern() + "]");
                NEW_REGEX_LIST.add(pattern);
            }catch (Throwable e){
                FinalConsoleFilter.getLog().warning("Failed to load regex [" + regex + "]");
                e.printStackTrace();
            }
        }

        //Replace lists sync to prevent concurrent
        synchronized (CFSettings.REGEX_LIST){
            CFSettings.REGEX_LIST = NEW_REGEX_LIST;
        }

        synchronized (CFSettings.CONTAINS_LIST){
            CFSettings.CONTAINS_LIST = NEW_CONTAINS_LIST;
        }

        ConfigManager.getMainConfig().saveIfNewDefaults();
    }

    public static void save(){
        ConfigManager.getMainConfig().setValue("HidePatterns.ContainsList", CONTAINS_LIST);
        ConfigManager.getMainConfig().setValue("HidePatterns.RegexList", REGEX_LIST.stream().map(Pattern::pattern).collect(Collectors.toList()));
        ConfigManager.getMainConfig().saveAsync();
    }

    public static boolean shouldBlockMessage(String message){
        for (String contains : CONTAINS_LIST) {
            if (StringUtils.containsIgnoreCase(message, contains)){
                return true;
            }
        }
        for (Pattern pattern : REGEX_LIST) {
            if (pattern.matcher(message).matches()){
                return true;
            }
        }
        return false;
    }

}
