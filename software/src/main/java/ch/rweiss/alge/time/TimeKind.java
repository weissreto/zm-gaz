package ch.rweiss.alge.time;

public enum TimeKind 
{
	START ("SZ", null),
	MASS_START ("MS", null),
	FINSIH ("ZZ", "C"),
	INTERMEDIATE ("ZW", null),
	RUNNING ("LZ", ".");

	private String shortcutS3;
	private String shortcutOptic;
	private TimeKind(String shortcutS3, String shortcutOptic)
	{
		this.shortcutS3 = shortcutS3;
		this.shortcutOptic = shortcutOptic;
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
		return time.startsWith(shortcutS3) || 
			(shortcutOptic != null && time.startsWith(shortcutOptic));
	}
}
