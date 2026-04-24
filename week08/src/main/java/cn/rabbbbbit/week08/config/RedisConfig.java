package cn.rabbbbbit.week08.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Redis配置类，配置RedisTemplate和自定义序列化器
 */
@Configuration
public class RedisConfig {

    /**
     * 配置 RedisTemplate。
     * key 使用 String 序列化器，value 使用 FastJson2 序列化器，保证 Redis 中的数据可读。
     *
     * @param factory Redis 连接工厂
     * @return 配置好的 RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.setConnectionFactory(factory);
        return template;
    }

    /**
     * FastJson2 Redis序列化器。
     *
     * @param <T> 序列化的对象类型
     */
    record FastJsonRedisSerializer<T>(Class<T> clazz) implements RedisSerializer<T> {

        public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

        private static final String AUTO_TYPE_ACCEPT_PREFIX = "cn.rabbbbbit.week08.";

        @Override
        public byte[] serialize(T t) throws SerializationException {
            if (t == null) {
                return new byte[0];
            }
            return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
        }

        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            String str = new String(bytes, DEFAULT_CHARSET);
            JSONReader.Context context = JSONFactory.createReadContext(
                    JSONReader.autoTypeFilter(AUTO_TYPE_ACCEPT_PREFIX)
            );
            return JSON.parseObject(str, clazz, context);
        }
    }
}
