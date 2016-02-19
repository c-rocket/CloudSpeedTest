<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div flex ng-cloak class="device-content"
	data-ng-controller="SpeedTestController" data-ng-init="init()">
	<md-grid-list id="deviceContent" md-cols-sm="1" md-cols-md="2"
		md-cols-lg="3" md-cols-gt-lg="4" md-row-height="100px"
		class="content-grid" md-gutter="30"> <md-grid-tile
		ng-show="currentId" data-ng-repeat="(key, value) in device.metrics"
		class="md-whiteframe-z4 tile{{$index%7}}">
	<section layout="row">
		<div class="metricLabel">{{key}}</div>
		<div class="metricValue">{{value | metricFilter}}</div>
	</section>
	</md-grid-tile> </md-grid-list>
</div>