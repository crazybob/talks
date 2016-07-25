package org.crazybob.talks.androidsquared.http;
public interface Callback<T> {
  void call(T t);
  void sessionExpired();
  void networkError();
  void clientError(String message);
  void serverError(String message);
  void unexpectedError(Throwable t);
}
