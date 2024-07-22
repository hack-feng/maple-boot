package com.maple.common.util;

import com.maple.common.config.LocalFileProperties;
import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCheckException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2024/1/10
 */
@Slf4j
@Component
@AllArgsConstructor
public class LocalFileUtil {
    
    private final LocalFileProperties fileProperties;

    private static final List<String> FILE_TYPE_LIST_IMAGE = Arrays.asList(
            "image/png",
            "image/jpg",
            "image/jpeg",
            "image/bmp");

    /**
     * 上传图片
     */
    public String uploadImage(MultipartFile file) {
        // 检查图片类型
        String contentType = file.getContentType();
        if (!FILE_TYPE_LIST_IMAGE.contains(contentType)) {
            throw new RuntimeException("上传失败，不允许的文件类型");
        }
        int size = (int) file.getSize();
        if (size > fileProperties.getMaxFileSize()) {
            throw new RuntimeException("文件过大");
        }
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String afterName = StringUtils.substringAfterLast(fileName, ".");
        //获取文件前缀
        String prefName = StringUtils.substringBeforeLast(fileName, ".");
        //获取一个时间毫秒值作为文件名
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + prefName + "." + afterName;
        File filePath = new File(fileProperties.getImageFilePath(), fileName);

        //判断文件是否已经存在
        if (filePath.exists()) {
            throw new RuntimeException("文件已经存在");
        }
        //判断文件父目录是否存在
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            log.error("图片上传失败", e);
            throw new RuntimeException("图片上传失败");
        }
        return "/fileApi/local/images/" + fileName;
    }

    /**
     * 批量上传文件
     */
    public List<Map<String, Object>> uploadFiles(MultipartFile[] files) {
        int size = 0;
        for (MultipartFile file : files) {
            size = (int) file.getSize() + size;
        }
        if (size > fileProperties.getMaxFileSize()) {
            throw new RuntimeException("文件过大");
        }
        List<Map<String, Object>> fileInfoList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> map = new HashMap<>();
            String fileName = files[i].getOriginalFilename();
            //获取文件后缀
            String afterName = StringUtils.substringAfterLast(fileName, ".");
            //获取文件前缀
            String prefName = StringUtils.substringBeforeLast(fileName, ".");

            String fileServiceName = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new Date()) + i + "_" + prefName + "." + afterName;
            File filePath = new File(fileProperties.getDocFilePath(), fileServiceName);
            // 判断文件父目录是否存在
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(filePath);
            } catch (IOException e) {
                log.error("文件上传失败", e);
                throw new RuntimeException("文件上传失败");
            }
            map.put("fileName", fileName);
            map.put("filePath", filePath);
            map.put("fileServiceName", "/fileApi/local/doc/" + fileServiceName);
            fileInfoList.add(map);
        }
        return fileInfoList;
    }

    /**
     * 批量删除文件
     *
     * @param fileNameArr 服务端保存的文件的名数组
     */
    public void deleteFile(String[] fileNameArr) {
        for (String fileName : fileNameArr) {
            String filePath = fileProperties.getDocFilePath() + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                    log.warn("文件删除失败", e);
                }
            } else {
                log.warn("文件: {} 删除失败，该文件不存在", fileName);
            }
        }
    }

    /**
     * 下载文件
     */
    public void downLoadFile(HttpServletResponse response, String fileName) {
        String encodeFileName = null;
        try {
            encodeFileName = URLDecoder.decode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "下载文件失败");
        }
        File file = new File(fileProperties.getDocFilePath() + encodeFileName);
        // 下载文件
        if (!file.exists()) {
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "文件不存在！");
        }
        try (FileInputStream inputStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {
            response.reset();
            // 设置响应类型	PDF文件为"application/pdf"，WORD文件为："application/msword"， EXCEL文件为："application/vnd.ms-excel"。
            response.setContentType("application/octet-stream;charset=utf-8");
            //设置响应的文件名称,并转换成中文编码
            String afterName = StringUtils.substringAfterLast(fileName, "_");
            //保存的文件名,必须和页面编码一致,否则乱码
            afterName = response.encodeURL(new String(afterName.getBytes(), StandardCharsets.ISO_8859_1.displayName()));
            response.setHeader("Content-type", "application-download");
            //attachment作为附件下载；inline客户端机器有安装匹配程序，则直接打开；注意改变配置，清除缓存，否则可能不能看到效果
            response.addHeader("Content-Disposition", "attachment;filename=" + afterName);
            response.addHeader("filename", afterName);
            //将文件读入响应流
            int length = 1024;
            byte[] buf = new byte[1024];
            int readLength = inputStream.read(buf, 0, length);
            while (readLength != -1) {
                outputStream.write(buf, 0, readLength);
                readLength = inputStream.read(buf, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
