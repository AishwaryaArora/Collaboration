/**
 * UserService
 */
app.factory('UserService',function($http)
{
	var BASE_URL="http://localhost:9090/collabmiddleware"
	var userService={}
	userService.registerUser=function(user)
	{
		//http://localhost:9090/collabmiddleware/ + /registeruser
		//BASE_URL                                        +/registeruser
		return $http.post(BASE_URL + "/registeruser",user);//4
	}
	
	userService.login=function(user)
	{
		return $http.post(BASE_URL + "/login",user);
	}
	
	userService.logout=function()
	{
		return $http.get(BASE_URL + "/logout")
	}
	
	userService.getUser=function()
	{
		return $http.get(BASE_URL + "/getuser")
	}
	
	userService.editUserProfile=function(user)
	{
		return $http.put(BASE_URL + "/edituserprofile",user)
	}
	
	userService.getUserDetails=function(username)
	{
		return $http.get(BASE_URL +"/getuser/" + username)
	}
	
	userService.getListofMutualFriends=function(username){
		return $http.get(BASE_URL + "/mutualfriends/" + username)
	}
	
	
	
	return userService;
	
	
})