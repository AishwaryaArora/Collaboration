package net.aish.dao;



import javax.transaction.Transactional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;

import net.aish.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void registerUser(User user) {
		Session session=sessionFactory.getCurrentSession();
        session.save(user);

	}

	@Override
	public boolean isEmailValid(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where email='"+email+"'");
		User user=(User)query.uniqueResult();
		if(user==null)//email is valid,unique
			return true;
		else
			return false;
	}

	@Override
	public boolean isUsernameValid(String username) {
		Session session=sessionFactory.getCurrentSession();
		User user=(User)session.get(User.class, username);
		if(user==null)
			return true;
		else
			return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public User login(User user) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where username=? and password=?");
		query.setString(0,user.getUsername());
		query.setString(1, user.getPassword());
		User validUser=(User)query.uniqueResult();
		return validUser;//either null or 1 user object
	
	}

	@Override
	public void updateUser(User user) {
		Session session=sessionFactory.getCurrentSession();
		session.update(user);//update User set online=true where username=?
	
		
	}

	@Override
	public User getUserByUsername(String username) {
		Session session=sessionFactory.getCurrentSession();
		User user=(User)session.get(User.class, username);
		return user;
	}

}
