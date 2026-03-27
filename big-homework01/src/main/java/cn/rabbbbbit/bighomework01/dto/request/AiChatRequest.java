package cn.rabbbbbit.bighomework01.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AiChatRequest(@NotBlank(message = "question 不能为空") String question) {
}
