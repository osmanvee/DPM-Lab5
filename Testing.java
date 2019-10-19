package ca.mcgill.ecse211.lab5;

import static ca.mcgill.ecse211.lab5.Resources.ACCELERATION;
import static ca.mcgill.ecse211.lab5.Resources.FORWARD_SPEED;
import static ca.mcgill.ecse211.lab5.Resources.ROTATE_SPEED;
import static ca.mcgill.ecse211.lab5.Resources.TILE_SIZE;
import static ca.mcgill.ecse211.lab5.Resources.TIMEOUT_PERIOD;
import static ca.mcgill.ecse211.lab5.Resources.TRACK;
import static ca.mcgill.ecse211.lab5.Resources.WHEEL_RAD;
import static ca.mcgill.ecse211.lab5.Resources.launchMotor1;
import static ca.mcgill.ecse211.lab5.Resources.launchMotor2;
import static ca.mcgill.ecse211.lab5.Resources.leftMotor;
import static ca.mcgill.ecse211.lab5.Resources.rightMotor;
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
    launchMotor1.setAcceleration(999999);
    launchMotor2.setAcceleration(999999);
    leftMotor.setAcceleration(2000);
    rightMotor.setAcceleration(2000);
    leftMotor.setSpeed(300);
    rightMotor.setSpeed(300);
    leftMotor.rotate(convertAngle(360), true);
    rightMotor.rotate(-convertAngle(360), false);
    leftMotor.stop();
    rightMotor.stop();
    Lab5.sleepFor(2000);
    
    Sound.beepSequenceUp();
   // Navigation.turn(180);
    launchMotor1.setSpeed(8000);
    launchMotor2.setSpeed(8000);
    
  /*  for(int i = 0; i < 5; i ++)
    {Resources.launchMotor1.rotate(-(70 + 5 * i), true);
    Resources.launchMotor2.rotate(-(70 + 5 * i), false);
    Resources.launchMotor1.stop();    
    Resources.launchMotor2.stop();
    Lab5.sleepFor(1000);
    /*
     * reset position
     */
    launch(115);
    launchMotor1.stop();
    launchMotor2.stop();
    Lab5.sleepFor(500);
    
    launch(-100);
    launchMotor1.flt();
    launchMotor2.flt();
 
    Lab5.sleepFor(3000);
    Sound.beepSequence();
    launch(115);
  //  } */
    System.exit(0);
  }
  public static void launch(int angle)
  {
    Resources.launchMotor1.rotate(-angle, true);
    Resources.launchMotor2.rotate(-angle, false);
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
