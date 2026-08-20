package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.disabled;

public class LoginPage {
    private SelenideElement loginInput = $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    private void sendLoginForm(String login, String password) {
        loginButton.shouldBe(enabled);

        loginInput.clear();
        loginInput.setValue(login);

        passwordInput.clear();
        passwordInput.setValue(password);

        loginButton.click();
    }

    public void invalidLogin(String login, String password) {
        sendLoginForm(login, password);
    }

    public VerificationPage validLogin(String login, String password) {
        sendLoginForm(login, password);
        VerificationPage page = new VerificationPage();
        return page;
    }

    public void verifyErrorMessage(String expectedText) {
        errorNotification.shouldBe(visible);
        errorNotification.shouldHave(text(expectedText));
    }

    public void verifyLoginButtonIsDisabled() {
        loginButton.shouldBe(disabled);
    }

    public void waitErrorNotification() {
        errorNotification.shouldBe(visible);
    }
}