//data-ng-controller
app.controller('SpeedTestController', function($scope, $http, $mdUtil, $window, SpeedTestService) {
	$scope.results = [];

	function speedTestHandler(results) {
		if (results.length != 0) {
		} else {
		}
	}

	$scope.init = function() {
		$scope.results = [];
		SpeedTestService.getResults().success(speedTestHandler);
	}

	function runningHandler(running) {
		if (running) {

		} else {

		}
	}

	$scope.runTests = function() {
		SpeedTestService.runTests();
	}

});
