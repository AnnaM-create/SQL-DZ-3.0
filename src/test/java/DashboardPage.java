import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement dashboardHeading = $("[data-test-id=dashboard]");

    public void verifyDashboardVisible() {
        dashboardHeading.shouldBe(visible);
    }
}
