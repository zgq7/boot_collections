package com.boot.sharding.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 2019-10-17 18:19.
 *
 * @author zgq7
 */
public class Test {

    public static void main(String[] args) throws SQLException {
        JdbcPool jdbcPool = new JdbcPool("127.0.0.1", "3306", "atest", "root", "root", "serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        String sql = "select * from cn";
        ResultSet resultSet = jdbcPool.excute(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt("cn");
            String cnmae = resultSet.getString("cname");
            System.out.println(id + "----" + cnmae);
        }
        jdbcPool.closeAll();
    }
}
