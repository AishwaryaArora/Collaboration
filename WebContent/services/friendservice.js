/**
 * FriendService
 */
app.factory('FriendService',function($http){
	var friendService={}
	var BASE_URL="http://localhost:9090/collabmiddleware"
	friendService.getAllSuggestedUsers=function()
	{
		return $http.get(BASE_URL + "/suggestedusers")
	}
	
	friendService.addFriendRequest=function(toId){
		return $http.post(BASE_URL + "/addfriendrequest/" + toId)
	}
	
	friendService.getAllPendingRequests=function()
	{
		return $http.get(BASE_URL + "/pendingrequests")
	}
	
	friendService.updatePendingRequest=function(pendingrequest)
	{
		return $http.put(BASE_URL + "/updatependingrequest",pendingrequest)
	}
	
	friendService.getListofFriends=function(){
		return $http.get(BASE_URL + "/getfriends")
	}
	
	friendService.getListofMutualFriends=function(toId){
		return $http.get(BASE_URL + "/mutualfriends/" + toId)
	}
	
	friendService.getAllSentRequests=function()
	{
		return $http.get(BASE_URL + "/sentrequests")
	}
	
	return friendService;
	
})