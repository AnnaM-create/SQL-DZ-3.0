package data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.DriverManager;

public class DbHelper {
    private static final String DB_URL = "jdbc:mysql://185.119.57.164:3306/app";
    private static final String DB_USER = "vasya";
    private static final String DB_PASS = "pass";

    public static String getVerificationCode() throws Exception {
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        var codeSql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        var code = runner.query(conn, codeSql, new ScalarHandler<>());
        conn.close();
        return code.toString();
    }

    public static void cleanDatabase() throws Exception {
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        runner.update(conn, "DELETE FROM card_transactions;");
        runner.update(conn, "DELETE FROM cards;");
        runner.update(conn, "DELETE FROM auth_codes;");
        runner.update(conn, "DELETE FROM users WHERE login != 'vasya';");
        runner.update(conn, "UPDATE users SET status = 'active' WHERE login = 'vasya';");
        conn.close();
    }

    public static String getUserStatus(String login) throws Exception {
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        var statusSql = "SELECT status FROM users WHERE login = ?;";
        var status = runner.query(conn, statusSql, new ScalarHandler<>(), login);
        conn.close();
        return status.toString();
    }
}