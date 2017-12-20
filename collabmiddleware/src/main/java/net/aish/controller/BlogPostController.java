package net.aish.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.aish.dao.BlogPostDao;
import net.aish.dao.UserDao;
import net.aish.model.BlogPost;
import net.aish.model.ErrorClazz;
import net.aish.model.User;

@Controller
public class BlogPostController {

	@Autowired
	private BlogPostDao blogPostDao;
	
	@Autowired
	private UserDao userDao;
	
	public BlogPostController(){
		System.out.println("BlogPostController is Instantiated");

	}
	
	@RequestMapping(value ="/saveBlog",method=RequestMethod.POST)
	public ResponseEntity<?> saveBlogPost(@RequestBody BlogPost blogpost, HttpSession session)//, HttpSession session
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		//String username="yuvi";
		User user = userDao.getUserByUsername(username);
		blogpost.setPostedOn(new Date());
		blogpost.setPostedBy(user); // FK column postedby_username['adam']
		try
		{
			blogPostDao.saveBlogPost(blogpost);
		} 
		catch (Exception e) 
		{
			ErrorClazz error = new ErrorClazz(8, "Unable to insert Blog Details " + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BlogPost>(blogpost, HttpStatus.OK);

	}
	
	//@RequestBody->TO CONVERT FROM json to java
		@RequestMapping(value="/updateapprovalstatus",method=RequestMethod.PUT)
		public ResponseEntity<?>updateApprovalStatus(@RequestBody BlogPost blogPost,@RequestParam(required=false) String rejectionReason,HttpSession session)
		{
			String username =(String) session.getAttribute("username");
			if (username == null) {
				
				ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);  //401
			}
			//String username ="ser";
			try
			{
				//if admin selects approve,blogPost.approved=1
				//if admin selects reject,blogPost.approved=0
				blogPostDao.updateBlogPost(blogPost,rejectionReason);
			}
			catch (Exception e) {
				ErrorClazz error=new ErrorClazz(7,"Unable to update blogpost approval status"+ e.getMessage());
				return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		

}
