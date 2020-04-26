package com.example.controller;

import com.example.dto.ResultDTO;
import com.example.exception.CustomizeErrorCode;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.service.UserInfoService;
import com.example.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/1/28.
 * PS: Not easy to write code, please indicate.
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    private Boolean haveCreatePath = false;
    @Value("${avatar.file}")
    private  String avatarLocation;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/queryInfo")
    public String queryInfo(Model model, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        UserInfo userInfo = userInfoService.queryuserinfo(user);
        if (userInfo!=null){
            model.addAttribute("userInfo",userInfo);
        }
        return "userInfo";
    }
    @PostMapping("/updateInfo")
    @ResponseBody
    public ResultDTO updateInfo(@RequestBody UserInfo userInfoModel){
        userInfoModel.setGmtModified(TimeUtils.formatNow("yyyy-MM-dd HH:mm:ss"));
        boolean b = userInfoService.updateUser(userInfoModel);
        if (b)
        {
            return ResultDTO.okOf(userInfoModel);
        }
        return ResultDTO.errorOf(CustomizeErrorCode.UPDATE_ERROR);
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public ResultDTO insert(HttpServletRequest request
            ,@RequestParam("file") MultipartFile file) throws Exception{
             if(file!=null){
            String fileName;
            try {
                String oldFileName = file.getOriginalFilename();
                        //上传文件，随机名称，";"分号隔开
                fileName =  UUID.randomUUID() + "." + StringUtils.substringAfterLast(oldFileName, ".");
                String destPath = avatarLocation;
                //判断该路径是否存在,如果不存在则创建该路径
                if (!haveCreatePath) {
                    File destFile = new File(destPath);
                    destFile.mkdirs();
                    haveCreatePath = true;
                }
                String fileUrl="/avatar/"+fileName;
                User user = (User) request.getSession().getAttribute("user");
                UserInfo userInfo = userInfoService.queryuserinfo(user);
                userInfo.setAvatarUrl(fileUrl);
                userInfoService.updateById(userInfo);
                file.transferTo(new File(destPath+fileName));
                ResultDTO.okOf(fileUrl);
                return  ResultDTO.okOf(fileUrl);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultDTO.errorOf(CustomizeErrorCode.FILEUPLOAD_REEOR);
            }
        }
        return ResultDTO.errorOf(CustomizeErrorCode.FILEUPLOAD_REEOR);
    }

}
