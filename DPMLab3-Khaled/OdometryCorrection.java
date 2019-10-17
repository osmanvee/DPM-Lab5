package ca.mcgill.ecse211.lab3;
import static ca.mcgill.ecse211.lab3.Resources.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import lejos.hardware.Sound;

public class OdometryCorrection implements Runnable {
  private static final long CORRECTION_PERIOD = 20;
  /*
   * Here is where the odometer correction code should be run.
   */
  private float[] lightData2 = new float[3];

  public OdometryCorrection() // initial constructor to set up the light sensor.
  {
    // sets it to red mode to measure light intensity.
  }

  /*
   * Variables declared
   */
  int light = 0;
  double lastI = 0;
  /*
   * TrueX and TrueY values
   * Starts at 1,1.
   */
  double trueX = TILE_SIZE; // increment/decrement when crossing a line
  double trueY = TILE_SIZE;
  
  /*
   * We are displaying vertical and horizontal lines, each of them should be 6
   */
  int linesCrossedVert = 0; //variable to store number of lines for testing.
  int linesCrossedHori = 0; // differentiate vertical and horizontal linse

  public void run() {


    long correctionStart, correctionEnd;
    while (true) {
      correctionStart = System.currentTimeMillis();
      /**
       * Using the RGB mode of our sensor and storing it
       * into lightData2 Array
       */
      colorSensor.getRGBMode().fetchSample(lightData2, 0);

      
      for (int i = 0; i < 3; i++) {
      /**
       * Resizing the actual intensity values to make it more readable
       * and thus easier to test
       */
        
        lightData2[i] *= 2000; 
      }
      /*
       * For intensity, we are adding up the RGB values 
       */
      double intensity = lightData2[0] + lightData2[1] + lightData2[2];
    //  writer.write((int) intensity + ", "+ System.currentTimeMillis() + "\n");
      /**
       * Displaying the intensity on EV3 Screen for testing purposes
       */
      LCD.drawString(("intensity: " + intensity), 0, 5);
      
      /**
       * If intensity is 90% less than the previous intensity then it detects the line
       */
      if (intensity < 0.92 * lastI && (leftMotor.getSpeed() + rightMotor.getSpeed()) > 0)
      // i.e, not rotating in place or stationary. This prevents it from reading lines if picking up the robot and such

      {
        double angle = odometer.getXYT()[2];
        /**
         * This angle specification specifies the NORTH direction
         */
        if (angle < 10 || angle > 350) 
        {
        /**
         * Increase the trueY by the constant and 
         * set the odometer reading to the current TrueY but subtract the offset value
         * from sensor to wheel (which is negative in our case since it's ahead)
         */
          trueY += TILE_SIZE;
          odometer.setY(trueY - SENSOR_TO_WHEEL_DISTANCE ); 
          /**
           * Add a Beep sound and increase Lines for testing purposes.
           */
          linesCrossedVert++;
          Sound.beep();
        }
        /**
         * This angle specifies the EAST direction
         */
        else if (angle < 100 && angle > 80) {
          
          trueX += TILE_SIZE;
          odometer.setX(trueX - SENSOR_TO_WHEEL_DISTANCE);
         //Testing
          linesCrossedHori++;
          Sound.beep();
          /**
           * This angle specifies the SOUTH direction
           */
        } else if (angle < 190 && angle > 170) {
          
          odometer.setY(trueY - SENSOR_TO_WHEEL_DISTANCE - 1);
          trueY -= TILE_SIZE;
          //Testing
          linesCrossedVert++;
          Sound.beep();
        } 
        /**
         * This angle specifies the WEST direction
         */
        else if (angle < 280 && angle > 260) {
          odometer.setX(trueX - SENSOR_TO_WHEEL_DISTANCE);
          trueX -= TILE_SIZE;
         //Testing
          linesCrossedHori++;
          Sound.beep();
          
        }
         try {
        Thread.sleep(50); // so that it only reads the line once
        //sleep after reading a line so that each line is only detected once
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
         e.printStackTrace();
      } 
     }
     
      
      /**
       * Displaying the lines for testing purposes
       */
      LCD.drawString("ver Lines: " + linesCrossedVert, 0, 3);
      LCD.drawString("hor Lines: " + linesCrossedHori, 0, 4);
      
      /**
       * Storing the intensity values so we can compare later
       */
      lastI = intensity;
      // this ensures the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis(); // keeps on repeating until it reaches this duration
      if (correctionEnd - correctionStart < CORRECTION_PERIOD)
        Main.sleepFor(CORRECTION_PERIOD - (correctionEnd - correctionStart));

    }
  }
}
