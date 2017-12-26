package net.aish.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.aish.dao.NotificationDao;
import net.aish.model.ErrorClazz;
import net.aish.model.Notification;

@Controller
public class NotificationController {

	@Autowired 
	private NotificationDao notificationDao;
	
	@RequestMapping(value="/getnotification/{viewed}")
	public ResponseEntity<?> getNotification(@PathVariable int viewed,HttpSession session){
		String username = (String)session.getAttribute("username");
		if(username==null){//user id not logged in 
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		List<Notification> notifications=notificationDao.getNotification(username, viewed);
		return new ResponseEntity<List<Notification>>(notifications,HttpStatus.OK);
	}
}