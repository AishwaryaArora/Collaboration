/**
 * HomeController
 */
app.controller('HomeController',function($rootScope,$location,$scope,HomeService)
{
// 2 variables blogsapproved, BlogsWitingForApproval
	
	// Statement to initialize variable blogsApproved
	
	 //list of suggested users
	function getAllSuggestedUsers()
	{
		HomeService.getAllSuggestedUsers().then(function(response){
			$scope.suggestedusers=response.data //A- (B U C) query
		},function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	HomeService.getBlogsPostedByApproved().then(function(response)
			{
			  $scope.blogspostedByApproved=response.data //select *  from blogpost where approved=1
				       
					},function(response)
					{
						if(response.status==401)
						{
							$location.path("/login")
						}
						
					})
	
	//add friend
	$scope.addFriendRequest=function(toId)
	{
		HomeService.addFriendRequest(toId).then(function(response) {
		alert('Friend Request has been sent successfully')	
		getAllSuggestedUsers()
		},function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	
    //pending requests 
	function getAllPendingRequests(){    //to find pending requests for logged in user
		HomeService.getAllPendingRequests().then(function(response){
			$scope.pendingRequests=response.data   //select * from friend where toId=? and status=P 
		},function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	//pendingrequest-friend object with status P
	//update status char[ A / R ] from pendingrequest.html
	//pending request -[id=2313,"fromId"="ser","toId":"aish","status='P']
	//updated status ='A'
	$scope.updatePendingRequest=function(pendingrequest,updatedstatus)
	{
		pendingrequest.status=updatedstatus
		$scope.friend=pendingrequest.fromUser
		//pending request -[id=2313,"fromId"="ser","toId":"aish","status='A']
		//pending request with status as 'A'/'R'
		HomeService.updatePendingRequest(pendingrequest).then(function(response){
			if(pendingrequest.status=='A')
			alert('You are now friends with ' + $scope.friend.firstname + ' ' +  $scope.friend.lastname)
			getAllPendingRequests()
		},function(response){
			
			if(response.status==401)
				$location.path('/login')
			
		})
	}
	
	
	
	
	HomeService.getBlogsApproved().then(function(response)
	{
	  $scope.blogsApproved=response.data //select *  from blogpost where approved=1
		       
			},function(response)
			{
				if(response.status==401)
				{
					$location.path("/login")
				}
				
			})
			
			
			
	function getJobNotifications()
	{
		HomeService.getJobNotifications().then(function(response){
			$rootScope.jobnotifications=response.data
			/*alert($rootScope.jobnotifications.length)*/
			console.log($rootScope.jobnotifications)
		},function(response)
		{
			if(response.status==401)
			{
				$location.path("/login")
			}
			
		})		
	}
	
	getJobNotifications()
			
	function getNotification()
	{
		HomeService.getNotificationNotViewed().then(function(response) {
			$rootScope.notificationNotViewed = response.data // select * from
																// no.where
																// username=?
																// and viewed=0
			$rootScope.notificationNotViewedLength=$rootScope.notificationNotViewed.length
		}, function(response) {
			if (response.status == 401) {
				$location.path('/login')
			}
		})

		HomeService.getNotificationViewed().then(function(response) // select *
																	// from no.where username=? and	 viewed=1
		{
			$rootScope.notificationViewed = response.data
			$rootScope.notificationViewedLength=$rootScope.notificationViewed.length

		}, function(response) {
			if (response.status == 401) {
				$location.path('/login')
			}
		})
		
		

	
		
		HomeService.getNotificationNotViewedLike().then(function(response) {
			$rootScope.notificationNotViewedLike = response.data // select * from
																// no.where
																// username=?
																// and viewed=0
			$rootScope.notificationNotViewedLikeLength=$rootScope.notificationNotViewedLike.length
		}, function(response) {
			if (response.status == 401) {
				$location.path('/login')
			}
		})

		HomeService.getNotificationViewedLike().then(function(response) // select *
																	// from no.where username=? and	 viewed=1
		{
			$rootScope.notificationViewedLike = response.data
			$rootScope.notificationViewedLikeLength=$rootScope.notificationViewedLike.length

		}, function(response) {
			if (response.status == 401) {
				$location.path('/login')
			}
		})
		
		HomeService.getNotificationNotViewedComment().then(function(response) {
			$rootScope.notificationNotViewedComment = response.data // select * from
																// no.where
																// username=?
																// and viewed=0
			$rootScope.notificationNotViewedCommentLength=$rootScope.notificationNotViewedComment.length
		}, function(response) {
			if (response.status == 401) {
				$location.path('/login')
			}
		})

		HomeService.getNotificationViewedComment().then(function(response) // select *
																	// from no.where username=? and	 viewed=1
		{
			$rootScope.notificationViewedComment = response.data
			$rootScope.notificationViewedCommentLength=$rootScope.notificationViewedComment.length

		}, function(response) {
			if (response.status == 401) {
				$location.path('/login')
			}
		})
				
		
	}
	
	getNotification()
	
	$rootScope.updateLength=function()
	{
		$rootScope.jobnotifications.length=0
		$rootScope.notificationNotViewedLength=0
		$rootScope.notificationNotViewedLikeLength=0
		$rootScope.notificationNotViewedCommentLength=0
	}
	
	
	$rootScope.updateNotification=function(notificationId)
	{
		HomeService.updateNotification(notificationId).then(function(response){
			getNotification()
			
		},function(response){
			if(response.status==401)
			{
				$location.path('/login')
			}
			
		})
		
	}
	$rootScope.updateNotificationComment=function(notificationId)
	{
		HomeService.updateNotificationComment(notificationId).then(function(response){
			getNotification()
			
		},function(response){
			if(response.status==401)
			{
				$location.path('/login')
			}
			
		})
		
	}
	
	$rootScope.addnotifiedUser=function(jobId)
	{
		HomeService.addnotifiedUser(jobId).then(function(response){
			getNotification()
			
		},function(response){
			if(response.status==401)
			{
				$location.path('/login')
			}
			
		})
		
	}
	
	
	$rootScope.updateNotificationLike=function(notificationId)
	{
		HomeService.updateNotificationLike(notificationId).then(function(response){
			getNotification()
			
		},function(response){
			if(response.status==401)
			{
				$location.path('/login')
			}
			
		})
		
	}
	
	getJobNotifications()
	getAllSuggestedUsers()
	getAllPendingRequests()
		
})