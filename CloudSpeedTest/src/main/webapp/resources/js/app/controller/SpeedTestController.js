//data-ng-controller
app
		.controller(
				'SpeedTestController',
				function($scope, $http, SpeedTestService, $mdDialog, $mdMedia, $timeout) {
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
						$scope.testsRunning = false;
					}

					var startTime, endTime;
					var uiTests = [];
					var currentTest = 0;
					uiTests
							.push({
								title : 'UI Test: Ping',
								url : baseUrl + 'speedtest/uitest/ping',
								description : 'This test defines a simple ping to test client-side timings with the WebLogic server, there is no DB interaction.',
								details : []
							});
					uiTests
							.push({
								title : 'UI Test: Small',
								url : baseUrl + 'speedtest/uitest/small',
								description : 'This test defines a small packet UI test of a list of objects. It gives us client-side timings with the WebLogic server, there is no DB interaction.',
								details : []
							});
					uiTests
							.push({
								title : 'UI Test: Medium',
								url : baseUrl + 'speedtest/uitest/medium',
								description : 'This test defines a medium packet UI test of a list of objects. It gives us client-side timings with the WebLogic server, there is no DB interaction.',
								details : []
							});
					uiTests
							.push({
								title : 'UI Test: Large',
								url : baseUrl + 'speedtest/uitest/large',
								description : 'This test defines a large packet UI test of a list of objects. It gives us client-side timings with the WebLogic server, there is no DB interaction.',
								details : []
							});

					$scope.runTests = function() {
						$scope.testInformation = 'Starting tests';
						$scope.results = [];
						$scope.testsRunning = true;
						currentTest = 0;
						for (var i = 0; i < uiTests.length; i++) {
							uiTests[i].details = []
						}
						runUITest();
					}

					function uiTestHandler(result) {
						endTime = (new Date()).getTime() - startTime;
						uiTests[currentTest].details.push(createTestRecord(uiTests[currentTest].title, endTime, result.length));

						if (uiTests[currentTest].details.length >= UI_TEST_RUN_MAX) {
							$scope.results.push(average(uiTests[currentTest].details));
							currentTest = currentTest + 1;

						}
						if (currentTest < uiTests.length) {
							runUITest();
						} else {
							 runDBTests();
//							$scope.testInformation = 'Tests Completed';
//							$scope.testsRunning = false;
//							SpeedTestService.getResults().success(resultsHandler);
						}
						return;
					}

					function runUITest() {
						$scope.testInformation = 'Running test: ' + uiTests[currentTest].title + ' '
								+ (uiTests[currentTest].details.length + 1) + ' of ' + UI_TEST_RUN_MAX;
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
						record.records = (recordCount) ? recordCount : 1;
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

					$scope.showDetails2 = function(testCount, ev) {
						$mdDialog.show($mdDialog.alert().parent(angular.element(document.querySelector('#testgrid'))).clickOutsideToClose(
								true).title(uiTests[testCount].title).textContent(uiTests[testCount].description).ariaLabel(
								uiTests[testCount].title).ok('Got it!').targetEvent(ev));
					};

					$scope.showDetails = function(testCount, ev) {
						var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
						$mdDialog.show({
							controller : DialogController,
							templateUrl : baseUrl + 'resources/js/app/views/details.jsp',
							parent : angular.element(document.querySelector('#testgrid')),
							targetEvent : ev,
							clickOutsideToClose : true,
							fullscreen : useFullScreen,
							locals : {
								title : uiTests[testCount].title,
								description : uiTests[testCount].description,
								details : uiTests[testCount].details
							}
						});
						$scope.$watch(function() {
							return $mdMedia('xs') || $mdMedia('sm');
						}, function(wantsFullScreen) {
							$scope.customFullscreen = (wantsFullScreen === true);
						});
					};

				});

function DialogController($scope, $mdDialog, $timeout, title, description, details) {
	$scope.title = title;
	$scope.description = description;
	$scope.details = details;
	$scope.series = [ 'Test Averages (ms)' ];

	var data = [];
	var start = [];
	var labels = [];
	for (var i = 0; i < details.length; i++) {
		labels.push('Test ' + (i + 1));
		data.push(details[i].average);
		start.push(0);
	}

	$scope.labels = labels;
	$scope.data = [ start ];
	$timeout(function() {
		$scope.data = [ data ];
	}, 500);


	console.log($scope.data);

	$scope.hide = function() {
		$mdDialog.cancel();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
}
