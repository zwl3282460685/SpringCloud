package com.zwl.openfeign;

import com.zwl.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author zwl
 * @data 2021/3/14 20:43
 **/
@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello(){
        return helloService.hello();
    }

    @GetMapping("/hello2")
    public String hello2() throws UnsupportedEncodingException {
        String s = helloService.hello2("zwl");
        System.out.println(s);
        User user = new User();
        user.setId(1);
        user.setUsername("zwl");
        user.setPassword("123");
        User u = helloService.addUser2(user);
        System.out.println(u);
        helloService.deleteUser2(1);
        helloService.getUserByName(URLEncoder.encode("zwl", "UTF-8"));
        return helloService.hello();
    }
}
