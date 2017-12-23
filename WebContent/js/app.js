/**
 * Angular Js Module
 */
var app=angular.module("app",['ngRoute','ngCookies'])
//1 st parameter is module name.
//2nd param is array of dependent modules.[]-->no dependent modules.
//['ngRoute']--> For Single Page Application.

app.config(function($routeProvider){
	$routeProvider//for single page application
	.when('/register',{
		templateUrl:'views/registrationform.html',
		controller:'UserController'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
	})
	.when('/editprofile',{
		templateUrl:'views/userprofile.html',
		controller:'UserController'
	})
	.when('/addjob',{ //Data is from jobForm(view) to controller
		templateUrl:'views/jobform.html',
		controller:'JobController'
	})
	/*.when('/getJob/{jobId}',{ //controller to view
		templateUrl:'views/jobprofile.html',
		controller:'JobController'
	})*/
	.when('/alljobs',{  //from Controller to view
		templateUrl:'views/joblist.html',
		controller:'JobController'
	})
	.when('/addblog',{  //from Controller to view
		templateUrl:'views/blogform.html',
		controller:'BlogPostController'
	})
	.when('/getblogs',{
		templateUrl:'views/bloglist.html',
		controller:'BlogPostController'		
	})
	.when('/admin/getblog/:id',{
		templateUrl:'views/approvalform.html',
		controller:'BlogPostDetailsController'			
	})
	/*.when('/getblog/:id',{
		templateUrl:'views/blogdetails.html',
		controller:'BlogPostDetailsController'			
	})*/
	.otherwise({templateUrl:'views/home.html'})
})
app.run(function($rootScope,$cookieStore,UserService,$location){
	if($rootScope.currentUser==undefined)
		$rootScope.currentUser=$cookieStore.get('currentUser')
		
	$rootScope.logout=function(){
		/*
		 * Call middleware logout url -> Middleware - remove HttpSession attribute,update online status to false
		 * on success - in frontend , remove cookieStore attribute currentUser, delete $rootScope.
		 */
		UserService.logout().then(function(response){
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser')
			$location.path('/login')
			
		},function(response){
			console.log(response.status)
			$location.path('/login')
		})
	}
})
