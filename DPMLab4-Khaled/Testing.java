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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Testing {
  public Testing()
  {
    
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
        Lab4.sleepFor(TIMEOUT_PERIOD);

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
