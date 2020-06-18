package logging;

/**
 * This class is intended to write logs out to a file
 */


import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogThis {

    public static void logStuff(String message, Exception record) {
        Logger LOGGER = Logger.getLogger(String.valueOf(record));


// TODO: 2020. 06. 16. Need to change place to log to a better place!!!
        try {
            File logPath = new File("C:\\Users\\Siraly\\testing\\game\\src\\test");
            if (!(logPath.exists())) {
                logPath.mkdir();
            }
            FileHandler fileHandler = new FileHandler(logPath + File.separator + "output.log", true);
            /* Format logs to a human readable format using SimpleFormatter*/
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.info(message);
            fileHandler.close();

        } catch (IOException e) {
            /* Avoiding the use of e.printStackTrace() -> it's output is consuming memory and prints out everything
             also information might not needed(confusing) in case of debugging a application */
            LOGGER.log(Level.INFO, e.getMessage(), e);


        }

    }

}
