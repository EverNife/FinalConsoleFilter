package br.com.finalcraft.finalconsolefilter.consolefilter.filters;

import br.com.finalcraft.evernifecore.util.FCReflectionUtil;
import br.com.finalcraft.evernifecore.version.MCVersion;
import br.com.finalcraft.finalconsolefilter.FinalConsoleFilter;
import br.com.finalcraft.finalconsolefilter.config.CFSettings;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Log4jFilter implements Filter {

	private boolean started = false;

	@Override
	public Filter.Result filter(LogEvent event) {
		if (CFSettings.shouldBlockMessage(event.getMessage().getFormattedMessage())){
			return Result.DENY;
		}

		return Result.NEUTRAL;
	}

	public static void applyFilter() {
		((Logger) LogManager.getRootLogger()).addFilter(new Log4jFilter());
		if (MCVersion.isEqual(MCVersion.v1_7_10) && FCReflectionUtil.isClassLoaded("net.minecraft.server.MinecraftServer")){
			Logger newLogger = (Logger) LogManager.getLogger("FinalConsoleFilter");
			try {
				Class clazz_MinecraftServer = FCReflectionUtil.getClass("net.minecraft.server.MinecraftServer");
				Field theField = clazz_MinecraftServer.getDeclaredField("field_147145_h");
				theField.setAccessible(true);

				//Man, fuck reflections...

				//Set Field to NonFinal
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(theField, theField.getModifiers() & ~Modifier.FINAL);

				//Refresh field's "getFieldAccessor()"
				Field overrideFieldAccessorField = Field.class.getDeclaredField("overrideFieldAccessor");
				Field fieldAccessorField = Field.class.getDeclaredField("fieldAccessor");
				Field rootField = Field.class.getDeclaredField("root");
				overrideFieldAccessorField.setAccessible(true);
				fieldAccessorField.setAccessible(true);
				rootField.setAccessible(true);
				overrideFieldAccessorField.set(theField, null);
				fieldAccessorField.set(theField, null);
				rootField.set(theField, null);

				//In fact change the field
				theField.set(null, newLogger);
				FinalConsoleFilter.info("Replacing DEFAULT LOGGER 'net.minecraft.server.MinecraftServer' field_147145_h");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public Result getOnMismatch() {
		return Result.NEUTRAL;
	}

	@Override
	public Result getOnMatch() {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
		return Result.NEUTRAL;
	}

	@Override
	public State getState() {
		return started ? State.STARTED : State.STOPPED;
	}

	@Override
	public void initialize() {

	}

	@Override
	public void start() {
		started = true;
	}

	@Override
	public void stop() {
		started = false;
	}

	@Override
	public boolean isStarted() {
		return started == true;
	}

	@Override
	public boolean isStopped() {
		return started == false;
	}
}
