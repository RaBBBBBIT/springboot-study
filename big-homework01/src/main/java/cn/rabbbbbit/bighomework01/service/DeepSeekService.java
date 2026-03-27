package cn.rabbbbbit.bighomework01.service;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.rabbbbbit.bighomework01.config.DeepSeekProperties;
import cn.rabbbbbit.bighomework01.dto.response.AiChatResponse;
import org.springframework.stereotype.Service;

@Service
public class DeepSeekService {

    private final DeepSeekProperties deepSeekProperties;

    public DeepSeekService(DeepSeekProperties deepSeekProperties) {
        this.deepSeekProperties = deepSeekProperties;
    }

    public AiChatResponse chat(String question) {
        if (deepSeekProperties.getApiKey() == null || deepSeekProperties.getApiKey().isBlank()) {
            throw new IllegalStateException("DeepSeek API Key 未配置，请设置环境变量 DEEPSEEK_API_KEY");
        }

        JSONObject payload = JSONUtil.createObj()
                .set("model", deepSeekProperties.getModel())
                .set("stream", false)
                .set("messages", JSONUtil.createArray()
                        .put(JSONUtil.createObj()
                                .set("role", "user")
                                .set("content", question)));

        try (HttpResponse response = HttpUtil.createPost(deepSeekProperties.getBaseUrl())
                .header("Authorization", "Bearer " + deepSeekProperties.getApiKey())
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(payload.toString())
                .execute()) {

            String responseBody = response.body();
            if (!response.isOk()) {
                throw new IllegalStateException("DeepSeek 调用失败: HTTP " + response.getStatus() + " - " + responseBody);
            }

            JSONObject json = JSONUtil.parseObj(responseBody);
            JSONArray choices = json.getJSONArray("choices");
            if (choices == null || choices.isEmpty()) {
                throw new IllegalStateException("DeepSeek 返回数据缺少 choices");
            }

            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            if (message == null || message.getStr("content") == null) {
                throw new IllegalStateException("DeepSeek 返回数据缺少回答内容");
            }

            return new AiChatResponse(
                    deepSeekProperties.getModel(),
                    question,
                    message.getStr("content").trim()
            );
        }
    }
}
