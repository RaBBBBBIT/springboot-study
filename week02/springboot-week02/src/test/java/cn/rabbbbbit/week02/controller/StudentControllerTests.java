package cn.rabbbbbit.week02.controller;

import cn.rabbbbbit.week02.constant.GenderEnum;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import cn.rabbbbbit.week02.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService.reset();
    }

    @Test
    void listStudentsShouldReturnSeedData() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1001))
                .andExpect(jsonPath("$.data[0].name").value("张三"));
    }

    @Test
    void getStudentShouldReturnExistingStudent() throws Exception {
        mockMvc.perform(get("/api/students/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1001))
                .andExpect(jsonPath("$.data.name").value("张三"))
                .andExpect(jsonPath("$.data.gender").value(GenderEnum.MALE.name()));
    }

    @Test
    void createStudentShouldCreateNewStudent() throws Exception {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("name", "mqxu");
        requestBody.put("avatar", "https://rabbbbbit.cn/avatar.jpg");
        requestBody.put("mobile", "12345678901");
        requestBody.put("gender", "MALE");
        requestBody.put("birthday", LocalDate.of(1999, 1, 1));

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student created."))
                .andExpect(jsonPath("$.data.id").value(1003))
                .andExpect(jsonPath("$.data.name").value("mqxu"));
    }

    @Test
    void updateStudentShouldModifyStudent() throws Exception {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("name", "张三111");
        requestBody.put("avatar", "https://rabbbbbit.cn/new.jpg");
        requestBody.put("mobile", "12345678901");

        mockMvc.perform(put("/api/students/1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student updated."))
                .andExpect(jsonPath("$.data.id").value(1001))
                .andExpect(jsonPath("$.data.name").value("张三111"))
                .andExpect(jsonPath("$.data.mobile").value("12345678901"));
    }

    @Test
    void searchStudentsShouldReturnMatches() throws Exception {
        mockMvc.perform(get("/api/students/search").param("name", "张"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void deleteStudentShouldRemoveStudent() throws Exception {
        mockMvc.perform(delete("/api/students/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student deleted."));

        mockMvc.perform(get("/api/students/1001"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Student not found."));
    }
}
