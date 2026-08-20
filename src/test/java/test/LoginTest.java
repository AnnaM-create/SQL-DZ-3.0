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
    static void cleanUp() {
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
        var invalidAuthInfo = DataHelper.getInvalidAuthInfo();
        var validAuthInfo = DataHelper.getValidAuthInfo();
        var loginPage = new LoginPage();


        loginPage.invalidLogin(invalidAuthInfo.getLogin(), invalidAuthInfo.getPassword());
        loginPage.verifyErrorMessage("Неверно указан логин или пароль");

        loginPage.invalidLogin(invalidAuthInfo.getLogin(), invalidAuthInfo.getPassword());
        loginPage.verifyErrorMessage("Неверно указан логин или пароль");

        loginPage.invalidLogin(invalidAuthInfo.getLogin(), invalidAuthInfo.getPassword());
        loginPage.verifyErrorMessage("Неверно указан логин или пароль");

        loginPage.invalidLogin(validAuthInfo.getLogin(), validAuthInfo.getPassword());

        loginPage.verifyErrorMessage("блокирован");
    }
}
