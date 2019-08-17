package com.example.interceptor;

import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.UserExample;
import com.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by codedrinker on 2019/5/16.
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

//    @Value("${github.redirect.uri}")
//    private String redirectUri;
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
                    UserExample UserExample = new UserExample();
                    UserExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(UserExample);
                    //User user = userMapper.findByToken(token);
                    if (users.size()>0){
                        request.getSession().setAttribute("user",users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId().longValue());
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
