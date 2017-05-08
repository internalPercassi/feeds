/**
 * 
 */
var loadTimeViewModel = function () {
    var that = this;
    that.dateFrom = ko.observable(moment().add(-7, 'day'));
    that.dateTo = ko.observable(moment().add(-1, 'day'));
    that.chartData = ko.observable();
    that.chartOptions = ko.observable();

    this.drawChartDaily = function () {
        chartFactory.drawChartDaily(appConstants.chartType.loadTime, that, that.dateFrom(), that.dateTo());
    };

    this.drawChartWeekly = function () {
        chartFactory.drawChartWeekly(appConstants.chartType.loadTime, that);
    };
    
    this.drawChartMonthly = function () {
        chartFactory.drawChartMonthly(appConstants.chartType.loadTime, that);
    };



};