var statisticsViewModel = function () {
    var that = this;
    that.chartType = appConstants.chartType.pageViewMillions;
    that.dateFrom = ko.observable(moment().add(-7, 'day'));
    that.dateTo = ko.observable(moment().add(-1, 'day'));
    that.chartData = ko.observable();
    that.chartOptions = ko.observable();


    that.statistics = {
        endUser: {
            pageViews: {
                lastValue: ko.observable(),
                deltaToLastWeekVal: ko.observable(),
                deltaToLastYearWeekVal: ko.observable(),
                deltaToLastWeek: ko.observable(),
                deltaToLastYearWeek: ko.observable()
            },
            loadTime: {
                lastValue: ko.observable(),
                deltaToLastWeekVal: ko.observable(),
                deltaToLastYearWeekVal: ko.observable(),
                deltaToLastWeek: ko.observable(),
                deltaToLastYearWeek: ko.observable()
            }
        },
        appServer: {
            requests: {
                lastValue: ko.observable(),
                deltaToLastWeekVal: ko.observable(),
                deltaToLastYearWeekVal: ko.observable(),
                deltaToLastWeek: ko.observable(),
                deltaToLastYearWeek: ko.observable()
            },
            respTime: {
                lastValue: ko.observable(),
                deltaToLastWeekVal: ko.observable(),
                deltaToLastYearWeekVal: ko.observable(),
                deltaToLastWeek: ko.observable(),
                deltaToLastYearWeek: ko.observable()
            }}
    };

    this.drawChartDaily = function () {
        var dateFrom = that.dateFrom().setHours(0, 0, 0, 0);
        var dateTo = that.dateTo().setHours(23, 59, 59, 0);
        chartFactory.drawChartDaily(that.chartType, that, dateFrom, dateTo);
    };  

    this.drawChartWeekly = function () {
        chartFactory.drawChartWeekly(that.chartType, that, true);
    };

    this.drawChartWeeklyPageViewMillions = function () {
        $('.hoverBoxActive').removeClass('hoverBoxActive');
        $('#pageViewMillions').addClass('hoverBoxActive');
        $('.nav-tabs a[href="#2"]').tab('show');
        that.chartType = appConstants.chartType.pageViewMillions;
        chartFactory.drawChartWeekly(that.chartType, that, true);
    };

    this.drawChartWeeklyLoadTime = function () {
        $('.hoverBoxActive').removeClass('hoverBoxActive');
        $('#loadTime').addClass('hoverBoxActive');
        $('.nav-tabs a[href="#2"]').tab('show');
        that.chartType = appConstants.chartType.loadTime;
        chartFactory.drawChartWeekly(that.chartType, that, true);
    };

    this.drawChartWeeklyRequestMillions = function () {
        $('.hoverBoxActive').removeClass('hoverBoxActive');
        $('#requestMillions').addClass('hoverBoxActive');
        $('.nav-tabs a[href="#2"]').tab('show');
        that.chartType = appConstants.chartType.requestMillions;
        chartFactory.drawChartWeekly(that.chartType, that, true);
    };

    this.drawChartWeeklyResponseTime = function () {
        $('.hoverBoxActive').removeClass('hoverBoxActive');
        $('#responseTime').addClass('hoverBoxActive');
        $('.nav-tabs a[href="#2"]').tab('show');
        that.chartType = appConstants.chartType.responseTime;
        chartFactory.drawChartWeekly(that.chartType, that, true);
    };

    this.drawChartMonthly = function () {
        chartFactory.drawChartMonthly(that.chartType, that);
    };

    this.initPage = function () {
        chartFactory.drawChartWeekly(appConstants.chartType.pageViewMillions, that, true);
        chartFactory.drawChartWeekly(appConstants.chartType.loadTime, that, false);
        chartFactory.drawChartWeekly(appConstants.chartType.requestMillions, that, false);
        chartFactory.drawChartWeekly(appConstants.chartType.responseTime, that, false);
    }
};
