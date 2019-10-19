package ca.mcgill.ecse211.lab5;

import static ca.mcgill.ecse211.lab5.Resources.colorSensor;
import static ca.mcgill.ecse211.lab5.Resources.leftMotor;
import static ca.mcgill.ecse211.lab5.Resources.odometer;
import static ca.mcgill.ecse211.lab5.Resources.rightMotor;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class LightLocalizer {
  public static int[] buffer = new int[5];


  /**
   * Performs light localization
   * Assumes it starts facing 0 degrees in the corner block
   * Makes the robot at 1,1
   */
  public static void localizeDistance() {
    /**
     * Float Array to store RGB Raw values
     */
    leftMotor.setSpeed(100);
    rightMotor.setSpeed(100);
    leftMotor.forward();
    rightMotor.forward();
    // if light sensing returns to 0 to prevent multiple line readings.
    boolean steadyState = true;
    sleepFor(100);
    /*
     * in the sweep. stops when either of the motors stop.
     */
    while (true) {
      
      int diff = lightPoller.getIntensity();
      if (diff > 0) // negative value, so has returned to steady state (fluctuations around 0,0)
        steadyState = true;
      /*
       * spike of less than -75 implies a line has been detected. steadyState makes it
       * so that it only looks for lines again after it goes back to 0, i.e doesn't detect same lines multiple times.
       */
      if (diff < -75 && steadyState == true) {
        
        Sound.beepSequence();
        steadyState = false;
        break;

      }
      sleepFor(100);
    }
    odometer.setY(-Resources.SENSOR_TO_WHEEL_DISTANCE);
    leftMotor.stop();
    rightMotor.stop();
    //back up
//    leftMotor.rotate(-Navigation.convertDistance(Resources.BACKUP_DISTANCE), true);
   // rightMotor.rotate(-Navigation.convertDistance(Resources.BACKUP_DISTANCE), false);
  //  Sound.beepSequenceUp();
    Navigation.turnTo(85);
    
    leftMotor.forward();
    rightMotor.forward();
    steadyState = true;
    while (true) {
      int diff = lightPoller.getIntensity();
      if (diff > 0) // negative value, so has returned to steady state (fluctuations around 0,0)
        steadyState = true;
      /*
       * spike of less than -75 implies a line has been detected. steadyState makes it
       * so that it only looks for lines again after it goes back to 0, i.e doesn't detect same lines multiple times.
       */
      LCD.drawString("Diff: " + diff, 0, 5);
      if (diff < -Resources.DIFF_THRESHOLD && steadyState == true) {
        
        Sound.beepSequence();
        steadyState = false;
        break;

      }
      sleepFor(40);
    }
    odometer.setX(-Resources.SENSOR_TO_WHEEL_DISTANCE + 3);
    leftMotor.stop();
    rightMotor.stop();
    
    Navigation.travelTo(0, 0);
    sleepFor(800);
    Navigation.turnTo(0);
  }

  /**
   * sleeps thread for a set amount of time
   * 
   * @param duration amount to sleep for
   */
  public static void sleepFor(int duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

 /**
  * performs angle correction to 0,0.
  */
 public static void localizeAngle()
 {
   leftMotor.setSpeed(150);
   rightMotor.setSpeed(150);
   //Resources.SENSOR_TO_WHEEL_ANGLE;
   leftMotor.rotate(Navigation.convertAngle(370), true);
   rightMotor.rotate(-Navigation.convertAngle(370), true);
   int count = 0;
   /*
    * sum of the errors
    */
   int sum = 0;
   sleepFor(100);
   boolean steadyState = true;
   while(leftMotor.isMoving())
   {

     int diff = lightPoller.getIntensity();
     if (diff > 0) // negative value, so has returned to steady state (fluctuations around 0,0)
       steadyState = true;
     /*
      * spike of less than -75 implies a line has been detected. steadyState makes it
      * so that it only looks for lines again after it goes back to 0, i.e doesn't detect same lines multiple times.
      */
     if (diff < -Resources.DIFF_THRESHOLD && steadyState == true) {
       
       Sound.beepSequence();
       steadyState = false;      
       LCD.drawString("lines: " + count, 0, 6);
       sum += odometer.getXYT()[2] - (count%4) * 90 - Resources.SENSOR_TO_WHEEL_ANGLE;
       //System.out.println("Line detected at: " + odometer.getXYT()[2]);
       count++; //increment lines detected
     }
     sleepFor(40);
     //int error = theta - detectionAngle;
   }
   //get average error
   sum /= count;
   odometer.incrementTheta(-sum);
   sleepFor(1000);
   Navigation.turnTo(0);
 }
}


