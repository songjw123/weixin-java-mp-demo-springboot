package com.github.binarywang.demo.wechat.handler;

import com.github.binarywang.demo.wechat.builder.TextBuilder;
import com.github.binarywang.demo.wechat.service.SaveUserMessageService;
import com.github.binarywang.demo.wechat.utils.JsonUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

	@Autowired
	private SaveUserMessageService userMessage;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (wxMessage.getMsgType().equals(WxConsts.CUSTOM_MSG_TEXT)) {
            //TODO 可以选择将消息保存到本地
        	this.logger.info(wxMessage.toString());
        	String openId = wxMessage.getFromUser();
        	userMessage.SaveUserMessage(wxMessage.getContent(),openId);
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                    && weixinService.getKefuService().kfOnlineList()
                    .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //TODO 组装回复消息
        String content = "测试！测试！" ;

        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
