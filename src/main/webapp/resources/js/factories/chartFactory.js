'use strict';
var chartFactory = function () {

    var weekData = {
        endUser: {
            pageViews: {},
            loadTime: {}
        },
        appServer: {
            requests: {},
            respTime: {}
        }
    };

    var _drawChartDaily = function (chartType, vm, dateFrom, dateTo) {
        var labels = [];
        var data = [];
        var drawChartDailyCallBack = function (res) {
            _.forEach(res.data, function (value, key) {
                var dateTmp = new Date(value.day.$date);
                var dateStr = dateTmp.getDate() + "-" + (dateTmp.getMonth() + 1) + "-" + dateTmp.getFullYear();
                labels.push(dateStr);
                data.push(value.value);
            });

            var chartDataTmp = {
                labels: labels,
                datasets: [
                    {
                        label: chartType.label,
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
//                      pointHoverBackgroundColor: "rgba(75,192,192,1)",
//                      pointHoverBorderColor: "rgba(220,220,220,1)",
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: data,
                        spanGaps: false
                    }
                ],
                height: "30"
            };

            var chartOptions = chartType.optionsDaily;
            vm.chartOptions(chartOptions);
            vm.chartData(chartDataTmp);

        };
        documentService.getChartDataDaily(chartType, utilsService.toBackEndDataFrom(dateFrom), utilsService.toBackEndDataTo(dateTo), drawChartDailyCallBack);
    };

    var _drawChartWeekly = function (chartType, vm, renderChart) {
        var labels = [];
        for (var i = 1; i <= 52; i++) {
            labels.push(i);
        }
        var data = [];
        var years = {};

        var defDatasets =
                {
                    label: chartType.label,
                    fill: false,
                    lineTension: 0.3,
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 7,
                    pointHoverRadius: 5,
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: data,
                    spanGaps: false
                };

        var setChartData = function () {
            var datasets = [];
            _.forEach(years, function (value, key) {
                var dataSetTmp = $.extend({}, defDatasets);
                dataSetTmp.data = value;
                dataSetTmp.label = key;
                dataSetTmp.backgroundColor = appConstants.colors[key];
                dataSetTmp.borderColor = appConstants.colors[key];
                dataSetTmp.borderCapStyle = appConstants.colors[key];
                dataSetTmp.pointBorderColor = appConstants.colors[key];
                dataSetTmp.pointHoverBackgroundColor = appConstants.colors[key];
                dataSetTmp.pointHoverBorderColor = appConstants.colors[key];
                datasets.push(dataSetTmp);
            });


            var chartDataTmp = {
                labels: labels,
                height: "30",
                datasets: datasets
            };
            var chartOptions = chartType.optionsWeekly;
            vm.chartOptions(chartOptions);
            vm.chartData(chartDataTmp);
        };

        var drawChartWeeklyCallBack = function (res) {
            var maxYear = 0;
            _.forEach(res.data, function (value, key) {
                if (value.weekNumber) {
                    var weekNumber = value.weekNumber;
                    var year = Math.floor(weekNumber / 100);
                    if (maxYear < year) {
                        maxYear = year;
                    }
                    if (!years.hasOwnProperty(year)) {
                        years[year] = [];
                        for (var i = 1; i <= 52; i++) {
                            years[year].push(undefined);
                        }
                    }
                }
            });
            var secondLastYear = maxYear - 1;

            var maxWeekNumber = 0;
            _.forEach(res.data, function (value, key) {
                if (value.weekNumber) {
                    var weekNumber = value.weekNumber;
                    var year = Math.floor(weekNumber / 100);
                    var weekNumber = weekNumber % 100;
                    years[year][weekNumber] = value.value;
                    if (maxWeekNumber < weekNumber) {
                        maxWeekNumber = weekNumber;
                    }
                }
            });
            var secondLastWeek = maxWeekNumber - 1;

            var lastValue = 0;
            try {
                lastValue = parseFloat(years[maxYear][maxWeekNumber]);
            } catch (Err) {
                console.warn('weekly chart: error getting last value');
            }
            var secondLastValue = 0;
            try {
                secondLastValue = parseFloat(years[maxYear][secondLastWeek]);
            } catch (Err) {
                console.warn('weekly chart: error getting secondLastValue');
            }
            try {
                var previousYearValue = 0;
                previousYearValue = parseFloat(years[secondLastYear][maxWeekNumber]);
            } catch (Err) {
                console.warn('weekly chart: error getting previousYearValue');
            }


            var deltaPrevious = 0;
            if (secondLastValue != 0) {
                deltaPrevious = Math.round(((lastValue - secondLastValue) / secondLastValue) * 100);
            }
            var deltaPreviousYear = 0;
            if (previousYearValue != 0) {
                deltaPreviousYear = Math.round(((lastValue - previousYearValue) / previousYearValue) * 100);
            }

            var arrowUp = "<i style='padding-left:10px' class='fa fa-long-arrow-up'></i>";
            var arrowDown = "<i style='padding-left:10px' class='fa fa-long-arrow-down'></i>";
            var arrowZero = "<i style='padding-left:3px' class='fa fa-arrows-h'></i>";
            var deltaPreviousHTML =  deltaPrevious + '%' + arrowZero;
            if (deltaPrevious > 0){
                deltaPreviousHTML =  deltaPrevious + '%' + arrowUp;
            } else if (deltaPrevious < 0){
                deltaPreviousHTML =  deltaPrevious + '%' + arrowDown;
            }
            
            var deltaPreviousYearHTML =  deltaPreviousYear + '%' + arrowZero;
            if (deltaPreviousYear > 0){
                deltaPreviousYearHTML =  deltaPreviousYear + '%' + arrowUp;
            } else if (deltaPreviousYear < 0){
                deltaPreviousYearHTML =  deltaPreviousYear + '%' + arrowDown;
            }
            
            if (chartType.metricName == 'EndUser') {
                if (chartType.valueName == 'call_count') {
                    vm.statistics.endUser.pageViews.lastValue(Math.round(lastValue / 1000).toLocaleString('it-IT') + 'k');
                    vm.statistics.endUser.pageViews.deltaToLastWeekVal(deltaPrevious);
                    vm.statistics.endUser.pageViews.deltaToLastYearWeekVal(deltaPreviousYear);
                    vm.statistics.endUser.pageViews.deltaToLastWeek(deltaPreviousHTML);
                    vm.statistics.endUser.pageViews.deltaToLastYearWeek(deltaPreviousYearHTML);
                    weekData.endUser.pageViews = res;
                    if (renderChart) {
                        setChartData();
                    }
                } else if (chartType.valueName == 'average_response_time') {
                    vm.statistics.endUser.loadTime.lastValue(Math.round(lastValue / 1000).toLocaleString('it-IT') + ' sec.');
                    vm.statistics.endUser.loadTime.deltaToLastWeek(deltaPreviousHTML);
                    vm.statistics.endUser.loadTime.deltaToLastYearWeek(deltaPreviousYearHTML);
                    weekData.endUser.loadTime = res;
                    if (renderChart) {
                        setChartData();
                    }
                }
            } else if (chartType.metricName == 'HttpDispatcher') {
                if (chartType.valueName == 'call_count') {
                    vm.statistics.appServer.requests.lastValue(Math.round(lastValue / 1000000).toLocaleString('it-IT') + 'M');
                    vm.statistics.appServer.requests.deltaToLastWeek(deltaPreviousHTML);
                    vm.statistics.appServer.requests.deltaToLastYearWeek(deltaPreviousYearHTML);
                    weekData.appServer.requests = res;
                    if (renderChart) {
                        setChartData();
                    }
                } else if (chartType.valueName == 'average_response_time') {
                    vm.statistics.appServer.respTime.lastValue(lastValue + 'ms');
                    vm.statistics.appServer.respTime.deltaToLastWeek(deltaPreviousHTML);
                    vm.statistics.appServer.respTime.deltaToLastYearWeek(deltaPreviousYearHTML);
                    weekData.appServer.respTime = res;
                    if (renderChart) {
                        setChartData();
                    }
                }
            }
        };
        if (chartType.metricName == 'EndUser') {
            if (chartType.valueName == 'call_count') {
                if (weekData.endUser.pageViews.hasOwnProperty('data')) {
                    drawChartWeeklyCallBack(weekData.endUser.pageViews);
                } else {
                    documentService.getChartDataWeekly(chartType, drawChartWeeklyCallBack);
                }

            } else if (chartType.valueName == 'average_response_time') {
                if (weekData.endUser.loadTime.hasOwnProperty('data')) {
                    drawChartWeeklyCallBack(weekData.endUser.loadTime);
                } else {
                    documentService.getChartDataWeekly(chartType, drawChartWeeklyCallBack);
                }

            }
        } else if (chartType.metricName == 'HttpDispatcher') {
            if (chartType.valueName == 'call_count') {
                if (weekData.appServer.requests.hasOwnProperty('data')) {
                    drawChartWeeklyCallBack(weekData.appServer.requests);
                } else {
                    documentService.getChartDataWeekly(chartType, drawChartWeeklyCallBack);
                }

            } else if (chartType.valueName == 'average_response_time') {
                if (weekData.appServer.respTime.hasOwnProperty('data')) {
                    drawChartWeeklyCallBack(weekData.appServer.respTime);
                } else {
                    documentService.getChartDataWeekly(chartType, drawChartWeeklyCallBack);
                }

            }
        }

    };

    var _drawChartMonthly = function (chartType, vm) {
        var labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        var data = [];
        var years = {};
        var drawChartMonthlyCallBack = function (res) {
            var defDatasets =
                    {
                        label: chartType.label,
                        fill: false,
                        lineTension: 0.3,
                        borderCapStyle: 'butt',
                        borderDash: [],
                        borderDashOffset: 0.0,
                        borderJoinStyle: 'miter',
                        pointBackgroundColor: "#fff",
                        pointBorderWidth: 7,
                        pointHoverRadius: 5,
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: data,
                        spanGaps: false
                    };

            _.forEach(res.data, function (value, key) {
                var yearMonth = value.yearMonth.toString();
                var year = yearMonth.substring(0, 4);
                if (!years.hasOwnProperty(year)) {
                    years[year] = [undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined];
                }
            });

            var maxMonth = 0;
            _.forEach(res.data, function (value, key) {
                var yearMonth = value.yearMonth.toString();
                var year = yearMonth.substring(0, 4);
                var month = Number(yearMonth.substring(4)) - 1;
                years[year][month] = value.value;
            });

            var datasets = [];
            _.forEach(years, function (value, key) {
                var dataSetTmp = $.extend({}, defDatasets);
                dataSetTmp.data = value;
                dataSetTmp.label = key;
                dataSetTmp.backgroundColor = appConstants.colors[key];
                dataSetTmp.borderColor = appConstants.colors[key];
                dataSetTmp.borderCapStyle = appConstants.colors[key];
                dataSetTmp.pointBorderColor = appConstants.colors[key];
                dataSetTmp.pointHoverBackgroundColor = appConstants.colors[key];
                dataSetTmp.pointHoverBorderColor = appConstants.colors[key];
                datasets.push(dataSetTmp);
            });

            var chartDataTmp = {
                labels: labels,
                height: "30",
                datasets: datasets
            };
            var chartOptions = chartType.optionsMonthly;
            vm.chartOptions(chartOptions);
            vm.chartData(chartDataTmp);
        };
        documentService.getChartDataMonthly(chartType, drawChartMonthlyCallBack);
    };

    return {
        drawChartDaily: function (chartType, vm, dateFrom, dateTo) {
            return _drawChartDaily(chartType, vm, dateFrom, dateTo);
        },
        drawChartMonthly: function (chartType, vm) {
            return _drawChartMonthly(chartType, vm);
        },
        drawChartWeekly: function (chartType, vm, renderChart) {
            return _drawChartWeekly(chartType, vm, renderChart);
        }
    };
}()
