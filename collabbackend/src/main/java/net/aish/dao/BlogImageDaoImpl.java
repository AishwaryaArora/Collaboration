package net.aish.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.BlogImage;

@Repository
@Transactional
public class BlogImageDaoImpl implements BlogImageDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveorUpdateBlogImage(BlogImage blogImage) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(blogImage);  //insert or update.

	}

	@Override
	public BlogImage getBlogImage(int id) {
		Session session=sessionFactory.getCurrentSession();
		BlogImage blogImage=(BlogImage)session.get(BlogImage.class,id);		
		return blogImage;
	}

}
