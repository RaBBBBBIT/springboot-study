package cn.rabbbbbit.bighomework01.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ConfigControllerDevTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAppInfoInDevProfile() throws Exception {
        mockMvc.perform(get("/api/config/app-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("Spring Boot 第03周大作业"))
                .andExpect(jsonPath("$.data.serverPort").value(8080))
                .andExpect(jsonPath("$.data.database.host").value("localhost"))
                .andExpect(jsonPath("$.data.activeProfiles[0]").value("dev"));
    }

    @Test
    void shouldReturnDbInfoInDevProfile() throws Exception {
        mockMvc.perform(get("/api/config/db-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.host").value("localhost"))
                .andExpect(jsonPath("$.data.activeProfile").value("dev"));
    }
}
