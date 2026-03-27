package cn.rabbbbbit.bighomework01.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.rabbbbbit.bighomework01.dto.response.IdResponse;
import cn.rabbbbbit.bighomework01.dto.response.Md5Response;
import cn.rabbbbbit.bighomework01.dto.response.Result;
import cn.rabbbbbit.bighomework01.dto.response.UploadResponse;
import cn.rabbbbbit.bighomework01.service.HutoolFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/hutool")
public class HutoolController {

    private final HutoolFileService hutoolFileService;

    public HutoolController(HutoolFileService hutoolFileService) {
        this.hutoolFileService = hutoolFileService;
    }

    @GetMapping("/id")
    public Result<IdResponse> getId() {
        return Result.success(new IdResponse(IdUtil.fastSimpleUUID()));
    }

    @GetMapping("/md5")
    public Result<Md5Response> getMd5(@RequestParam String text) {
        return Result.success(new Md5Response(text, SecureUtil.md5(text)));
    }

    @PostMapping("/upload")
    public Result<UploadResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return Result.success(hutoolFileService.upload(file));
    }
}
