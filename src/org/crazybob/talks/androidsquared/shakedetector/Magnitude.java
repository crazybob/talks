package org.crazybob.talks.androidsquared.shakedetector;
import org.crazybob.talks.androidsquared.stubs.Activity;
import org.crazybob.talks.androidsquared.stubs.SensorEvent;
import org.crazybob.talks.androidsquared.stubs.SensorEventListener;
public class Magnitude extends Activity
    implements SensorEventListener {

  public void onSensorChanged(SensorEvent event) {
    float ax = event.values[0];
    float ay = event.values[1];
    float az = event.values[2];

    /// HIGHLIGHT
    double magnitude = Math.sqrt(ax * ax + ay * ay + az * az);
    /// NORMAL
  }
}
