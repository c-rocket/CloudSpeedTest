<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div flex ng-cloak class="content" data-ng-init="init()">
	<md-grid-list id="content-list" md-cols-sm="1" md-cols-md="2" md-cols-lg="3" md-cols-gt-lg="4" md-row-height="200px" class="content-grid"
		md-gutter="30"> <md-grid-tile data-ng-repeat="result in results" class="md-whiteframe-z4 tile{{$index%7}}">
	<section layout="column">
		<div class="resultName">{{result.name}}</div><br />
		<div class="resultAverage">Timing: {{result.average | metricFilter}} ms</div>
		<div class="resultCount">Records Tested: {{result.records}}</div>
	</section>
	</md-grid-tile> </md-grid-list>
</div>