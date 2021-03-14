package com.zwl.hystrix;


import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * @author zwl
 * @data 2021/3/14 10:12
 **/
public class HelloCommand extends HystrixCommand<String> {

    RestTemplate restTemplate;

    public HelloCommand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        //int i = 1/0;
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    //请求失败的回调,并获取异常信息
    @Override
    protected String getFallback() {
        return "error-command:" + getExecutionException().getMessage();
    }
}