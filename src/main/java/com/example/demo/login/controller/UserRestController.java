package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.service.RestService;


@RestController
public class UserRestController {
	@Autowired
	RestService service;
}
