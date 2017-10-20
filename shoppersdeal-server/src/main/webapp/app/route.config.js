angular.module('shoppersApp')
.run(['$rootScope', '$state',function($rootScope, $state) {
	$rootScope.$state = $state;
	
	$rootScope.$on('$stateChangeSuccess', function(evt, toState, fromState, params) {
		window.onload = loadAutoScroll;
		window.onscroll = scrollAutoScroll;
		if(toState.redirectTo) {
	        evt.preventDefault();
	        $state.go(toState.redirectTo, params, {location: 'replace'});
	    }
	});
}])
.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider
	
	/*.state("register",{
		url: "/register",
	})
	
	.state("login",{
		url: "/login",
	})
	
	.state("logout",{
		url: "/logout",
	})
	
	.state("account",{			//Will be used for MyAccount in header //If login go to profile if not go to account.html
		url: "/account",
	})*/
	
	.state("home",{
		url: "/",
		templateUrl: "view/mainPage.html",
		controller: "mainController"
	})
			
	/*.state("shop",{
		url: "/shop/" //:categoryName/:subcategoryName/{filter:[a-zA-Z0-9/-_ -.]*}",
	})
	
	.state("hotdeals",{
		url: "/hotdeals/"   //:categoryName/:subcategoryName/{filter:[a-zA-Z0-9/-_ -.]*}",
	})
	
	.state("topRated",{
		url: "/top/" //:categoryName/:subcategoryName/{filter:[a-zA-Z0-9/-_ -.]*}",
	})
	
	.state("detail",{
		url: "/product/{pro:[a-zA-Z0-9/-_ -.]*}",    //ProductNameWithId
		
	})
	
	.state("search",{
		url: "/search/:query/{filter:[a-zA-Z0-9/-_ -.]*}",
	})
	
	.state("cart",{
		url: "/cart",
	})
	
	.state("wishlist",{
		url: "/wishlist",
	})
	
	.state("checkout",{
		url: "/checkout",
	})
	
	.state("afterPayment",{
		url: "/payment/:status",    //Here Status means success or fail...
	})
	
	.state("trackOrder",{
		url: "/track/order/:orderNumber",
	})
    
	.state("404",{
		url: "/error/404",
		templateUrl: "error.html",
	})
	
	.state("204",{           //204 = NO Content
		url: "/error/204",
		templateUrl: "searchNotFound.html",
	})*/
	
	$urlRouterProvider.otherwise("/");
});