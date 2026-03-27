package cn.rabbbbbit.bighomework01.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "server.port=9090")
@AutoConfigureMockMvc
class ConfigControllerPortOverrideTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReadOverriddenServerPort() throws Exception {
        mockMvc.perform(get("/api/config/app-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.serverPort").value(9090));
    }
}
