package ca.mcgill.ecse211.lab4;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

/**
 * This class is used to define static resources in one place for easy access and to avoid 
 * cluttering the rest of the codebase. All resources can be imported at once like this:
 * 
 * <p>{@code import static ca.mcgill.ecse211.lab3.Resources.*;}
 */
public class Resources {
  /**
   * bandcentre for the wall follower
   */
  public static final double BANDCENTRE = 51;
  /**
   * Threshold for detection of an edge (for localization)
   */
  public static final int EDGE_THRESHOLD = 20;
  /**
   * The wheel radius in centimeters.
   */
  public static final double WHEEL_RAD = 2.130;
  
  /**
   * The robot width in centimeters.
   */
  public static final double TRACK = 14.8; 
  /**
   * Wall follower speed
   */
  public static final int FOLLOWER_SPEED = 150;
  /**
   * The speed at which the robot moves forward in degrees per second.
   */
  public static final int FORWARD_SPEED = 170; //slowed it down to give motor to increase correction   
  
  /**
   * The speed at which the robot rotates in degrees per second.
   */
  public static final int ROTATE_SPEED = 155;
  
  /**
   * The motor acceleration in degrees per second squared.
   */
  public static final int ACCELERATION = 500;
  
  /*
   * Time to sleep to gather samples when wall following.
   */
  public static final int SLEEP_TIME = 500;
  /**
   * Timeout period in milliseconds.
   */
  public static final int TIMEOUT_PERIOD = 3000;
  /**
   * Offset between sensor and wheels. (magnitude)
   */
  public static final double SENSOR_TO_WHEEL_DISTANCE = 4;
  
  /**
   * angle between light sensor position and wheelbase centre in degrees
   */
  public static final double SENSOR_TO_WHEEL_ANGLE = 70;
  /**
   * The tile size in centimeters.
   */
  public static final double TILE_SIZE = 30.48;
  
  /**
   * The left motor.
   */
  public static final EV3LargeRegulatedMotor leftMotor =
      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));

  /**
   * The right motor.
   */
  public static final EV3LargeRegulatedMotor rightMotor =
      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
  /**
   * The motor for the sesnor
   */
 // public static final EV3MediumRegulatedMotor sensorMotor =
   //   new EV3MediumRegulatedMotor(LocalEV3.get().getPort("C"));
  /**
   * The color sensor.
   */
  public static final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
/**
 * The ultrasonic sensor
 */
  public static final EV3UltrasonicSensor US_SENSOR = 
      new EV3UltrasonicSensor(LocalEV3.get().getPort("S4"));
  /**
   * The LCD.
   */
  public static final TextLCD LCD = LocalEV3.get().getTextLCD();
  
  /**
   * The odometer.
   */
  public static Odometer odometer = Odometer.getOdometer();
  
}
