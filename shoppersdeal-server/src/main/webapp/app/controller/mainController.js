angular.module('shoppersApp').controller('mainController',['$scope','$rootScope','mainService','$state','$window','modals','sessionService', function($scope, $rootScope, mainService, $state, $window, modals, sessionService) {
	
	$scope.menuList = function() {
		mainService.getMenuList().success(function(response) {
			$scope.menuList = response;
		});
	}
	
	$scope.getShopDeals = function() {
		mainService.getShopDeals().success(function(response){
			$scope.topShopDeals = response.data.splice(0,8);
		});
	}
	
	$scope.getHotdeals = function() {
		mainService.getHotDealList().success(function(response){
			$scope.hotDealList = response.data;
			$scope.hotestDeal = $scope.hotDealList[0];
		});
	}
	
	$scope.getSpecialList = function() {
		mainService.getSpecialList().success(function(response) {
			var specialProductList = response.data;
			$scope.specialProducts = specialProductList.slice(0,15);
		});
	}
	
	$scope.goToCategoryByMenu = function (category) {
		var subcategory = category.subcategory;
		if(subcategory.length == 0)
			$state.go('shop', {'categoryName' : category.categoryName})
	}
	
	$scope.openQuickView = function(product) {
		var promise = modals.open("quickView", {'productDetail' : product});
        promise.then(function handleResolve(response) {},function handleReject(response){});
	}
	
	$scope.goToSubcategoryByMenu = function (category, subcategory) {
		$state.go('shop', {'categoryName' : category.categoryName, 'subcategoryName' : subcategory.subcategoryName});
	}
	
	$scope.goToTopRated = function() {
		$state.go('topRated');
	}
	
	$scope.goToHotDeals = function() {
		$state.go('hotDeals');
	}
	
	$scope.goToShop = function() {
		$state.go('shop');
	}
	
	$scope.refreshSlider = function() {
		$('.tp-banner').revolution({
	        delay:15000,
	        startwidth:1280,
	        startheight:500,
	        hideThumbs:10,
	        navigationType:"bullet",							
	        navigationStyle:"preview1",
	        hideArrowsOnMobile:"on",
	        touchenabled:"on",
	        onHoverStop:"on",
	        spinner:"spinner4"
	    });
	}
	
	$scope.refreshMenuList = function() {
		$(".mega-menu-title").click(function() {
			if ($('.mega-menu-category').is(':visible')) {
				$('.mega-menu-category').slideUp();
			} else {
				$('.mega-menu-category').slideDown();
			}
		});
	}
	$scope.addToCartHotestDeal = function (productDetail) {
		$scope.addToCart(productDetail);
	}
	
	$scope.addToCartSpecialDeal = function (productDetail) {
		$scope.addToCart(productDetail);
	}
	
	$scope.addToCart = function (productDetail) {
		var cart=[];
		var product = {};
		product.productName= productDetail.productName;
		product.price = productDetail.price;
		console.log(product);
		var getCart = sessionService.get('cart');
		if(getCart != null) {
			cart = JSON.parse(getCart);
			var index = cart.map(function(d) { return d['productName']; }).indexOf(product.productName)
			if(index == -1) {
				cart.push(product);
				sessionService.create(cart, 'cart');
				$scope.getCartList();
				$rootScope.cartList.size += 1;
			}
		}
		else {
			cart.push(product);
			sessionService.create(cart, 'cart');
			$scope.getCartList();
			$rootScope.cartList.size += 1;
		}
	}
	
	$scope.getCartList = function() {
		$rootScope.cartList.data = JSON.parse(sessionService.get('cart'));
		$rootScope.cartList.subtotal = 0;
		$rootScope.cartList.data.map(function(d) {
			$rootScope.cartList.subtotal += d['price'].valueOf();
		});
	}
	
	
	$scope.removeFromCart = function (product) {
		var getCart = sessionService.get('cart');
		cart = JSON.parse(getCart);
		var index = cart.map(function(d) { return d['productName']; }).indexOf(product.productName);
		if(index != -1) {
			cart.splice(index, 1);
			console.log(cart);
			sessionService.create(cart, 'cart');
		}
		$scope.getCartList();
		$rootScope.cartList.size -= 1;
	}
	
}]);

/*$scope.getSubcategoryList = function(categoryId) {
$scope.subcategoryList = [];
$scope.subcategoryListLeft = [];
$scope.subcategoryListRight = []
angular.forEach($scope.allSubcategories, function(i){
	if(i.categoryId == categoryId)
		$scope.subcategoryList.push(i);
});

var arrSize = $scope.subcategoryList.length;
if(arrSize >= 2){
	if(arrSize % 2 == 0)
		size = arrSize /2;
	else
		size = (arrSize + 1) /2
	
	$scope.subcategoryListLeft = $scope.subcategoryList.slice(0, size);
	$scope.subcategoryListRight = $scope.subcategoryList.slice(size, arrSize);
}
else {
	$scope.subcategoryListLeft = $scope.subcategoryList;
}
}*/