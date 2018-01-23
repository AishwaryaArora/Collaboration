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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.aish.dao.BlogImageDao;
import net.aish.dao.BlogPostDao;
import net.aish.dao.BlogPostLikesDao;
import net.aish.dao.UserDao;
import net.aish.model.BlogComment;
import net.aish.model.BlogImage;
import net.aish.model.BlogPost;
import net.aish.model.BlogPostLikes;
import net.aish.model.ErrorClazz;
import net.aish.model.User;

@Controller
public class BlogPostController {
	
	@Autowired
	private BlogImageDao blogImageDao;

	@Autowired
	private BlogPostDao blogPostDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogPostLikesDao blogPostLikesDao;
	
	public BlogPostController(){
		System.out.println("BlogPostController is Instantiated");

	}
	
	@RequestMapping(value ="/saveBlog",method=RequestMethod.POST)
	public ResponseEntity<?> saveBlogPost(@RequestBody BlogPost blogpost, HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		//String username="ser";
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
	
	@RequestMapping(value="/getblogs/{approved}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogs(HttpSession session,@PathVariable int approved)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		//String username="ser";	
		User user=userDao.getUserByUsername(username);
		if(approved==0)    //list of blogs waiting for approval
		{
			if(!user.getRole().equals("ADMIN"))
			{
				ErrorClazz error=new ErrorClazz(7,"Access Denied");
				return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
			}
		
		}
		List<BlogPost> blogPosts=blogPostDao.getBlogs(approved);
		
		return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getblog/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBlogPost(@PathVariable int id, HttpSession session) 
	{
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		//String username="ser";
		
		BlogPost blogpost = blogPostDao.getBlogById(id);
		return new ResponseEntity<BlogPost>(blogpost, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblogspostedby/{approved}", method = RequestMethod.GET)
	public ResponseEntity<?> getBlogPostByPostedBy(@PathVariable int approved, HttpSession session) 
	{
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		//String username="ser";
		User user=userDao.getUserByUsername(username);
		if(approved==0)    //list of blogs waiting for approval
		{
			if(!user.getRole().equals("ADMIN"))
			{
				ErrorClazz error=new ErrorClazz(7,"Access Denied");
				return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
			}
		
		}
		List<BlogPost> blogpost = blogPostDao.getBlogByPostedBy(user,approved);
		return new ResponseEntity<List<BlogPost>>(blogpost, HttpStatus.OK);
	}
	
	@RequestMapping(value="/uploadblogimage/{id}",method=RequestMethod.POST)
	public ResponseEntity<?> uploadBlogImage(@PathVariable int id , @RequestParam CommonsMultipartFile image, HttpSession session)
	{
		 String username=(String)session.getAttribute("username");
		   if(username==null)
			{
			   ErrorClazz error=new ErrorClazz(5,"UnAuthorized Access");
				return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //401
			}
		  BlogImage blogImage=new BlogImage();
		  blogImage.setImage(image.getBytes());
		 blogImage.setId(id);
		  blogImageDao.saveorUpdateBlogImage(blogImage);
		  return new ResponseEntity<BlogImage>(blogImage,HttpStatus.OK);
	}
	
	//login: smith
	//http://localhost:9090/collabmiddleware/getimage/{{username}}
	@RequestMapping(value="/getblogimage/{id}",method=RequestMethod.GET)
	public @ResponseBody byte[] getBlogImage(@PathVariable int id,HttpSession session)
	{
		String loginId=(String) session.getAttribute("username");
		if(loginId==null)
		{
			return null;
		}
		BlogImage blogImage=blogImageDao.getBlogImage(id);
		if(blogImage==null)
			return null;
		else
			return blogImage.getImage();
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
	
	@RequestMapping(value="/userLikes/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> userLikes(@PathVariable int id,HttpSession session)
	{
		String username =(String) session.getAttribute("username");
		if (username == null) {
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);  //401
		}
		User user=userDao.getUserByUsername(username);
		BlogPost blogPost=blogPostDao.getBlogById(id);
		//blogPostLikes=null/1 object.
		//if user has not yet liked the blogPost,blogPostLikes=null
		//if user has liked the blogpost already ,blogPostLikes=1 object.
	   BlogPostLikes blogPostLikes=blogPostLikesDao.userLikedPost(blogPost, user);
		return new ResponseEntity<BlogPostLikes>(blogPostLikes,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/updatelikes",method=RequestMethod.PUT)
	public ResponseEntity<?> updateLikes(@RequestBody BlogPost blogPost,HttpSession session)
	{
		String username =(String) session.getAttribute("username");
		if (username == null) {
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);  //401
		}
	   User user=userDao.getUserByUsername(username);
	   BlogPost updatedBlogPost=blogPostLikesDao.updateLikes(blogPost, user);
	   return new ResponseEntity<BlogPost>(updatedBlogPost,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/addcomment",method=RequestMethod.POST)
	//http://localhost:9090/collabmiddleware/addcomment?commentTxt='Thanks'&id=2197
	public ResponseEntity<?> addblogComment(@RequestParam String commentTxt, @RequestParam  int id, HttpSession session)
	{
		String username =(String) session.getAttribute("username");
		if (username == null) {
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);  //401
		}
		User commentedBy=userDao.getUserByUsername(username);
		BlogPost blogPost=blogPostDao.getBlogById(id);
		//Construct blogComment object
		BlogComment blogComment=new BlogComment();	
		blogComment.setBlogPost(blogPost);
		blogComment.setCommentTxt(commentTxt);
		blogComment.setCommentedBy(commentedBy);
		blogComment.setCommentedOn(new Date());
		try{
			
			blogPostDao.addComment(blogComment);
		}
		catch (Exception e) {
			ErrorClazz error = new ErrorClazz(7, "Unable to post comment" + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR); 
			
		}
		//List<BlogComment> blogComments=blogPost.getBlogComments();
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
}
