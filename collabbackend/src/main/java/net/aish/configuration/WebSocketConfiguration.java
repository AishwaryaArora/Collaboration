package net.aish.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //enable broker based stomp messaging-autodetecting annotaion @MessageMapping
@ComponentScan(basePackages="net.aish")
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//enable simple broker destination prefixes.-by spring controller to send messages to client.
				//to send data from server to client
				//queue is for chat message. ie to send chat messages
			     //topic is for newly joined user -- to notify the newly joined username
				//communication direction from server to client
				//server users destination /queue,/topic to send messages to client 
				//client will recieve the message by subscribing $scope.subscribe("/topic/join",..)
				registry.enableSimpleBroker("/queue/" ,  "/topic/"); //server to broker to client or to send data from server to client
				
				
				//client to server- destination prefix as app.
				//in client side ..send("/app/join",...)
				registry.setApplicationDestinationPrefixes("/app"); //from client to server
				
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//JS Stomp.over("../chatmodule")
				registry.addEndpoint("/chatmodule").withSockJS(); //to connect with websocket
				

	}

}
