/**
 * Osman Warsi and Khalid Bucheeri
 */
package ca.mcgill.ecse211.lab3;

import static ca.mcgill.ecse211.lab3.Resources.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import lejos.hardware.Button;

/**
 * The main driver class for the odometry lab.
 */
public class Main {

  /**
   * The main entry point.
   * 
   * @param args
   */
  public static void main(String[] args) {
    int buttonChoice;
    // TODO implement Odometer. need to return it?

    
    // new Thread(new OdometryCorrection()).start(); // TODO implement OdometryCorrection
   
    int[] x= {2, 3, 3, 1, 2}; //(1,3), (2,2), (3,3), (3,2), (2,1)
    int[] y = {1, 2, 3, 3, 2};
    
    new Thread(new Display()).start();
    buttonChoice = chooseType();
    new Thread(odometer).start();
    LCD.clear();
    new Thread(new ObstacleAvoider()).start();
    if (buttonChoice == Button.ID_LEFT) {
      Navigation.loadWaypoints(x, y);
      
    } else {
      Navigation.turn(1800);
      // implement obstacle detection
      System.out.println("Turning 10 times");
    }
    //  new Thread(new OdometryCorrectionTest()).start();
    
    while (true) {
      /*
       * navigated to last pooint, receive next command
       */
     
      
        if (Button.waitForAnyPress() != Button.ID_ESCAPE)
          System.exit(0);

    }
  }


  /**
   * Asks the user whether the motors should drive in a square or float.
   * 
   * @return the user choice
   */
  private static int chooseType() {
    int buttonChoice;
    Display.showText("< Left | Right >", "   "
        + "    |        ", "    "
            + "   | No ", "Obstacl| test navigato"
                + "  ", "       | !! ");

    do {
      buttonChoice = Button.waitForAnyPress(); // left or right press
    } while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);
    return buttonChoice;
  }

  /**
   * Asks the user whether odometry correction should be run or not.
   * 
   * @return the user choice
   */
  private static int chooseCorrectionOrNot() {
    int buttonChoice;
    Display.showText("< Left | Right >", "  No   | with   ", " corr- | corr-  ", " ection| ection ",
        "       |        ");

    do {
      buttonChoice = Button.waitForAnyPress();
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
