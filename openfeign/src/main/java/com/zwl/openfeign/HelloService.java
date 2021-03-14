package com.zwl.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zwl
 * @data 2021/3/14 20:04
 **/
@FeignClient("provider")
public interface HelloService {

    @GetMapping("/hello")
    String hello(); //这里的方法名可以随意取
}
