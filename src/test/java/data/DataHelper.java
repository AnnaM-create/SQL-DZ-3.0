package data;

import lombok.AllArgsConstructor;
import lombok.Data;

public class DataHelper {

    @Data
    @AllArgsConstructor
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo("vasya", "wrongpassword");
    }
}