package com.yongforever.practice;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 从txt读取100w条数据插入数据库
 * 采用preparedStatement的批量插入
 * 手动提交用时:8686s,自动提交用时: 9394s
 * 每次批量插入条数/用时
 * 1000-8686s
 * 10000-8850s
 * 100000-5515s
 * 1000000-6206s
 * */
public class importTxt {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/springsecuritytest?serverTimezone=GMT&useLocalSessionState=true&rewriteBatchedStatements=true";
    static final String USER = "root";
    static final String PASSWORD = "root";
    static final String sql = "insert into my_user(name,age,sex) values(?,?,?)";

    public static void main(String[] args) throws SQLException {
        BufferedReader br = null;

        Connection conn = getConnection();
        PreparedStatement ps = getPreparedStatement(conn);
        // 关闭自动提交
        conn.setAutoCommit(false);
        long startTime = System.currentTimeMillis();
        try {
            // 读取txt数据
            FileInputStream fis = new FileInputStream(new File("D:\\develop\\yongforever\\writer.txt"));
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            br = new BufferedReader(isr);
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null){
                String[] strArray = line.split(",");
                ps.setString(1,strArray[0]);
                ps.setString(2,strArray[1]);
                ps.setString(3,strArray[2]);
                ps.addBatch();
                count++;
                if (count % 1000 == 0){
                    ps.executeBatch();
                }
            }
            // 插入最后不足1000的几条数据
            ps.executeBatch();
            // 手动提交
            conn.commit();
            // 开启自动提交
            conn.setAutoCommit(true);

            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            close(conn,ps,br);
        }
    }

    // 获取Connection连接
    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    // 获取preparedStatement
    public static PreparedStatement getPreparedStatement(Connection conn){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    // 关闭连接
    public static void close(Connection conn,PreparedStatement ps, BufferedReader br){
        try {
            if (ps != null){
                ps.close();
            }
            if (conn != null){
                conn.close();
            }
            if (br != null){
                br.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
