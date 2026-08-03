import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeAll
    static void setUp() {
        Configuration.headless = true;
    }

    @BeforeEach
    void openApp() throws Exception {
        DbHelper.cleanDatabase();
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        var authInfo = DataHelper.getValidAuthInfo();
        var loginPage = new LoginPage();

        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());

        String code = DbHelper.getVerificationCode();

        var dashboardPage = verificationPage.validVerify(code);

        dashboardPage.verifyDashboardVisible();
    }

    @Test
    void shouldBeBlockedAfterThreeInvalidAttempts() {
        var authInfo = DataHelper.getInvalidAuthInfo();
        var loginPage = new LoginPage();

        loginPage.invalidLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.verifyErrorVisible();

        loginPage.invalidLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.verifyErrorVisible();

        loginPage.invalidLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.verifyErrorVisible();

        loginPage.verifyErrorVisible();
    }
}