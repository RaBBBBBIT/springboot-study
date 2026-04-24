package cn.rabbbbbit.week08.demo;

import cn.rabbbbbit.week08.cache.RedisKeyGroups;
import cn.rabbbbbit.week08.config.RedisDemoProperties;
import cn.rabbbbbit.week08.entity.User;
import com.alibaba.fastjson2.JSON;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "week08.redis-demo", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedisStringDemoRunner implements CommandLineRunner {

    private final StringRedisTemplate stringRedisTemplate;

    private final RedisDemoProperties properties;

    public RedisStringDemoRunner(StringRedisTemplate stringRedisTemplate, RedisDemoProperties properties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.properties = properties;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!isRedisAvailable()) {
            System.out.println("[week08] 未连接到 Redis，请先启动本机 Redis 或修改 application.yml 连接配置。");
            return;
        }

        String prefix = properties.getKeyPrefix();
        Duration ttl = properties.getTtl();
        String userKey = RedisKeyGroups.userProfile(prefix, 1001L);
        String countKey = RedisKeyGroups.userVisitCount(prefix, 1001L);
        String tokenKey = RedisKeyGroups.sessionToken(prefix, "demo-token");
        List<String> demoKeys = List.of(userKey, countKey, tokenKey);

        stringRedisTemplate.delete(demoKeys);

        String userJson = JSON.toJSONString(new User(1001L, "张三", 98));
        stringRedisTemplate.opsForValue().set(userKey, userJson, ttl);

        stringRedisTemplate.opsForValue().set(countKey, "1", ttl);
        Long currentVisitCount = stringRedisTemplate.opsForValue().increment(countKey, 4L);

        stringRedisTemplate.opsForValue().set(tokenKey, "token-value-abc", ttl);

        System.out.println("========== week08 StringRedisTemplate 演示 ==========");
        System.out.println("分组 key 前缀: " + prefix);
        System.out.println("写入用户缓存 key: " + userKey);
        System.out.println("写入访问次数 key: " + countKey);
        System.out.println("写入会话 token key: " + tokenKey);
        System.out.println("读取用户缓存 value: " + stringRedisTemplate.opsForValue().get(userKey));
        System.out.println("读取访问次数 value: " + currentVisitCount);
        System.out.println("读取 token value: " + stringRedisTemplate.opsForValue().get(tokenKey));
        System.out.println("用户缓存剩余过期时间: " + stringRedisTemplate.getExpire(userKey) + " 秒");
        System.out.println("请打开 Redis 桌面管理工具刷新观察以上 key，等待 key 自动过期。");

        Thread.sleep(properties.getWaitAfterWrite().toMillis());

        System.out.println("========== 等待过期后再次读取 ==========");
        System.out.println("过期后用户缓存 value: " + stringRedisTemplate.opsForValue().get(userKey));
        System.out.println("过期后访问次数 value: " + stringRedisTemplate.opsForValue().get(countKey));
        System.out.println("过期后 token value: " + stringRedisTemplate.opsForValue().get(tokenKey));
    }

    private boolean isRedisAvailable() {
        RedisConnectionFactory connectionFactory = stringRedisTemplate.getConnectionFactory();
        if (connectionFactory == null) {
            return false;
        }
        try (RedisConnection connection = connectionFactory.getConnection()) {
            String pong = connection.ping();
            return "PONG".equalsIgnoreCase(pong);
        } catch (RedisConnectionFailureException ex) {
            return false;
        }
    }
}
