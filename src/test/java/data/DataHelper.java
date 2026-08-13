package data;

import lombok.AllArgsConstructor;
import lombok.Data;

public class DataHelper {

    private DataHelper() {
    }

    @Data
    @AllArgsConstructor
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class VerificationCode {
        private String code;
    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo("vasya", "wrongpassword");
    }

    public static VerificationCode getVerificationCode() {
        String code = DbHelper.getVerificationCode(); // передаем логин васи
        return new VerificationCode(code);
    }
}