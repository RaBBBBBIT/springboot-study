package cn.rabbbbbit.week02.vo;

import java.time.LocalDateTime;
import cn.rabbbbbit.week02.constant.GenderEnum;
import lombok.Builder;
import lombok.Data;
import cn.rabbbbbit.week02.entity.Student;

@Data
@Builder
public class StudentVO {

    private Long id;
    private String name;
    private String mobile;
    private GenderEnum gender;
    private LocalDateTime createTime;

    public static StudentVO from(Student student) {
        return StudentVO.builder()
                .id(student.getId())
                .name(student.getName())
                .mobile(student.getMobile())
                .gender(student.getGender())
                .createTime(student.getCreateTime())
                .build();
    }
}
