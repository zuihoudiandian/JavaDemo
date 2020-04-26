package com.example.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/2/14.
 * PS: Not easy to write code, please indicate.
 */
@Component
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        System.out.println("session拦截！");
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",404);
        map.put("message","您已经在另外一个浏览器登陆，如不是正常登陆建议修改密码");
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(objectMapper.writeValueAsString(map));
        new ModelAndView("error",map);

    }
}
