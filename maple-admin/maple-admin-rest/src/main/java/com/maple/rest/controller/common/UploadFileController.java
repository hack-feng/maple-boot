package com.maple.rest.controller.common;

import com.maple.common.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLConnection;
import java.net.URLDecoder;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/3/24
 */
@Controller
@RequestMapping("/file")
@Api(tags = "基础功能-文件上传下载")
public class UploadFileController {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    /**
     * 文档存储路径
     */
    @Value("${file.docFilePath}")
    private String docFilePath;

    @PostMapping("uploadImage")
    @ApiOperation("图片上传")
    @ResponseBody
    public void uploadImage(@RequestParam(value = "file") MultipartFile file) {
    }

    @GetMapping(value = "download/{fileName:.*}")
    @ApiOperation("文件下载功能")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {

        try {
            String savePath = docFilePath;
            // 获取文件名称，中文可能被URL编码
            fileName = URLDecoder.decode(fileName, "UTF-8");
            //判断文件是否存在
            File file = new File(docFilePath + fileName);
            if (!file.exists()) {
                throw new RuntimeException("文件不存在！");
            }
            // 获取本地文件系统中的文件资源
            FileSystemResource resource = new FileSystemResource(savePath + fileName);

            // 解析文件的 mime 类型
            String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(fileName);
            // 无法判断MIME类型时，作为流类型
            mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
            // 实例化MIME
            MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);

            /*
             * 构造响应的头
             */
            HttpHeaders headers = new HttpHeaders();
            // 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
            //获取文件前缀
            String filenameRes;
            String realName = StringUtils.substringAfterLast(fileName, "_");
            filenameRes = java.net.URLEncoder.encode(realName, "UTF-8");
            headers.setContentDispositionFormData("attachment", filenameRes);
            headers.setContentType(mediaType);
            headers.add("filename", filenameRes);
            //返回状态信息
            return ResponseEntity.ok()
                    .headers(headers).contentLength(resource.getInputStream().available())
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件下载失败，原因：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
