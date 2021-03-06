package com.example.demo.domain.model.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.repository.UserDao;


@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {
    
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// Userテーブルの件数を取得
	@Override
	public int count() throws DataAccessException {
		// 全件取得してカウント
		int count =jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);

		return count;
	}
	
	// Userテーブルにデータを1件insert
	@Override
	public int insertOne(User user) throws DataAccessException {
		
		// パスワード暗号化
		String password = passwordEncoder.encode(user.getPassword());
		
		// ユーザーテーブルに1件登録するSQL
		String sql = "INSERT INTO m_user(user_id,"
				   + "password,"
				   + "user_name,"
				   + "birthday,"
				   + "age,"
				   + "marriage,"
				   + "role) "
				   + "VALUES(?, ?, ?, ?, ?, ?, ?)";
		
        // 1件登録
		int rowNumber =jdbc.update(sql
                ,user.getUserId()
				,password
				,user.getUserName()
				,user.getBirthday()
				,user.getAge()
				,user.isMarriage()
				,user.getRole());
		
		return rowNumber;
	}
	
	// Userテーブルのデータを1件取得
	@Override
	public User selectOne(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM m_user" + " WHERE user_id = ?", userId);
		
		// 結果返却用変数
		User user = new User();
		
		// 取得結果をインスタンスにセット
		// ユーザーID
		user.setUserId((String)map.get("user_id"));
		// パスワード
        user.setPassword((String)map.get("passoword"));
        // ユーザー名
        user.setUserName((String)map.get("user_name"));
        // 誕生日
        user.setBirthday((Date)map.get("birthday"));
        // 年齢
        user.setAge((Integer)map.get("age"));
        // 結婚ステータス
        user.setMarriage((Boolean)map.get("marriage"));
        // ロール
        user.setRole((String)map.get("role"));

		return user;
	}
	
	// Userテーブルの全データを取得
	@Override
	public List<User> selectMany() throws DataAccessException {
		// m_userテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");
		
		// 結果返却用リスト作成
		List<User> userList = new ArrayList<>();
		
		// 取得したデータを結果返却用のListに格納
		for (Map<String, Object> map : getList) {
			// Userインスタンスの生成
			User user = new User();
			
			// Userインスタンスに取得したデータをセット
			// ユーザーID
			user.setUserId((String)map.get("user_id"));
			// パスワード
			user.setPassword((String)map.get("password"));
			// ユーザー名
			user.setUserName((String)map.get("user_name"));
			// 誕生日
			user.setBirthday((Date)map.get("birthday"));
			// 年齢
			user.setAge((Integer)map.get("age"));
			// 結婚ステータス
			user.setMarriage((Boolean)map.get("marriage"));
			// ロール
			user.setRole((String)map.get("role"));
			
			// 結果返却用リストに追加
			userList.add(user);
		}
		return userList;
	}
	
	// Userテーブルを1件更新
	@Override
	public int updateOne(User user) throws DataAccessException {
		
		// パスワード暗号化
		String password = passwordEncoder.encode(user.getPassword());
		
		// 1件更新するSQL
		String sql = "UPDATE M_USER"
			   	   + " SET"
				   + " password=?,"
				   + " user_name=?,"
				   + " birthday=?,"
				   + " age=?,"
				   + " marriage=?"
				   + " WHERE user_id=?";
		
		// 1件更新
		int rowNumber = jdbc.update(sql
				, password
				, user.getUserName()
				, user.getBirthday()
				, user.getAge()
				, user.isMarriage()
				, user.getUserId()
				);
		
		// 一時的に追加
		//if(rowNumber > 0) {
		//	throw new DataAccessException("トランザクション確認") {};
		//}
		
		return rowNumber;
	}
	
	// Userテーブルを1件削除
	@Override
	public int deleteOne(String userId) throws DataAccessException {
		// 1件削除
		int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);
		
        return rowNumber;
	}
	
	// Userテーブルの全データをCSVに出力
	@Override
	public void userCsvOut() throws DataAccessException {
		// m_userテーブルのデータを全件取得
		String sql = "SELECT * FROM m_user";
		
		// ResultSetExtractorの生成
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		
		// SQLの実行＆CSV出力
		jdbc.query(sql, handler);
	}
}
