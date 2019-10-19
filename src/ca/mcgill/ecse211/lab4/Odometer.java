
package ca.mcgill.ecse211.lab4;

import java.util.concurrent.locks.Condition;
import lejos.hardware.*;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import ca.mcgill.ecse211.lab4.Odometer;
// static import to avoid duplicating variables and make the code easier to read
import static ca.mcgill.ecse211.lab4.Resources.*;

/**
 * The odometer class keeps track of the robot's (x, y, theta) position.
 * 
 * @author Rodrigo Silva
 * @author Dirk Dubois
 * @author Derek Yu
 * @author Karim El-Baba
 * @author Michael Smith
 * @author Younes Boubekeur
 */

public class Odometer extends Thread {
    // odometer update period, in ms
    private static final long PERIOD = 25;
    public static final double WHEEL_RADIUS = 2.05;
    public static final double WHEEL_DISTANCE = 14.9;
    private static final double TWO_PI = Math.PI * 2;
    // robot position
    private double x = 0, y = 0, theta = Math.PI / 2;
    // Tachometer last readings in radians, for right and left
    private double lastRho = 0, lastLambda = 0;
    
    // Thread control tools
    /**
     * Fair lock for concurrent writing
     */
    private static Lock lock = new ReentrantLock(true);

    /**
     * Indicates if a thread is trying to reset any position parameters
     */
    private volatile boolean isResetting = false;

    /**
     * Lets other threads know that a reset operation is over.
     */
    private Condition doneResetting = lock.newCondition();
    
    // Left and right motors
    private static Odometer odo; // Returned as singleton


    public void run() {
        long updateStart, updateEnd;
        // reset motor tachos
        rightMotor.resetTachoCount();
        leftMotor.resetTachoCount();
        //
        while (true) {
            updateStart = System.currentTimeMillis();
            // compute rho and lambda
            double rho = Math.toRadians(rightMotor.getTachoCount());
            double lambda = Math.toRadians(leftMotor.getTachoCount());
            // compute the delta rho and lambda from last values
            double deltaRho = rho - lastRho;
            double deltaLambda = lambda - lastLambda;
            // update last values to current
            lastRho = rho;
            lastLambda = lambda;
            // multiply rho and lambda by the wheel radius
            double deltaRhoRadius = deltaRho * WHEEL_RADIUS;
            double deltaLambdaRadius = deltaLambda * WHEEL_RADIUS;
            // compute delta C
            double deltaC = (deltaRhoRadius + deltaLambdaRadius) / 2;
            // compute delta theta and it's half
            double deltaTheta = (deltaRhoRadius - deltaLambdaRadius) / WHEEL_DISTANCE;
            double halfDeltaTheta = deltaTheta / 2;
            // compute delta x and y, using y forward and a right handed system (x right)
            double deltaX = deltaC * Math.cos(theta + halfDeltaTheta);
            double deltaY = deltaC * Math.sin(theta + halfDeltaTheta);
            // update position
            synchronized (lock) {
                // update x, y, and theta by their deltas
                x += deltaX;
                y += deltaY;
                theta = wrapAngle(theta + deltaTheta);
            }
            // this ensures that the odometer only runs once every period
            updateEnd = System.currentTimeMillis();
            if (updateEnd - updateStart < PERIOD) {
                try {
                    Thread.sleep(PERIOD - (updateEnd - updateStart));
                } catch (InterruptedException e) {
                    // there is nothing to be done here because it is not
                    // expected that the odometer will be interrupted by
                    // another thread
                }
            }
        }
    }

    public double getX() {
        synchronized (lock) {
            return x;
        }
    }

    public double getY() {
        synchronized (lock) {
            return y;
        }
    }

    public double getTheta() {
        synchronized (lock) {
            return theta;
        }
    }

    public Position getPosition() {
        synchronized (lock) {
            return new Position(x, y, theta);
        }
    }

    public void setTheta(double theta) {
        synchronized (lock) {
            this.theta = theta;
        }
    }

    public void setPosition(double x, double y, double theta) {
        synchronized (lock) {
            this.x = x;
            this.y = y;
            this.theta = theta;
        }
    }

    // Wraps an angle between 0 (inclusive) and 2pi (exclusive)
    public static double wrapAngle(double rads) {
        return ((rads % TWO_PI) + TWO_PI) % TWO_PI;
    }

    // Represents a position with a heading
    public static class Position {
        public final double x, y, theta;

        public Position(double x, double y, double theta) {
            this.x = x;
            this.y = y;
            this.theta = theta;
        }
    }
    
    public double[] getXYT() {
      double[] position = new double[3];
      lock.lock();
      try {
        while (isResetting) { // If a reset operation is being executed, wait until it is over.
          doneResetting.await(); // Using await() is lighter on the CPU than simple busy wait.
        }

        position[0] = x;
        position[1] = y;
        position[2] = theta;
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }

      return position;
    }
    /**
     * Overrides the values of x, y and theta. Use for odometry correction.
     * 
     * @param x the value of x
     * @param y the value of y
     * @param theta the value of theta in degrees
     */
    public void setXYT(double x, double y, double theta) {
      lock.lock();
      isResetting = true;
      try {
        this.x = x;
        this.y = y;
        this.theta = theta;
        isResetting = false;
        doneResetting.signalAll();
      } finally {
        lock.unlock();
      }
    }


    /**
     * Returns the Odometer Object. Use this method to obtain an instance of Odometer.
     * 
     * @return the Odometer Object
     */
    public synchronized static Odometer getOdometer() {
      if (odo == null) {
        odo = new Odometer();
      }

      return odo;
    }

   
}
