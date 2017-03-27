package com.github.binarywang.demo.wechat.controller;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Binary Wang
 */
@RestController
@RequestMapping("/wechat/test")
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMpService wxService;

    @Autowired
    private WxMpMessageRouter router;

    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(
    ) throws WxErrorException {

        WxMpQrcodeService qrcodeService = this.wxService.getQrcodeService();
        WxMpQrCodeTicket wxMpQrCodeTicket = qrcodeService.qrCodeCreateLastTicket("123");
        String url = qrcodeService.qrCodePictureUrl(wxMpQrCodeTicket.getTicket(), true);

        return url;
    }

    public void getWelcomeUrl() {
        //返回(静默授权|网页授权)地址
        wxService.oauth2buildAuthorizationUrl("http://www.test.com/sss/", "snsapi_base|snsapi_userinfo", "u=123546");
    }

    //如果用户没有授权,则code为空,state在getWelcomeUrl()中指定
    public void welcomePage(String code,String state) throws WxErrorException {
        //用户确认授权后,根据code获取access_token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);

        //根据access_token获取用户基本信息(只有在用户授权下才能使用,静默授权无法使用)
        WxMpUser user = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.router.route(message);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

}
