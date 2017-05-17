'use strict';
var chartFactory = function () {

    var _toBackEndData = function (aDate) {
        if (aDate) {
            return moment(aDate).format('YYYY-MM-DD') + " 00:00";
        } else {
            return moment().format('YYYY-MM-DD') + " 00:00";
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
        documentService.getChartDataDaily(chartType, _toBackEndData(dateFrom), _toBackEndData(dateTo), drawChartDailyCallBack);
    };

    var _drawChartWeekly = function (chartType, vm) {
        var labels = [];
        for (var i = 1; i <= 52; i++) {
//            var d = new Date(2017, 0, 1+((i-1)*7));
//            labels.push(i+" ("+d.getDay()+"-"+(d.getMonth()+1)+")");
            labels.push(i);
        }
        var data = [];
        var years = {};

        var drawChartWeeklyCallBack = function (res) {
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

            var maxYear = 0;
            _.forEach(res.data, function (value, key) {
                var dateTmp = new Date(value.day.$date);
                var year = dateTmp.getFullYear();
                if (maxYear < year) {
                    maxYear = year;
                }
                if (!years.hasOwnProperty(year)) {
                    years[year] = [];
                    for (var i = 1; i <= 52; i++) {
                        years[year].push(undefined);
                    }
                }
            });
            var secondLastYear = maxYear - 1;

            var maxWeekNumber = 0;
            _.forEach(res.data, function (value, key) {
                var dateTmp = new Date(value.day.$date);
                var year = dateTmp.getFullYear();
                var weekNumber = value.weekNumber-1;
                years[year][weekNumber] = value.value;
                if (maxWeekNumber < weekNumber) {
                    maxWeekNumber = weekNumber;
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

            vm.deltaToPrevious(deltaPrevious);
            vm.deltaToPreviousYear(deltaPreviousYear);

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
        documentService.getChartDataWeekly(chartType, drawChartWeeklyCallBack);
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
            var maxYear = 0;
            _.forEach(res.data, function (value, key) {
                var yearMonth = value.yearMonth.toString();
                var year = yearMonth.substring(0, 4);
                if (year > maxYear) {
                    maxYear = year;
                }
                if (!years.hasOwnProperty(year)) {
                    years[year] = [undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined];
                }
            });
            var secondLastYear = maxYear-1;
            
            var maxMonth = 0;
            _.forEach(res.data, function (value, key) {
                var yearMonth = value.yearMonth.toString();
                var year = yearMonth.substring(0, 4);
                var month = Number(yearMonth.substring(4)) - 1;
                if (year == maxYear && month > maxMonth) {
                    maxMonth = month;
                }
                years[year][month] = value.value;
            });
            var secondLastMonth = maxMonth-1;
            var secondLastMonthYear = maxYear;
            if (secondLastMonth < 0){
                secondLastMonth = 11;
                secondLastMonthYear = maxYear-1
            }
            
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


            var lastValue = 0;
            try {
                lastValue = parseFloat(years[maxYear][maxMonth]);
            } catch (Err) {
                console.warn('Monhtly chart: error getting last value');
            }
            var secondLastValue = 0;
            try {
                secondLastValue = parseFloat(years[secondLastMonthYear][secondLastMonth]);
            } catch (Err) {
                console.warn('Monhtly chart: error getting secondLastValue');
            }
            var previousYearValue = 0;
            try {
                previousYearValue = parseFloat(years[secondLastYear][maxMonth]);
            } catch (Err) {
                console.warn('Monhtly chart: error getting previousYearValue');
            }


            var deltaPrevious = 0;
            if (secondLastValue != 0) {
                deltaPrevious = Math.round(((lastValue - secondLastValue) / secondLastValue) * 100);
            }
            var deltaPreviousYear = 0;
            if (previousYearValue != 0) {
                deltaPreviousYear = Math.round(((lastValue - previousYearValue) / previousYearValue) * 100);
            }

            vm.deltaToPrevious(deltaPrevious);
            vm.deltaToPreviousYear(deltaPreviousYear);

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
        drawChartWeekly: function (chartType, vm) {
            return _drawChartWeekly(chartType, vm);
        }
    };
}()
