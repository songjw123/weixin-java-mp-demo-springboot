package com.github.binarywang.demo.wechat.websocket;

/**
 *
 */

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;
import static io.undertow.Handlers.websocket;

/**
 * @author Stuart Douglas
 */
//@UndertowExample("Web Sockets")
public class WebSocketServer1 {

    public static void main(final String[] args) {

    }
    public static void start(){
        Undertow server = Undertow.builder()
                .addHttpListener(8011, "10.26.255.62")
//                .addHttpListener(8011, "118.178.126.83")
                .setHandler(path()
                        .addPrefixPath("/websocket", websocket(new WebSocketConnectionCallback() {


                            @Override
                            public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
                                channel.getReceiveSetter().set(new AbstractReceiveListener() {

                                    @Override
                                    protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                        WebSockets.sendText(message.getData(), channel, null);
                                    }
                                });
                                channel.resumeReceives();
                            }
                        }))
                        .addPrefixPath("/", resource(new ClassPathResourceManager(WebSocketServer1.class.getClassLoader(), WebSocketServer1.class.getPackage())).addWelcomeFiles("index.html")))
                .build();
        server.start();
    }

}