// Copyright 2010 Square, Inc.
package org.crazybob.talks.androidsquared.stubs;

/** @author Eric Burke (eric@squareup.com) */
public class Activity implements Context {
  protected void onResume() {

  }

  protected void onPause() {

  }
  
  public Object getSystemService(int serviceType) {
    return new SensorManager();
  }
}
