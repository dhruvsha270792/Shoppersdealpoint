shoppersApp.controller('modalController',['$scope','modals', '$rootScope', function($scope, modals, $rootScope) {
	
	$scope.quickViewModal = function(){
		$scope.product = modals.params().productDetail;
	    $scope.close = modals.reject;
	};
}]);