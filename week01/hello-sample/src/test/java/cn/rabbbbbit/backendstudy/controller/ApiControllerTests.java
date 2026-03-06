package cn.rabbbbbit.backendstudy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloShouldReturnGreeting() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Spring Boot!"));
    }

    @Test
    void healthShouldReturnUpStatus() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.uptimeMs").isNumber());
    }

    @Test
    void systemInfoShouldContainKeyFields() throws Exception {
        mockMvc.perform(get("/api/system-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.osName").isString())
                .andExpect(jsonPath("$.javaVersion").isString())
                .andExpect(jsonPath("$.availableProcessors").isNumber())
                .andExpect(jsonPath("$.hostName").isString())
                .andExpect(jsonPath("$.hostAddress").isString());
    }
}
