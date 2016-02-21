//data-ng-controller
app.controller('SpeedTestController', function($scope, $http, SpeedTestService) {
	$scope.results = [];
	var UI_TEST_RUN_MAX = 3;

	function speedTestHandler(results) {
		console.log(results);
		if (results && results.length != 0) {
			$scope.results = $scope.results.concat(results)
		}
	}

	$scope.init = function() {
		console.log('Init');
		$scope.results = [];
		SpeedTestService.getResults().success(speedTestHandler);
	}

	var startTime, endTime;
	var tests = [];
	var results = [];
	function smallUITestHandler(result) {
		endTime = (new Date()).getTime() - startTime;
		console.log(result);
		tests.push(createTestRecord("UI Test: Small", endTime, result.length));

		if (tests.length >= UI_TEST_RUN_MAX) {
			results.push(average(tests));
			tests = [];
			runMediumUITests();
		} else {
			runSmallUITests();
		}
		return;
	}

	function runSmallUITests() {
		console.log("running small UI Test");
		startTime = (new Date()).getTime();
		SpeedTestService.uiSmallTest().success(smallUITestHandler)
	}

	function mediumUITestHandler(result) {
		endTime = (new Date()).getTime() - startTime;
		console.log(result);
		tests.push(createTestRecord("UI Test: Medium", endTime, result.length));

		if (tests.length >= UI_TEST_RUN_MAX) {
			results.push(average(tests));
			tests = [];
			runLargeUITests();
		} else {
			runMediumUITests();
		}
		return;
	}

	function runMediumUITests() {
		console.log("running medium UI Test");
		startTime = (new Date()).getTime();
		SpeedTestService.uiMediumTest().success(mediumUITestHandler)
	}

	function largeUITestHandler(result) {
		endTime = (new Date()).getTime() - startTime;
		console.log(result);
		tests.push(createTestRecord("UI Test: Large", endTime, result.length));

		if (tests.length >= UI_TEST_RUN_MAX) {
			results.push(average(tests));
			$scope.results = $scope.results.concat(results);
			runDBTests();
		} else {
			runLargeUITests();
		}
	}

	function runLargeUITests() {
		console.log("running large UI Test");
		startTime = (new Date()).getTime();
		SpeedTestService.uiLargeTest().success(largeUITestHandler)
	}

	function runningHandler(result) {
		console.log("Still running: " + result);
		if (result === true) {
			SpeedTestService.isRunning().success(runningHandler)
		} else {
			SpeedTestService.getResults().success(speedTestHandler);
		}
	}

	function runDBTestsHandler(result) {
		console.log(result);
		if (result === true) {
			SpeedTestService.isRunning().success(runningHandler)
		} else {
			alert('Could not start DB tests');
		}
	}

	function runDBTests() {
		console.log("starting DB tests");
		SpeedTestService.runDBTests().success(runDBTestsHandler);
	}

	function createTestRecord(name, timing, recordCount) {
		var record = {};
		record.name = name;
		record.average = timing;
		record.records = recordCount;
		return record;
	}

	function average(records) {
		if (!records || records.length == 0) {
			return {};
		}
		var record = {};
		record.name = records[0].name;
		record.records = records[0].records;
		var total = 0;
		for (var i = 0; i < records.length; i++) {
			total = total + records[i].average;
		}
		record.average = total / records.length;
		return record;
	}

	$scope.runTests = function() {
		console.log("starting tests");
		$scope.results = [];
		tests = [];
		results = [];
		runSmallUITests();
	}

});
