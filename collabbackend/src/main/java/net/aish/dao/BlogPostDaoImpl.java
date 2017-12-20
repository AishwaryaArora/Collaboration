package net.aish.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.BlogPost;

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

}}
