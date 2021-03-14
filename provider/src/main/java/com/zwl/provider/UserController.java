package com.zwl.provider;

import com.zwl.commons.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zwl
 * @data 2021/3/14 15:09
 **/
@RestController
public class UserController {

    /**
     * 此接口既可以处理合并后的请求，也可以处理单个请求
     * @param ids
     * @return
     */
    @GetMapping("/user/{ids}")
    public List<User> getUserByIds(@PathVariable String ids){
        System.out.println(ids);
        String[] splits = ids.split(",");
        List<User> users = new ArrayList<>();
        for(String s : splits){
            User u = new User();
            u.setId(Integer.parseInt(s));
            users.add(u);
        }
        return users;
    }
}
