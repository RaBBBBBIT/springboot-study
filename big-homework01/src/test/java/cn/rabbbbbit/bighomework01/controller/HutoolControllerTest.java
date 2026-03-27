package cn.rabbbbbit.bighomework01.controller;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "app.upload-dir=target/test-uploads/hutool-controller")
@AutoConfigureMockMvc
class HutoolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanUp() {
        FileUtil.del("target/test-uploads/hutool-controller");
    }

    @Test
    void shouldReturnFastSimpleUuid() throws Exception {
        mockMvc.perform(get("/api/hutool/id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id", matchesPattern("^[0-9a-f]{32}$")));
    }

    @Test
    void shouldReturnMd5Hash() throws Exception {
        mockMvc.perform(get("/api/hutool/md5").param("text", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.md5").value("e10adc3949ba59abbe56e057f20f883e"));
    }

    @Test
    void shouldUploadFileWithUniqueName() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "hello hutool".getBytes()
        );

        MvcResult result = mockMvc.perform(multipart("/api/hutool/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.originalFilename").value("hello.txt"))
                .andExpect(jsonPath("$.data.savedFilename", matchesPattern("^[0-9a-f]{32}\\.txt$")))
                .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
        String savedPath = root.path("data").path("savedPath").asText();
        Path savedFile = Paths.get(savedPath);

        assertTrue(Files.exists(savedFile));
    }
}
