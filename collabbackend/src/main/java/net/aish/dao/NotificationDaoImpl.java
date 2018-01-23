package net.aish.dao;

import java.util.List;

import javax.transaction.Transactional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.CommentNotification;
import net.aish.model.LikeNotification;
import net.aish.model.Notification;

@Repository
@Transactional
public class NotificationDaoImpl implements NotificationDao {
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public List<Notification> getNotification(String username, int viewedblog) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Notification where username=? and viewedblog=?");
		query.setString(0,username);
		query.setInteger(1, viewedblog);
		List<Notification> notifications=query.list();
		return notifications;
	}

	@Override
	public Notification updateNotification(int notificationId) {
	Session session=sessionFactory.getCurrentSession();
	Notification notification=session.get(Notification.class,notificationId);
	notification.setViewedblog(true);
	session.update(notification);
	return notification;
	}

	@Override
	public List<LikeNotification> getNotificationLike(String username,int viewedlike) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from LikeNotification  where  username=? and viewedlikes=?");
		query.setString(0,username);
		query.setInteger(1, viewedlike);
		List<LikeNotification> likenotifications=query.list();
		return likenotifications;
	}

	@Override
	public LikeNotification updateNotificationLike(int notificationId) {
		Session session=sessionFactory.getCurrentSession();
		LikeNotification likenotification=session.get(LikeNotification.class,notificationId);
		likenotification.setViewedlikes(true);
		session.update(likenotification);
		return likenotification;
		
	}

	@Override
	public List<CommentNotification> getNotificationComment(String username, int viewedcomment) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from CommentNotification  where  username=? and viewedcomments=?");
		query.setString(0,username);
		query.setInteger(1, viewedcomment);
		List<CommentNotification> commentnotify=query.list();
		return commentnotify;
	
	}

	@Override
	public CommentNotification updateNotificationComment(int notificationId) {
		Session session=sessionFactory.getCurrentSession();
		CommentNotification commentnotify =session.get(CommentNotification.class,notificationId);
		commentnotify.setViewedcomments(true);
		session.update(commentnotify);
		return commentnotify;
		
	}
}
