package ch.rweiss.alge;

import java.io.IOException;

public class TimeDisplay 
{
    private BigScreenTimeDisplay display;
    private SevenSegDisplay sevenSegDisplay;

	public static void main(String[] argv) throws InterruptedException, IOException
	{
    	TimeDisplay display = new TimeDisplay();
    	display.start();
	}
    
    private void start() throws InterruptedException, IOException 
    {
//    	TimerS3 timer = new TimerS3();
//    	timer.start();
    	sevenSegDisplay = new SevenSegDisplay();
    	sevenSegDisplay.start();
//    	System.out.println("Start");
    	sevenSegDisplay.test();
//    	System.out.println("Test");
    	int count = 0;
    	while (true)
    	{
    		int digit0 = count % 10;
    		int digit1 = (count + 1) % 10;
    		int digit2 = (count + 2) % 10;
    		int digit3 = (count + 3) % 10;
    		int digit4 = (count + 4) % 10;
    		int digit5 = (count + 5) % 10;
         	sevenSegDisplay.display("      ");
         	Thread.sleep(1000l);
    		sevenSegDisplay.display(""+digit0+digit1+digit2+digit3+digit4+digit5);
         	Thread.sleep(1000l);
    		count++;
    	}
//    	timer.addTimeListener(sevenSegDisplay);
//    	display = new BigScreenTimeDisplay();
//    	display.start();
//    	timer.addTimeListener(display);
//    	timer.read();

    	//    	while (true)
//    	{
//    		Calendar cal = Calendar.getInstance();
//    		String time = format(cal.get(Calendar.SECOND));
//			sevenSegDisplay.display(time);
//			display.setTime(time);
//			System.out.println(time);
//    				
//    		Thread.sleep(100);
//    	}
	}
}
