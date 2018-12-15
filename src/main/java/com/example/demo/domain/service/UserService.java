package com.example.demo.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.repository.UserDao;


@Service
public class UserService {
    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;
    
    // insert用メソッド
    public boolean insert(User user) {
    	// insert実行
    	int rowNumber = dao.insertOne(user);
    	
    	// 判定用変数
    	boolean result = false;
    	
    	if(rowNumber > 0) {
    		// insert 成功
    		result = true;
    	}
    	
    	return result;
    }
    
    // カウント用メソッド
    public int count() {
    	return dao.count();
    }
    
    // 全件取得用メソッド
    public List<User> selectMany() {
    	// 全件取得
    	return dao.selectMany();
    }
    
    // 一件取得用メソッド
    public User selectOne(String userId) {
    	// 取得実行
    	return dao.selectOne(userId);
    }
    
    // 1件更新
    public Boolean updateOne(User user) {
    	// 1件更新
    	int rowNumber = dao.updateOne(user);
    	// 判定用変数
    	boolean result = false;
    	
    	if(rowNumber > 0) {
    		// 更新成功
    		result = true;
    	}
    	
    	return result;
    }
    
    // 1件削除メソッド
    public boolean deleteOne(String userId) {
    	// 1件削除
    	int rowNumber = dao.deleteOne(userId);
    	
    	// 判定用変数
    	boolean result = false;
    	
    	if (rowNumber > 0) {
    		// 削除成功
    		result = true;
    	}
    	
    	return result;
    }
    
    // ユーザー一覧をCSV出力
    public void userCsvOut() throws DataAccessException {
    	// CSV出力
    	dao.userCsvOut();
    }
    
    // サーバーに保存されたファイルを取得する（Byte配列に変換）
    public byte[] getFile(String fileName) throws IOException {
    	// ファイルシステムの取得
    	FileSystem fs = FileSystems.getDefault();
    	
    	// ファイル取得
    	Path p = fs.getPath(fileName);
    	
    	// ファイルをByte配列に変換
    	byte[] bytes = Files.readAllBytes(p);
    	
    	return bytes;
    }
}
