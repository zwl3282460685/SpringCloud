package com.zwl.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import org.junit.Test;

import java.time.Duration;

/**
 * @author zwl
 * @data 2021/3/15 21:16
 **/
public class Resilience4jTest {

    @Test
    public void test1(){
        //获取一个CircuitBreakerRegistry实例，可以调用ofDefaults获取一个实例，也可以自己定义
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                //故障率阈值百分比，超过这个阈值，断路器就会打开
                .failureRateThreshold(50)
                //断路器保持打开的时间，在到达设置的时间之后，断路器回进入到half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //当断路器处于half open状态时，环形缓冲区的大小
                .ringBufferSizeInClosedState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb1 = r1.circuitBreaker("zwl");
        CircuitBreaker cb2 = r1.circuitBreaker("zwl2", config);
        io.vavr.CheckedFunction0<T> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
    }
}
