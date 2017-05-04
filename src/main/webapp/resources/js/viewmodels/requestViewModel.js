/**
 * 
 */
var requestViewModel = function () {
    var that = this;
    that.dateFrom = ko.observable(moment().add(-7, 'day'));
    that.dateTo = ko.observable(moment());
    that.chartData = ko.observable();

    this.drawChartDaily = function () {
        chartFactory.drawChartDaily(appConstants.chartType.requestMillions, that, that.dateFrom(), that.dateTo());
    };

    this.drawChartMonthly = function () {
        chartFactory.drawChartMonthly(appConstants.chartType.requestMillions, that);
    };
};