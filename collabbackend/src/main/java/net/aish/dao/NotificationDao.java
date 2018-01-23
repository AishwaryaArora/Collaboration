package net.aish.dao;

import java.util.List;

import net.aish.model.CommentNotification;
import net.aish.model.LikeNotification;
import net.aish.model.Notification;

public interface NotificationDao {
	//select * frm notification where username='james' and viewed=0
		//list of notifications not yet viewed by user
		public List<Notification> getNotification(String username,int viewedblog);
		public Notification updateNotification(int notificationId);
		//notification for user like post.
		public List<LikeNotification> getNotificationLike(String username,int viewedlike);
		public LikeNotification updateNotificationLike(int notificationId);
		
		//notification for user commented on  post.
		public List<CommentNotification> getNotificationComment(String username,int viewedcomment);
		public CommentNotification updateNotificationComment(int notificationId);
		
}
