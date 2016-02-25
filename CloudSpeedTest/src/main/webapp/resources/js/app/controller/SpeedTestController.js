//data-ng-controller
app.controller('SpeedTestController',
		function($scope, $http, SpeedTestService) {
			$scope.results = [];
			var UI_TEST_RUN_MAX = 10;
			$scope.testsRunning = false;
			$scope.testInformation = 'Tests are not running'

			function resultsHandler(results) {
				if (results && results.length != 0) {
					$scope.results = $scope.results.concat(results)
				}
			}

			$scope.init = function() {
				$scope.results = [];
				SpeedTestService.getResults().success(resultsHandler);
			}

			var startTime, endTime;
			var currentTests = [];
			var results = [];
			var uiTests = [];
			var currentTest = 0;
			uiTests.push({
				title : 'UI Test: Ping',
				url : baseUrl + 'speedtest/uitest/ping'
			});
			uiTests.push({
				title : 'UI Test: Small',
				url : baseUrl + 'speedtest/uitest/small'
			});
			uiTests.push({
				title : 'UI Test: Medium',
				url : baseUrl + 'speedtest/uitest/medium'
			});
			uiTests.push({
				title : 'UI Test: Large',
				url : baseUrl + 'speedtest/uitest/large'
			});

			$scope.runTests = function() {
				$scope.testInformation = 'Starting tests';
				$scope.results = [];
				$scope.testsRunning = true;
				currentTests = [];
				currentTest = 0;
				runUITest();
			}

			function uiTestHandler(result) {
				endTime = (new Date()).getTime() - startTime;
				currentTests.push(createTestRecord(uiTests[currentTest].title, endTime, result.length));

				if (currentTests.length >= UI_TEST_RUN_MAX) {
					$scope.results.push(average(currentTests));
					currentTests = [];
					currentTest = currentTest + 1;

				}
				if (currentTest < uiTests.length) {
					runUITest();
				} else {
					runDBTests();
				}
				return;
			}

			function runUITest() {
				$scope.testInformation = 'Running test: ' + uiTests[currentTest].title + ' ' + (currentTests.length + 1) + ' of '
						+ UI_TEST_RUN_MAX;
				startTime = (new Date()).getTime();
				SpeedTestService.uiTest(uiTests[currentTest].url).success(uiTestHandler)
			}

			function messageHandler(result) {
				$scope.testInformation = result.message;
				SpeedTestService.isRunning().success(runningHandler)
			}

			function runningHandler(result) {
				if (result === true) {
					SpeedTestService.getMessage().success(messageHandler)
				} else {
					$scope.testInformation = 'Tests Completed';
					$scope.testsRunning = false;
					SpeedTestService.getResults().success(resultsHandler);
				}
			}

			function runDBTestsHandler(result) {
				SpeedTestService.isRunning().success(runningHandler)
			}

			function runDBTests() {
				$scope.testInformation = 'Running DB Tests';
				SpeedTestService.runDBTests().success(runDBTestsHandler);
			}

			function createTestRecord(name, timing, recordCount, timesRun) {
				var record = {};
				record.name = name;
				record.average = timing;
				record.records = recordCount;
				record.timesRun = timesRun;
				return record;
			}

			function average(records) {
				if (!records || records.length == 0) {
					return {};
				}
				var record = {};
				record.name = records[0].name;
				record.records = records[0].records;
				record.timesRun = records.length;
				var total = 0;
				for (var i = 0; i < records.length; i++) {
					total = total + records[i].average;
				}
				record.average = total / records.length;
				return record;
			}

		});
