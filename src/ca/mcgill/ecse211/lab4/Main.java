package ca.mcgill.ecse211.lab4;


import lejos.hardware.Button;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import static ca.mcgill.ecse211.lab4.Resources.*;
import ca.mcgill.ecse211.lab4.UltrasonicLocalizer.LocalizationType;



public class Main {

  //define constants
  public static final TextLCD lcd = LocalEV3.get().getTextLCD();
  public static UltrasonicLocalizer usl;
  public static LightLocalizer2 lsl;
  private static Navigation navigator = Resources.navigator;
  private static Odometer odometer = Resources.odometer;


  public static void main(String[] args) {
    int buttonChoice;
    
    Display odometryDisplay = new Display(lcd); // No need to change.

    do {
      // Clear the display.
      lcd.clear();

      // Ask the user whether the motors should drive in a square or float.
      lcd.drawString("< Left | Right >", 0, 0);
      lcd.drawString("       |        ", 0, 1);
      lcd.drawString("Falling| Rising ", 0, 2);
      lcd.drawString("Edge   | Edge  ", 0, 3);
      lcd.drawString("       |     ", 0, 4);

      buttonChoice = Button.waitForAnyPress(); // Record the user's choice (left or right press).
    }
    
    while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);

    if (buttonChoice == Button.ID_LEFT) { // Falling edge has been selected.
      lcd.clear();
 
      // Start odometer thread.
      Thread odoThread = new Thread(odometer);
      odoThread.start();
      
      //start display thread
      Thread odoDisplayThread = new Thread(odometryDisplay);
      odoDisplayThread.start();
      
      //start navigator thread
      Thread navigatorThread = new Thread(navigator);
      navigatorThread.start();
      
      //instantiate localizer with selected type and start thread
      usl = new UltrasonicLocalizer(LocalizationType.FALLING_EDGE); 
      Thread uslThread = new Thread(usl);
      uslThread.start();
      
      
      Button.waitForAnyPress();
      
      //initiate light localizer and start thread
      lsl = new LightLocalizer2();
      Thread lslThread = new Thread(lsl);
      lslThread.start();
      
      //exit program
      Button.waitForAnyPress();
      System.exit(0);

  

    } else { // Rising edge has been selected 
      LCD.clear();

      //display thread
      Thread odoDisplayThread = new Thread(odometryDisplay);
      odoDisplayThread.start();

      // Start odometer thread.
      Thread odoThread = new Thread(odometer);
      odoThread.start();
      
      //start navigator thread
      navigator.start();
      
      
      //instantiate us localizer with selected type
      usl = new UltrasonicLocalizer(LocalizationType.RISING_EDGE);
      
      Thread uslThread = new Thread(usl);
      uslThread.start();

      Button.waitForAnyPress();
      // perform the light sensor localization
      lsl = new LightLocalizer2();
      Thread lslThread = new Thread(lsl);
      lslThread.start();
      

    }
    
    //exit program
    while (Button.waitForAnyPress() != Button.ID_ESCAPE);
    System.exit(0);

  }


}
