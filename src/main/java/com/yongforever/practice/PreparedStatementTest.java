package com.yongforever.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * 批量导入数据
 */
public class PreparedStatementTest {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/springsecuritytest?serverTimezone=GMT&useLocalSessionState=true&rewriteBatchedStatements=true";
    static final String USER = "root";
    static final String PASSWORD = "root";
    static final String sql = "insert into sys_user(name,password) values(?,?)";

    public static void main(String[] args) {

        Connection con = null;

        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            con.setAutoCommit(false);
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    preparedStatement.setString(1,"jerry"+i+j);
                    preparedStatement.setString(2,"123");
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
            con.commit();
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime); // 75997
            // 100*1000 5430

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
