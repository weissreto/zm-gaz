package ch.rweiss.alge.time;

import java.util.Formatter;

public class TimeToStringConverter
{
  private final Time time;

  public TimeToStringConverter(Time time)
  {
    this.time = time;
  }
  
  public String toString(TimePrecision formatPrecision)
  {
    formatPrecision = time.getPrecision().lower(formatPrecision);
    StringBuilder builder = new StringBuilder();
    try (Formatter formatter = new Formatter(builder))
    {
      formatter.format("%02d:%02d:%02d", time.getHours(), time.getMinutes(), time.getSeconds());
      if (formatPrecision != TimePrecision.SECOND)
      {
        int milliDigits = formatPrecision.getMilliDigits();
        formatter.format(".%0"+milliDigits+"d", getMilliSeconds(formatPrecision));
      }
    }
    return builder.toString();
  }
  
  public String toShortString(TimePrecision formatPrecision)
  {
    formatPrecision = time.getPrecision().lower(formatPrecision);
    StringBuilder builder = new StringBuilder();
    try (Formatter formatter = new Formatter(builder))
    {
      if (time.getHours() > 0)
      {
        formatter.format("%02d:%02d:%02d", time.getHours(), time.getMinutes(), time.getSeconds());
      }
      else if (time.getMinutes() > 0)
      {
        formatter.format("%02d:%02d", time.getMinutes(), time.getSeconds());
      }
      else 
      {
        formatter.format("%02d", time.getSeconds());
      }
        
      if (formatPrecision != TimePrecision.SECOND)
      {
        int milliDigits = formatPrecision.getMilliDigits();
        formatter.format(".%0"+milliDigits+"d", getMilliSeconds(formatPrecision));
      }
    }
    return builder.toString();
  }
  
  public String toMinutesSecondsHundrethsString()
  {
    StringBuilder builder = new StringBuilder();
    try (Formatter formatter = new Formatter(builder))
    {
      boolean onlyBlanks = true;
      if (time.getMinutes() == 0)
      {
        formatter.format("  ");
      }
      else
      {
        formatter.format("%2d", time.getMinutes());
        onlyBlanks = false;
      }
      if (onlyBlanks)
      {
        builder.append(" ");
        formatter.format("%2d", time.getSeconds());
      }
      else
      {
        builder.append(":");
        formatter.format("%02d", time.getSeconds());
      }
      if (time.getPrecision() == TimePrecision.SECOND)
      {
        builder.append("   ");
      }
      else
      {
        builder.append(".");
        if (time.getPrecision() == TimePrecision.TENTH)
        {
          formatter.format("%1d ", getMilliSeconds(time.getPrecision()));
        }
        else
        {
          formatter.format("%02d", getMilliSeconds(TimePrecision.HUNDREDTH));
        }
      }
    }
    return builder.toString();
  }

  
  private int getMilliSeconds(TimePrecision formatPrecision) 
  {
    if (formatPrecision == TimePrecision.SECOND)
    {
      return 0;     
    }
    int milliseconds = time.getMilliSeconds();
    if (formatPrecision == TimePrecision.TENTH)
    {
      return milliseconds/100;
    }
    if (formatPrecision == TimePrecision.HUNDREDTH)
    {
      return time.getHundredths();
    }
    return milliseconds;
  }


}
