/**
 * Angular JS Module
 */
var app=angular.module("app",['ngRoute','ngCookies','ngAnimate'])
//1 st parameter is module name.
//2nd param is array of dependent modules.[]-->no dependent modules.
//['ngRoute']--> For Single Page Application.
app.config(function($routeProvider){
	$routeProvider //for single page application
	.when('/register',{
		templateUrl:'views/registrationform.html',
		controller:'UserController'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
	})
	.when('/editprofile',{
		templateUrl:'views/editprofile.html',
		controller:'UserController'
	})
	 .when('/chat',{
		templateUrl:'views/chat.html',
		controller:'ChatController'
	})
	.when('/suggestedusers',{
		templateUrl:'views/suggestedusers.html',
		controller:'FriendController'
	})
	.when('/pendingrequests',{
		templateUrl:'views/pendingrequests.html',
		controller:'FriendController'
	})
	.when('/friends',{
		templateUrl:'views/friendslist.html',
		controller:'FriendController'		
	})
	.when('/addjob',{ //Data is from jobForm to controller
		templateUrl:'views/jobform.html',
		controller:'JobController'
	})
	.when('/getJob/:id',{ //controller to view
		templateUrl:'views/jobprofile.html',
		controller:'JobController'
	})
	.when('/getUser/:username',{
		templateUrl:'views/userprofile.html',
		controller:'UserController'		
	})
	.when('/alljobs',{  //from Controller to view
		templateUrl:'views/joblist.html',
		controller:'JobController'
	})
	.when('/addblog',{   //view to controller
		templateUrl:'views/blogform.html',
		controller:'BlogPostController'
	})
	.when('/myblogs',{
			templateUrl:'views/mybloglist.html',
			controller:'HomeController'
	})
	.when('/getblogs',{
		templateUrl:'views/bloglist.html',
		controller:'BlogPostController'		
	})
	.when('/admin/getblog/:id',{
		templateUrl:'views/approvalform.html',
		controller:'BlogPostDetailsController'			
	})
	.when('/getblog/:id',{
		templateUrl:'views/blogdetails.html',
		controller:'BlogPostDetailsController'			
	})
	.when('/blogimage/:id',{
			templateUrl:'views/blogimage.html',
			controller:'BlogPostDetailsController'		
	})
	.when('/uploadpic',{
		templateUrl:'views/profilepicture.html'		
	})
	
	.when('/myprofile',{
		templateUrl:'views/myprofile.html',
		controller:'UserController'
		
	})
	.when('/home',{
		templateUrl:'views/home.html',
		controller:'HomeController'			
	})
		
	.otherwise({templateUrl:'views/home.html', controller:'HomeController'})
})



app.run(function($rootScope,$cookieStore,UserService,$location){
	
	$rootScope.$on('$routeChangeStart',function(event,next,current){
		if($location.path()=='/home')
			{
			  $rootScope.showit=true;
			};
	});
	
	if($rootScope.currentUser==undefined)
		$rootScope.currentUser=$cookieStore.get('currentUser')
	$rootScope.logout=function(){
		/*
		 * Call Middleware logout url -remove HttpSession attribute and update online status to false.
		 * on success-> in frontend,remove cookieStore attribute currentUser ,delete $rootScope.
		 */
	UserService.logout().then(function(response){
		delete $rootScope.currentUser;
		$cookieStore.remove('currentUser')
		alert("Logged out  Successfully !");
		$location.path('/login')
		
	},function(response){
		console.log(response.status)
		console.log(response.data)
		$location.path('/login')
		
	})
	}
})