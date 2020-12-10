package com.ysmork.blog.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @program: blog
 * @description: webSocket配置
 * @author: YangShun
 * @create: 2020-12-10 15:35
 **/
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    //注册STOMP协议的节点(endpoint),并映射指定的url
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个 /notification 端点，前端通过这个端点进行连接
        registry.addEndpoint("/socketBlog")
                //解决跨域问题
                .setAllowedOrigins("*").withSockJS();
    }

    @Override
    //配置消息代理(Message Broker)
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //定义了一个客户端订阅地址的前缀信息代理将会处理前缀为“/topic”和“/que”的消息，也就是客户端接收服务端发送消息的前缀信息
        //启用简单代理
        registry.enableSimpleBroker("/topic");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），处理指定用户发送消息
//        registry.setUserDestinationPrefix("/user");

    }
}
