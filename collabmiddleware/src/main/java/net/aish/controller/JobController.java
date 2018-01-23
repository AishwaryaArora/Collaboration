package net.aish.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.aish.dao.JobDao;
import net.aish.dao.UserAppliedJobDao;
import net.aish.dao.UserDao;
import net.aish.model.ErrorClazz;
import net.aish.model.Job;
import net.aish.model.User;
import net.aish.model.UserAppliedJob;

@Controller
public class JobController {

	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserAppliedJobDao userAppliedJobDao;
	
	
	public JobController(){
		System.out.println("JobController is Instantiated");
	}
	
	@RequestMapping(value="/savejob",method=RequestMethod.POST)
	public ResponseEntity<?> saveJob(@RequestBody Job job,HttpSession session)
	{
		String username=(String)session.getAttribute("username");
		
		if(username==null)//login method not executed ,session.setAttribute("username")is not yet executed.
		{
			ErrorClazz error=new ErrorClazz(5,"UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		} //user is authenticated ,so check for role Authorization.
		User user=userDao.getUserByUsername(username);
		if(!user.getRole().equals("ADMIN"))
		{
			ErrorClazz error=new ErrorClazz(6,"Access Denied");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		job.setApplyJob(0);
		job.setPostedOn(new Date());
		
		try{
			jobDao.saveJob(job);			
		}
		catch (Exception e) {
			ErrorClazz error=new ErrorClazz(7,"Unable to insert job details" + e.getMessage());
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getjob/{id}",method=RequestMethod.GET)
   public ResponseEntity<?> getJob(@PathVariable int id,HttpSession session)
   {
	   String username=(String)session.getAttribute("username");
	   if(username==null)
		{
		   ErrorClazz error=new ErrorClazz(5,"UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
	   Job job=jobDao.getJob(id);
	   return new ResponseEntity<Job>(job,HttpStatus.OK);
   }
	
	@RequestMapping(value="/alljobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session)
	{
		String username=(String)session.getAttribute("username");
		
		if(username==null)//login method not executed ,session.setAttribute("username")is not yet executed.
		{
			ErrorClazz error=new ErrorClazz(5,"UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		} 
       
		List<Job> jobs=jobDao.getAllJobs();
		if(jobs.isEmpty())
		{
			ErrorClazz error=new ErrorClazz(8,"No records available.");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/userAppliedJob/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> userAppliedJob(@PathVariable int id,HttpSession session)
	{
		String username =(String) session.getAttribute("username");
		if (username == null) {
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);  //401
		}
		User user=userDao.getUserByUsername(username);
		Job job=jobDao.getJob(id);
		//applyJob=null/1 object.
		//if user has not yet applied the job,UserApplyJob=null
		//if user has applied for job already ,applyjob=1 object.
	   UserAppliedJob userAppliedJob=userAppliedJobDao.userAppliedJob(job, user);
		return new ResponseEntity<UserAppliedJob>(userAppliedJob,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/updateJob",method=RequestMethod.PUT)
	public ResponseEntity<?> updateJob(@RequestBody Job job,HttpSession session)
	{
		String username =(String) session.getAttribute("username");
		if (username == null) {
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);  //401
		}
	   User user=userDao.getUserByUsername(username);
	   Job updateApplyJob=userAppliedJobDao.updateJob(job, user);
	   return new ResponseEntity<Job>(updateApplyJob,HttpStatus.OK);
		
	}}
