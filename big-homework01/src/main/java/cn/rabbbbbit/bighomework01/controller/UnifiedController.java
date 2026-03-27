package cn.rabbbbbit.bighomework01.controller;

import cn.rabbbbbit.bighomework01.dto.request.AiChatRequest;
import cn.rabbbbbit.bighomework01.dto.response.AiChatResponse;
import cn.rabbbbbit.bighomework01.dto.response.Result;
import cn.rabbbbbit.bighomework01.service.DeepSeekService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unified")
public class UnifiedController {

    private final DeepSeekService deepSeekService;

    public UnifiedController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/ai/chat")
    public Result<AiChatResponse> chat(@Valid @RequestBody AiChatRequest request) {
        return Result.success(deepSeekService.chat(request.question()));
    }
}
