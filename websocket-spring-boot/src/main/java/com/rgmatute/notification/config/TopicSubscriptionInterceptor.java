package com.rgmatute.notification.config;

import org.slf4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class TopicSubscriptionInterceptor implements ChannelInterceptor {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(TopicSubscriptionInterceptor.class);

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel chanenel) {
		return message;
	}

	@Override
	public void postSend(Message<?> message, MessageChannel chanel, boolean sent) {
	}

	@Override
	public boolean preReceive(MessageChannel channel) {
		return true;
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		logger.info(headerAccessor.getDestination());
		/*
		 * if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand()) &&
		 * headerAccessor.getHeader("simpUser") !=null &&
		 * headerAccessor.getHeader("simpUser") instanceof
		 * UsernamePasswordAuthenticationToken) { UsernamePasswordAuthenticationToken
		 * userToken = (UsernamePasswordAuthenticationToken)
		 * headerAccessor.getHeader("simpUser");
		 * if(!validateSubscription((User)userToken.getPrincipal(),
		 * headerAccessor.getDestination())) { throw new
		 * IllegalArgumentException("No permission for this topic"); } }
		 */
		return message;
	}
}