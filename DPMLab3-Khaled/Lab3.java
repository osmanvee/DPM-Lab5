package ca.mcgill.ecse211.lab3;

import ca.mcgill.ecse211.odometer.*;
import ca.mcgill.ecse211.wallfallowing.*;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

/**
 * /**
 * This class runs the main program containing the different threads established
 * in the code
 *
 *
 * @author Carlo D'Angelo and Mohamed Samee
 *
 */
public class Lab3 {
	
	/* Motors */
	public static final EV3LargeRegulatedMotor leftMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	public static final EV3LargeRegulatedMotor rightMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
	
	
	/* Odometer */
	private static final TextLCD lcd = LocalEV3.get().getTextLCD();
	public static final double WHEEL_RAD = 2.1;
	public static final double TRACK = 15.6;

	/**
	 *  Main entry point - instantiate objects used and set up sensor
	 * @param args Not used
	 * @throws OdometerExceptions
	 */
	public static void main(String[] args) throws OdometerExceptions {	
		
		/* Odometer */
		int buttonChoice;

		// Odometer related objects
		Odometer odometer = Odometer.getOdometer(leftMotor, rightMotor, TRACK, WHEEL_RAD); 
		    
		Display odometryDisplay = new Display(lcd); // No need to change 
		    
		do {
		  // clear the display
		  lcd.clear();

		  // asks the user to start program
		  lcd.drawString("< Left |       >", 0, 0);
		  lcd.drawString("       |        ", 0, 1);
		  lcd.drawString(" Start |        ", 0, 2);
		  lcd.drawString("       |        ", 0, 3);
		  lcd.drawString("       |        ", 0, 4);

		  buttonChoice = Button.waitForAnyPress(); // Record choice (left press)
		} while (buttonChoice != Button.ID_LEFT);

		if (buttonChoice == Button.ID_LEFT) {
			Thread odoThread = new Thread(odometer);
			  odoThread.start();
			
			Thread odoDisplayThread = new Thread(odometryDisplay);
			odoDisplayThread.start();

		  // spawn a new Thread to avoid Navigation.drive() from blocking
		  (new Thread() {
		   public void run() {
		      try {
				Navigation.drive();
			} catch (OdometerExceptions e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		  }).start();
		}
				
		// Wait here forever until button pressed to terminate Lab3
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
	}
	
}
