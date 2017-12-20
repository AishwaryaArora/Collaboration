/**
 * JobService
 */

app.factory('JobService',function($http)
{
	var BASE_URL="http://localhost:8081/collabmiddleware"
	var jobService={}
	jobService.addJob=function(job)
	{
		//http://localhost:8080/project2middleware  /registeruser
		//BASE_URL                                + "/registeruser"
		return $http.post(BASE_URL+"/savejob",job);//4
	}
	
	jobService.getAllJobs=function()
	{
		return $http.get(BASE_URL +"/alljobs")
	}
	
	jobService.getJob=function(jobId)
	{
		return $http.get(BASE_URL +"/getjob/" + jobId)
	}
	
	return jobService;
	
})