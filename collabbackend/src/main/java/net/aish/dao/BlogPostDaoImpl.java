package net.aish.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.BlogPost;
import net.aish.model.Notification;

@Repository("blogPostDao")
@Transactional
public class BlogPostDaoImpl implements BlogPostDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveBlogPost(BlogPost blog) {
		try{Session session= sessionFactory.getCurrentSession();
		session.save(blog);
		}
		catch (Exception e ){
			//TODO Auto-generated catch block
			e.printStackTrace();

	}

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
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class,id);
			return blogPost;
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

		
	}}
