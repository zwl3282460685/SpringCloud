package com.zwl.consumer;


import com.zwl.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * @author zwl
 * @data 2021/3/11 20:47
 **/
@RestController
public class UserHelloController {
    @Autowired
    DiscoveryClient discoveryClient;

    //用于简化代码
    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate restTemplate;

    @GetMapping("/hello1")
    public String hello1() {
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://localhost:1113/hello");
            con = (HttpURLConnection) url.openConnection();
            if(con.getResponseCode() == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = br.readLine();
                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return "error";
    }

    @GetMapping("/hello2")
    public String hello2() {
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        ServiceInstance instance = list.get(0);
        String host = instance.getHost();
        int port = instance.getPort();
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(host + ":").append(port).append("/hello");
        String ss = restTemplate.getForObject(sb.toString(), String.class);
        return ss;
    }


    //该负载均衡的RestTemplate
    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate2;
    @GetMapping("/hello3")
    public String hello3() {
        return restTemplate2.getForObject("http://provider/hello", String.class);
    }

    @GetMapping("/hello4")
    public void hello4(){
        String s1 = restTemplate2.getForObject("http://provider/hello2?name={1}", String.class, "zwl");
        System.out.println(s1);
        ResponseEntity<String> responseEntity = restTemplate2.getForEntity("http://provider/hello2?name={1}", String.class, "javaboy");
        String body = responseEntity.getBody();
        System.out.println("body: " + body);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("状态码：" + statusCode);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println("statusCodeValue: " + statusCodeValue);
        HttpHeaders headers = responseEntity.getHeaders();
        Set<String> sets = headers.keySet();
        System.out.println("++++++++++++++++++++++++++++++header+++++++++++++++++++++++++" );
        for(String set : sets){
            System.out.println(set + ":" + headers.get(set));

        }
    }

    @GetMapping("/hello5")
    public void hello5(){
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "zwl");
        map.add("password", "123");
        map.add("id", 99);
        User user = restTemplate2.postForObject("http://provider/user1", map, User.class);
        System.out.println(user);

        user.setId(98);
        User user1 = restTemplate2.postForObject("http://provider/user2",user,User.class);
        System.out.println(user1);
    }

    @GetMapping("/hello6")
    public void hello6(){
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "zwl");
        map.add("password", "123");
        map.add("id", 99);
        URI uri = restTemplate2.postForLocation("http://provider/register", map);
        System.out.println(uri);
        String s = restTemplate2.getForObject(uri, String.class);
        System.out.println(s);
    }

    @GetMapping("/hello7")
    public void hello7(){
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "zwl");
        map.add("password", "123");
        map.add("id", 99);
        restTemplate2.put("http://provider/user1", map);

        User user = new User();
        user.setId(100);
        user.setUsername("zwlll");
        user.setPassword("123456");

        restTemplate2.put("http://provider/user2", user);
    }

    @GetMapping("/hello8")
    public void hello8(){
        restTemplate2.delete("http://provider/user1?id={1}", 99);
        restTemplate2.delete("http://provider/user2/{1}", 99);
    }
}
