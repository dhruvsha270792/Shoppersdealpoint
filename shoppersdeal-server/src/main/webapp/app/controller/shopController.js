angular.module('shoppersApp').controller('shopController',['$scope','$rootScope','mainService','$state','modals','sessionService', function($scope, $rootScope, mainService, $state, modals, sessionService) {
	
	$scope.getShopPageList = function() {
		var criteria = "?n=0&pos=0";
		var categoryName = $state.params.categoryName;
		var subcategoryName = $state.params.subcategoryName;
		var productId = $state.params.productId;
		if(categoryName != null && categoryName != "") {
			criteria = criteria+"&categoryName="+categoryName;
			if(subcategoryName != null && categoryName != "") {
				criteria = criteria+"&subcategoryName="+subcategoryName;
				if(productId != null && productId != "")
					criteria = criteria+"&productId="+productId;
			}
		}
		mainService.getShopDeals(criteria).success(function(response) {
			$scope.shopPageList = response.data;
		});
	}
	
	$scope.addToCartShop = function (productDetail) {
		var cart=[];
		var product = {};
		product.productName= productDetail.productName;
		product.price = productDetail.price;
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
			$scope.getCartListShop();
			$rootScope.cartList.size += 1;
		}
	}
	
	$scope.getCartListShop = function() {
		$rootScope.cartList.data = JSON.parse(sessionService.get('cart'));
		$rootScope.cartList.subtotal = 0;
		$rootScope.cartList.data.map(function(d) {
			$rootScope.cartList.subtotal += d['price'].valueOf();
		});
	}
	
	
	$scope.removeFromCartShop = function (product) {
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