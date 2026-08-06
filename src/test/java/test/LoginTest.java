package test;

import data.DataHelper;
import data.DbHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import page.DashboardPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

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


    @Disabled
    @Test
    void shouldBeBlockedAfterThreeInvalidAttempts() throws Exception {
        var authInfo = DataHelper.getInvalidAuthInfo();
        var loginPage = new LoginPage();

        loginPage.invalidLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.verifyErrorVisible();

        loginPage.invalidLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.verifyErrorVisible();

        loginPage.invalidLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.verifyErrorVisible();

        String status = DbHelper.getUserStatus(authInfo.getLogin());
        assertTrue(status.equals("blocked"), "Пользователь должен быть заблокирован");
    }
}