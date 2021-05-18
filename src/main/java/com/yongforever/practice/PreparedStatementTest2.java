package com.yongforever.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 单条数据导入
 */
public class PreparedStatementTest2 {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/springsecuritytest?serverTimezone=GMT&useLocalSessionState=true";
    static final String USER = "root";
    static final String PASSWORD = "root";
    static final String sql = "insert into sys_user(name,password) values(?,?)";

    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = con.prepareStatement(sql);

            // 关闭自动提交
            con.setAutoCommit(false);
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 1000; j++) {
                    preparedStatement.setString(1,"jerry");
                    preparedStatement.setString(2,"123");
                    preparedStatement.executeUpdate();

                }
            }
            con.commit();
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime); // 4705
            // 100*10000,46128s
            // 1000*1000,50364s

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
