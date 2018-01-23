/**
 * UserController
 */
app.controller('UserController',function($scope,UserService,$location,$routeParams,$rootScope,$cookieStore){

	var username=$routeParams.username;
	
	if($rootScope.currentUser!=undefined){ //Fetch user details
		UserService.getUser().then(function(response){
			$scope.user=response.data   //user object
			
		},function(response)//401,500
		{
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser')
			if(response.status==401)
			{
				$location.path('/login')
			}			
		})
		
	}
	
	if($rootScope.currentUser!=undefined){
	UserService.getUserDetails(username).then(function(response)
	{
		$scope.userdetails=response.data
		
	},function(response)
	{
		if(response.status==401)
		{
			$location.path('/login')
		}
	})
	
	UserService.getListofMutualFriends(username).then(function(response)
		{
			$scope.mutualfriends=response.data
		},function(response)
		{
			if(response.status==401)
				$location.path('/login')			
		})
	}
	
	$scope.registerUser=function()//2
	{
		console.log($scope.user)
		UserService.registerUser($scope.user)//3
		.then(function (response)
		{
			$rootScope.message="Registration Successful " + $scope.user.username + "Please login to your account."
			$location.path('/login')
			
			
			},function(response)
			{
				console.log(response.data)
				console.log(response.status)
				$scope.error=response.data//Error Class Object in JSON.
			})//9
				
	}
	
	$scope.login=function()
	{
		UserService.login($scope.user)
		.then(function(response) //200 user
		{
			$rootScope.currentUser=response.data;
			$cookieStore.put('currentUser',response.data)
		 $location.path('/home')
		},function(response){ //401 ,500
			console.log(response.data);
			console.log(response.status);
			if(response.status==401){
				$scope.error=response.data;//errorclass in json format.
				$location.path('/login')
			}
		
			
				})		
	 }
	
	$scope.editUserProfile=function()
	{
		UserService.editUserProfile($scope.user)
		.then(function(response)
		{
			alert(' User Details Updated Successfully')
			$location.path('/home')
			
		},function(response)
	     {
			if(response.status==401)
			{
				$location.path('/login')
			}
			if(response.status==500)
			{
				$scope.error=response.data
				$location.path('/editprofile')
			}
			
			
		 })
		
	}
	
})