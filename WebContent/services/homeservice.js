/*HomeService
 * 
 */
app.factory('HomeService',function($http){
	var homeService={}
	var BASE_URL="http://localhost:8081/collabmiddleware"
		homeService.getNotificationNotViewed=function(){
		return $http.get(BASE_URL + "/getnotification/"+0)
	}
	homeService.getNotificationViewed=function(){
		return $http.get(BASE_URL + "/getnotification/"+1)
	}
	
	homeService.updateNotification=function(notificationId){
		return $http.put(BASE_URL + "/updateNotification/"+notificationId)
	}
	
	
	return homeService;
	
})