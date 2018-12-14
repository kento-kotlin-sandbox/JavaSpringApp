package com.example.demo.domain.model.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.demo.domain.model.User;

public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {

	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {

		// User 格納用List
		List<User> userList = new ArrayList<>();
		
		// 取得件数分のloop
		while(rs.next()) {
			// Listに追加するインスタンスを生成
			User user = new User();
			
			// 取得したレコードをUserインスタンスに設定
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setUserName(rs.getString("user_name"));
			user.setBirthday(rs.getDate("birthday"));
			user.setAge(rs.getInt("age"));
			user.setMarriage(rs.getBoolean("marriage"));
			user.setRole(rs.getString("role"));
			
			// ListにUserを追加
			userList.add(user);
		}
		
		// 1件も存在しない場合
		if(userList.size() == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return userList;
	}
}
