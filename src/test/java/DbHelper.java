import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.DriverManager;

public class DbHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
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

        conn.close();
    }
}