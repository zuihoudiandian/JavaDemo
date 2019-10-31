package com.example.controller;

import com.example.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * Created by codedrinker on 2019/6/26.
 */
@Controller
@Slf4j
public class FileController {
    private Boolean haveCreatePath = false;
    @Value("${custom.tmpLocation}")
    private  String tmpLocation;
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        String fileName = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("editormd-image-file");

        if (!multipartFile.isEmpty()) {
            try {
                //上传文件的源文件名
                String oldFileName = multipartFile.getOriginalFilename();

                //处理后的文件名
                fileName = StringUtils.substringBeforeLast(oldFileName, ".") + "-" + UUID.randomUUID() + "." + StringUtils.substringAfterLast(oldFileName, ".");

                // 存储在当前web服务路径下的upload路径
                String destPath = tmpLocation;

                //判断该路径是否存在,如果不存在则创建该路径
                if (!haveCreatePath) {
                    File destFile = new File(destPath);
                    destFile.mkdirs();
                    haveCreatePath = true;
                }
                String fileUrl="/uploadImg/"+fileName;
                System.out.println(fileUrl+"地址");
               multipartFile.transferTo(new File(destPath+fileName));
                FileDTO fileDTO = new FileDTO();
                fileDTO.setSuccess(1);
                fileDTO.setUrl(fileUrl);
                return fileDTO;
            } catch (Exception e) {
                e.printStackTrace();
                log.error("uploadImg error", e);
                FileDTO fileDTO = new FileDTO();
                fileDTO.setSuccess(0);
                fileDTO.setMessage("上传失败");
                return fileDTO;

            }

        }
        return null;
    }

}

