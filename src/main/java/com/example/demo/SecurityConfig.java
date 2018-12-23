package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// データソース
	@Autowired
	private DataSource dataSource;
	
	// ユーザーIDとパスワードを取得するSQL
	private static final String USER_SQL = "SELECT"
			+ " user_id,"
			+ " password,"
			+ " true"
			+ " FROM"
			+ " m_user"
			+ " WHERE"
			+ " user_id = ?";
	
	// ユーザーのロールを取得するSQL
	private static final String ROLE_SQL = "SELECT"
			+ " user_id,"
			+ " role"
			+ " FROM"
			+ " m_user"
			+ " WHERE"
			+ " user_id = ?";
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 静的リソースへのアクセスには、セキュリティを適用しない
		web.ignoring().antMatchers("/webjars/**", "/css/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ログイン不要ページの設定
		http.authorizeRequests()
			.antMatchers("/webjars/**").permitAll()  // webjarsへのアクセス許可
			.antMatchers("/css/**").permitAll()      // cssへのアクセス許可
			.antMatchers("/login").permitAll()       // ログインページは直リンク許可
			.antMatchers("/signup").permitAll()      // ユーザー登録画面は直リンク許可
			.anyRequest().authenticated();           // それ以外は直リンク禁止
		
		// ログイン処理
		http.formLogin()
			.loginProcessingUrl("/login")       // ログイン処理のパス
			.loginPage("/login")                // ログインページの指定
			.failureUrl("/login")               // ログイン失敗時の遷移先
			.usernameParameter("userId")        // ログインページのユーザーID
			.passwordParameter("password")      // ログインページのパスワード
			.defaultSuccessUrl("/home", true);  // ログイン成功後の遷移先
		
		// CSRF対策を無効に設定（一時的）
		http.csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// ログイン処理時のユーザー情報を、DBから取得
		auth.jdbcAuthentication()
		    .dataSource(dataSource)
		    .usersByUsernameQuery(USER_SQL)
		    .authoritiesByUsernameQuery(ROLE_SQL);
	}
}
