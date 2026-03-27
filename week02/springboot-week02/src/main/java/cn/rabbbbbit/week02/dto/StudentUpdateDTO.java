package cn.rabbbbbit.week02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rabbbbbit
 * @date 2026/3/13
 * @description 修改学生的DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentUpdateDTO {

    private String name;
    private String mobile;
    private String avatar;
}
