package cn.rabbbbbit.week02.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import cn.rabbbbbit.week02.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rabbbbbit
 * @date 2026/3/13
 * @description Student
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    private Long id;
    private String name;
    private String avatar;
    private String mobile;
    private GenderEnum gender;
    private Boolean enabled;
    private LocalDate birthday;
    private LocalDateTime createTime;
}
