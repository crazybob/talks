// Copyright 2010 Square, Inc.
package org.crazybob.talks.androidsquared.stubs;

/** @author Eric Burke (eric@squareup.com) */
public interface Context {
  public static final int SENSOR_SERVICE = 1;
  public Object getSystemService(int serviceType);
}
