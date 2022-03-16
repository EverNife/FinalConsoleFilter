package br.com.finalcraft.finalconsolefilter.consolefilter.filters;

import br.com.finalcraft.finalconsolefilter.config.CFSettings;

import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JavaFilter implements Filter {

	@Override
	public boolean isLoggable(LogRecord record) {
		if (CFSettings.shouldBlockMessage(record.getMessage())){
			return false;
		}
		return true;
	}

	public static void applyFilter() {
		Logger.getLogger("").setFilter(new JavaFilter());
	}

}
