package com.github.binarywang.demo.wechat.Dao.WeiXinUserInfo;

import org.apache.ibatis.annotations.Mapper;

import com.github.binarywang.demo.wechat.model.WeiXinUserInfo.WeiXinUserInfo;


@Mapper
public interface WeiXinUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeiXinUserInfo record);

    int insertSelective(WeiXinUserInfo record);

    WeiXinUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeiXinUserInfo record);

    int updateByPrimaryKey(WeiXinUserInfo record);
}