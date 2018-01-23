/**
 *HomeService 
 */
app.factory('HomeService', function($http)
{
	var BASE_URL="http://localhost:9090/collabmiddleware" 
	var homeService={}
	
	homeService.getListofMutualFriends=function(toId){
		return $http.get(BASE_URL + "/mutualfriends/" + toId)
	}
	
	homeService.getBlogsPostedByApproved=function()
	{
		return $http.get(BASE_URL + "/getblogspostedby/"+ 1);
	}
	
	
	homeService.addFriendRequest=function(toId){
		return $http.post(BASE_URL + "/addfriendrequest/" + toId)
	}
	
	homeService.getAllSuggestedUsers=function()
	{
		return $http.get(BASE_URL + "/suggestedusers")
	}
	
	homeService.getBlogsApproved=function()
	{
		return $http.get(BASE_URL + "/getblogs/"+ 1);
	}
	
	homeService.getNotificationNotViewed=function()
	{
		//select * from notification where username=? and viewed=0
		return $http.get(BASE_URL + "/getnotification/"+ 0);
	}
	
	homeService.getNotificationViewed=function()
	{
		//select * from notification where username=? and viewed=1
		return $http.get(BASE_URL + "/getnotification/"+ 1);
	}
	
	homeService.updateNotification=function(notificationId)
	{
		return $http.put(BASE_URL + "/updatenotification/"+notificationId);
	}
	
	homeService.getJobNotifications=function()
	{
		//select * from notification where username=? 
		return $http.get(BASE_URL + "/getjobnotification");
	}
	
	homeService.getNotificationViewedJob=function()
	{
		//select * from notification where username=? and viewed=1
		return $http.get(BASE_URL + "/getjobnotification");
	}
	
	homeService.addnotifiedUser=function(jobId)
	{
		return $http.post(BASE_URL + "/addnotifieduser/"+jobId);
	}
	
	homeService.getNotificationNotViewedLike=function()
	{
		//select * from notification where username=? and viewed=0
		return $http.get(BASE_URL + "/getnotificationlike/"+ 0);
	}
	
	homeService.getNotificationViewedLike=function()
	{
		//select * from notification where username=? and viewed=1
		return $http.get(BASE_URL + "/getnotificationlike/"+ 1);
	}
	
	homeService.updateNotificationLike=function(notificationId)
	{
		return $http.put(BASE_URL + "/updatenotificationlike/"+notificationId);
	}
	
	homeService.getNotificationNotViewedComment=function()
	{
		//select * from notification where username=? and viewed=0
		return $http.get(BASE_URL + "/getnotificationcomment/"+ 0);
	}
	
	//comment notification
	homeService.getNotificationViewedComment=function()
	{
		//select * from notification where username=? and viewed=1
		return $http.get(BASE_URL + "/getnotificationcomment/"+ 1);
	}
	
	//comment notification -update and change color
	homeService.updateNotificationComment=function(notificationId)
	{
		return $http.put(BASE_URL + "/updatenotificationcomment/"+notificationId);
	}
	
	//pending requests
	homeService.getAllPendingRequests=function()
	{
		return $http.get(BASE_URL + "/pendingrequests")
	}
	
	homeService.updatePendingRequest=function(pendingrequest)
	{
		return $http.put(BASE_URL + "/updatependingrequest",pendingrequest)
	}
	
	
	return homeService;
})