package com.zwl.openfeign;

import com.zwl.api.IUserService;
import com.zwl.commons.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @author zwl
 * @data 2021/3/14 20:04
 **/
@FeignClient(value = "provider", fallback = HelloServiceFallBack.class)
public interface HelloService extends IUserService {

   /* @GetMapping("/hello")
    String hello(); //这里的方法名可以随意取

    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);

    @PostMapping("/user2")
    User addUser(@RequestBody User user);

    @DeleteMapping("/user2/{id}")
    void deleteUserById(@PathVariable("id") Integer id);

    @GetMapping("/user3")
    void getUserByName(@RequestHeader("name") String name) throws UnsupportedEncodingException;*/
}
