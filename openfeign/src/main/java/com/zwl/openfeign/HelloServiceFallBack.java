package com.zwl.openfeign;

import com.zwl.commons.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

/**
 * @author zwl
 * @date : 2021/3/15 16:17
 */
@Component
//@RequestMapping("/zwl") //防止请求地址重复
public class HelloServiceFallBack implements HelloService{
    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello2(String name) {
        return "error2";
    }

    @Override
    public User addUser2(User user) {
        return null;
    }

    @Override
    public void deleteUser2(Integer id) {

    }

    @Override
    public void getUserByName(String name) throws UnsupportedEncodingException {

    }
}
