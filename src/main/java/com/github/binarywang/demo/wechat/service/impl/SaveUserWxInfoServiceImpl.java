package com.github.binarywang.demo.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.Dao.WeiXinUserInfo.WeiXinUserInfoMapper;
import com.github.binarywang.demo.wechat.model.WeiXinUserInfo.WeiXinUserInfo;
import com.github.binarywang.demo.wechat.service.SaveUserWxInfoService;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Service
public class SaveUserWxInfoServiceImpl implements SaveUserWxInfoService {
	@Autowired
	private WeiXinUserInfoMapper userInfoMapper;
    @Override
    public void saveUserWxInfo(WxMpUser userWxInfo) {
    	WeiXinUserInfo user = new WeiXinUserInfo();
    	user.setCity(userWxInfo.getCity());
    	user.setCountry(userWxInfo.getCountry());
    	user.setHeadImgUrl(userWxInfo.getHeadImgUrl());
    	user.setLanguage(userWxInfo.getLanguage());
    	user.setNickName(userWxInfo.getNickname());
    	user.setOpenId(userWxInfo.getOpenId());
    	user.setProvince(userWxInfo.getProvince());
    	user.setSex(userWxInfo.getSex());
    	userInfoMapper.insertSelective(user);
    }
}
