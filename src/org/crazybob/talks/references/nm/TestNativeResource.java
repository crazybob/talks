/**
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.crazybob.talks.references.nm;

/**
 *
 *
 */
public class TestNativeResource {
  private boolean finalized;
  public synchronized void write(byte[] data) {
    if (finalized)
      System.err.println("Yikes!");
  }
  protected synchronized void finalize() {
    finalized = true;
  }

  static class Trash {
    final TestNativeResource nr = new TestNativeResource();
    final TestSegfaultFactory sf = new TestSegfaultFactory(nr);
    @Override
    protected void finalize() throws Throwable {
      System.out.print('.');
    }
  }
  private static void makeTrash() {
    new TestNativeResource.Trash();
  }
  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 1000; i++) {
      makeTrash();
      System.gc();
      Thread.sleep(10);
    }
  }
}

class TestSegfaultFactory {
  private final TestNativeResource nr;
  public TestSegfaultFactory(TestNativeResource nr) {
    this.nr = nr;
  }
  @Override protected void finalize() {
    nr.write("garbage".getBytes());
  }
}