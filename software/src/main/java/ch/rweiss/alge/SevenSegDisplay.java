package ch.rweiss.alge;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

public class SevenSegDisplay 
{	
//	private static final byte ADDRESS_REGISTER_NOOP         = 0x00;
	private static final byte ADDRESS_REGISTER_DIGIT_0      = 0x01;
//	private static final byte ADDRESS_REGISTER_DIGIT_1      = 0x02;
//	private static final byte ADDRESS_REGISTER_DIGIT_2      = 0x03;
//	private static final byte ADDRESS_REGISTER_DIGIT_3      = 0x04;
//	private static final byte ADDRESS_REGISTER_DIGIT_4      = 0x05;
//	private static final byte ADDRESS_REGISTER_DIGIT_5      = 0x06;
//	private static final byte ADDRESS_REGISTER_DIGIT_6 	    = 0x07;
//	private static final byte ADDRESS_REGISTER_DIGIT_7	    = 0x08;
	private static final byte ADDRESS_REGISTER_DECODE_MODE  = 0x09;
	private static final byte ADDRESS_REGISTER_INTENSITY    = 0x0A;
	private static final byte ADDRESS_REGISTER_SCANLIMIT    = 0x0B;
	private static final byte ADDRESS_REGISTER_SHUTDOWN     = 0x0C;
	private static final byte ADDRESS_REGISTER_DISPLAY_TEST = 0x0F;
	private static final byte FIVE = 0x05;
	private static final byte ONE = 0x01;
	private static final byte ZERO = 0x00;
	private static final String EMPTY = "       ";
    private static final byte SEG_G = 0b0000_0001;
    private static final byte SEG_F = 0b0000_0010;
    private static final byte SEG_E = 0b0000_0100;
    private static final byte SEG_D = 0b0000_1000;
    private static final byte SEG_C = 0b0001_0000;
    private static final byte SEG_B = 0b0010_0000;
    private static final byte SEG_A = 0b0100_0000;
    private static final byte SEG_DP = (byte)0b1000_0000;
	
    private static final byte BLANK = ZERO;
	private static final byte[] NUMBER_CODES = {
			(byte)(SEG_A+SEG_B+SEG_C+SEG_D+SEG_E+SEG_F),  		// 0
			(byte)(SEG_B+SEG_C),						  		// 1	
			(byte)(SEG_A+SEG_B+SEG_G+SEG_D+SEG_E),        		// 2
			(byte)(SEG_A+SEG_B+SEG_G+SEG_C+SEG_D), 		  		// 3
			(byte)(SEG_F+SEG_G+SEG_B+SEG_C),              		// 4
			(byte)(SEG_A+SEG_F+SEG_G+SEG_C+SEG_D),	      		// 5
			(byte)(SEG_A+SEG_F+SEG_E+SEG_D+SEG_C+SEG_G),  		// 6
			(byte)(SEG_A+SEG_B+SEG_C),                    		// 7
			(byte)(SEG_A+SEG_B+SEG_C+SEG_D+SEG_E+SEG_F+SEG_G), 	// 8
			(byte)(SEG_A+SEG_B+SEG_G+SEG_F+SEG_C+SEG_D)   		// 9
	};
	private static final byte[] LETTERS_CODES = {
			(byte)(SEG_A+SEG_B+SEG_C+SEG_E+SEG_F+SEG_G),		// A
			(byte)(SEG_F+SEG_E+SEG_D+SEG_C+SEG_G),  			// b
			(byte)(SEG_A+SEG_F+SEG_E+SEG_D),		  			// C
			(byte)(SEG_B+SEG_C+SEG_D+SEG_E+SEG_G),  			// d
			(byte)(SEG_A+SEG_F+SEG_E+SEG_D+SEG_G),  			// E
			(byte)(SEG_A+SEG_F+SEG_E+SEG_G),  			        // F
			(byte)(SEG_A+SEG_F+SEG_E+SEG_D+SEG_C+SEG_G),        // G
			(byte)(SEG_F+SEG_E+SEG_G+SEG_C),			        // h
			(byte)(SEG_F+SEG_E),						        // I
			(byte)(SEG_B+SEG_C+SEG_D),					        // J
			(byte)(SEG_F+SEG_E+SEG_G+SEG_A+SEG_C),		        // k
			(byte)(SEG_F+SEG_E+SEG_D),					        // L
			(byte)(SEG_E+SEG_G+SEG_C),					        // m
			(byte)(SEG_E+SEG_G+SEG_C),							// n
			(byte)(SEG_E+SEG_G+SEG_C+SEG_D),					// o
			(byte)(SEG_E+SEG_F+SEG_A+SEG_B+SEG_G),				// p
			(byte)(SEG_A+SEG_F+SEG_G+SEG_B+SEG_C),				// q
			(byte)(SEG_E+SEG_G),								// r
			(byte)(SEG_A+SEG_F+SEG_G+SEG_C+SEG_D),  			// S
			(byte)(SEG_F+SEG_E+SEG_G+SEG_D),					// t
			(byte)(SEG_F+SEG_E+SEG_D+SEG_C+SEG_B),				// U
			(byte)(SEG_E+SEG_D+SEG_C),							// v
			(byte)(SEG_E+SEG_D+SEG_C),							// w
			(byte)(SEG_F+SEG_E+SEG_G+SEG_B+SEG_C),			    // X
			(byte)(SEG_F+SEG_E+SEG_G+SEG_B),				    // Y
			(byte)(SEG_A+SEG_B+SEG_G+SEG_E+SEG_D),				// Z
	};
	
	private static final Logger LOGGER = LogManager.getFormatterLogger();
    
	private SpiDevice max7215Driver;

	public void start() throws IOException 
	{
        max7215Driver = SpiFactory.getInstance(SpiChannel.CS0,
                SpiDevice.DEFAULT_SPI_SPEED, // default spi speed 1 MHz
                SpiDevice.DEFAULT_SPI_MODE); // default spi mode 0


        max7215Driver.write(ADDRESS_REGISTER_SCANLIMIT, FIVE); //show digit 0..5
        max7215Driver.write(ADDRESS_REGISTER_DECODE_MODE, ZERO); // use matrix (not digits)
        test(false);
        max7215Driver.write(ADDRESS_REGISTER_SHUTDOWN, ONE); // not shutdown mode
        max7215Driver.write(ADDRESS_REGISTER_INTENSITY, (byte)0x0F);      
	}

	public void test() throws IOException, InterruptedException 
	{
		for (int pos=0; pos < 5; pos++)
		{
			testOn();
			Thread.sleep(1000);
			testOff();
			Thread.sleep(1000);
		}
		display("hello ");
		Thread.sleep(5000);
		for (int pos=0; pos < 0x10; pos++)
		{
			byte intensity = (byte)(0x0F-pos%0x10);
			LOGGER.info("Intensity: "+intensity);
			max7215Driver.write(ADDRESS_REGISTER_INTENSITY, intensity);
			Thread.sleep(100);
		}
		clear();
		max7215Driver.write(ADDRESS_REGISTER_INTENSITY, (byte)0x0F);
	}

	private void testOff() throws IOException 
	{
		test(false);
		clear();
	}

	private void clear() 
	{
		display(EMPTY);
	}

	private void testOn() throws IOException 
	{
		test(true);
	}

	private void test(boolean test) throws IOException 
	{
		max7215Driver.write(ADDRESS_REGISTER_DISPLAY_TEST, test ? ONE : ZERO);	
	}

	public void shutdown() throws IOException 
	{
		max7215Driver.write(ADDRESS_REGISTER_DISPLAY_TEST, (byte)0x00); // no display test
		clear();
	}

	public void display(String text) 
	{
		LOGGER.debug("Display %s", text);
		try
		{
		  boolean decimalPoint = false;
		  byte digit = 0;
			for (int pos = text.length()-1; pos >= 0; pos--)
			{
				char character = text.charAt(pos);
				if (isDecimalPoint(character))
				{
				  decimalPoint = true;
				}
				else
				{
				  display(digit, text.charAt(pos), decimalPoint);
				  digit += 1;
				  decimalPoint = false;
				}
			}
		}
		catch(IOException ex)
		{
			LOGGER.error("Cannot communicate with 7-Segment display", ex);
		}
	}

	private static boolean isDecimalPoint(char character)
  {
    return ':' == character ||
           '.' == character ||
           ',' == character;
  }

  private void display(byte digit, char character, boolean showDecimalPoint) throws IOException 
	{
	  byte sevenSegCode = BLANK;
		if (character >= '0' && character <= '9')
		{
		  sevenSegCode = NUMBER_CODES[character-'0'];
		}
		else if (character >= 'A' && character <= 'Z')
		{
			sevenSegCode = LETTERS_CODES[character-'A'];
		}
		else if (character >= 'a' && character <= 'z')
		{
			sevenSegCode = LETTERS_CODES[character-'a'];
		}
		if (showDecimalPoint)
		{
		  sevenSegCode += SEG_DP;
		}
		display(digit, sevenSegCode);
	}
	
	private void display(byte digit, byte sevenSegCode) throws IOException
	{
		byte address = (byte)(ADDRESS_REGISTER_DIGIT_0 + digit);
		max7215Driver.write(address, sevenSegCode);
	}
}
