package ch.rweiss.alge.time;

import org.apache.commons.lang3.StringUtils;

/**
 * Format
 * Character Position   0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 
 * 					    1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4
 * Timer S3             s s s K K       H H : M M : S S . Z h T     N 
 * Alge Optic                 K         H H : M M : S S . Z h T       N 
 *
 * K: Time Kind. TimerS3: "SZ", "MS", "ZZ", "ZW", "LZ", " ". Alge Optic: " ",".", "C".
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
	private static final String MILLI_DELIMITER = ".";
	private static final String DELIMITER = ":";
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
		String timeString = time;
		timeString = parseTimeKind(timeString);
		timeString = parseHours(timeString);
		timeString = parseMinutes(timeString);
		timeString = parseSeconds(timeString);
		parseMilliSeconds(timeString);
	}

	private String parseTimeKind(String timeString) 
	{
	  String timeKind = StringUtils.substring(timeString, 0, 5);
		kind = TimeKind.parse(timeKind);
		timeString = StringUtils.substring(timeString, 5);
		return timeString.trim();
	}

	private String parseHours(String timeString) 
	{
		if (StringUtils.countMatches(timeString, DELIMITER)==2)
		{
			hours = parseInt(timeString, DELIMITER);
			timeString = StringUtils.substringAfter(timeString, DELIMITER);
		}
		else
		{
			hours = 0;
		}
		return timeString;
	}
	
	private String parseMinutes(String timeString)
	{
		minutes = parseInt(timeString, DELIMITER);
		timeString = StringUtils.substringAfter(timeString,  DELIMITER);
		return timeString;
	}
	
	private String parseSeconds(String timeString) 
	{
		if (StringUtils.contains(timeString, MILLI_DELIMITER))
		{
			seconds = parseInt(timeString, MILLI_DELIMITER);
			timeString = StringUtils.substringAfter(timeString, MILLI_DELIMITER);
		}
		else if (StringUtils.isBlank(timeString))
		{
			seconds = 0;			
			timeString="";
		}
		else
		{
			seconds = Integer.parseInt(timeString);
			timeString = "";
		}
		return timeString;
	}
	
	private void parseMilliSeconds(String timeString)
	{
		if (timeString.isEmpty())
		{
			precision = TimePrecision.SECOND;
			milliseconds = 0;
			return;
		}
		milliseconds = Integer.parseInt(timeString);
		if (timeString.length()==1)
		{
			precision = TimePrecision.TENTH;
			milliseconds = milliseconds*100;
		}
		else if (timeString.length() == 2)
		{
			precision = TimePrecision.HUNDREDTH;
			milliseconds = milliseconds*10;
		}
		else 
		{
			precision = TimePrecision.TAUSENDTH;
		}
	}

	private static int parseInt(String str, String delimiter)
	{
		return Integer.parseInt(StringUtils.substringBefore(str,  delimiter));
	}

	public boolean isNull() 
	{		
		return hours==0 && minutes==0 && seconds == 0 && milliseconds == 0 && precision == TimePrecision.HUNDREDTH;
	}
}
