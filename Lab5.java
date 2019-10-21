/**
 * Osman Warsi and Khalid Bucheeri
 */
package ca.mcgill.ecse211.lab5;

import static ca.mcgill.ecse211.lab5.Resources.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import lejos.hardware.Button;
import lejos.hardware.Sound;

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
    new Thread(new Display()).start();
    double Tx = 30 + 15, Ty = 6 * 30 + 15;
    new Thread(odometer).start();
    Button.waitForAnyPress();
    localize();
    travelToLaunchPoint(Tx, Ty);
    //didn't test turnToPoint
    Navigation.turnToPoint(Tx, Ty);
    Launcher.launchThenWaitTest();
    Button.waitForAnyPress();
    
    
    System.out.println("max speed" + launchMotor1.getMaxSpeed());
    Sound.beepSequenceUp();
   
    Button.waitForAnyPress();
    
    LCD.clear();
 /*
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
   * initiates localization routines
   */
  private static void localize()
  {
    System.out.println("max speed: " + launchMotor1.getMaxSpeed());
    new Thread(new UltrasonicPoller()).start();
    sleepFor(1000);
    UltrasonicLocalizer.RisingEdge();
    sleepFor(500);
    Sound.buzz();
    UltrasonicPoller.setSleepTime(2000);
    new Thread(new lightPoller()).start();
    Button.waitForAnyPress();
    
    //increase sleep time so the thread doesnt consume as much time

    
    
    new Thread(new lightPoller()).start();
    LightLocalizer.localizeDistance();
    sleepFor(1000);
    LightLocalizer.localizeAngle();
  }
  /**
   * Travels to face launch point T
   * @param Tx - centre of the square x coordinate
   * @param Ty - centre of the square y coordinate
   */
  public static void travelToLaunchPoint(double Tx, double Ty)
  {
    
    double Typ = (Ty - (Ty % 30));
    double dist = Math.hypot(Tx, Ty);
    double bound = (120.0 + Math.max(20, TRACK));
    if (Math.abs(dist - 120.0) < 0.0001) {
        Navigation.turnTo(90 - Math.toDegrees(Math.atan(Ty / Tx)));
    } else if (dist < bound) {
        Navigation.travelTo(Tx, Typ + 30 + 120);
        Navigation.turnTo(180);
    } else if (dist > bound) {
        Navigation.travelTo(Tx, Ty - 7 - 120);
        Sound.twoBeeps();
        sleepFor(1000);
        Navigation.turnTo(0);
    } 
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
