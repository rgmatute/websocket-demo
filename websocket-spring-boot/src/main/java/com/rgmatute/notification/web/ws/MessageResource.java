package com.rgmatute.notification.web.ws;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.rgmatute.notification.web.ws.dto.MessageDTO;
import com.rgmatute.notification.web.ws.dto.MessageResponse;
import com.rgmatute.notification.web.ws.util.Utils;

@Controller
public class MessageResource {
	
	@MessageMapping("/notification/{channelId}")
	@SendTo("/topic/notification/{channelId}")
	public Object messenger(MessageDTO request, @DestinationVariable String channelId) {
		System.out.println("ok, " + request.toString());
		
		MessageResponse response = new MessageResponse();
		response.setMessage(request.getMessage());
		response.setUsername(request.getUsername());
		response.setDate(Utils.currentDate("dd/MM/yyyy HH:mm:ss.SSS"));
		
		return response;
	}
	
	@MessageMapping
	@SendTo
	public Object messenger(Object request) {
		return request;
	}
	
}
