package net.aish.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.aish.dao.JobDao;
import net.aish.dao.JobNotificationDao;
import net.aish.dao.NotificationDao;
import net.aish.dao.UserDao;
import net.aish.model.CommentNotification;
import net.aish.model.ErrorClazz;
import net.aish.model.LikeNotification;
import net.aish.model.Notification;

@Controller
public class NotificationController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JobDao jobDao;
	
	@Autowired 
	private NotificationDao notificationDao;
	
	@Autowired
	private JobNotificationDao jobNotificationDao;
	
	
	@RequestMapping(value="/getnotification/{viewedblog}",method=RequestMethod.GET)
	public ResponseEntity<?> getNotification(@PathVariable int viewedblog,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}		
		List<Notification> notifications=notificationDao.getNotification(username, viewedblog);
		return new ResponseEntity<List<Notification>>(notifications,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/updatenotification/{notificationId}",method=RequestMethod.PUT)
	public ResponseEntity<?> updateNotification(@PathVariable int notificationId,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);//401
		}
		
		Notification notification=notificationDao.updateNotification(notificationId);
		return new ResponseEntity<Notification>(notification,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getnotificationlike/{viewedlike}",method=RequestMethod.GET)
	public ResponseEntity<?> getNotificationLike(@PathVariable int viewedlike,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		
		List<LikeNotification> likenotifications=notificationDao.getNotificationLike(username,viewedlike);
		return new ResponseEntity<List<LikeNotification>>(likenotifications,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/updatenotificationlike/{notificationId}",method=RequestMethod.PUT)
	public ResponseEntity<?> updateNotificationlike(@PathVariable int notificationId,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);//401
		}
		
		LikeNotification notification=notificationDao.updateNotificationLike(notificationId);
		return new ResponseEntity<LikeNotification>(notification,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getnotificationcomment/{viewedcomment}",method=RequestMethod.GET)
	public ResponseEntity<?> getNotificationComment(@PathVariable int viewedcomment,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		
		List<CommentNotification> commentnotify=notificationDao.getNotificationComment(username,viewedcomment);
		return new ResponseEntity<List<CommentNotification>>(commentnotify,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/updatenotificationcomment/{notificationId}",method=RequestMethod.PUT)
	public ResponseEntity<?> updateNotificationComment(@PathVariable int notificationId,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);//401
		}
		
		CommentNotification commentnotify=notificationDao.updateNotificationComment(notificationId);
		return new ResponseEntity<CommentNotification>(commentnotify,HttpStatus.OK);
		
	}	
	
	
}
