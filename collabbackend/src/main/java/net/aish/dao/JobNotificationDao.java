package net.aish.dao;

import java.util.List;

import net.aish.model.Job;
import net.aish.model.User;

public interface JobNotificationDao {

	public List<Job> JobNotification(User user);
	public void addNotifiedUser(Job job,User user);

}
