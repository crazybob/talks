package org.crazybob.talks.androidsquared.shakedetector;
import org.crazybob.talks.androidsquared.stubs.*;
public class ShakeDemo extends Activity implements ShakeDetector.Listener {

  /// HIGHLIGHT
  private ShakeDetector shakeDetector = new ShakeDetector(this);
  /// NORMAL

  @Override protected void onResume() {
    super.onResume();

    SensorManager sensorMgr = (SensorManager) getSystemService(
        Context.SENSOR_SERVICE);
    /// HIGHLIGHT
    shakeDetector.start(sensorMgr);
    /// NORMAL
  }

  @Override protected void onPause() {
    /// HIGHLIGHT
    shakeDetector.stop();
    /// NORMAL
  }

  public void hearShake() {
    /// HIGHLIGHT
    // The phone was shaking...
    /// NORMAL
  }
}
