package cn.rabbbbbit.bighomework01.dto.response;

import cn.rabbbbbit.bighomework01.config.AppConfig;

import java.util.List;

public record AppInfoResponse(
        String name,
        String version,
        String description,
        String uploadDir,
        AppConfig.Database database,
        int serverPort,
        List<String> activeProfiles
) {
}
