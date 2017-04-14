/**
 * 
 */
var loadTimeViewModel = function () {
	
	var _myData = loadTimeController.getData();
	
	var _labels = [];
	var _data = [];
	_.forEach(_myData.monthly, function(value, key) {
		_labels.push(value.month);
		_data.push(value.value);
	});
	
	this.fromDateFilter = ko.observable();
	this.toDateFilter = ko.observable();

	var _fromDay = new Date();
	_fromDay.setDate(_fromDay.getDate() - 7);
	this.fromDateFilter(_fromDay);
	var _toDay = new Date();
	this.toDateFilter(_toDay);
	
	this.chartData = {
		labels: _labels,
		    datasets: [
		        {
		        	label: "Load Time",
		            fill: false,
		            lineTension: 0.3,
		            backgroundColor: "rgba(75,192,192,0.4)",
		            borderColor: "rgba(75,192,192,1)",
		            borderCapStyle: 'butt',
		            borderDash: [],
		            borderDashOffset: 0.0,
		            borderJoinStyle: 'miter',
		            pointBorderColor: "rgba(75,192,192,1)",
		            pointBackgroundColor: "#fff",
		            pointBorderWidth: 7,
		            pointHoverRadius: 5,
		            pointHoverBackgroundColor: "rgba(75,192,192,1)",
		            pointHoverBorderColor: "rgba(220,220,220,1)",
		            pointHoverBorderWidth: 2,
		            pointRadius: 1,
		            pointHitRadius: 10,
		            data: _data,
		            spanGaps: false,
		        }
		    ],
		    height: "30"
		};
		
};