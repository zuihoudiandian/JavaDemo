package com.example.service;

import com.example.dto.LoginUser;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/2/9.
 * PS: Not easy to write code, please indicate.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //配置用户权限
        User sysuser = userService.getuser(username);
        if (sysuser==null){
            return null;
        }
        LoginUser loginUser = new LoginUser();
        List<String> role = new ArrayList<>();
        if (sysuser.getAccountId().equals("admin")){
            role.add("admin");
        }
        loginUser.setPermissions(role);
        loginUser.setUsername(sysuser.getAccountId());
        loginUser.setPassword(sysuser.getPassword());
        loginUser.setUser(sysuser);
        return loginUser;
    }
}
