package cn.rabbbbbit.week05.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "User", description = "通过 MyBatisX 生成的用户实体")
public class User {

    @Schema(description = "主键 ID", example = "1")
    private Long id;

    @Schema(description = "用户名", example = "rabbit")
    private String username;

    @Schema(description = "密码", example = "123456")
    private String password;

    @Schema(description = "年龄", example = "24")
    private Integer age;

    @Schema(description = "邮箱", example = "rabbit@example.com")
    private String email;

    @Schema(description = "创建时间", example = "2026-04-03T10:00:00")
    private LocalDateTime createTime;
}
