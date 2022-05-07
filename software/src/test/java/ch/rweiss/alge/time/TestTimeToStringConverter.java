package ch.rweiss.alge.time;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TestTimeToStringConverter
{
  @Test
  void toMinutesSecondsHundrethsString()
  {
    assertThat(converter("   LZ   00:12:34.56    ").toMinutesSecondsHundrethsString())
        .isEqualTo("12:34.56");
    assertThat(converter("   LZ   00:12:34       ").toMinutesSecondsHundrethsString())
        .isEqualTo("12:34   ");
    assertThat(converter("   LZ   00:12:34.00    ").toMinutesSecondsHundrethsString())
        .isEqualTo("12:34.00");
    assertThat(converter("   LZ   00:12:34.567   ").toMinutesSecondsHundrethsString())
        .isEqualTo("12:34.56");
    assertThat(converter("   LZ      00:34.5     ").toMinutesSecondsHundrethsString())
        .isEqualTo("   34.5 ");
    assertThat(converter("   LZ      01:34.5     ").toMinutesSecondsHundrethsString())
        .isEqualTo(" 1:34.5 ");
    assertThat(converter("   LZ       1:34.5     ").toMinutesSecondsHundrethsString())
        .isEqualTo(" 1:34.5 ");
    assertThat(converter("   .           0.1     ").toMinutesSecondsHundrethsString())
        .isEqualTo("    0.1 ");
    assertThat(converter("   .           1.2     ").toMinutesSecondsHundrethsString())
        .isEqualTo("    1.2 ");
    assertThat(converter("004.          52.3     ").toMinutesSecondsHundrethsString())
        .isEqualTo("   52.3 ");
    assertThat(converter("004.          12.3     ").toMinutesSecondsHundrethsString())
        .isEqualTo("   12.3 ");
    assertThat(converter("   .          12.34    ").toMinutesSecondsHundrethsString())
        .isEqualTo("   12.34");
    assertThat(converter("   .          12.346   ").toMinutesSecondsHundrethsString())
        .isEqualTo("   12.34");
  }

  @Test
  void toShortString_second()
  {
    assertThat(converter("   LZ   00:12:34.56    ").toShortString(TimePrecision.SECOND))
        .isEqualTo("12:34");
    assertThat(converter("   LZ   00:12:34       ").toShortString(TimePrecision.SECOND))
        .isEqualTo("12:34");
    assertThat(converter("   LZ   00:12:34.00    ").toShortString(TimePrecision.SECOND))
        .isEqualTo("12:34");
    assertThat(converter("   LZ   00:12:34.567   ").toShortString(TimePrecision.SECOND))
        .isEqualTo("12:34");
    assertThat(converter("   LZ      00:34.5     ").toShortString(TimePrecision.SECOND))
        .isEqualTo("34");
    assertThat(converter("   LZ      01:34.5     ").toShortString(TimePrecision.SECOND))
        .isEqualTo("01:34");
    assertThat(converter("   LZ   14:01:34.5     ").toShortString(TimePrecision.SECOND))
        .isEqualTo("14:01:34");

  }
  
  @Test
  void toShortString_tenth() 
  {
    assertThat(converter("   LZ   00:12:34.56    ").toShortString(TimePrecision.TENTH))
        .isEqualTo("12:34.5");
    assertThat(converter("   LZ   00:12:34       ").toShortString(TimePrecision.TENTH))
        .isEqualTo("12:34");
    assertThat(converter("   LZ   00:12:34.00    ").toShortString(TimePrecision.TENTH))
        .isEqualTo("12:34.0");
    assertThat(converter("   LZ   00:12:34.567   ").toShortString(TimePrecision.TENTH))
        .isEqualTo("12:34.5");
    assertThat(converter("   LZ      00:34.5     ").toShortString(TimePrecision.TENTH))
        .isEqualTo("34.5");
    assertThat(converter("   LZ      01:34.5     ").toShortString(TimePrecision.TENTH))
        .isEqualTo("01:34.5");
    assertThat(converter("   LZ   14:01:34.5     ").toShortString(TimePrecision.TENTH))
        .isEqualTo("14:01:34.5");
  }

  @Test
  void toShortString_hundredth() 
  {
    assertThat(converter("   LZ   00:12:34.56    ").toShortString(TimePrecision.HUNDREDTH))
        .isEqualTo("12:34.56");
    assertThat(converter("   LZ   00:12:34       ").toShortString(TimePrecision.HUNDREDTH))
        .isEqualTo("12:34");
    assertThat(converter("   LZ   00:12:34.00    ").toShortString(TimePrecision.HUNDREDTH))
        .isEqualTo("12:34.00");
    assertThat(converter("   LZ   00:12:34.567   ").toShortString(TimePrecision.HUNDREDTH))
        .isEqualTo("12:34.56");
    assertThat(converter("   LZ      00:34.5     ").toShortString(TimePrecision.HUNDREDTH))
        .isEqualTo("34.5");
    assertThat(converter("   LZ      01:34.5     ").toShortString(TimePrecision.HUNDREDTH))
        .isEqualTo("01:34.5");
    assertThat(converter("   LZ   14:01:34.5     ").toShortString(TimePrecision.HUNDREDTH))
        .isEqualTo("14:01:34.5");
  }
  
  @Test
  void toShortString_tausendth() 
  {
    assertThat(converter("   LZ   00:12:34.56    ").toShortString(TimePrecision.TAUSENDTH))
        .isEqualTo("12:34.56");
    assertThat(converter("   LZ   00:12:34       ").toShortString(TimePrecision.TAUSENDTH))
        .isEqualTo("12:34");
    assertThat(converter("   LZ   00:12:34.00    ").toShortString(TimePrecision.TAUSENDTH))
        .isEqualTo("12:34.00");
    assertThat(converter("   LZ   00:12:34.567   ").toShortString(TimePrecision.TAUSENDTH))
        .isEqualTo("12:34.567");
    assertThat(converter("   LZ      00:34.5     ").toShortString(TimePrecision.TAUSENDTH))
        .isEqualTo("34.5");
    assertThat(converter("   LZ      01:34.5     ").toShortString(TimePrecision.TAUSENDTH))
        .isEqualTo("01:34.5");
    assertThat(converter("   LZ   14:01:34.5     ").toShortString(TimePrecision.TAUSENDTH))
        .isEqualTo("14:01:34.5");
  }

  @Test
  void toString_second()
  {
    assertThat(converter("   LZ   00:12:34.56    ").toString(TimePrecision.SECOND))
        .isEqualTo("00:12:34");
    assertThat(converter("   LZ   00:12:34       ").toString(TimePrecision.SECOND))
        .isEqualTo("00:12:34");
    assertThat(converter("   LZ   00:12:34.00    ").toString(TimePrecision.SECOND))
        .isEqualTo("00:12:34");
    assertThat(converter("   LZ   00:12:34.567   ").toString(TimePrecision.SECOND))
        .isEqualTo("00:12:34");
    assertThat(converter("   LZ      00:34.5     ").toString(TimePrecision.SECOND))
        .isEqualTo("00:00:34");
    assertThat(converter("   LZ      01:34.5     ").toString(TimePrecision.SECOND))
        .isEqualTo("00:01:34");
    assertThat(converter("   LZ   14:01:34.5     ").toString(TimePrecision.SECOND))
        .isEqualTo("14:01:34");
  }
  
  @Test
  void toString_tenth() 
  {
    assertThat(converter("   LZ   00:12:34.56    ").toString(TimePrecision.TENTH))
        .isEqualTo("00:12:34.5");
    assertThat(converter("   LZ   00:12:34       ").toString(TimePrecision.TENTH))
        .isEqualTo("00:12:34");
    assertThat(converter("   LZ   00:12:34.00    ").toString(TimePrecision.TENTH))
        .isEqualTo("00:12:34.0");
    assertThat(converter("   LZ   00:12:34.567   ").toString(TimePrecision.TENTH))
        .isEqualTo("00:12:34.5");
    assertThat(converter("   LZ      00:34.5     ").toString(TimePrecision.TENTH))
        .isEqualTo("00:00:34.5");
    assertThat(converter("   LZ      01:34.5     ").toString(TimePrecision.TENTH))
        .isEqualTo("00:01:34.5");
    assertThat(converter("   LZ   14:01:34.5     ").toString(TimePrecision.TENTH))
        .isEqualTo("14:01:34.5");
  }

  @Test
  void toString_hundredth() 
  {
    assertThat(converter("   LZ   00:12:34.56    ").toString(TimePrecision.HUNDREDTH))
        .isEqualTo("00:12:34.56");
    assertThat(converter("   LZ   00:12:34       ").toString(TimePrecision.HUNDREDTH))
        .isEqualTo("00:12:34");
    assertThat(converter("   LZ   00:12:34.00    ").toString(TimePrecision.HUNDREDTH))
        .isEqualTo("00:12:34.00");
    assertThat(converter("   LZ   00:12:34.567   ").toString(TimePrecision.HUNDREDTH))
        .isEqualTo("00:12:34.56");
    assertThat(converter("   LZ      00:34.5     ").toString(TimePrecision.HUNDREDTH))
        .isEqualTo("00:00:34.5");
    assertThat(converter("   LZ      01:34.5     ").toString(TimePrecision.HUNDREDTH))
        .isEqualTo("00:01:34.5");
    assertThat(converter("   LZ   14:01:34.5     ").toString(TimePrecision.HUNDREDTH))
        .isEqualTo("14:01:34.5");
  }
  
  @Test
  void toString_tausendth() 
  {
    assertThat(converter("   LZ   00:12:34.56    ").toString(TimePrecision.TAUSENDTH))
        .isEqualTo("00:12:34.56");
    assertThat(converter("   LZ   00:12:34       ").toString(TimePrecision.TAUSENDTH))
        .isEqualTo("00:12:34");
    assertThat(converter("   LZ   00:12:34.00    ").toString(TimePrecision.TAUSENDTH))
        .isEqualTo("00:12:34.00");
    assertThat(converter("   LZ   00:12:34.567   ").toString(TimePrecision.TAUSENDTH))
        .isEqualTo("00:12:34.567");
    assertThat(converter("   LZ      00:34.5     ").toString(TimePrecision.TAUSENDTH))
        .isEqualTo("00:00:34.5");
    assertThat(converter("   LZ      01:34.5     ").toString(TimePrecision.TAUSENDTH))
        .isEqualTo("00:01:34.5");
    assertThat(converter("   LZ   14:01:34.5     ").toString(TimePrecision.TAUSENDTH))
        .isEqualTo("14:01:34.5");
  }
  
  private static TimeToStringConverter converter(String time)
  {
    return new TimeToStringConverter(Time.parse(time));
  }
}
