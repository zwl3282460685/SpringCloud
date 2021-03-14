package com.zwl.provider;

import com.zwl.commons.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zwl
 * @data 2021/3/13 15:54
 **/
@Controller
public class RegisterController {
    @PostMapping("/register")
    public String register(User user){
        return "redirect:http://provider/loginPage?username=" + user.getUsername();
    }

    @GetMapping("/loginPage")
    @ResponseBody
    public String loginPage(String username){
       return "loginPage:" + username;
    }
}
