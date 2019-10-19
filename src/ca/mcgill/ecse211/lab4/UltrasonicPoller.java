package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;
import ca.mcgill.ecse211.lab4.UltrasonicPoller;
import lejos.robotics.SampleProvider;

/**
 * Samples the US sensor and invokes the selected controller on each cycle.
 * 
 * Control of the wall follower is applied periodically by the UltrasonicPoller thread. The while loop at the bottom
 * executes in a loop. Assuming that the us.fetchSample, and cont.processUSData methods operate in about 20ms, and that
 * the thread sleeps for 50 ms at the end of each loop, then one cycle through the loop is approximately 70 ms. This
 * corresponds to a sampling rate of 1/70ms or about 14 Hz.
 */


public class UltrasonicPoller implements Runnable {

  private UltrasonicController controller;
  private float[] usData;
  public int distance;
  //private static UltrasonicPoller ultraSPollInst;
  
  public UltrasonicPoller() {
    usData = new float[US_SENSOR.sampleSize()];
    //controller = Main.usl;
  }
  

  /*
   * Sensors now return floats using a uniform protocol. Need to convert US result to an integer [0,255] (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {
    int distance;
    while (true) {
      US_SENSOR.getDistanceMode().fetchSample(usData, 0); // acquire distance data in meters
      distance = (int) (usData[0] * 100.0); // extract from buffer, convert to cm, cast to int
      //controller.doLocalization(distance); // now take action depending on value
      try {
        Thread.sleep(50);
      } catch (Exception e) {
      } // Poor man's timed sampling
    }
  }

  public int getDistance() {
    int distance;
    US_SENSOR.getDistanceMode().fetchSample(usData, 0); // acquire distance data in meters
    distance = (int) (usData[0] * 100.0); // extract from buffer, convert to cm, cast to int  
    return distance;
  }

  
//  public static UltrasonicPoller getInstance(SampleProvider us, float[] usData, UltrasonicLocalizer cont,
//      final double MIN_DISTANCE) {
//    if (ultraSPollInst == null) {
//      ultraSPollInst = new UltrasonicPoller(us, usData, cont, MIN_DISTANCE);
//    }
//    return ultraSPollInst;
//  }

}
