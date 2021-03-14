package com.zwl.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * @author zwl
 * @data 2021/3/13 21:55
 **/
@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 在这个方法中，将发起一个远程调用，去调用provider中提供的 /hello接口
     * 但是这个调用可能会失败
     * 在这个方法上添加@HystrixCommand，配置fallbackMethod属性，该属性表示调用失败时的临时替代方法（服务降级）
     * ignoreExceptions: 忽略特定异常
     * @return
     */
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    public String hello(){
        //int i = 1/ 0;
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    /**
     * 开启请求缓存
     * @CacheResult 表示请求结果会被缓存起来，缓存的key是方法的参数，value是方法的返回值
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "error2")
    @CacheResult
    public String hello3(String name){
        return restTemplate.getForObject("http://provider/hello2?name={1}", String.class, name);

    }


    public String error2(String name){
        return "error:" + name;
    }

    //实现异步调用
    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2(){
        return new AsyncResult<String>(){
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/hello", String.class);
            }
        };
    }

    /**
     * 注意这个方法要和fallbackMethod一致
     * 方法返回值也要和对应的方法一致
     * 参数加上Throwable 可以在调用抛出异常时进行捕获
     * @return
     */
    public String error(Throwable t){
        return "error" + t.getMessage();
    }
}
