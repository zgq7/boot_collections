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
		//JdbcPool jdbcPool = new JdbcPool("127.0.0.1", "3306", "atest", "root", "root", "serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
		JdbcPool jdbcPool = new JdbcPool("47.105.62.96", "3306", "cloudhismg",
				"cloudhis", "cloudhis@2019",
				"serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
		String sql = "select url,username,password,orgid from vdf_mg_t_database";
		ResultSet resultSet = jdbcPool.excute(sql);
		while (resultSet.next()) {
			String url = resultSet.getString("url");
			String username = resultSet.getString("username");
			String password = resultSet.getString("password");
			String orgid = resultSet.getString("orgid");
			System.out.println(url + "-" + username + "-" + password + "-" + orgid);
		}
		jdbcPool.closeAll();
	}
}
