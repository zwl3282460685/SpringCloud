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
//@FeignClient(value = "provider", fallback = HelloServiceFallBack.class)
@FeignClient(value = "provider", fallbackFactory = HelloServiceFallBackFactory.class)
public interface HelloService extends IUserService {
}
