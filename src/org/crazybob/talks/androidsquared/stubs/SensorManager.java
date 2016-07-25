// Copyright 2010 Square, Inc.
package org.crazybob.talks.androidsquared.stubs;

/** @author Eric Burke (eric@squareup.com) */
public class SensorManager {
  public void registerListener(SensorEventListener listener,
                               Sensor sensor, int rate) {
  }

  public Sensor getDefaultSensor(int type) {
    return new Sensor();
  }

  public static final int SENSOR_DELAY_GAME = 1;
}
