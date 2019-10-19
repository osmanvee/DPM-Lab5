package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;

import static ca.mcgill.ecse211.lab4.Resources.WHEEL_RAD;
import static ca.mcgill.ecse211.lab4.Resources.leftMotor;
import static ca.mcgill.ecse211.lab4.Resources.rightMotor;
import java.util.Timer;
import java.util.TimerTask;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3ColorSensor;

public class LightLocalizer2 implements Runnable{
  private float sampleData[] = new float [1];
  private float brightness;
  private Odometer odometer = Resources.odometer;
  private Navigation navigator = Resources.navigator;
  private EV3ColorSensor colorSensor = Resources.colorSensor;
  private boolean lineNotReached = true;
  private Timer timer;

  
  private static int convertDistance(double radius, double distance) {
    return (int) ((180.0 * distance) / (Math.PI * radius));
  }

  private static int convertAngle(double radius, double width, double angle) {
    return convertDistance(radius, Math.PI * width * angle / 360.0);
  }
  
  @Override
  public void run() {

    //rotate the vehicle 45 degrees, so it faces the point (1,1)
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    int angle = 45;
    leftMotor.rotate(convertAngle(WHEEL_RAD, TRACK, angle), true);
    rightMotor.rotate(-convertAngle(WHEEL_RAD, TRACK, angle), false);
        
    while(lineNotReached) {
      //fetch samples from light sensor
      colorSensor.getRedMode().fetchSample(sampleData, 0);
      brightness = sampleData[0]*100;
      
      
      //set vehicle moving forward
      leftMotor.setSpeed(FORWARD_SPEED);
      rightMotor.setSpeed(FORWARD_SPEED);
      leftMotor.forward();
      rightMotor.forward();
      
      
      //when it reaches a black line, stop it
      if(brightness<22.00) {
        lineNotReached = false;
        leftMotor.stop();
        rightMotor.stop();
       
      }

    }
    
  //rotate the vehicle 45 degrees, so it faces the point (1,1)
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    int angle2 = 45;
    leftMotor.rotate(-convertAngle(WHEEL_RAD, TRACK, angle2), true);
    rightMotor.rotate(convertAngle(WHEEL_RAD, TRACK, angle2), false);


    
  }

    
  }


  
  


