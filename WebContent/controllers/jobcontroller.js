/**
 * JobController
 */
app.controller('JobController', function($scope, JobService, $location,$routeParams) {
	$scope.showDetails = false
	$scope.addJob = function() {

		JobService.addJob($scope.job).then(function(response) {
			console.log("job details posted successfully")
			alert('Job Details posted successfully')
			$location.path('/home')
		}, function(response) {
			if (response.status == 401) {
				if (response.data.code == 6)
					alert('Access Denied')
				else {
					$scope.error = response.data
					$location.path('/login')
				}

			}
			if (response.status == 500) {
				$scope.error = response.data
				$location.path('/addjob')
			}
		})

	}

	var jobId=$routeParams.id;
	
	$scope.getJob = function(jobId) {
		$scope.showDetails = true
		JobService.getJob(jobId).then(function(response) {
			$scope.job = response.data

		}, function(response) {
			if (response.status == 401) {
				$scope.error = response.data
				$location.path('/login')
			}
		})
	}

	/*$scope.applyJob = function() {
		alert('Applied to Job Successfully');
	}*/

	function getAllJobs() {
		JobService.getAllJobs().then(function(response) {
			$scope.jobs = response.data // List<Job> in JSON format

		}, function(response) {
			if (response.status == 401) {
				$scope.error = response.data
				$location.path('/login')
			}
		})
	}
	getAllJobs() // function call

})