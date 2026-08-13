package test;

import data.DataHelper;
import data.DbHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @BeforeEach
    void openApp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanUp() throws Exception {
        DbHelper.cleanDatabase();
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
        loginPage.verifyInvalidLoginError();

        loginPage.invalidLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.verifyInvalidLoginError();

        loginPage.invalidLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.verifyInvalidLoginError();
    }
}