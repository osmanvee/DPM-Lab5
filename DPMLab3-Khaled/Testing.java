package ca.mcgill.ecse211.lab3;

import static ca.mcgill.ecse211.lab3.Resources.ACCELERATION;
import static ca.mcgill.ecse211.lab3.Resources.FORWARD_SPEED;
import static ca.mcgill.ecse211.lab3.Resources.ROTATE_SPEED;
import static ca.mcgill.ecse211.lab3.Resources.TILE_SIZE;
import static ca.mcgill.ecse211.lab3.Resources.TIMEOUT_PERIOD;
import static ca.mcgill.ecse211.lab3.Resources.TRACK;
import static ca.mcgill.ecse211.lab3.Resources.WHEEL_RAD;
import static ca.mcgill.ecse211.lab3.Resources.leftMotor;
import static ca.mcgill.ecse211.lab3.Resources.rightMotor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Testing {
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
        Main.sleepFor(TIMEOUT_PERIOD);

        for (int i = 0; i < 4; i++) {
          /**
           * drive forward three tiles
           */
          leftMotor.setSpeed(400/(i+1));
          rightMotor.setSpeed(400/(i+1));

          leftMotor.rotate(convertDistance(3 * TILE_SIZE), true);
          rightMotor.rotate(convertDistance(3 * TILE_SIZE), false);

          /**
           * turn 90 degrees clockwise
           */
          leftMotor.setSpeed(ROTATE_SPEED);
          rightMotor.setSpeed(ROTATE_SPEED);
          leftMotor.rotate(convertAngle(90), true);
          rightMotor.rotate(-convertAngle(90), false);
          
        }
        leftMotor.setSpeed(0); //added these so that we can easily check if robot is travelling in a straight line 

        rightMotor.setSpeed(0);
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

public static int convertAngle(double angle) {
  return convertDistance(Math.PI * TRACK * angle / 360.0);
}
}
