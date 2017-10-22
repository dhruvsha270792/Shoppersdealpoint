angular.module('shoppersApp').controller('dealController',['$scope','mainService','$state','modals','sessionService','$rootScope', function($scope, mainService, $state, modals, sessionService, $rootScope) {
	
	$scope.getDealPageList = function() {
		mainService.getHotDealList().success(function(response) {
			$scope.dealPageList = response.data;
		});
	}
	
	$scope.addToCartDeal = function(productDetail) {
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
	
	$scope.getCartListDeal = function() {
		$rootScope.cartList.data = JSON.parse(sessionService.get('cart'));
		$rootScope.cartList.subtotal = 0;
		$rootScope.cartList.data.map(function(d) {
			$rootScope.cartList.subtotal += d['price'].valueOf();
		});
	}
	
	/*$scope.addToCartDeal = function (productDetail) {
		console.log(productDetail);
		
	}
	
	
	
	
	$scope.removeFromCartDeal = function (product) {
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
	}*/
	
	
}]);