package game.pong.core.config;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Initializing Logging.
 * 
 * @author princearora
 */
public class LoggerConfig {
	
	private static ConsoleAppender consoleAppender = null;
	private static FileAppender fileAppender = null;
	
	private static final String PATTERN = "%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p:: %m%n";
	
	public static final void init() {
		initConsoleAppender();
		initFileAppender();
	}
	
	@SuppressWarnings("unused")
	private static void initConsoleAppender() {
		consoleAppender = new ConsoleAppender();
		consoleAppender.setLayout(new PatternLayout(PATTERN));
		consoleAppender.setThreshold(Level.INFO);
		consoleAppender.activateOptions();
		Logger.getRootLogger().addAppender(consoleAppender);
	}
	
	@SuppressWarnings("unused")
	private static void initFileAppender() {
		fileAppender = new FileAppender();
		fileAppender.setName("file");
		fileAppender.setFile(System.getProperty("user.dir") + "/status.log");
		fileAppender.setLayout(new PatternLayout(PATTERN));
		fileAppender.setThreshold(Level.ERROR);
		fileAppender.setAppend(true);
		fileAppender.activateOptions();
		Logger.getRootLogger().addAppender(fileAppender);
	}
	
}
