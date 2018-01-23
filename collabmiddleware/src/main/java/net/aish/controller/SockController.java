package net.aish.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import net.aish.dao.UserDao;
import net.aish.model.Chat;

@Controller
public class SockController {

	@Autowired
	UserDao userDao;
	//to send data to client
	
	private final SimpMessagingTemplate messagingTemplate;
	//list of username's who has joined the chat room
	private List<String> usernames=new ArrayList<String>();
	

	
	
	@Autowired
	public SockController(SimpMessagingTemplate messagingTemplate)
	{
		this.messagingTemplate = messagingTemplate;
	}
	

	//$stompClient.subscribeMapping("/app/join/adam",
	@SubscribeMapping("/join/{username}")
	public List<String> join(@DestinationVariable String username)
	{
		if(!usernames.contains(username))
			usernames.add(username);
		messagingTemplate.convertAndSend("/topic/join",username); //all the other users who have already joined the chatroom
		return usernames;  //newly joined user
	}
	
	@MessageMapping(value="/chat")
	public void chatRecieved(Chat chat) //to ,from,message
	{
		//group chat
		if("all".equals(chat.getTo()))
		{
			System.out.println("IN CHAT REVEIVED " + chat.getMessage() + " " + chat.getFrom() + " to " + chat.getTo());
			messagingTemplate.convertAndSend("/queue/chats",chat);
			
		}		
		//from :user1 ,  to: user2
		else
		{
			//queue/chat/ser
			//queue/chat/aish
			System.out.println("CHAT TO " + chat.getTo() + " From " + chat.getFrom() + " Message " + chat.getMessage());
			messagingTemplate.convertAndSend("/queue/chats/" + chat.getFrom(),chat);
			messagingTemplate.convertAndSend("/queue/chats/" + chat.getTo(),chat);
		}
		
	}
}
