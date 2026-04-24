package cn.rabbbbbit.week08;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import cn.rabbbbbit.week08.entity.Address;
import cn.rabbbbbit.week08.entity.User;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest(properties = "week08.redis-demo.enabled=false")
class RedisTemplateObjectTest {

    private static final String OBJECT_KEY = "user:001";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        assumeTrue(isRedisAvailable(), "Redis 未启动，跳过需要真实 Redis 的测试。");
        stringRedisTemplate.delete(OBJECT_KEY);
    }

    @Test
    void shouldSaveAndReadUserObjectWithoutSerializable() {
        Address address = new Address();
        address.setCity("南京市");
        address.setStreet("栖霞区羊山北路1号");
        address.setZipCode("210000");

        User user = new User();
        user.setName("张三");
        user.setAge(22);
        user.setEmail("zhangsan@qq.com");
        user.setAddress(address);

        redisTemplate.opsForValue().set(OBJECT_KEY, user);

        Object userObj = redisTemplate.opsForValue().get(OBJECT_KEY);
        User savedUser = JSON.parseObject(JSON.toJSONString(userObj), User.class);
        String rawJsonValue = stringRedisTemplate.opsForValue().get(OBJECT_KEY);

        System.out.println("[week08-object-test] key=" + OBJECT_KEY);
        System.out.println("[week08-object-test] redis raw json=" + rawJsonValue);
        System.out.println("[week08-object-test] redisTemplate object=" + userObj);
        System.out.println("[week08-object-test] parsed user=" + savedUser);

        assertThat(savedUser.getName()).isEqualTo("张三");
        assertThat(savedUser.getAge()).isEqualTo(22);
        assertThat(savedUser.getEmail()).isEqualTo("zhangsan@qq.com");
        assertThat(savedUser.getAddress().getCity()).isEqualTo("南京市");
        assertThat(savedUser.getAddress().getStreet()).isEqualTo("栖霞区羊山北路1号");
        assertThat(savedUser.getAddress().getZipCode()).isEqualTo("210000");
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
