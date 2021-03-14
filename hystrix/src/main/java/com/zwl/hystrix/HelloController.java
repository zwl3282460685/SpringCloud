package com.zwl.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.zwl.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author zwl
 * @data 2021/3/13 21:58
 **/
@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return helloService.hello();
    }


    @GetMapping("/hello2")
    public void hello2(){
        HelloCommand helloCommand = new HelloCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.
                Factory.asKey("zwl")), restTemplate);
        String execute = helloCommand.execute();//直接执行
        System.out.println(execute);

        HelloCommand helloCommand2 = new HelloCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.
                Factory.asKey("zwl")), restTemplate);
        try {
            Future<String> queue = helloCommand2.queue();
            String s = queue.get();
            System.out.println(s); //先入队后执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/hello3")
    public void hello3(){
        Future<String> hello2 = helloService.hello2();
        try {
            String s = hello2.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/hello4")
    public void hello4(){
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        String name = helloService.hello3("zwl");
        name = helloService.hello3("zwl");
        ctx.close();
    }

    @GetMapping("/hello5")
    public void hello5() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        UserCollapseCommand cmd1 = new UserCollapseCommand(userService, 99);
        UserCollapseCommand cmd2 = new UserCollapseCommand(userService, 98);
        UserCollapseCommand cmd3 = new UserCollapseCommand(userService, 97);
        UserCollapseCommand cmd4 = new UserCollapseCommand(userService, 96);
        Future<User> q1 = cmd1.queue();
        Future<User> q2 = cmd2.queue();
        Future<User> q3 = cmd3.queue();
        Future<User> q4 = cmd4.queue();
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        User u4 = q4.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        ctx.close();
    }

    @GetMapping("/hello6")
    public void hello6() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        Future<User> q1 = userService.getUserById(99);
        Future<User> q2 = userService.getUserById(98);
        Future<User> q3 = userService.getUserById(97);
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        Thread.sleep(2000);
        Future<User> q4 = userService.getUserById(96);
        User u4 = q4.get();
        System.out.println(u4);
        ctx.close();
    }
}
