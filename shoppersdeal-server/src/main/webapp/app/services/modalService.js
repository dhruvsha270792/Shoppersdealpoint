angular.module("shoppersApp")
.service("modals", function($rootScope, $q) {
    var modals = {
        deferred: null,
        params: null
    };
    
    return({
        open: open,
        params: params,
        proceedTo: proceedTo,
        reject: reject,
        resolve: resolve
    });

    function open(type, params, pipeResponse) {
        var previousDeferred = modals.deferred;
        modals.deferred = $q.defer();
        modals.params = params;
        
        if (previousDeferred && pipeResponse) {
            modals.deferred.promise.then( previousDeferred.resolve, previousDeferred.reject);
        }
        else if (previousDeferred) {
            previousDeferred.reject();
        }
        $rootScope.$emit("modals.open", type);
        return(modals.deferred.promise);
    }

    function params() {
        return(modals.params || {});
    }

    function proceedTo(type, params) {
        return(open(type, params, true));
    }

    function reject(reason) {
        if (!modals.deferred) {
            return;
        }
        modals.deferred.reject(reason);
        modals.deferred = modals.params = null;
        $rootScope.$emit("modals.close");
    }

    function resolve(response) {
        if (!modals.deferred) {
            return;
        }
        modals.deferred.resolve(response);
        modals.deferred = modals.params = null;
        $rootScope.$emit("modals.close");
    }
});