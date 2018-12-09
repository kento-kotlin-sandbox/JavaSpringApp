package com.example.demo.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.User;
import com.example.demo.domain.service.UserService;


@Controller
public class HomeController {
    @Autowired
    UserService userService;
    
    // ユーザー一覧画面のGET
    @GetMapping("/home")
    public String getHome(Model model) {
    	// 部分テンプレートの設定
    	model.addAttribute("contents", "login/home::home_contents");
    	
    	return "login/homeLayout";
    }
    
    // ユーザー一覧画面
    @GetMapping("/userList")
    public String getUserList(Model model) {
    	// ユーザー一覧表示用文字列の登録
    	model.addAttribute("contents", "login/userList::userList_contents");
    	
    	// ユーザー一覧生成
    	List<User> userList = userService.selectMany();
    	
    	// Modelにユーザー一覧を登録
    	model.addAttribute("userList", userList);
    	
    	// データ件数取得
    	int count = userService.count();
    	model.addAttribute("userListCount", count);
    	
    	return "login/homeLayout";
    }
    
    // ログアウト
    @PostMapping("/logout")
    public String postLogout() {
    	
    	// ログイン画面にリダイレクト
    	return "redirect:/login";
    }
    
    // ユーザー一覧のCSV出力
    @GetMapping("/userList/csv")
    public String getUserListCsv(Model model) {
    	// TODO:あとで実装する
    	return getUserList(model);
    }
}
