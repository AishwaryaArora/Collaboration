package net.aish.dao;

import java.util.List;

import net.aish.model.Notification;

public interface NotificationDao {
	public List<Notification> getNotification(String username,int viewed);

}
