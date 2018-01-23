/**
 * JobService
 */

app.factory('JobService',function($http)
{
	var BASE_URL="http://localhost:9090/collabmiddleware"
	var jobService={}
	jobService.addJob=function(job)
	{
		//http://localhost:9090/collabmiddleware/ + /addJob
		//BASE_URL                                        +/addJob
		return $http.post(BASE_URL + "/savejob",job);//4
	}
	
	jobService.getAllJobs=function()
	{
		return $http.get(BASE_URL +"/alljobs")
	}
	
	jobService.getJob=function(jobId)
	{
		return $http.get(BASE_URL +"/getjob/" + jobId)
	}
	
    jobService.userAppliedJob=function(jobId)
	{
		/*alert(jobId);*/
		console.log(jobId);
		return $http.get(BASE_URL + "/userAppliedJob/"+ jobId);
	}
    
    jobService.updateJob=function(job){
		return $http.put(BASE_URL + "/updateJob",job);
	}
	
	return jobService;
	
})