package cn.rabbbbbit.bighomework01.controller;

import cn.rabbbbbit.bighomework01.config.AppConfig;
import cn.rabbbbbit.bighomework01.dto.response.AppInfoResponse;
import cn.rabbbbbit.bighomework01.dto.response.DbInfoResponse;
import cn.rabbbbbit.bighomework01.dto.response.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final AppConfig appConfig;
    private final Environment environment;

    @Value("${server.port}")
    private int serverPort;

    public ConfigController(AppConfig appConfig, Environment environment) {
        this.appConfig = appConfig;
        this.environment = environment;
    }

    @GetMapping("/app-info")
    public Result<AppInfoResponse> getAppInfo() {
        return Result.success(new AppInfoResponse(
                appConfig.getName(),
                appConfig.getVersion(),
                appConfig.getDescription(),
                appConfig.getUploadDir(),
                appConfig.getDatabase(),
                serverPort,
                getActiveProfiles()
        ));
    }

    @GetMapping("/db-info")
    public Result<DbInfoResponse> getDbInfo() {
        return Result.success(new DbInfoResponse(
                appConfig.getDatabase().getHost(),
                appConfig.getDatabase().getUsername(),
                getPrimaryProfile()
        ));
    }

    private List<String> getActiveProfiles() {
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length == 0) {
            return List.of("default");
        }
        return Arrays.asList(profiles);
    }

    private String getPrimaryProfile() {
        return getActiveProfiles().get(0);
    }
}
