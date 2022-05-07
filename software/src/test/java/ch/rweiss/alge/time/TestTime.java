package ch.rweiss.alge.time;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TestTime
{
  @Test
  void s3_start() 
  {
    Time time = Time.parse("   SZ   12:13:14.56   ");
    assertThat(time.getKind()).isEqualTo(TimeKind.START);
    assertThat(time.getHours()).isEqualTo(12);
    assertThat(time.getMinutes()).isEqualTo(13);
    assertThat(time.getSeconds()).isEqualTo(14);
    assertThat(time.getHundredths()).isEqualTo(56);
    assertThat(time.getMilliSeconds()).isEqualTo(560);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.HUNDREDTH);
  }
  
  @Test
  void s3_massStart() 
  {
    Time time = Time.parse("   MS   00:01:23      ");
    assertThat(time.getKind()).isEqualTo(TimeKind.MASS_START);
    assertThat(time.getHours()).isEqualTo(00);
    assertThat(time.getMinutes()).isEqualTo(01);
    assertThat(time.getSeconds()).isEqualTo(23);
    assertThat(time.getHundredths()).isEqualTo(0);
    assertThat(time.getMilliSeconds()).isEqualTo(0);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.SECOND);
  }

  @Test
  void s3_finsih() 
  {
    Time time = Time.parse("   ZZ   23:59:31.789  ");
    assertThat(time.getKind()).isEqualTo(TimeKind.FINSIH);
    assertThat(time.getHours()).isEqualTo(23);
    assertThat(time.getMinutes()).isEqualTo(59);
    assertThat(time.getSeconds()).isEqualTo(31);
    assertThat(time.getHundredths()).isEqualTo(78);
    assertThat(time.getMilliSeconds()).isEqualTo(789);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.TAUSENDTH);
  }

  @Test
  void s3_intermediate() 
  {
    Time time = Time.parse("   ZW   21:59:48.5    ");
    assertThat(time.getKind()).isEqualTo(TimeKind.INTERMEDIATE);
    assertThat(time.getHours()).isEqualTo(21);
    assertThat(time.getMinutes()).isEqualTo(59);
    assertThat(time.getSeconds()).isEqualTo(48);
    assertThat(time.getHundredths()).isEqualTo(50);
    assertThat(time.getMilliSeconds()).isEqualTo(500);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.TENTH);
  }

  @Test
  void s3_running() 
  {
    Time time = Time.parse("   LZ   00:12:34.56   ");
    assertThat(time.getKind()).isEqualTo(TimeKind.RUNNING);
    assertThat(time.getHours()).isEqualTo(00);
    assertThat(time.getMinutes()).isEqualTo(12);
    assertThat(time.getSeconds()).isEqualTo(34);
    assertThat(time.getHundredths()).isEqualTo(56);
    assertThat(time.getMilliSeconds()).isEqualTo(560);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.HUNDREDTH);
  }

  @Test
  void optic_running() 
  {
    Time time = Time.parse("   .    00:12:34.5     ");
    assertThat(time.getKind()).isEqualTo(TimeKind.RUNNING);
    assertThat(time.getHours()).isEqualTo(00);
    assertThat(time.getMinutes()).isEqualTo(12);
    assertThat(time.getSeconds()).isEqualTo(34);
    assertThat(time.getHundredths()).isEqualTo(50);
    assertThat(time.getMilliSeconds()).isEqualTo(500);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.TENTH);
  }

  @Test
  void optic_winner() 
  {
    Time time = Time.parse("   C    15:26:37.489   ");
    assertThat(time.getKind()).isEqualTo(TimeKind.WINNER);
    assertThat(time.getHours()).isEqualTo(15);
    assertThat(time.getMinutes()).isEqualTo(26);
    assertThat(time.getSeconds()).isEqualTo(37);
    assertThat(time.getHundredths()).isEqualTo(48);
    assertThat(time.getMilliSeconds()).isEqualTo(489);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.TAUSENDTH);
  }
  
  @Test
  void no_hour() 
  {
    Time time = Time.parse("   C       26:37.489   ");
    assertThat(time.getKind()).isEqualTo(TimeKind.WINNER);
    assertThat(time.getHours()).isEqualTo(0);
    assertThat(time.getMinutes()).isEqualTo(26);
    assertThat(time.getSeconds()).isEqualTo(37);
    assertThat(time.getHundredths()).isEqualTo(48);
    assertThat(time.getMilliSeconds()).isEqualTo(489);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.TAUSENDTH);
  }
  
  @Test
  void no_minutes() 
  {
    Time time = Time.parse("001.           0.1     ");
    assertThat(time.getKind()).isEqualTo(TimeKind.RUNNING);
    assertThat(time.getHours()).isEqualTo(0);
    assertThat(time.getMinutes()).isEqualTo(0);
    assertThat(time.getSeconds()).isEqualTo(0);
    assertThat(time.getHundredths()).isEqualTo(10);
    assertThat(time.getMilliSeconds()).isEqualTo(100);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.TENTH);

    time = Time.parse("002C          55.79      ");
    assertThat(time.getKind()).isEqualTo(TimeKind.WINNER);
    assertThat(time.getHours()).isEqualTo(0);
    assertThat(time.getMinutes()).isEqualTo(0);
    assertThat(time.getSeconds()).isEqualTo(55);
    assertThat(time.getHundredths()).isEqualTo(79);
    assertThat(time.getMilliSeconds()).isEqualTo(790);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.HUNDREDTH);
  }

  @Test
  void no_seconds() 
  {
    Time time = Time.parse("   C       26:         ");
    assertThat(time.getKind()).isEqualTo(TimeKind.WINNER);
    assertThat(time.getHours()).isEqualTo(0);
    assertThat(time.getMinutes()).isEqualTo(26);
    assertThat(time.getSeconds()).isEqualTo(0);
    assertThat(time.getHundredths()).isEqualTo(0);
    assertThat(time.getMilliSeconds()).isEqualTo(0);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.SECOND);
  }

  @Test
  void no_millis() 
  {
    Time time = Time.parse("   C       26:37       ");
    assertThat(time.getKind()).isEqualTo(TimeKind.WINNER);
    assertThat(time.getHours()).isEqualTo(0);
    assertThat(time.getMinutes()).isEqualTo(26);
    assertThat(time.getSeconds()).isEqualTo(37);
    assertThat(time.getHundredths()).isEqualTo(0);
    assertThat(time.getMilliSeconds()).isEqualTo(0);
    assertThat(time.getPrecision()).isEqualTo(TimePrecision.SECOND);
  }
  
  @Test
  void isNull()
  {
    assertThat(Time.parse("   C           0.00      ").isNull()).as("OPTIc3").isTrue();
    assertThat(Time.parse("   C    00:00:00.00      ").isNull()).as("OPTIc").isTrue();
    assertThat(Time.parse("   C    00:00:00         ").isNull()).isFalse();
    assertThat(Time.parse("   C    00:00:00.000     ").isNull()).isFalse();
    assertThat(Time.parse("   C    00:00:00.001     ").isNull()).isFalse();
    assertThat(Time.parse("   C    00:00:00.0       ").isNull()).isFalse();
    assertThat(Time.parse("   C    00:00:02.1       ").isNull()).isFalse();
    assertThat(Time.parse("   C    00:03:00.00      ").isNull()).isFalse();
    assertThat(Time.parse("   C    04:00:00.00      ").isNull()).isFalse();
  }
  
  @Test
  void isBlank() 
  {
    assertThat(Time.parse("                         ").isBlank()).isTrue();
    assertThat(Time.parse("1                        ").isBlank()).isFalse();
    assertThat(Time.parse("                        1").isBlank()).isFalse();
    assertThat(Time.parse("            1            ").isBlank()).isFalse();
  }
  
  @Test
  void string()
  {
    assertThat(Time.parse("   C    15:26:37.489     ").toString()).isEqualTo("   C    15:26:37.489     ");    
  }
}
