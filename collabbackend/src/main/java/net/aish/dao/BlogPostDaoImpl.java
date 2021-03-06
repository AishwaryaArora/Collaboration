package net.aish.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.BlogComment;
import net.aish.model.BlogPost;
import net.aish.model.CommentNotification;
import net.aish.model.Notification;
import net.aish.model.User;

@Repository("blogPostDao")
@Transactional
public class BlogPostDaoImpl implements BlogPostDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveBlogPost(BlogPost blog) {
		Session session= sessionFactory.getCurrentSession();
		session.save(blog);

	}


	@Override
	public List<BlogPost> getBlogs(int approved) {
	Session session=sessionFactory.getCurrentSession();
	Query query=session.createQuery("from BlogPost where approved="+approved);	
		return query.list();
	}


	@Override
	public BlogPost getBlogById(int id) {
	Session session=sessionFactory.getCurrentSession();
	BlogPost blog=(BlogPost)session.get(BlogPost.class,id);
		return blog;
	}
	
	@Override
	public List<BlogPost> getBlogByPostedBy(User postedBy,int approved) {
		Session session=sessionFactory.getCurrentSession();
	 Query query=session.createQuery("from BlogPost where postedBy=? and approved=?");
	 System.out.println("BlogPost postedBy"+postedBy);
	 System.out.println("Username"+approved);
	 query.setEntity(0,postedBy);
	 query.setInteger(1,approved);
		return query.list();
	}


	@Override
	public void updateBlogPost(BlogPost blogPost, String rejectionReason) {
		Session session = sessionFactory.getCurrentSession();
		Notification notification = new Notification();
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setUsername(blogPost.getPostedBy().getUsername());// author who posted the  blog
		if (blogPost.isApproved())// true admin approves blogpost(Approve radio	 button is selected)
		{
			session.update(blogPost);// update blogpost set approved=1 where
										// id=?
			notification.setApprovalStatus("Approved");
			session.save(notification);//insert  into notification values
		} 
		else// false admin rejects blogpost(Reject radio button is selected by admin)
		{
			if(rejectionReason==null)
			{
				notification.setRejectionReason("Not Mentioned By Admin");
			}
			else
			notification.setRejectionReason(rejectionReason);							
			notification.setApprovalStatus("Rejected");
			session.save(notification);// insert details in notification table.values[..]
			session.delete(blogPost);//delete from blogpost where id=?
			
			
		}

	}


	@Override
	public void addComment(BlogComment blogComment) {
     Session session=sessionFactory.getCurrentSession();
     //insert into comment-notification
     CommentNotification commentnotify=new CommentNotification();
     commentnotify.setBlogTitle(blogComment.getBlogPost().getBlogTitle());
     commentnotify.setCommentedBy(blogComment.getCommentedBy());
     commentnotify.setUsername(blogComment.getBlogPost().getPostedBy().getUsername());
     session.save(commentnotify);  
     session.save(blogComment); //insert into blogComment.
		
	}
	
}