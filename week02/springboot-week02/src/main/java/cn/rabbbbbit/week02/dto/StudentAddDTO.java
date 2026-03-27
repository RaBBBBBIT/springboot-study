package cn.rabbbbbit.week02.dto;

import cn.rabbbbbit.week02.constant.GenderEnum;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rabbbbbit
 * @date 2026/3/13
 * @description 新增学生的DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAddDTO {

    private String name;
    private String mobile;
    private GenderEnum gender;
    private String avatar;
    private LocalDate birthday;
}
