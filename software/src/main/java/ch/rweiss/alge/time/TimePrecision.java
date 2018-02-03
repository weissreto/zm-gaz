package ch.rweiss.alge.time;

public enum TimePrecision 
{
	SECOND 
	{
		public int getMilliDigits()
		{
			return 0;
		}
	},
	TENTH
	{
		public int getMilliDigits()
		{
			return 1;
		}
	},
	HUNDREDTH
	{
		public int getMilliDigits()
		{
			return 2;
		}
	},
	TAUSENDTH
	{
		public int getMilliDigits()
		{
			return 3;
		}
	};

	public abstract int getMilliDigits();

	public TimePrecision lower(TimePrecision other) 
	{
		return this.ordinal() < other.ordinal() ? this : other;
	} 
}
