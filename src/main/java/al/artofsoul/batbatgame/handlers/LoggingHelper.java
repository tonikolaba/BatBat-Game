package al.artofsoul.batbatgame.handlers;

import java.util.logging.Logger;

public class LoggingHelper {

    public static final Logger LOGGER = Logger.getLogger(LoggingHelper.class.getName());

    private LoggingHelper() {
        throw new IllegalStateException("Utility Class");
    }
}
