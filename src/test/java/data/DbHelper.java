package data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
    private static final String DB_URL = "jdbc:mysql://185.119.57.164:3306/app";
    private static final String DB_USER = "vasya";
    private static final String DB_PASS = "pass";

    private DbHelper() {
    }

    public static String getVerificationCode() {
        var runner = new QueryRunner();
        var codeSql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        try (var conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            var code = runner.query(conn, codeSql, new ScalarHandler<>());
            return code.toString();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении кода верификации", e);
        }
    }

    public static void cleanDatabase() {
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            runner.update(conn, "DELETE FROM card_transactions;");
            runner.update(conn, "DELETE FROM cards;");
            runner.update(conn, "DELETE FROM auth_codes;");
            runner.update(conn, "DELETE FROM users;");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при очистке БД", e);
        }
    }

    public static String getUserStatus(String login) {
        var runner = new QueryRunner();
        var statusSql = "SELECT status FROM users WHERE login = ?;";
        try (var conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            var status = runner.query(conn, statusSql, new ScalarHandler<>(), login);
            return status.toString();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении статуса пользователя", e);
        }
    }
}