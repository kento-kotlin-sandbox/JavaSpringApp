package com.example.demo.trySpring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {
	@GetMapping("/hello")
	public String GetHello() {
		// hello.htmlに画面遷移
		return "hello";
	}
}
