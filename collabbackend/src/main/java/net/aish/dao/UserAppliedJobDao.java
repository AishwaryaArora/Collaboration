package net.aish.dao;

import net.aish.model.Job;
import net.aish.model.User;
import net.aish.model.UserAppliedJob;

public interface UserAppliedJobDao {

	//select * from userAppliedJob where job-id=? and user_username=?
		//if user already applied for the Job ,1 object
		//if user has not yet applied the Job,null object.
		public UserAppliedJob userAppliedJob(Job job,User user);
		
		//Disable apply Job button if user already applied.
		//insert into userAppliedJob.
		public Job updateJob(Job job,User user);
}
