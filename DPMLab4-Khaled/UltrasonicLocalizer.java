package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import ca.mcgill.ecse211.lab4.Resources;
import lejos.hardware.Button;
import lejos.hardware.Sound;

public class UltrasonicLocalizer {
  public static boolean sweepDone = false; // currently sweeping

  public static void FallingEdge() {
 // record first edge angle, second edge angle, get average.
    int firstEdge = 370; // initalize to impossible value for the conditions later on
    int secondEdge = 370;
    int prevData = 100;
    leftMotor.setSpeed(150);
    rightMotor.setSpeed(150);
    leftMotor.forward();
    rightMotor.backward();
    //stop when both are not 370
    while (firstEdge == 370) {
      int theta = (int) Resources.odometer.getXYT()[2];
      int data = UltrasonicPoller.getDistance();
      /**
       * Is below threshold and there was also a drop 
       * TODO implement noise margin
       */
      if (data < Resources.EDGE_THRESHOLD && prevData > Resources.EDGE_THRESHOLD) {
        firstEdge = theta;
        Sound.beep();
        /*
         * switch directions
         */
      }
      prevData = data;
      
    }
    leftMotor.backward();
    rightMotor.forward();
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    prevData = 100;
    while (secondEdge == 370) {
      int theta = (int) Resources.odometer.getXYT()[2];
      int data = UltrasonicPoller.getDistance();
      /**
       * Is below threshold and there was also a drop TODO implement noise margin
       */
      if (data < Resources.EDGE_THRESHOLD && prevData > Resources.EDGE_THRESHOLD) {
        secondEdge = theta;
        Sound.beep();
        /*
         * switch directions
         */
        
      }
      prevData = data;
      
    }
    //slow down ultrasonic polling because its no longer necessary; occupy less processor time.
    UltrasonicPoller.setSleepTime(500);
    // get average of both data
    int ave = (firstEdge + secondEdge) / 2;
    System.out.println("\n\n\n\n");
      System.out.println(firstEdge + ",  " + secondEdge + " average: " + ave);
    double dtheta;
    //detects right wall first, due to spin direction
    if(firstEdge < secondEdge)
      dtheta = 240 - ave;
    else
      dtheta = 240 - 180 - ave;
    Resources.odometer.incrementTheta(dtheta);
 //   Navigation.turnTo(0);
    Sound.beepSequence();
    
    //wait for reading to stabilize before measuring vertical distance.
   
    Navigation.turnTo(0);
   
    if(Button.waitForAnyPress() == Button.ID_ESCAPE)
      System.exit(0);
  }
  

  public static void RisingEdge() {
 // record first edge angle, second edge angle, get average.
    int firstEdge = 370; // initalize to impossible value for the conditions later on
    int secondEdge = 370;
    int prevData = 100;
    leftMotor.setSpeed(150);
    rightMotor.setSpeed(150);
    leftMotor.forward();
    rightMotor.backward();
    //stop when both are not 370
    while (firstEdge == 370) {
      int theta = (int) Resources.odometer.getXYT()[2];
      int data = UltrasonicPoller.getDistance();
      /**
       * Is below threshold and there was also a drop 
       * TODO implement noise margin
       */
      if (data > Resources.EDGE_THRESHOLD && prevData < Resources.EDGE_THRESHOLD) {
        firstEdge = theta;
        Sound.beep();
        /*
         * switch directions
         */
      }
      prevData = data;
      
    }
    leftMotor.backward();
    rightMotor.forward();
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    prevData = 100;
    while (secondEdge == 370) {
      int theta = (int) Resources.odometer.getXYT()[2];
      int data = UltrasonicPoller.getDistance();
      /**
       * Is below threshold and there was also a drop TODO implement noise margin
       */
      if (data > Resources.EDGE_THRESHOLD && prevData < Resources.EDGE_THRESHOLD) {
        secondEdge = theta;
        Sound.beep();
        /*
         * switch directions
         */
        
      }
      prevData = data;
      
    }
    //slow down ultrasonic polling because its no longer necessary; occupy less processor time.
    UltrasonicPoller.setSleepTime(500);
    // get average of both data
    int ave = (firstEdge + secondEdge) / 2;
    System.out.println("\n\n\n\n");
      System.out.println(firstEdge + ",  " + secondEdge + " average: " + ave);
    double dtheta;
    //detects left wall first
    if(firstEdge > secondEdge)
      dtheta = 220 - ave;
    else
      dtheta = 220 - 180 - ave;
    Resources.odometer.incrementTheta(dtheta);
 //   Navigation.turnTo(0);
    Sound.beepSequence();
    
    //wait for reading to stabilize before measuring vertical distance.
   
    Navigation.turnTo(0);
   
    if(Button.waitForAnyPress() == Button.ID_ESCAPE)
      System.exit(0);
  }
}
