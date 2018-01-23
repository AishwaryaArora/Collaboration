/**
 * JobController
 */
app.controller('JobController',function($scope,JobService,$routeParams,$rootScope,$location)
{
	var jobId=$routeParams.id;

	JobService.getJob(jobId).then(function(response)
	{
		$scope.job=response.data
		
		
	},function(response)
	{
		if(response.status==401)
		{
			$scope.error=response.data
			$location.path('/login')
		}
	})
	
	//UserAppliedJob -disable apply Job  button depending on whether user has applied for job or not.
	JobService.userAppliedJob(jobId).then(function(response){
		if(response.data=='')  //user has not yet applied for job
			$scope.applyJob=false;
		else
		{
			$scope.applyJob=true;
			alert('You have already applied for this Job');
		}
		/*alert($scope.applyJob);*///user has applied for job.
		  
	},function(response)
	{
		if(response.status==401)
		{
			$location.path('/login')
		}
		
	})
	
	$scope.updateJob=function(){
		JobService.updateJob($scope.job).then(function(response){
			$scope.job=response.data;
			alert('Applied to Job Successfully');
			//$scope.applyJob=!$scope.applyJob; //change applyJob property evrytime user updates ie apply .
			//blogdetails.html
			//updated likes ,glyphicon thumbs up in alternate color.
		},function(response)
		{
			if(response.status==401)
			{
				$location.path('/login')
			}
		})
	}

	$scope.addJob=function(){
		JobService.addJob($scope.job).then(function(response)
		{
			alert('Job Details posted successfully')
			$location.path("/home")
		},function(response)
		{
			if(response.status==401)
				{
				if(response.data.code==6)
				alert('Access Denied')
				else
				{
					 $scope.error=response.data
					  $location.path("/login")
					}
				 
				}
			if(response.status==500)
			{
				$scope.error=response.data
				$location.path("/addjob")
			}
		})
		
	}
	
	
	
	
	
	
	function getAllJobs()
	{
		JobService.getAllJobs().then(function(response){
			$scope.jobs=response.data  //List<Job> in JSON format
			
		},function(response)
		{
			if(response.status==401)
			{
				$scope.error=response.data
				$location.path("/login")
			}
		})
	}
	getAllJobs() //function call
	
})