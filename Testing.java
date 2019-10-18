package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.ACCELERATION;
import static ca.mcgill.ecse211.lab4.Resources.FORWARD_SPEED;
import static ca.mcgill.ecse211.lab4.Resources.ROTATE_SPEED;
import static ca.mcgill.ecse211.lab4.Resources.TILE_SIZE;
import static ca.mcgill.ecse211.lab4.Resources.TIMEOUT_PERIOD;
import static ca.mcgill.ecse211.lab4.Resources.TRACK;
import static ca.mcgill.ecse211.lab4.Resources.WHEEL_RAD;
import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import static ca.mcgill.ecse211.lab4.Resources.launchMotor2;
import static ca.mcgill.ecse211.lab4.Resources.launchMotor1;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import lejos.hardware.Sound;
import lejos.robotics.RegulatedMotor;

public class Testing {
  public Testing()
  {
    
  }
  public static void trackTest()
  {
    leftMotor.stop();
    rightMotor.stop();
    launchMotor1.setAcceleration(20000);
    launchMotor2.setAcceleration(20000);
  /* 
    leftMotor.setAcceleration(ACCELERATION);
    rightMotor.setAcceleration(ACCELERATION);
    leftMotor.setSpeed(250);
    rightMotor.setSpeed(250);
    leftMotor.rotate(convertAngle(360), true);
    rightMotor.rotate(-convertAngle(360), false);
    */

    Sound.beepSequenceUp();
    Navigation.turn(180);
    launchMotor1.setSpeed(launchMotor1.getMaxSpeed());
    launchMotor2.setSpeed(launchMotor2.getMaxSpeed());
    
  /*  for(int i = 0; i < 5; i ++)
    {Resources.launchMotor1.rotate(-(70 + 5 * i), true);
    Resources.launchMotor2.rotate(-(70 + 5 * i), false);
    Resources.launchMotor1.stop();    
    Resources.launchMotor2.stop();
    Lab5.sleepFor(1000);
    /*
     * reset position
     */
    Resources.launchMotor1.rotate(70, true);
    Resources.launchMotor2.rotate(70, false);
   
  //  } */
    System.exit(0);
  }
  
  public static void lightSensorTest()
  {
    (new Thread() {
      public void run() {
        /**
         *  reset the motors
         */
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.setAcceleration(ACCELERATION);
        rightMotor.setAcceleration(ACCELERATION);

        /***
         *  Sleep for 2 seconds
         */
        Lab5.sleepFor(TIMEOUT_PERIOD);

        for (int i = 0; i < 1; i++) {
          /**
           * Rotate for 360 Degrees, to plot the distance
           */
          leftMotor.setSpeed(150);
          rightMotor.setSpeed(150);
          leftMotor.rotate(convertAngle(400), true);
          rightMotor.rotate(-convertAngle(400), false);
          System.out.println("i = " + i + "\n\n\n\n\n\n");
          try {
            Thread.sleep(200);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }   
        }
        leftMotor.setSpeed(0); //added these so that we can easily check if robot is travelling in a straight line 

        rightMotor.setSpeed(0);
        UltrasonicLocalizer.sweepDone = true;
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
       
        System.exit(0);
      }
    }).start();
  }
  /**
   * Converts input distance to the total rotation of each wheel needed to cover that distance.
   * 
   * @param distance
   * @return the wheel rotations necessary to cover the distance
   */
  public static int convertDistance(double distance) {
    return (int) ((180.0 * distance) / (Math.PI * WHEEL_RAD));
  }
  /**
   * Converts input angle to the total rotation of each wheel needed to rotate the robot by that
   * angle.
   * 
   * @param angle
   * @return the wheel rotations necessary to rotate the robot by the angle
   */
  public static void Test()
  {
    while(true)
    {
    leftMotor.setSpeed(500);
    rightMotor.setSpeed(500);
    System.out.println("");
    }
  }
public static int convertAngle(double angle) {
  return convertDistance(Math.PI * TRACK * angle / 360.0);
}
}
