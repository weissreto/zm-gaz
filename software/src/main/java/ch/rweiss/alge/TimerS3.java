package ch.rweiss.alge;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;

import ch.rweiss.alge.time.Time;

public class TimerS3 
{
	private StringBuilder inputBuffer = new StringBuilder();
	private List<TimeListener> listeners = new ArrayList<>();
	private Serial serial;
	private static final Logger LOGGER = LogManager.getFormatterLogger();

	public void start() throws InterruptedException
	{
        serial = SerialFactory.createInstance();        
        serial.open(Serial.DEFAULT_COM_PORT, 2400);
	}
	
	public void read()
	{
		LOGGER.trace("Start reading serial data from Timer S3");
		while (true)
		{
			String line = readNextLine();
			line = removeLeadingRaceNumber(line);
			try
			{
				Time time = Time.parse(line);
				sendTimeToListeners(time);
			}
			catch(NumberFormatException ex)
			{
				LOGGER.error("Could not parse time", ex);
			}
		}
	}
	
	private String removeLeadingRaceNumber(String line) 
	{
		return StringUtils.substring(line, 3);
	}

	private String readNextLine() 
	{
		while (true)
		{
			char data = readData();			
			inputBuffer.append(data);
			
			if (isLineEnd(data))
			{
				String input = inputBuffer.toString();
				LOGGER.trace("Line: %s", input);
				inputBuffer = new StringBuilder();
				if (isCompleteLine(input))
				{
					return input;
				}
			}
		}
	}

	private char readData()
	{
		char data;
		do
		{
			data=serial.read();
		} 
		while (data == 0xffff);
		data = convertTo7DataBits(data);
		LOGGER.trace("%02x", (int)data);
		return data;
	}

	private char convertTo7DataBits(char data)
	{
		data = (char)(data&0b0111_1111);
		return data;
	}

	private boolean isLineEnd(char data) 
	{
		return data == '\r';
	}
	
	private boolean isCompleteLine(String input) 
	{
		return input.length()>= 19;
	}

	private void sendTimeToListeners(Time time) 
	{
		for (TimeListener listener : listeners)
		{
			listener.newTimeAvailable(time);
		}
	}

	public void addTimeListener(TimeListener listener) 
	{
		listeners.add(listener);
	}
}
