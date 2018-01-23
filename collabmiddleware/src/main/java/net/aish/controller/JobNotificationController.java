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
import net.aish.dao.UserDao;
import net.aish.model.ErrorClazz;
import net.aish.model.Job;
import net.aish.model.User;

@Controller
public class JobNotificationController {

	@Autowired
	private JobNotificationDao jobNotificationDao;
	
	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/addnotifieduser/{jobId}",method=RequestMethod.POST)
	public ResponseEntity<?> addNotifiedUser(@PathVariable int jobId,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUserByUsername(username);
		Job job=jobDao.getJob(jobId);
		jobNotificationDao.addNotifiedUser(job, user);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getjobnotification",method=RequestMethod.GET)
	public ResponseEntity<?> getJobNotifications(HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUserByUsername(username);
		List<Job> jobNotifications=jobNotificationDao.JobNotification(user);
		return new ResponseEntity<List<Job>>(jobNotifications,HttpStatus.OK);
		
	}
}
