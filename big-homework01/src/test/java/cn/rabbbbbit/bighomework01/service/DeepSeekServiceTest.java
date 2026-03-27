package cn.rabbbbbit.bighomework01.service;

import cn.rabbbbbit.bighomework01.config.DeepSeekProperties;
import cn.rabbbbbit.bighomework01.dto.response.AiChatResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeepSeekServiceTest {

    @Test
    void shouldCallDeepSeekAndParseAnswer() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody("""
                            {
                              "choices": [
                                {
                                  "message": {
                                    "content": "这是来自 mock DeepSeek 的回答"
                                  }
                                }
                              ]
                            }
                            """));
            server.start();

            DeepSeekProperties properties = new DeepSeekProperties();
            properties.setBaseUrl(server.url("/chat/completions").toString());
            properties.setApiKey("test-api-key");
            properties.setModel("deepseek-chat");

            DeepSeekService deepSeekService = new DeepSeekService(properties);
            AiChatResponse response = deepSeekService.chat("你好");

            RecordedRequest request = server.takeRequest();
            String requestBody = request.getBody().readUtf8();
            assertThat(request.getHeader("Authorization")).isEqualTo("Bearer test-api-key");
            assertThat(request.getHeader("Content-Type")).contains("application/json");
            assertThat(requestBody).contains("\"model\":\"deepseek-chat\"");
            assertThat(requestBody).contains("\"content\":\"你好\"");
            assertThat(response.answer()).isEqualTo("这是来自 mock DeepSeek 的回答");
            assertThat(response.model()).isEqualTo("deepseek-chat");
        }
    }

    @Test
    void shouldFailWhenApiKeyIsMissing() {
        DeepSeekProperties properties = new DeepSeekProperties();
        properties.setBaseUrl("https://api.deepseek.com/chat/completions");
        properties.setModel("deepseek-chat");

        DeepSeekService deepSeekService = new DeepSeekService(properties);

        assertThatThrownBy(() -> deepSeekService.chat("你好"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("DEEPSEEK_API_KEY");
    }

    @Test
    void shouldFailWhenDeepSeekReturnsError() throws IOException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse().setResponseCode(500).setBody("{\"error\":\"boom\"}"));
            server.start();

            DeepSeekProperties properties = new DeepSeekProperties();
            properties.setBaseUrl(server.url("/chat/completions").toString());
            properties.setApiKey("test-api-key");
            properties.setModel("deepseek-chat");

            DeepSeekService deepSeekService = new DeepSeekService(properties);

            assertThatThrownBy(() -> deepSeekService.chat("你好"))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("HTTP 500");
        }
    }
}
