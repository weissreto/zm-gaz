package ch.rweiss.alge.time;

import java.util.Formatter;

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
	
	public String toString()
	{
		return time;
	}	

	public String formatTime(TimePrecision precision)
	{
		precision = this.precision.lower(precision);
		StringBuilder builder = new StringBuilder();
		try (Formatter formatter = new Formatter(builder))
		{
			if (hours > 0)
			{
				formatter.format("%02d:%02d:%02d", hours, minutes, seconds);
			}
			else if (minutes > 0)
			{
				formatter.format("%02d:%02d", minutes, seconds);
			}
			else 
			{
				formatter.format("%02d", seconds);
			}
				
			if (precision != TimePrecision.SECOND)
			{
				int milliDigits = precision.getMilliDigits();
				formatter.format(".%0"+milliDigits+"d", getMilliSeconds(precision));
			}
		}
		return builder.toString();
	}

	public String formatTimeFull(TimePrecision precision)
	{
		precision = this.precision.lower(precision);
		StringBuilder builder = new StringBuilder();
		try (Formatter formatter = new Formatter(builder))
		{
			formatter.format("%02d:%02d:%02d", hours, minutes, seconds);
			if (precision != TimePrecision.SECOND)
			{
				int milliDigits = precision.getMilliDigits();
				formatter.format(".%0"+milliDigits+"d", getMilliSeconds(precision));
			}
		}
		return builder.toString();
	}
	
	public String formatTimeFullWithoutDelimiters(TimePrecision precision)
	{
		StringBuilder builder = new StringBuilder();
		try (Formatter formatter = new Formatter(builder))
		{
			boolean onlyBlanks = true;
			if (hours == 0)
			{
				formatter.format("  ");
			}	
			else
			{
				formatter.format("%2d", hours);
				onlyBlanks = false;
			}
			if (minutes == 0 && onlyBlanks)
			{
				formatter.format("  ");
			}
			else
			{
				if (onlyBlanks)
				{
					formatter.format("%2d", minutes);
				}
				else
				{
					formatter.format("%02d", minutes);
				}
				onlyBlanks = false;
			}
			if (onlyBlanks)
			{
				formatter.format("%2d", seconds);
			}
			else
			{
				formatter.format("%02d", seconds);
			}
			if (precision != TimePrecision.SECOND)
			{				
				int milliDigits = precision.getMilliDigits();
				TimePrecision lowerPrecision = this.precision.lower(precision);
				int availDigits = lowerPrecision.getMilliDigits();
				if (availDigits != 0)
				{
					formatter.format("%0"+availDigits+"d", getMilliSeconds(lowerPrecision));
				}
				for (int blanks = 0; blanks < milliDigits-availDigits; blanks++)
				{
					formatter.format(" ");
				}
			}
		}
		return builder.toString();
	}
	
	public static Time parse(String time)
	{
		return new Time(time);
	}

	private int getMilliSeconds(TimePrecision precision) 
	{
		if (precision == TimePrecision.SECOND)
		{
			return 0;			
		}
		if (precision == TimePrecision.TENTH)
		{
			return milliseconds/100;
		}
		if (precision == TimePrecision.HUNDREDTH)
		{
			return getHundredths();
		}
		return milliseconds;
	}

	private void parse() 
	{
		System.out.println("Time: "+time);
		String timeString = time;
		timeString = parseTimeKind(timeString);
		timeString = parseHours(timeString);
		timeString = parseMinutes(timeString);
		timeString = parseSeconds(timeString);
		parseMilliSeconds(timeString);
	}

	private String parseTimeKind(String timeString) 
	{
		kind = TimeKind.parse(timeString);
		timeString = StringUtils.substring(timeString, 3);
		System.out.println("Kind "+kind+" left "+timeString);
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
		System.out.println("Hours: "+hours+" left "+timeString);
		return timeString;
	}
	
	private String parseMinutes(String timeString)
	{
		minutes = parseInt(timeString, DELIMITER);
		timeString = StringUtils.substringAfter(timeString,  DELIMITER);
		System.out.println("Minutes: "+minutes+" left "+timeString);
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
		System.out.println("Seconds: "+seconds+" left "+timeString);
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
		System.out.println("Millis: "+milliseconds);
	}

	private int parseInt(String str, String delimiter)
	{
		return Integer.parseInt(StringUtils.substringBefore(str,  delimiter));
	}

	public boolean isNull() 
	{		
		return hours==0 && minutes==0 && seconds == 0&& milliseconds == 0 && precision == TimePrecision.HUNDREDTH;
	}
}
