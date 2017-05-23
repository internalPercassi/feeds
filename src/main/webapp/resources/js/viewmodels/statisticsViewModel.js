var statisticsViewModel = function () {
    var that = this;
    that.dateFrom = ko.observable(moment().add(-7, 'day'));
    that.dateTo = ko.observable(moment().add(-1, 'day'));
    that.chartData = ko.observable();
    that.chartOptions = ko.observable();


    that.statistics = {
        endUser: {
            pageViews: {
                lastValue: ko.observable(),
                deltaToLastWeek: ko.observable(),
                deltaToLastYearWeek: ko.observable(),
            },
            loadTime: {
                lastValue: ko.observable(),
                deltaToLastWeek: ko.observable(),
                deltaToLastYearWeek: ko.observable(),
            }
        },
        appServer: {
            requests: {
                lastValue: ko.observable(),
                deltaToLastWeek: ko.observable(),
                deltaToLastYearWeek: ko.observable(),
            },
            respTime: {
                lastValue: ko.observable(),
                deltaToLastWeek: ko.observable(),
                deltaToLastYearWeek: ko.observable(),
            }}
    };


    this.drawChartDaily = function () {
        chartFactory.drawChartDaily(appConstants.chartType.pageViewMillions, that, that.dateFrom(), that.dateTo());
    };

    this.drawChartWeekly = function () {
        chartFactory.drawChartWeekly(appConstants.chartType.pageViewMillions, that);
    };

    this.drawChartMonthly = function () {
        chartFactory.drawChartMonthly(appConstants.chartType.pageViewMillions, that);
    };

    this.initPage = function () {
        chartFactory.drawChartWeekly(appConstants.chartType.pageViewMillions, that, true);
        chartFactory.drawChartWeekly(appConstants.chartType.loadTime, that, false);
        chartFactory.drawChartWeekly(appConstants.chartType.requestMillions, that, false);
        chartFactory.drawChartWeekly(appConstants.chartType.responseTime, that, false);
    }
};
