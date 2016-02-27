<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<md-dialog aria-label="Test Details"  ng-cloak>
  <form>
    <md-toolbar>
      <div class="md-toolbar-tools">
        <h2>{{title}}</h2>
        <span flex></span>
        <md-button class="close-details" title="Run Tests" aria-label="Close dialog" ng-click="cancel()"></md-button>
      </div>
    </md-toolbar>
    <md-dialog-content>
      <div class="md-dialog-content">
        <h2>Description</h2>
        <p>
          {{description}}
        </p>
        <h2>Test Runs</h2>
		<section layout="row" class="details">
			<ol>
				<li ng-repeat="detail in details">{{detail.average}} ms</li>
			</ol>
			<div id="graph-container">
	      <canvas id="tests-graph" class="chart chart-bar" chart-legend="true" chart-data="data" chart-labels="labels" width="600" height="200" chart-series="series"></canvas>
			</div>
	    </section>
	</div>
    </md-dialog-content>
    <md-dialog-actions layout="row">
      <span flex></span>
      <md-button ng-click="cancel()" style="margin-right:20px;">
        OK Got it
      </md-button>
    </md-dialog-actions>
  </form>
</md-dialog>