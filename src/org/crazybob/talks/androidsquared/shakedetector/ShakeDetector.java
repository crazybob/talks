package org.crazybob.talks.androidsquared.shakedetector;
import org.crazybob.talks.androidsquared.stubs.SensorManager;
public class ShakeDetector {
  public ShakeDetector(Listener listener) {
    /// ...
  }

  public void start(SensorManager sensorMgr) {
    /// ...
  }

  public void stop() {
    /// ...
  }

  /// HIGHLIGHT
  /** Listens for shakes. */
  public interface Listener {
    /** Called on the main thread when the device is shaken. */
    void hearShake();
  }
  /// NORMAL
}
