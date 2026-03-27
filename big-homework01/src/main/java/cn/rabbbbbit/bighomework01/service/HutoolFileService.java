package cn.rabbbbbit.bighomework01.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.rabbbbbit.bighomework01.config.AppConfig;
import cn.rabbbbbit.bighomework01.dto.response.UploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class HutoolFileService {

    private final AppConfig appConfig;

    public HutoolFileService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public UploadResponse upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        String extension = FileUtil.extName(file.getOriginalFilename());
        String generatedName = IdUtil.fastSimpleUUID();
        String savedFilename = extension.isBlank() ? generatedName : generatedName + "." + extension;

        Path uploadDir = Paths.get(appConfig.getUploadDir()).toAbsolutePath().normalize();
        FileUtil.mkdir(uploadDir.toFile());

        Path savedPath = uploadDir.resolve(savedFilename);
        FileUtil.writeBytes(file.getBytes(), savedPath.toFile());

        return new UploadResponse(file.getOriginalFilename(), savedFilename, savedPath.toString());
    }
}
