package net.springproject.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void testSendMail(){
        redisTemplate.opsForValue().set("email","lol2003olo@gmail.com");
        Object salary = redisTemplate.opsForValue().get("salary");
        int a = 1;
        //redis sertializer adn deserializer are different than java serializer and deserializer, so we need to use the same serializer and deserializer for both set and get operations
    }
}
