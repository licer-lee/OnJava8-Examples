//: logging/LoggingLevels.java
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingLevels {
  private static Logger
  lgr = Logger.getLogger("com"),
  lgr2 = Logger.getLogger("com.bruceeckel"),
  util= Logger.getLogger("com.bruceeckel.util"),
  test= Logger.getLogger("com.bruceeckel.test"),
  rand = Logger.getLogger("random");
  private static void logMessages() {
    lgr.info("com : info");
    lgr2.info("com.bruceeckel : info");
    util.info("util : info");
    test.severe("test : severe");
    rand.info("random : info");
  }
  public static void main(String[] args) {
    lgr.setLevel(Level.SEVERE);
    System.out.println("com level: SEVERE");
    logMessages();
    util.setLevel(Level.FINEST);
    test.setLevel(Level.FINEST);
    rand.setLevel(Level.FINEST);
    System.out.println(
        "individual loggers set to FINEST");
    logMessages();
    lgr.setLevel(Level.SEVERE);
    System.out.println("com level: SEVERE");
    logMessages();
  }
} /* Output:
com level: SEVERE
individual loggers set to FINEST
com level: SEVERE
May 13, 2015 10:43:36 AM LoggingLevels logMessages
SEVERE: test : severe
May 13, 2015 10:43:36 AM LoggingLevels logMessages
INFO: random : info
May 13, 2015 10:43:36 AM LoggingLevels logMessages
INFO: util : info
May 13, 2015 10:43:36 AM LoggingLevels logMessages
SEVERE: test : severe
May 13, 2015 10:43:36 AM LoggingLevels logMessages
INFO: random : info
May 13, 2015 10:43:36 AM LoggingLevels logMessages
INFO: util : info
May 13, 2015 10:43:36 AM LoggingLevels logMessages
SEVERE: test : severe
May 13, 2015 10:43:36 AM LoggingLevels logMessages
INFO: random : info
*///:~