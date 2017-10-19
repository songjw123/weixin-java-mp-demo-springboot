package com.github.binarywang.demo.wechat.Dao.WeiXinUserMessage;

import org.apache.ibatis.annotations.Mapper;

import com.github.binarywang.demo.wechat.model.WeiXinUserMessage.WeiXinUserMessage;

@Mapper
public interface WeiXinUserMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeiXinUserMessage record);

    int insertSelective(WeiXinUserMessage record);

    WeiXinUserMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeiXinUserMessage record);

    int updateByPrimaryKey(WeiXinUserMessage record);
}