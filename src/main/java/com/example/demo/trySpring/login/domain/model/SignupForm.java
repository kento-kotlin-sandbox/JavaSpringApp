package com.example.demo.trySpring.login.domain.model;

import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Data
public class SignupForm {
	// ユーザーID
	@NotBlank
	@Email
	private String userId;

	// パスワード
	@NotBlank
	@Length(min=4, max=100)
	@Pattern(regexp="^[a-zA-Z0-9]+$")
	private String password;

	// ユーザー名
	@NotBlank
	private String userName;
	
	// 誕生日
	@NotNull
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birthday;
	
	// 年齢
	@Min(20)
	@Max(100)
	private int age;

	// 結婚ステータス
	@AssertFalse
	private boolean marriage;
}
