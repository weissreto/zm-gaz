package ch.rweiss.alge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ch.rweiss.alge.time.Time;
import ch.rweiss.alge.time.TimeKind;
import ch.rweiss.alge.time.TimePrecision;

public class BigScreenTimeDisplay implements TimeListener  
{
	private JFrame frame;
	private JLabel timeLabel;
	private JLabel topLabel;
	private JLabel bottomLabel;
	private JTextArea eastTimeLabel;
	private JTextArea westTimeLabel;
	private String timeString;
	private String finishTimeString = "";
	
	public void start()
	{
		frame = new JFrame("Time Display");
		timeLabel = new JLabel("Hello World");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setForeground(Color.GREEN);
		eastTimeLabel = new JTextArea();
		eastTimeLabel.setForeground(Color.GREEN);
		eastTimeLabel.setBackground(Color.BLACK);
		westTimeLabel = new JTextArea();
		westTimeLabel.setForeground(Color.GREEN);
		westTimeLabel.setBackground(Color.BLACK);
		
		topLabel = new JLabel("Zeitmessteam");
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topLabel.setForeground(Color.GREEN);

		bottomLabel = new JLabel("TV Küssnacht");
		bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLabel.setForeground(Color.GREEN);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().add(topLabel, BorderLayout.NORTH);
	    frame.getContentPane().add(timeLabel, BorderLayout.CENTER);
	    frame.getContentPane().add(bottomLabel, BorderLayout.SOUTH);
	    frame.getContentPane().add(westTimeLabel, BorderLayout.WEST);
	    frame.getContentPane().add(eastTimeLabel, BorderLayout.EAST);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	    frame.setUndecorated(true);
	    frame.addWindowListener(new WindowAdapter(){
	    	@Override
	    	public void windowOpened(WindowEvent e) {
	    	    setLabelFont();
	    	}
	    });
	    frame.addComponentListener(new ComponentAdapter(){
	    	@Override
	    	public void componentResized(ComponentEvent e) {
	    		setLabelFont();
	    	}
	    });
	    frame.setVisible(true);
	    setLabelFont();
	}
	
	private void setLabelFont() 
	{
		Rectangle bounds = frame.getBounds();
		System.out.println(bounds);
	    float height = (float)bounds.getHeight() * 0.2f;
	    Font font = timeLabel.getFont();
	    System.out.println(height);
	    font = font.deriveFont(height);
	    topLabel.setFont(font);
		timeLabel.setFont(font);
		bottomLabel.setFont(font);
		eastTimeLabel.setFont(font.deriveFont(20.0f));
		westTimeLabel.setFont(font.deriveFont(20.0f));
	}

	@Override
	public synchronized void newTimeAvailable(Time time) 
	{
		System.out.println(time);
		if (time.getKind() == TimeKind.RUNNING)
		{
			this.timeString = time.formatTime(TimePrecision.HUNDREDTH);
		}
		else if (time.getKind() == TimeKind.FINSIH)			
		{
			if (!this.finishTimeString.isEmpty())
			{
				this.finishTimeString += "\n";
			}
			this.finishTimeString += time.formatTime(TimePrecision.HUNDREDTH);
		}
		else if (time.getKind() == TimeKind.MASS_START ||
				 time.getKind() == TimeKind.START)
		{
			this.finishTimeString = "";
		}
		SwingUtilities.invokeLater(this::updateTime);
	}

	private synchronized void updateTime() 
	{
		timeLabel.setText(timeString);
		westTimeLabel.setText(finishTimeString);
	}
}
