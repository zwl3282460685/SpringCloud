package com.zwl.openfeign;

import com.zwl.commons.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author zwl
 * @data 2021/3/15 20:55
 **/
@Component
public class HelloServiceFallBackFactory implements FallbackFactory<HelloService> {
    @Override
    public HelloService create(Throwable throwable) {
        return new HelloService() {
            @Override
            public String hello() {
                return "error---";
            }

            @Override
            public String hello2(String name) {
                return "error2---";
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
        };
    }
}
