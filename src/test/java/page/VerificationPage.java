package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeInput = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeInput.shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        codeInput.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }
}
