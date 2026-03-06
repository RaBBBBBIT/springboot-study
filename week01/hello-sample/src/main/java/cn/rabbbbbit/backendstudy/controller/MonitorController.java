package cn.rabbbbbit.backendstudy.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MonitorController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", "UP");
        result.put("timestamp", Instant.now().toString());
        result.put("uptimeMs", runtimeMXBean.getUptime());
        return result;
    }

    @GetMapping("/system-info")
    public Map<String, Object> systemInfo() {
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("osName", System.getProperty("os.name"));
        result.put("osVersion", System.getProperty("os.version"));
        result.put("osArch", System.getProperty("os.arch"));
        result.put("javaVersion", System.getProperty("java.version"));
        result.put("javaVendor", System.getProperty("java.vendor"));
        result.put("userTimezone", System.getProperty("user.timezone"));
        result.put("availableProcessors", runtime.availableProcessors());
        result.put("freeMemoryBytes", runtime.freeMemory());
        result.put("totalMemoryBytes", runtime.totalMemory());
        result.put("maxMemoryBytes", runtime.maxMemory());

        try {
            InetAddress localHost = InetAddress.getLocalHost();
            result.put("hostName", localHost.getHostName());
            result.put("hostAddress", localHost.getHostAddress());
        } catch (Exception ex) {
            result.put("hostName", "unknown");
            result.put("hostAddress", "unknown");
        }
        return result;
    }
}
