package com.example.controller;


import com.example.domain.User;
import com.example.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController implements ErrorController {
	@Autowired
	UserInfoService userInfoService;
	@GetMapping("register")
	public String index() {
		return "register";
	}
	
	@GetMapping("/admin") 
	public String admin() {
		User user = userInfoService.getUserInfo();
		return "admin";
	}
	
	@GetMapping("/")
	public String member() {
        System.out.println("member");
	    return "admin";
	}

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
	
	@GetMapping("/login-user")
	public String getLoginUser() {
		System.out.println("login-user");
		return "";
	}

	@GetMapping("/login")
	public String getLogin() {
		System.out.println("login");
		return "login";
	}
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "401";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        System.out.println("error");
        return "/error";
    }
}
