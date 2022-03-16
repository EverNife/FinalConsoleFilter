package br.com.finalcraft.finalconsolefilter.consolefilter;

import br.com.finalcraft.finalconsolefilter.FinalConsoleFilter;
import br.com.finalcraft.finalconsolefilter.consolefilter.filters.JavaFilter;
import br.com.finalcraft.finalconsolefilter.consolefilter.filters.Log4jFilter;
import br.com.finalcraft.finalconsolefilter.consolefilter.filters.SystemFilter;

public class ConsoleFilter {

    public static void initialize(){
        FinalConsoleFilter.info("Applying JavaFilter");
        JavaFilter.applyFilter();
        FinalConsoleFilter.info("Applying Log4jFilter");
        Log4jFilter.applyFilter();
        FinalConsoleFilter.info("Applying SystemFilter");
        SystemFilter.applyFilter();
    }

}
