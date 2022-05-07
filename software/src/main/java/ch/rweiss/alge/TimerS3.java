package ch.rweiss.alge;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;

import ch.rweiss.alge.time.Time;
import ch.rweiss.alge.time.TimeListener;

public class TimerS3
{
  private StringBuilder inputBuffer = new StringBuilder();
  private List<TimeListener> listeners = new ArrayList<>();
  private Serial serial;
  private static final Logger LOGGER = LogManager.getFormatterLogger();

  public void start()
  {
    LOGGER.info("Start Serial Port "+Serial.DEFAULT_COM_PORT +" with 2400 Baud");
    serial = SerialFactory.createInstance();
    serial.open(Serial.DEFAULT_COM_PORT, 2400);
  }

  public void read()
  {
    LOGGER.info("Start reading serial data from ALGE Timing");
    while (true)
    {
      String line = readNextLine();
      LOGGER.debug("Received line: %s", line);
      try
      {
        Time time = Time.parse(line);
        sendTimeToListeners(time);
      }
      catch (NumberFormatException ex)
      {
        LOGGER.error("Could not parse line '"+line+"'", ex);
      }
    }
  }

  private String readNextLine()
  {
    while (true)
    {
      char data = readData();
      inputBuffer.append(data);

      if (isLineEnd(data))
      {
        String input = inputBuffer.toString();
        inputBuffer = new StringBuilder();
        if (isCompleteLine(input))
        {
          return input;
        }
      }
    }
  }

  private char readData()
  {
    char data;
    do
    {
      data = serial.read();
    }
    while (data == 0xffff);
    data = convertTo7DataBits(data);
    LOGGER.trace("Received Byte: %02x", (int) data);
    return data;
  }

  private static char convertTo7DataBits(char data)
  {
    data = (char) (data & 0b0111_1111);
    return data;
  }

  private static boolean isLineEnd(char data)
  {
    return data == '\r';
  }

  private static boolean isCompleteLine(String input)
  {
    return input.length() >= 19;
  }

  private void sendTimeToListeners(Time time)
  {
    for (TimeListener listener : listeners)
    {
      listener.newTimeAvailable(time);
    }
  }

  public void addTimeListener(TimeListener listener)
  {
    listeners.add(listener);
  }
}
