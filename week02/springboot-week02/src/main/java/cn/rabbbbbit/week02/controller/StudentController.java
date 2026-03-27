package cn.rabbbbbit.week02.controller;

import cn.rabbbbbit.week02.dto.StudentAddDTO;
import cn.rabbbbbit.week02.dto.StudentUpdateDTO;
import cn.rabbbbbit.week02.service.StudentService;
import cn.rabbbbbit.week02.vo.ApiResponse;
import cn.rabbbbbit.week02.vo.StudentVO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生控制器
 * <p>提供学生信息的 CRUD 操作接口</p>
 *
 * @author Rabbit
 * @version 1.0
 * @since 2025-03-13
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 获取所有学生列表
     *
     * @return 统一响应结果，包含学生 VO 列表
     */
    @GetMapping
    public ApiResponse<List<StudentVO>> listStudents() {
        return ApiResponse.ok(studentService.getAllStudents());
    }

    /**
     * 根据姓名模糊查询学生信息
     *
     * @param name 学生姓名（支持模糊匹配）
     * @return 统一响应结果，包含匹配的学生 VO 列表
     */
    @GetMapping("/search")
    public ApiResponse<List<StudentVO>> getStudentByName(@RequestParam String name) {
        return ApiResponse.ok(studentService.getStudentByName(name));
    }

    /**
     * 根据 ID 查询单个学生信息
     *
     * @param id 学生 ID
     * @return 响应实体，包含学生 VO 信息；若未找到返回 404 状态码
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentVO>> getStudent(@PathVariable Long id) {
        StudentVO student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Student not found."));
        }
        return ResponseEntity.ok(ApiResponse.ok(student));
    }

    /**
     * 创建新学生
     *
     * @param studentAddDTO 学生新增数据传输对象，包含学生基本信息
     * @return 响应实体，HTTP 201 状态码表示创建成功，返回包含学生 VO 的响应
     */
    @PostMapping
    public ResponseEntity<ApiResponse<StudentVO>> createStudent(@RequestBody StudentAddDTO studentAddDTO) {
        StudentVO studentVO = studentService.addStudent(studentAddDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Student created.", studentVO));
    }

    /**
     * 更新学生信息
     *
     * @param id 学生 ID
     * @param studentUpdateDTO 学生更新数据传输对象，包含要更新的信息
     * @return 响应实体，更新成功返回 200 状态码和学生 VO；若未找到返回 404 状态码
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentVO>> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDTO studentUpdateDTO) {
        StudentVO student = studentService.updateStudent(id, studentUpdateDTO);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Student not found."));
        }
        return ResponseEntity.ok(ApiResponse.ok("Student updated.", student));
    }

    /**
     * 删除学生信息
     *
     * @param id 学生 ID
     * @return 响应实体，删除成功返回 200 状态码；若未找到返回 404 状态码
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        if (!studentService.deleteStudent(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Student not found."));
        }
        return ResponseEntity.ok(ApiResponse.ok("Student deleted.", null));
    }
}
