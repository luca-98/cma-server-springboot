package com.github.cmateam.cmaserver.configuration;

import com.github.cmateam.cmaserver.service.AuthChannelInterceptorImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthChannelInterceptorImpl authChannelInterceptorImpl;

    @Autowired
    public WebSocketConfig(AuthChannelInterceptorImpl authChannelInterceptorImpl) {
        this.authChannelInterceptorImpl = authChannelInterceptorImpl;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }

    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry registry) {
    //     registry.setApplicationDestinationPrefixes("/app");
    //     registry.enableSimpleBroker("/topic");
    // }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptorImpl);
    }
}