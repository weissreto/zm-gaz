package ch.rweiss.alge;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import ch.rweiss.alge.time.Time;
import ch.rweiss.alge.time.TimeKind;
import ch.rweiss.alge.time.TimeToStringConverter;

public class TimeDisplay
{
  private static boolean test;
  private static boolean timeOfDay;
  private SevenSegDisplay sevenSegDisplay;
  private static final Logger LOGGER = LogManager.getFormatterLogger();

  public static void main(String[] argv) throws InterruptedException, IOException
  {    
    init(argv);
    TimeDisplay display = new TimeDisplay();
    display.start();
  }

  private static void init(String[] argv)
  {
    for (String arg : argv)
    {
      if ("-debug".equalsIgnoreCase(arg))
      {
        Configurator.setRootLevel(Level.DEBUG);
        LOGGER.info("Log level is DEBUG");
      }
      if ("-trace".equalsIgnoreCase(arg))
      {
        Configurator.setRootLevel(Level.TRACE);
        LOGGER.info("Log level is TRACE");
      }

      if ("-test".equalsIgnoreCase(arg))
      {
        LOGGER.info("Test mode");
        test = true;
      }   
      if ("-daytime".equalsIgnoreCase(arg))
      {
        LOGGER.info("Display time of day");
        timeOfDay = true;
      }
    }
  }

  private void start() throws InterruptedException, IOException
  {
    startSevenSegDisplay();
    if (test)
    {
      displayTest();
    }
    else if (timeOfDay)
    {
      displayTimeOfDay();
    }
    TimerS3 timer = new TimerS3();
    timer.start();
    timer.addTimeListener(this::newTimeAvailable);
    timer.read();
  }

  private void newTimeAvailable(Time time)
  {
    if (time.getKind() == TimeKind.MASS_START || time.getKind() == TimeKind.START)
    {
      sevenSegDisplay.display("start ");
      return;
    }
    if (time.isNull())
    {
      sevenSegDisplay.display("ready ");
      return;
    }
    if (time.isBlank()) 
    {
      sevenSegDisplay.display("      ");
      return;
    }
    if (time.getKind() == TimeKind.RUNNING || time.getKind() == TimeKind.WINNER)
    {
      String timeString = new TimeToStringConverter(time).toMinutesSecondsHundrethsString();
      sevenSegDisplay.display(timeString);
      return;
    } 
  }

  private void displayTest() throws InterruptedException
  {
    int count = 0;
    while (true)
    {
      int digit0 = count % 10;
      int digit1 = (count + 1) % 10;
      int digit2 = (count + 2) % 10;
      int digit3 = (count + 3) % 10;
      int digit4 = (count + 4) % 10;
      int digit5 = (count + 5) % 10;
      sevenSegDisplay.display("      ");
      Thread.sleep(1000l);
      sevenSegDisplay.display("" + digit0 + digit1 + digit2 + digit3 + digit4 + digit5);
      Thread.sleep(1000l);
      count++;
    }
  }

  private void startSevenSegDisplay() throws IOException, InterruptedException
  {
    sevenSegDisplay = new SevenSegDisplay();
    sevenSegDisplay.start();
    sevenSegDisplay.test();
  }
  
  private void displayTimeOfDay() throws InterruptedException
  {
    while(true)
    {
      Date date = new Date();
      String time = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.GERMAN).format(date);
      sevenSegDisplay.display(time);
      Thread.sleep(1000L);
    }
  }
}
