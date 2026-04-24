package cn.rabbbbbit.week05.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnDemoUser() throws Exception {
        mockMvc.perform(get("/api/test/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("成功"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("test"))
                .andExpect(jsonPath("$.data.email").value("test@qq.com"));
    }
}
