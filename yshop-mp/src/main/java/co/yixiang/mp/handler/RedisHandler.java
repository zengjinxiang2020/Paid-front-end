/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.mp.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisHandler{

    @Autowired
    RedisTemplate redisTemplate;


    public String getVal(String key) {
        try {
            String value = redisTemplate.opsForValue().get(key).toString();
            return value;
        }catch (Exception e){
            return "";
        }
    }


    public Object getObj(String key) {
        return redisTemplate.opsForValue().get(key);
    }


}
