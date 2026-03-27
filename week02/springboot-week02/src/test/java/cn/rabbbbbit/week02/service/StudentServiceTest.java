package cn.rabbbbbit.week02.service;

import static org.assertj.core.api.Assertions.assertThat;

import cn.rabbbbbit.week02.constant.GenderEnum;
import cn.rabbbbbit.week02.dto.StudentAddDTO;
import cn.rabbbbbit.week02.dto.StudentUpdateDTO;
import cn.rabbbbbit.week02.vo.StudentVO;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class StudentServiceTest {

    @Resource
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService.reset();
    }

    @Test
    void getAllStudents() {
        List<StudentVO> allStudents = studentService.getAllStudents();
        allStudents.forEach(studentVO -> log.info("{}", studentVO));
        assertThat(allStudents)
                .hasSize(2)
                .extracting(StudentVO::getId)
                .containsExactly(1001L, 1002L);
    }

    @Test
    void addStudent() {
        StudentVO createdStudent = studentService.addStudent(StudentAddDTO.builder()
                .name("mqxu")
                .mobile("12345678901")
                .gender(GenderEnum.MALE)
                .avatar("https://rabbbbbit.cn/avatar.jpg")
                .birthday(LocalDate.of(1999, 1, 1))
                .build());
        log.info("添加成功");
        List<StudentVO> allStudents = studentService.getAllStudents();
        allStudents.forEach(studentVO -> log.info("{}", studentVO));
        assertThat(createdStudent.getId()).isEqualTo(1003L);
        assertThat(createdStudent.getName()).isEqualTo("mqxu");
        assertThat(allStudents).hasSize(3);
        assertThat(allStudents)
                .extracting(StudentVO::getName)
                .contains("mqxu");
    }

    @Test
    void getStudent() {
        StudentVO studentVO = studentService.getStudent(1001L);
        log.info("{}", studentVO);
        assertThat(studentVO).isNotNull();
        assertThat(studentVO.getName()).isEqualTo("张三");
        assertThat(studentVO.getGender()).isEqualTo(GenderEnum.MALE);
    }

    @Test
    void getStudentByName() {
        List<StudentVO> students = studentService.getStudentByName("张");
        students.forEach(item -> log.info("{}", item));
        assertThat(students).hasSize(2);
        assertThat(students)
                .extracting(StudentVO::getName)
                .containsExactly("张三", "张三三");
    }

    @Test
    void updateStudent() {
        StudentVO updatedStudent = studentService.updateStudent(1001L, StudentUpdateDTO.builder()
                .name("张三111")
                .mobile("12345678901")
                .avatar("https://rabbbbbit.cn/new.jpg")
                .build());
        log.info("修改成功");
        StudentVO studentVO = studentService.getStudent(1001L);
        log.info("{}", studentVO);
        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getName()).isEqualTo("张三111");
        assertThat(studentVO.getMobile()).isEqualTo("12345678901");
        assertThat(studentVO.getGender()).isEqualTo(GenderEnum.MALE);
    }

    @Test
    void deleteStudent() {
        boolean deleted = studentService.deleteStudent(1001L);
        log.info("删除成功");
        List<StudentVO> allStudents = studentService.getAllStudents();
        allStudents.forEach(studentVO -> log.info("{}", studentVO));
        assertThat(deleted).isTrue();
        assertThat(allStudents).hasSize(1);
        assertThat(allStudents)
                .extracting(StudentVO::getId)
                .containsExactly(1002L);
    }

    @Test
    void reset() {
        studentService.addStudent(StudentAddDTO.builder()
                .name("temp")
                .mobile("18888888888")
                .gender(GenderEnum.UNKNOWN)
                .avatar("https://rabbbbbit.cn/temp.png")
                .birthday(LocalDate.of(2000, 1, 1))
                .build());
        assertThat(studentService.getAllStudents()).hasSize(3);

        studentService.reset();

        List<StudentVO> allStudents = studentService.getAllStudents();
        assertThat(allStudents)
                .hasSize(2)
                .extracting(StudentVO::getId)
                .containsExactly(1001L, 1002L);
    }
}
