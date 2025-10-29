package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件
 *
 * @author ：0049003788
 * @date ：2022/12/12 14:42
 */
@RestController
@RequestMapping("file")
@Api(tags = "文件")
public class FileController {
    @Resource
    FileService fileService;

    @ApiOperation("上传文件")
    @PostMapping("upload")
    public Result uploadFile(@RequestPart(value = "file") MultipartFile file,
                             @RequestParam(value = "routeName", required = false) String route) {
        // 检查文件类型
        if (!isValidCommonFile(file)) {
            Result result = new Result();
            result.setCode(500);
            result.setMessage("只能上传xlsx|xls|doc|docx|pdf|PDF|pptx 类型的文件");
            return result;
        }
        return fileService.uploadFile(file, route);
    }

    private boolean isValidCommonFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        // 获取文件的原始文件名
        String originalFilename = file.getOriginalFilename();

        // 检查文件扩展名是否符合要求
        if (originalFilename != null) {
            String extension = getFileExtension(originalFilename);
            return "xlsx".equalsIgnoreCase(extension) || "xls".equalsIgnoreCase(extension) || "doc".equalsIgnoreCase(extension)
                    || "docx".equalsIgnoreCase(extension)|| "pdf".equalsIgnoreCase(extension)|| "PDF".equalsIgnoreCase(extension)
                    || "pptx".equalsIgnoreCase(extension);
        }

        return false;
    }

    @ApiOperation("上传视频")
    @PostMapping("uploadMp4")
    public Result uploadMp4(@RequestPart("file") MultipartFile file,
                             @RequestParam(value = "routeName", required = false) String route) {
        // 检查文件类型
        if (!isValidFileType(file)) {
            Result result = new Result();
            result.setCode(500);
            result.setMessage("只能上传 mp4 或 avr 类型的文件");
            return result;
        }

        return fileService.uploadFile(file, route);
    }


    @ApiOperation("上传图片")
    @PostMapping("uploadImage")
    public Result uploadImage(@RequestPart("file") MultipartFile file,
                             @RequestParam(value = "routeName", required = false) String route) {
        // 检查文件类型
        if (!isValidImageFile(file)) {
            Result result = new Result();
            result.setCode(500);
            result.setMessage("只能上传 png 或 jpg 类型的图片文件");
            return result;
        }

        return fileService.uploadFile(file, route);
    }


    @ApiOperation("上传音频")
    @PostMapping("uploadMp3")
    public Result uploadMp3(@RequestPart("file") MultipartFile file,
                             @RequestParam(value = "routeName", required = false) String route) {
        // 检查文件类型
        if (!isValidAudioFile(file)) {
            Result result = new Result();
            result.setCode(500);
            result.setMessage("只能上传 mp3 或 wav 类型的音频文件");
            return result;
        }

        return fileService.uploadFile(file, route);
    }

    private boolean isValidAudioFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        // 获取文件的原始文件名
        String originalFilename = file.getOriginalFilename();

        // 检查文件扩展名是否符合要求
        if (originalFilename != null) {
            String extension = getFileExtension(originalFilename);
            return "mp3".equalsIgnoreCase(extension) || "wav".equalsIgnoreCase(extension);
        }

        return false;
    }



    private boolean isValidImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        // 获取文件的原始文件名
        String originalFilename = file.getOriginalFilename();

        // 检查文件扩展名是否符合要求
        if (originalFilename != null) {
            String extension = getFileExtension(originalFilename);
            return "png".equalsIgnoreCase(extension) || "jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension);
        }

        return false;
    }


    private boolean isValidFileType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        // 获取文件的原始文件名
        String originalFilename = file.getOriginalFilename();

        // 检查文件扩展名是否符合要求
        if (originalFilename != null) {
            String extension = getFileExtension(originalFilename);
            if ("mp4".equalsIgnoreCase(extension) || "avr".equalsIgnoreCase(extension)) {
                // 检查文件的 MIME 类型是否符合要求
                if (file.getContentType() != null) {
                    return MediaType.valueOf(file.getContentType()).isCompatibleWith(MediaType.valueOf("video/mp4"))
                            || MediaType.valueOf(file.getContentType()).isCompatibleWith(MediaType.valueOf("audio/x-wav"));
                }
            }
        }

        return false;
    }

    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        }
        return null;
    }


    @ApiOperation("删除文件")
    @GetMapping("delete")
    public Result delete(@RequestParam(value = "url") String url) {
        return fileService.delete(url);
    }

}
