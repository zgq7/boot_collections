package com.boot.sharding.jdbc;

import java.sql.*;

/**
 * Created on 2019-10-17 17:53.
 *
 * @author zgq7
 */
public class JdbcPool {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public JdbcPool(String ip, String port, String dbname, String userName, String password, String otherParam) {
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbname + "?" + otherParam;
        System.out.println(url);
        //加载驱动
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet excute(String sql) {
        try {
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.resultSet;
    }

    public void closeAll() throws SQLException {
        this.connection.close();
        this.statement.cancel();
        this.resultSet.close();
    }

}
