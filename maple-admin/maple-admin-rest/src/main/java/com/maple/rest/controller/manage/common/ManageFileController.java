package com.maple.rest.controller.manage.common;

import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCommonException;
import com.maple.common.util.LocalFileUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 */
@RestController
@RequestMapping("/manage/file")
@Api(tags = "基础功能-文件上传下载")
@AllArgsConstructor
@Slf4j
public class ManageFileController {

    private final LocalFileUtil fileUtil;

    /**
     * 图片上传
     */
    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "图片内容为空，上传失败!");
        }
        return fileUtil.uploadImage(file);
    }

    @PostMapping("/uploadFile")
    public String uploadFiles(@RequestParam(value = "file") MultipartFile files) {
        return String.valueOf(fileUtil.uploadFiles(new MultipartFile[]{files}).get(0).get("fileServiceName"));
    }

    /**
     * 文件批量上传
     */
    @PostMapping("/uploadFiles")
    public List<Map<String, Object>> uploadFiles(@RequestParam(value = "file") MultipartFile[] files) {
        return fileUtil.uploadFiles(files);
    }

    /**
     * 批量删除文件
     */
    @PostMapping("/deleteFiles")
    public void deleteFiles(@RequestParam(value = "files") String[] files) {
        fileUtil.deleteFile(files);
    }

    /**
     * 文件下载功能
     */
    @GetMapping(value = "/download/{fileName:.*}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        try {
            fileUtil.downLoadFile(response, fileName);
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }
}
