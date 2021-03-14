package com.zwl.hystrix;

import com.netflix.hystrix.*;
import com.zwl.commons.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zwl
 * @data 2021/3/14 15:30
 **/
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Integer> {

    private UserService userService;
    private Integer id;

    public UserCollapseCommand(UserService userService, Integer id) {
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand"))
        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200)));
        this.userService = userService;
        this.id = id;
    }

    /**
     * 获取请求的参数
     * @return
     */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /**
     * 请求合并的方法
     * 把合并好的请求统一个发送
     * @param collection
     * @return
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collection) {
        List<Integer> ids = new ArrayList<>(collection.size());
        for(CollapsedRequest<User, Integer> userIntegerCollapsedRequest : collection){
            ids.add(userIntegerCollapsedRequest.getArgument());
        }
        return new UserBatchCommand(ids, userService);
    }

    /**
     * 请求结果分发
     * @param users provider返回的结果
     * @param collection 请求的集合
     */
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection) {
        int count = 0;
        for (CollapsedRequest<User, Integer> request : collection) {
            request.setResponse(users.get(count++));
        }
    }
}
