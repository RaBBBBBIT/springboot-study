package cn.rabbbbbit.week05.controller;

import cn.rabbbbbit.week05.common.Result;
import cn.rabbbbbit.week05.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Week05 测试接口")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Operation(summary = "返回带有 OpenAPI 注解的 User 实体类")
    @GetMapping("/user")
    public Result<User> getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("123456");
        user.setAge(18);
        user.setEmail("test@qq.com");
        user.setCreateTime(LocalDateTime.now());
        return Result.success("成功", user);
    }
}
