package cn.rabbbbbit.week02.service;

import cn.rabbbbbit.week02.constant.GenderEnum;
import cn.rabbbbbit.week02.dto.StudentAddDTO;
import cn.rabbbbbit.week02.dto.StudentUpdateDTO;
import cn.rabbbbbit.week02.entity.Student;
import cn.rabbbbbit.week02.vo.StudentVO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

/**
 * @author rabbbbbit
 * @date 2026/3/13
 * @description StudentService: 学生CRUD功能
 */
@Service
public class StudentService {

    private static final ConcurrentHashMap<Long, Student> STUDENT_DATA = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1002L);

    static {
        initStudentData();
    }

    public List<StudentVO> getAllStudents() {
        List<StudentVO> list = new ArrayList<>();
        STUDENT_DATA.values().forEach(student -> list.add(toStudentVO(student)));
        list.sort((left, right) -> Long.compare(left.getId(), right.getId()));
        return list;
    }

    public StudentVO addStudent(StudentAddDTO studentAddDTO) {
        Student student = Student.builder()
                .id(ID_GENERATOR.incrementAndGet())
                .name(studentAddDTO.getName())
                .mobile(studentAddDTO.getMobile())
                .gender(studentAddDTO.getGender())
                .avatar(studentAddDTO.getAvatar())
                .enabled(true)
                .birthday(studentAddDTO.getBirthday())
                .createTime(LocalDateTime.now())
                .build();
        STUDENT_DATA.put(student.getId(), student);
        return toStudentVO(student);
    }

    public StudentVO getStudent(Long id) {
        Student student = STUDENT_DATA.get(id);
        if (student == null) {
            return null;
        }
        return toStudentVO(student);
    }

    public List<StudentVO> getStudentByName(String name) {
        List<StudentVO> list = new ArrayList<>();
        STUDENT_DATA.values().forEach(student -> {
            if (student.getName() != null && student.getName().contains(name)) {
                list.add(toStudentVO(student));
            }
        });
        list.sort((left, right) -> Long.compare(left.getId(), right.getId()));
        return list;
    }

    public StudentVO updateStudent(Long id, StudentUpdateDTO studentUpdateDTO) {
        Student student = STUDENT_DATA.get(id);
        if (student == null) {
            return null;
        }
        student.setName(studentUpdateDTO.getName());
        student.setMobile(studentUpdateDTO.getMobile());
        student.setAvatar(studentUpdateDTO.getAvatar());
        return toStudentVO(student);
    }

    public boolean deleteStudent(Long id) {
        return STUDENT_DATA.remove(id) != null;
    }

    public void reset() {
        STUDENT_DATA.clear();
        ID_GENERATOR.set(1002L);
        initStudentData();
    }

    private static void initStudentData() {
        Student student1 = Student.builder()
                .id(1001L)
                .name("张三")
                .mobile("13888888888")
                .gender(GenderEnum.MALE)
                .avatar("https://rabbbbbit.cn/1.png")
                .enabled(true)
                .birthday(LocalDate.of(1990, 1, 1))
                .createTime(LocalDateTime.now())
                .build();
        Student student2 = Student.builder()
                .id(1002L)
                .name("张三三")
                .mobile("13888888889")
                .gender(GenderEnum.FEMALE)
                .avatar("https://rabbbbbit.cn/2.png")
                .enabled(true)
                .birthday(LocalDate.of(1990, 1, 1))
                .createTime(LocalDateTime.now())
                .build();
        STUDENT_DATA.put(student1.getId(), student1);
        STUDENT_DATA.put(student2.getId(), student2);
    }

    private StudentVO toStudentVO(Student student) {
        return StudentVO.builder()
                .id(student.getId())
                .name(student.getName())
                .mobile(student.getMobile())
                .gender(student.getGender())
                .createTime(student.getCreateTime())
                .build();
    }
}
