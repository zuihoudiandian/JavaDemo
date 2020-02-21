package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserInfoMapper;
import com.example.model.User;
import com.example.model.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/1/30.
 * PS: Not easy to write code, please indicate.
 */
@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfo> {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo queryuserinfo(User user) {
        QueryWrapper<UserInfo> Wrapper = new QueryWrapper<>();
        Wrapper.eq("ACCOUNT_ID",user.getAccountId());
        return userInfoMapper.selectOne(Wrapper);
    }

    public boolean updateUser(UserInfo userInfoModel) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoModel,userInfo);
        return updateById(userInfo);
    }



}
