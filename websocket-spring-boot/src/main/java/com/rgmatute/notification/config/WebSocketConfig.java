package com.rgmatute.notification.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app", "");
		registry.enableSimpleBroker("/topic", "");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket", "")
		.setAllowedOrigins("*")
		.addInterceptors(httpSessionHandshakeInterceptor());
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		//WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
		registration.setInterceptors(new TopicSubscriptionInterceptor());
	}

	@Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
		return new HandshakeInterceptor() {
			
			@Override
			public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
					Map<String, Object> attributes) throws Exception {
				// TODO Auto-generated method stub
				// aqui se valida la session segun el usuario --> pilas ojito [ true -> cuando se puede conectar, false -> cuando no puede conectarse]
				System.out.println("conectado....");
				return true;
			}
			
			@Override
			public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
					Exception exception) {
				// TODO Auto-generated method stub
				System.out.println("message....");
				
			}
		};
    }
}