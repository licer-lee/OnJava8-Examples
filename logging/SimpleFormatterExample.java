//: logging/SimpleFormatterExample.java
import java.util.logging.*;

public class SimpleFormatterExample {
  private static Logger logger =
    Logger.getLogger("SimpleFormatterExample");
  private static void logMessages() {
    logger.info("Line One");
    logger.info("Line Two");
  }
  public static void main(String[] args) {
    logger.setUseParentHandlers(false);
    Handler conHdlr = new ConsoleHandler();
    conHdlr.setFormatter(new Formatter() {
      public String format(LogRecord record) {
        return record.getLevel()  + "  :  "
          + record.getSourceClassName()
          + " -:- "
          + record.getSourceMethodName()
          + " -:- "
          + record.getMessage() + "\n";
      }
    });
    logger.addHandler(conHdlr);
    logMessages();
  }
} /* Output:
INFO  :  SimpleFormatterExample -:- logMessages -:- Line One
INFO  :  SimpleFormatterExample -:- logMessages -:- Line Two
*///:~