import android.app.Activity;
import android.os.Bundle;

public class TwoCents extends Activity {
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);

    Square square = new Square(this);
    if (square.installationStatus()
        != Square.InstallationStatus.AVAILABLE) {
      square.requestInstallation();
    } else {
      LineItem advice = new LineItem.Builder()
          .price(2, Currency.USD) // 2 cents
          .description("Advice")
          .build();
      square.squareUp(Bill.containing(advice));
    }
  }
}