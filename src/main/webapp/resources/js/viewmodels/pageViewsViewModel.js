/**
 * 
 */
var pageViewsViewModel = function () {
    var that = this;
    that.dateFrom = ko.observable(moment().add(-7, 'day'));
    that.dateTo = ko.observable(moment().add(-1, 'day'));
    that.chartData = ko.observable();
    that.chartOptions = ko.observable();
    that.deltaToPrevious = ko.observable(0);
    that.deltaToPreviousYear = ko.observable(0);
    
    this.drawChartDaily = function () {
        chartFactory.drawChartDaily(appConstants.chartType.pageViewMillions, that, that.dateFrom(), that.dateTo());
    };

    this.drawChartWeekly = function () {
        chartFactory.drawChartWeekly(appConstants.chartType.pageViewMillions, that);
    };
    
    this.drawChartMonthly = function () {
        chartFactory.drawChartMonthly(appConstants.chartType.pageViewMillions, that);
    };
};