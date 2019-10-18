/**
 * Osman Warsi and Khalid Bucheeri
 */
package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import lejos.hardware.Button;

/**
 * The main driver class for the odometry lab.
 */
public class Lab5 {

  /**
   * The main entry point.
   * 
   * @param args
   */
  public static void main(String[] args) {
    int buttonChoice;
   
    new Thread(new Display()).start();
    
  
    new Thread(odometer).start();
   // buttonChoice = chooseType();// chooseType();
    Testing.trackTest();
    LCD.clear();
 /*   
    if (buttonChoice == Button.ID_LEFT) {
      
     new Thread(new UltrasonicPoller()).start();
      //Testing.lightSensorTest(); 
      UltrasonicLocalizer.RisingEdge();
      Button.waitForAnyPress();
      new Thread(new lightPoller()).start();
      LightLocalizer.localizeDistance();
     // LightLocalizer.localizeAngle();
      System.exit(0);
      
      
    } else {
      new Thread(new UltrasonicPoller()).start();
      //Testing.lightSensorTest(); 
      UltrasonicLocalizer.FallingEdge();
      Button.waitForAnyPress();
      new Thread(new lightPoller()).start();
      LightLocalizer.localizeDistance();
      LightLocalizer.localizeAngle();
      System.exit(0);
    }
    //  new Thread(new OdometryCorrectionTest()).start();
    */
    while (true) {
      /*
       * navigated to last pooint, receive next command
       */
     
      
        if (Button.waitForAnyPress() != Button.ID_ESCAPE)
          System.exit(0);

    }
  }


  /**
   * Asks the user whether rising or falling edge localization should be used
   * 
   * @return the user choice
   */
  private static int chooseType() {
    int buttonChoice;
    Display.showText("< Left | Right >",
        "   "  + "    |        ", 
        " Falling   " + "   | Rising ",
        "edge| edge"+ "  ",
        "       | ");

    do {
      buttonChoice = Button.waitForAnyPress(); // left or right press
    } while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);
        return buttonChoice;
    
  } 

  /**
   * Sleeps current thread for the specified duration.
   * 
   * @param duration sleep duration in milliseconds
   */
  public static void sleepFor(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      // There is nothing to be done here
    }
  }

}
