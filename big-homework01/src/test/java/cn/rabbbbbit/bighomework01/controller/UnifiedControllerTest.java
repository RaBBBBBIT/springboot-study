package cn.rabbbbbit.bighomework01.controller;

import cn.rabbbbbit.bighomework01.dto.response.AiChatResponse;
import cn.rabbbbbit.bighomework01.service.DeepSeekService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UnifiedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeepSeekService deepSeekService;

    @Test
    void shouldReturnAiChatResult() throws Exception {
        when(deepSeekService.chat("你好")).thenReturn(new AiChatResponse("deepseek-chat", "你好", "你好，我是测试回复"));

        mockMvc.perform(post("/api/unified/ai/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "question": "你好"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.model").value("deepseek-chat"))
                .andExpect(jsonPath("$.data.answer").value("你好，我是测试回复"));
    }

    @Test
    void shouldReturnUnifiedValidationError() throws Exception {
        mockMvc.perform(post("/api/unified/ai/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "question": ""
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("question 不能为空"));
    }

    @Test
    void shouldReturnUnifiedServerErrorWhenUpstreamFails() throws Exception {
        when(deepSeekService.chat(anyString())).thenThrow(new IllegalStateException("DeepSeek 调用失败"));

        mockMvc.perform(post("/api/unified/ai/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "question": "请总结一下配置优先级"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("DeepSeek 调用失败"));
    }
}
