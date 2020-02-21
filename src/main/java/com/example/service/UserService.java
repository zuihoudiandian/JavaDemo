package com.example.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.userDTO;
import com.example.mapper.UserInfoMapper;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by codedrinker on 2019/5/23.
 */
@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public void createOrUpdate(User user, UserInfo userInfo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
       wrapper.eq("token",user.getToken());
        User users = userMapper.selectOne(wrapper);
        if (users == null) {
            // 插入
            user.setGmtCreate(TimeUtils.formatNow("yyyy-MM-dd HH:mm:ss"));
            user.setGmtModified(user.getGmtCreate());
            userInfoMapper.insert(userInfo);
            userMapper.insert(user);
        } else {
            //更新
            user.setGmtModified(TimeUtils.formatNow("yyyy-MM-dd HH:mm:ss"));
            UpdateWrapper<User> userUpdate = new UpdateWrapper<>();
            userMapper.update(user, userUpdate);
            userInfo.setGmtModified(TimeUtils.formatNow("yyyy-MM-dd HH:mm:ss"));
            UpdateWrapper<UserInfo> userInfoUpdate = new UpdateWrapper<>();
            int update= userInfoMapper.update(userInfo,userInfoUpdate);
        }
    }

    public Object checkUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("ACCOUNT_ID",username);
        User user = userMapper.selectOne(wrapper);
        JSONObject jo = new JSONObject();
        if (user!=null){
            jo.put("valid",false);
            return jo;
        }
        jo.put("valid",true);
        return jo;
    }

    public User getuser(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("ACCOUNT_ID",username);
        return userMapper.selectOne(wrapper);
    }

    public IPage<User> getUsersList(userDTO userDTO) {
        Page<User> page = new Page<>(userDTO.getPageNumber(), userDTO.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        IPage<User> iPage = userMapper.selectPage(page, wrapper);
        return iPage;
    }

}
