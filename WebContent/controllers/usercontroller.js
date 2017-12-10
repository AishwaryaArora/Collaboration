/**
 * UserController
 */
app.controller('UserController',function($scope,UserService,$location,$rootScope,$cookieStore){

	
	$scope.registerUser=function(){//2
		console.log($scope.user)
		UserService.registerUser($scope.user)//3
		.then(function(response){
			$location.path('/login')
		},function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.error=response.data  //ErrorClazz object in JSON
		})//9
	}
	
	$scope.login=function(){
		UserService.login($scope.user).then(function(response){//200,User
			$rootScope.currentUser=response.data
			$cookieStore.put('currentUser',response.data)
			$location.path('/home')
		},function(response){//401,500....
			if(response.status==401){
				$scope.error=response.data//errorClazz in JSON fmt
				$location.path('/login')
			}
		})
	}
})
