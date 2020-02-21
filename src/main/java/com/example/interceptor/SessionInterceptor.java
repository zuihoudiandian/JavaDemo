package com.example.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.UserInfoMapper;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by codedrinker on 2019/5/16.
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0)
        {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token"))
                {
                    String token = cookie.getValue();
                    QueryWrapper<User> wrapper = new QueryWrapper<>();
                    wrapper.eq("token",token);
                    User user = userMapper.selectOne(wrapper);
                    QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
                    userInfoQueryWrapper.eq("ACCOUNT_ID",user.getAccountId());
                    UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
                    if (userInfo != null){
                        request.getSession().setAttribute("user",user);
                        request.getSession().setAttribute("userInfo",userInfo);
                        Long unreadCount = notificationService.unreadCount(user.getId());
                        request.getSession().setAttribute("unreadCount",unreadCount);

                    }
                    break;
                }
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
