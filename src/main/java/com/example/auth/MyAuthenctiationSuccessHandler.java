package com.example.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dto.LoginUser;
import com.example.dto.ResultDTO;
import com.example.mapper.UserInfoMapper;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MyAuthenctiationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        response.setContentType("application/json;charset=UTF-8");

        LoginUser userDetails = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        //details里面可能存放了当前登录用户的详细信息，也可以通过cast后拿到
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("ACCOUNT_ID",user.getAccountId());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        if (userInfo != null){
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("userInfo",userInfo);
            Long unreadCount = notificationService.unreadCount(user.getId());
            request.getSession().setAttribute("unreadCount",unreadCount);

        }


        response.getWriter().write(objectMapper.writeValueAsString(ResultDTO.okOf("登陆成功!")));
    }
}