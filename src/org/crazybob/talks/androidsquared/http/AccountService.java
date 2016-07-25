package org.crazybob.talks.androidsquared.http;
public interface AccountService {
  @Path("login")
  void logIn(
      @Named("email") String email,
      @Named("password") String password,
      Callback<LoginResponse> callback);
}
