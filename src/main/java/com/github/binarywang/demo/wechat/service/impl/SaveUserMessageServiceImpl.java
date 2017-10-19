package com.github.binarywang.demo.wechat.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.Dao.WeiXinUserMessage.WeiXinUserMessageMapper;
import com.github.binarywang.demo.wechat.model.WeiXinUserInfo.WeiXinUserInfo;
import com.github.binarywang.demo.wechat.model.WeiXinUserMessage.WeiXinUserMessage;
import com.github.binarywang.demo.wechat.service.SaveUserMessageService;

@Service
public class SaveUserMessageServiceImpl implements SaveUserMessageService {

	@Autowired
	private WeiXinUserMessageMapper userMessage;
	/**
	 * 存储消息
	 */
	@Override
	public void SaveUserMessage(String mess,String openId) {
		WeiXinUserMessage mes = new WeiXinUserMessage();
		mes.setSaveMessage(mess);
		mes.setCreateTime(new Date());
		mes.setOpenId(openId);
		userMessage.insertSelective(mes);

	}

}
