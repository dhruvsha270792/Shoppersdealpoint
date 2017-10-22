angular.module('shoppersApp')
.directive("submenuHover", function() {
	return {
		restrict: "A",
		link: function(scope, elem, attrs) {
			var target = $(elem);
			$(elem).hover(function() {
                target.addClass("active");
                target.find('.popup').stop(true, true).fadeIn('slow');
			}, function() {
            	target.removeClass("active");
            	target.find('.popup').stop(true, true).fadeOut('slow');
            });
		}
	}
})
.directive('priceFilter', function() {
	return {
		restrict: "A",
		link: function(scope, elem, attrs) {
			$('.slider-range-price').each(function() {
				var min = jQuery(this).data('min');
				var max = jQuery(this).data('max');
				var unit = jQuery(this).data('unit');
				var value_min = jQuery(this).data('value-min');
				var value_max = jQuery(this).data('value-max');
				var label_reasult = jQuery(this).data('label-reasult');
				var t = jQuery(this);
				$(this).slider({
					range: true,
					min: min,
					max: max,
					values: [value_min, value_max],
					slide: function(event, ui) {
						var result = label_reasult + " " + unit + ui.values[0] + ' - ' + unit + ui.values[1];
						console.log(t);
						t.closest('.slider-range').find('.amount-range-price').html(result);
					}
				});
			});
		}
	}
})
.directive('wrapOwlcarousel', function() {
	return {
		restrict: 'E',
		transclude: false,
		link: function (scope) {
			scope.initCarousel = function(element) {
			  // provide any default options you want
				var defaultOptions = {
						itemsTablet: [640, 2],
						itemsMobile: [390, 1],
						navigation: !0,
						navigationText: ["<a class='flex-prev'></a>", "<a class='flex-next'></a>"],
						slideSpeed: 500,
						pagination: !1,
						autoPlay: false
				};
				var customOptions = scope.$eval($(element).attr('data-options'));
				// combine the two options objects
				for(var key in customOptions) {
					defaultOptions[key] = customOptions[key];
				}
				// init carousel
				$(element).owlCarousel(defaultOptions);
			};
		}
	};
})
.directive('owlCarouselItem', [function() {
	return {
		restrict: 'A',
		transclude: false,
		link: function(scope, element) {
			if(scope.$last) {
				scope.initCarousel(element.parent());
			}
		}
	};
}])
.directive("customModals", ["$rootScope","modals", function($rootScope, modals) {
    return( link );
    function link(scope, element, attributes) {
        scope.subview = null;
        element.on("click", function handleClickEvent(event) {
            if (element[ 0 ] !== event.target) {
                return;
            }
            scope.$apply(modals.reject);
        });

        $rootScope.$on("modals.open", function handleModalOpenEvent(event, modalType) {
            scope.subview = modalType;
        });

        $rootScope.$on("modals.close", function handleModalCloseEvent(event) {
            scope.subview = null;
        });
    }
}]);