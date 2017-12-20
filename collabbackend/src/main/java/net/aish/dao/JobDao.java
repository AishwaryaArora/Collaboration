package net.aish.dao;

import java.util.List;

import net.aish.model.Job;

public interface JobDao {

	public void saveJob(Job job);//insert into job
	public List<Job> getAllJobs();//select * from job
	public Job getJob(int jobId);//select * from job where id=?
}
