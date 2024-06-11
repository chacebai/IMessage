package com.javalang.imessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalang.imessage.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class ImessageApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void contextLoads() {
    }

    @Test
    void testJedis() {
        redisTemplate.opsForValue().set("testKey","baishuaishuai");
        Object testKey = redisTemplate.opsForValue().get("testKey");
        System.out.println("testKey:" + testKey);
        User user = new User();
        user.setId("1");
        user.setName("baishuaihsuai");
        redisTemplate.opsForValue().set("user:1", user);

        String sValue;
        try {
            sValue = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        stringRedisTemplate.opsForValue().set("user:2", sValue);

        stringRedisTemplate.opsForHash().put("user:3", "key1", sValue);
        stringRedisTemplate.opsForHash().put("user:3", "key2", sValue);
    }

}
