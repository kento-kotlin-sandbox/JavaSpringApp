package com.example.demo.domain.model.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.domain.model.User;


public interface UserDao {
    // Userテーブルの件数を取得
	public int count() throws DataAccessException;
	
	// Userテーブルのデータを1件insert
	public int insertOne(User user) throws DataAccessException;
	
	// Userテーブルのデータを1件取得
	public User selectOne(String userId) throws DataAccessException;
	
	// Userテーブルの全データを取得
	public List<User> selectMany() throws DataAccessException;
	
	// Userテーブルのデータ1件を更新
	public int updateOne(User user) throws DataAccessException;
	
	// Userテーブルのデータ1件を削除
	public int deleteOne(String userId) throws DataAccessException;
	
	// SQL取得結果をサーバーにCSVで保存する
	public void userCsvOut() throws DataAccessException;
}
