package org.crazybob.talks.androidsquared.http;
public class LoginActivity extends SquareActivity {
  @Inject Session session;
  private Login login;

  @Override protected void onCreate(Bundle state) {
    super.onCreate(state);
    login = new Login(session);
    /// ...
  }

  public void logIn() {
    login.call();
  }
  /// ...
  class Login extends ServerCall {
    private final Session session;
    @Inject private Login(Session session) {
      super("Logging in...", "Login failed.");
      this.session = session;
      /// manageWith(LoginActivity.this);
    }
    @Override protected void callServer(Callback<SimpleResponse> callback) {
      /// session.logIn(getEmail(), getPassword(), callback);
    }
    @Override protected void onSuccess() {
      /// finish();
    }
  }
}
