package com.example.logindb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String IndexForm(){
        return "index";
    }
    @GetMapping("result")//모든 사용자 접근 가능
    public String ResultForm(){
        return "result";
    }

    @GetMapping("/user")
    public String UserForm(){//User권한자만 접근가능
        return "user";
    }

    @GetMapping("/admin")
    public String AdminForm() { //ADMIN 권한자만 접근 가능
        return "admin";

    }

}
