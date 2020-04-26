package com.example;

import com.example.utils.TimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {
        @Autowired
     StringRedisTemplate stringRedisTemplate;  //操作字符串
        //@Autowired
      //  private RedisTemplate redisTemplate;  //操作对象的 KV 结构
        //添加
        @Test
        public void saveRedis(){
                //存数据
            stringRedisTemplate.opsForValue().set("a","test");
                //取数据
                String a = this.stringRedisTemplate.opsForValue().get("a").toString();
                System.out.println(a);
        }
        @Test
        public void Save2(){
                this.stringRedisTemplate.opsForValue().set("b","李四",5, TimeUnit.MINUTES);
        }

        //获取
        public String getRedis(){
            return stringRedisTemplate.opsForValue().get("a");
        }

        @Test
        public  void tset1(){
            Date date = TimeUtils.formatNow("yyyy-MM-dd hh:mm:ss");
            System.out.println(date.getTime());
        }

}
