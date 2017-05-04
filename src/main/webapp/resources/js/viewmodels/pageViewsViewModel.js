/**
 * 
 */
var pageViewsViewModel = function () {
    var that = this;
    that.dateFrom = ko.observable(moment().add(-7, 'day'));
    that.dateTo = ko.observable(moment().add(-1, 'day'));
    that.chartData = ko.observable();

    this.drawChartDaily = function () {
        chartFactory.drawChartDaily(appConstants.chartType.pageViewMillions, that, that.dateFrom(), that.dateTo());
    };


    this.drawChartMonthly = function () {
        chartFactory.drawChartMonthly(appConstants.chartType.pageViewMillions, that);
    };
};