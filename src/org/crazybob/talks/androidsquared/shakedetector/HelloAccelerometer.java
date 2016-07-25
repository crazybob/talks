package org.crazybob.talks.androidsquared.shakedetector;
import org.crazybob.talks.androidsquared.stubs.*;
public class HelloAccelerometer extends Activity
    implements SensorEventListener {

  @Override protected void onResume() {
    super.onResume();

    SensorManager sensorMgr = (SensorManager) getSystemService(
        Context.SENSOR_SERVICE);
    /// HIGHLIGHT
    Sensor accelerometer = sensorMgr.getDefaultSensor(
        Sensor.TYPE_ACCELEROMETER);

    sensorMgr.registerListener(this, accelerometer,
        SensorManager.SENSOR_DELAY_GAME);
    /// NORMAL
  }

  public void onSensorChanged(SensorEvent event) {
    /// HIGHLIGHT
    float ax = event.values[0];
    float ay = event.values[1];
    float az = event.values[2];
    /// NORMAL
  }
}
