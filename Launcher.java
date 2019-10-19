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
import static ca.mcgill.ecse211.lab5.Resources.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.RegulatedMotor;

public class Launcher{
  public static void launchTest()
  {
    leftMotor.stop();
    rightMotor.stop();
    launchMotor1.setAcceleration(999999);
    launchMotor2.setAcceleration(999999);
    leftMotor.setAcceleration(2000);
    rightMotor.setAcceleration(2000);
    leftMotor.setSpeed(300);
    rightMotor.setSpeed(300);
  //  leftMotor.rotate(Navigation.convertAngle(360), true);
   // rightMotor.rotate(-Navigation.convertAngle(360), false);
    
    
    
    Sound.beepSequenceUp();
   // Navigation.turn(180);
    launchMotor1.setSpeed(900);
    launchMotor2.setSpeed(900);
    
    /*
     * reset position
     */
    while(true)
    {
    moveLaunchers(120);
    launchMotor1.stop();
    launchMotor2.stop();
    Lab5.sleepFor(500);
   
    
    resetLauncher();
    launchMotor1.flt();
    launchMotor2.flt();
    if(Button.waitForAnyPress(0) == Button.ID_ESCAPE)
      break;
    Sound.beepSequence();
    Lab5.sleepFor(3000);
    Sound.beepSequence();
    }
  //  } */
    System.exit(0);
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
  //  leftMotor.rotate(Navigation.convertAngle(360), true);
   // rightMotor.rotate(-Navigation.convertAngle(360), false);
    
    
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
    moveLaunchers(115);
    launchMotor1.stop();
    launchMotor2.stop();
    Lab5.sleepFor(500);
    
    resetLauncher();
    launchMotor1.flt();
    launchMotor2.flt();
 
    Lab5.sleepFor(10000);
    Sound.beepSequence();
    moveLaunchers(115);
  //  } */
    System.exit(0);
  }
  /**
   * Rotates both launchers by the angle
   * @param angle angle relative to neutral/loading position
   */
  public static void moveLaunchers(int angle)
  {
    Resources.launchMotor1.rotateTo(-angle, true);
    Resources.launchMotor2.rotateTo(-angle, false);
  }
  /**
   * resets the launch to its initial position
   */
  public static void resetLauncher()
  {
    int initialSpeed = launchMotor1.getSpeed();
    launchMotor1.setSpeed(RESET_SPEED);
    launchMotor2.setSpeed(RESET_SPEED);
    moveLaunchers(-150);
    /**
     * set it back to the launch speed.
     */
    launchMotor1.setSpeed(initialSpeed);
    launchMotor2.setSpeed(initialSpeed);
  }
}