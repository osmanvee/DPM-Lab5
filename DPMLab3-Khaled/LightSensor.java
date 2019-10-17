package ca.mcgill.ecse211.lab3;

import static ca.mcgill.ecse211.lab3.Resources.colorSensor;

public class LightSensor implements Runnable {

  private float[] lightData = new float[colorSensor.sampleSize()];
  @Override
  public void run() {
    // TODO Auto-generated method stub
    while (true) {
      
      colorSensor.getRedMode().fetchSample(lightData, 0); 
      
    }
  }

}
