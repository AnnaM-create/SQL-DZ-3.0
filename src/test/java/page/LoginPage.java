package page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginInput = $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    private void sendLoginForm(String login, String password) {
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
        page.waitUntilLoaded();
        return page;
    }

    public void verifyErrorVisible() {
        errorNotification.shouldBe(visible);
        errorNotification.shouldHave(text("Неверно указан логин или пароль"));
    }

    public void verifyUserBlocked() {
        errorNotification.shouldBe(visible);
        errorNotification.shouldHave(text("блокирован"));
    }
}