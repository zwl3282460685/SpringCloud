package com.zwl.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zwl.commons.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author zwl
 * @data 2021/3/14 15:21
 **/
@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 注解方式实现请求合并
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "200")})
    public Future<User> getUserById(Integer id){
        return null;
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids){
        User[] users = restTemplate.getForObject("http://provider/user/{1}", User[].class, StringUtils.join(ids, ","));
        return Arrays.asList(users);
    }
}
