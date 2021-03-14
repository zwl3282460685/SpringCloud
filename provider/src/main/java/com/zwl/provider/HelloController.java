package com.zwl.provider;

import com.zwl.commons.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author zwl
 * @data 2021/3/11 20:39
 **/
@RestController
public class HelloController {
    @Value("${server.port}")
    Integer port;
    @GetMapping("/hello")
    public String hello(){
        return "hello zwl" + port;
    }

    @GetMapping("/hello2")
    public String hello1(String name){
        System.out.println(new Date() + ">>>"  + name);
        return "hello " + name;
    }

    @PostMapping("/user1")
    public User addUser(User user){
        return user;
    }

    @PostMapping("/user2")
    public User addUser2(@RequestBody User user){
        return user;
    }

    @PutMapping("/user1")
    public void updateUser(User user){
        System.out.println(user);
    }

    @PutMapping("/user2")
    public void updateUser2(@RequestBody User user){
        System.out.println(user);
    }

    @DeleteMapping("/user1")
    public void deleteUser(Integer id){
        System.out.println(id);
    }

    @DeleteMapping("/user2/{id}")
    public void deleteUser2(@PathVariable Integer id){
        System.out.println(id);
    }
}
