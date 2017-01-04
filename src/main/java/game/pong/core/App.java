package game.pong.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.pong.core.config.LoggerConfig;
import game.pong.frame.MainFrame;

/**
 * @author princearora
 */
public class App {

    
    public static void main(String[] args) {
    	LoggerConfig.init();
    	Logger initLog = LoggerFactory.getLogger(App.class);
    	
    	initLog.info("******************* Initializing Game *******************");
    	
        new MainFrame();
    }
}
