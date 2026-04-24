package cn.rabbbbbit.week08.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "week08.redis-demo")
public class RedisDemoProperties {

    private boolean enabled = true;

    private String keyPrefix = "week08";

    private Duration ttl = Duration.ofSeconds(30);

    private Duration waitAfterWrite = Duration.ofSeconds(35);

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Duration getTtl() {
        return ttl;
    }

    public void setTtl(Duration ttl) {
        this.ttl = ttl;
    }

    public Duration getWaitAfterWrite() {
        return waitAfterWrite;
    }

    public void setWaitAfterWrite(Duration waitAfterWrite) {
        this.waitAfterWrite = waitAfterWrite;
    }
}
