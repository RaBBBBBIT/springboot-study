package cn.rabbbbbit.week03.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectInfoController {

    @Value("${app.name-name:}")
    private String appName;

    @Value("${app.version:}")
    private String appVersion;

    @Value("${app.description:}")
    private String appDescription;

    @GetMapping("/info")
    public Map<String, Object> getProjectInfo() {
        return Map.of(
                "applicationName", appName,
                "version", appVersion,
                "description", appDescription
        );
    }
}
