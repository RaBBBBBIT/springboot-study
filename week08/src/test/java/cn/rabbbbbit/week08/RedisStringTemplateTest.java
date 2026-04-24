package cn.rabbbbbit.week08;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import cn.rabbbbbit.week08.cache.RedisKeyGroups;
import cn.rabbbbbit.week08.entity.User;
import com.alibaba.fastjson2.JSON;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest(properties = "week08.redis-demo.enabled=false")
class RedisStringTemplateTest {

    private static final String PREFIX = "week08:test";

    private static final String USER_KEY = RedisKeyGroups.userProfile(PREFIX, 1001L);

    private static final String COUNT_KEY = RedisKeyGroups.userVisitCount(PREFIX, 1001L);

    private static final String TOKEN_KEY = RedisKeyGroups.sessionToken(PREFIX, "junit-token");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        assumeTrue(isRedisAvailable(), "Redis 未启动，跳过需要真实 Redis 的测试。");
        stringRedisTemplate.delete(List.of(USER_KEY, COUNT_KEY, TOKEN_KEY));
    }

    @AfterEach
    void tearDown() {
        if (isRedisAvailable()) {
            stringRedisTemplate.delete(List.of(USER_KEY, COUNT_KEY, TOKEN_KEY));
        }
    }

    @Test
    void shouldSetAndGetStringValueWithGroupedKeyAndExpiration() {
        String userJson = JSON.toJSONString(new User(1001L, "李四", 88));

        stringRedisTemplate.opsForValue().set(USER_KEY, userJson, Duration.ofSeconds(10));

        String value = stringRedisTemplate.opsForValue().get(USER_KEY);
        Long ttl = stringRedisTemplate.getExpire(USER_KEY);
        User user = JSON.parseObject(value, User.class);

        System.out.println("[week08-test] key=" + USER_KEY);
        System.out.println("[week08-test] value=" + value);
        System.out.println("[week08-test] ttl=" + ttl + " 秒");

        assertThat(USER_KEY).startsWith("week08:test:user:");
        assertThat(value).isEqualTo(userJson);
        assertThat(ttl).isPositive();
        assertThat(user.getName()).isEqualTo("李四");
    }

    @Test
    void shouldStoreAndIncrementNumericStringValue() {
        stringRedisTemplate.opsForValue().set(COUNT_KEY, "10", Duration.ofSeconds(10));

        Long count = stringRedisTemplate.opsForValue().increment(COUNT_KEY, 5);
        String value = stringRedisTemplate.opsForValue().get(COUNT_KEY);

        System.out.println("[week08-test] key=" + COUNT_KEY);
        System.out.println("[week08-test] increment value=" + value);

        assertThat(count).isEqualTo(15L);
        assertThat(value).isEqualTo("15");
        assertThat(stringRedisTemplate.getExpire(COUNT_KEY)).isPositive();
    }

    @Test
    void shouldExpireStringValue() throws InterruptedException {
        stringRedisTemplate.opsForValue().set(TOKEN_KEY, "temporary-token", Duration.ofSeconds(1));

        assertThat(stringRedisTemplate.opsForValue().get(TOKEN_KEY)).isEqualTo("temporary-token");

        Thread.sleep(1500L);

        String expiredValue = stringRedisTemplate.opsForValue().get(TOKEN_KEY);
        System.out.println("[week08-test] expired key=" + TOKEN_KEY);
        System.out.println("[week08-test] expired value=" + expiredValue);

        assertThat(expiredValue).isNull();
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
