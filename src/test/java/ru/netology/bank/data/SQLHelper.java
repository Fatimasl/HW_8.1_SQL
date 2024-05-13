package ru.netology.bank.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner queryRunner = new QueryRunner();

    private SQLHelper(){

    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), System.getProperty("db.user"), System.getProperty("db.password"));
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConnection();
        var code = queryRunner.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static void cleanDatabase(){
        var conn = getConnection();
        queryRunner.execute(conn, "DELETE FROM auth_codes");
        queryRunner.execute(conn, "DELETE FROM card_transactions");
        queryRunner.execute(conn, "DELETE FROM cards");
        queryRunner.execute(conn, "DELETE FROM users");
    }

    @SneakyThrows
    public static void cleanAuthCodes(){
        var conn = getConnection();
        queryRunner.execute(conn, "DELETE FROM auth_codes");
    }
}
