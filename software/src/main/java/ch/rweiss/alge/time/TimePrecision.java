package ch.rweiss.alge.time;

public enum TimePrecision 
{
	SECOND 
	{
		@Override
    public int getMilliDigits()
		{
			return 0;
		}
	},
	TENTH
	{
		@Override
    public int getMilliDigits()
		{
			return 1;
		}
	},
	HUNDREDTH
	{
		@Override
    public int getMilliDigits()
		{
			return 2;
		}
	},
	TAUSENDTH
	{
		@Override
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
