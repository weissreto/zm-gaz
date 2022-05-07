package ch.rweiss.alge.time;

public enum TimeKind 
{
  /** Start time. TimerS3 only */
	START ("SZ"),
	/** Mass start time. TimerS3 only */
	MASS_START ("MS"),
	/** A finish time. TimerS3 only. Is sent for each stopped time */
	FINSIH ("ZZ"),
	/** The time of the winner. Optic only. Only the winner time is sent */
	WINNER("C"), 
	/** Intermediate time. TimerS3 only. Send if you press the red stop button */
	INTERMEDIATE ("ZW"),
	/** Running time */
	RUNNING (
	    "LZ",  // TimerS3 
	    "."    // Optic, Optic3
	    );

	private String[] shortcuts;
	private TimeKind(String... shortcuts)
	{
		this.shortcuts = shortcuts;
	}
	public static TimeKind parse(String time) 
	{
	  time = time.trim();
		for (TimeKind kind : values())
		{
			if (kind.matches(time))
			{
				return kind;
			}
		}
		return RUNNING;
	}
	
	private boolean matches(String time) 
	{
	  for (String shortcut : shortcuts)
	  {
	    if (time.startsWith(shortcut))
	    {
	      return true;
	    }
	  }
	  return false;
	}
}
