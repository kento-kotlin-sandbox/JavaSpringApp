package com.example.demo.trySpring.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.User;
import com.example.demo.domain.service.UserService;
import com.example.demo.trySpring.login.domain.model.SignupForm;


@Controller
public class SignupController {
	
	@Autowired
	private UserService userService;

	// ラジオボタンの実装
	private Map<String, String> radioMarriage;
	
	// ラジオボタンの初期化メソッド
	private Map<String, String> initRadioMarrige() {
		Map<String, String> radio = new LinkedHashMap<>();
		
		// 既婚、未婚をMapに格納
		radio.put("既婚", "true");
		radio.put("未婚", "false");
		
		return radio;
	}
	
	// ExceptionHandler（DB）
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		// 例外クラスのメッセージをModelに登録
		model.addAttribute("error", "内部サーバーエラー（DB）：ExceptionHandler");
		
		// 例外クラスのメッセージをModelに登録
		model.addAttribute("message", "SignupController で DataAccessExceptionが発生しました");
		
		// HTTPのエラーコード（500）をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
	// ExceptionHandler（基本）
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		// 例外クラスのメッセージをModelに登録
		model.addAttribute("error", "内部サーバーエラー： ExceptionHandler");
		
		// 例外クラスのメッセージをModelに登録
		model.addAttribute("message", "SignupController でExceptionが発生しました");
		
		// HTTPのエラーコード（500）をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
	// ユーザー登録画面のGET用コントローラー
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, Model model) {
		// ラジオボタンの初期化メソッド呼び出し
		radioMarriage = initRadioMarrige();
		// ラジオボタン用のMapを Modelに登録
		model.addAttribute("radioMarriage", radioMarriage);
		
		// signup.htmlに遷移
		return "login/signup.html";
	}
	
	// ユーザー登録画面のPOST用コントローラー
	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated SignupForm form, BindingResult bindingResult, Model model) {
		
		// データバインドが失敗した場合
		// ユーザー登録画面に遷移
		if (bindingResult.hasErrors()) {
			return getSignUp(form, model);
		}
		
		System.out.println(form);
		
		// insert用変数
		User user = new User();
		
		// ユーザーID
		user.setUserId(form.getUserId());
		// パスワード
		user.setPassword(form.getPassword());
		// ユーザー名
		user.setUserName(form.getUserName());
		// 誕生日
		user.setBirthday(form.getBirthday());
		// 年齢
		user.setAge(form.getAge());
		// 結婚ステータス
		user.setMarriage(form.isMarriage());
		// ロール(一般)
		user.setRole("ROLE_GENERAL");
		
		// ユーザー登録処理
		boolean result = userService.insert(user);
		
		// ユーザー登録結果判定
		if(result) {
			System.out.println("登録成功");
		} else {
			System.out.println("登録失敗");
		}
		
		// login.htmlに遷移（リダイレクト）
		return "redirect:/login";
	}
}
