package ch.rweiss.alge.time;

import org.apache.commons.lang3.StringUtils;

/**
 * Format
 * Character Position   0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 
 *                      1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4
 * Timer S3             s s s K K       H H : M M : S S . Z h T     N 
 * Alge Optic                 K         H H : M M : S S . Z h T       N
 * Alge Optic3          s s s K         H H : M M : S S . Z h T       N
 *
 * s: Run Number 
 * K: Time Kind. 
 *    TimerS3:     "SZ", "MS", "ZZ", "ZW", "LZ", " ". 
 *    Alge Optic:  " ",".", "C".
 *    Alge Optic3: " ", ".", "C"
 * H: Hour
 * M: Minutes
 * S: Seconds
 * Z: Tenths of a second
 * h: Hundreths of a second
 * T: Tausends of a second
 * N: New Line (character 0x0d)
 */
public class Time 
{
	private int hours;
	private int minutes;
	private int seconds;
	private int milliseconds;
	private TimePrecision precision;
	private TimeKind kind;
	private String time;
	
	private Time(String time)
	{
		this.time = time;
		parse();
	}

	public int getHours()
	{
		return hours;
	}
	
	public int getMinutes()
	{
		return minutes;
	}
	
	public int getSeconds()
	{
		return seconds;
	}
	
	public int getHundredths()
	{
		return milliseconds/10;
	}
	
	public int getMilliSeconds()
	{
		return milliseconds;
	}
	
	public TimePrecision getPrecision()
	{
		return precision;
	}
	
	public TimeKind getKind()
	{
		return kind;
	}	
	
	@Override
  public String toString()
	{
		return time;
	}	
	
	public static Time parse(String time)
	{
		return new Time(time);
	}

	private void parse() 
	{
		parseTimeKind();
		parseHours();
		parseMinutes();
		parseSeconds();
		parseMilliSeconds();
	}

	private void parseTimeKind() 
	{
	  String timeKind = part(3, 5);
		kind = TimeKind.parse(timeKind);
	}

  private void parseHours() 
	{
		hours = parseDigits(8, 10);
	}
	
	private void parseMinutes()
	{
		minutes = parseDigits(11, 13);
	}
	
	private void parseSeconds() 
	{
	  seconds = parseDigits(14, 16);
	}
	
	private void parseMilliSeconds()
	{
	  String millis = part(17, 20);
		if (millis.isEmpty())
		{
			precision = TimePrecision.SECOND;
			milliseconds = 0;
			return;
		}
		milliseconds = Integer.parseInt(millis);
		if (millis.length()==1)
		{
			precision = TimePrecision.TENTH;
			milliseconds = milliseconds*100;
		}
		else if (millis.length() == 2)
		{
			precision = TimePrecision.HUNDREDTH;
			milliseconds = milliseconds*10;
		}
		else 
		{
			precision = TimePrecision.TAUSENDTH;
		}
	}

	private int parseDigits(int start, int end)
	{
	  String digits = part(start, end);
    if (StringUtils.isBlank(digits)) 
    {
      return 0;
    }
		return Integer.parseInt(digits);
	}
	
	private String part(int start, int end)
  {
	  return StringUtils.substring(time, start, end).trim();
	}

	public boolean isNull() 
	{		
		return hours==0 && minutes==0 && seconds == 0 && milliseconds == 0 && precision == TimePrecision.HUNDREDTH;
	}

  public boolean isBlank()
  {
    return StringUtils.isBlank(time);
  }
}
