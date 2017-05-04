'use strict';
var chartFactory = function () {
    
    var _toBackEndData = function (aDate) {
        if (aDate) {
            return moment(aDate).format('YYYY-MM-DD') + " 00:00";
        } else {
            return moment().format('YYYY-MM-DD') + " 00:00";
        }
    };
    
    var _drawChartDaily = function (chartType,vm,dateFrom,dateTo) {
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
                        pointHoverBackgroundColor: "rgba(75,192,192,1)",
                        pointHoverBorderColor: "rgba(220,220,220,1)",
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: data,
                        spanGaps: false,
                    }
                ],
                height: "30"
            };

            vm.chartData(chartDataTmp);
        };
        documentService.getChartDataDaily(chartType,_toBackEndData(dateFrom), _toBackEndData(dateTo), drawChartDailyCallBack);
    };
    
   
    var _drawChartMonthly = function (chartType,vm) {
        var labels = ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Gug', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'];
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
                        spanGaps: false,
                    };
            _.forEach(res.data, function (value, key) {
                var yearMonth = value.yearMonth.toString();
                var year = yearMonth.substring(0, 4);
                if (!years.hasOwnProperty(year)) {
                    years[year] = [undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined];
                }
            });

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

            vm.chartData(chartDataTmp);
        };
        documentService.getChartDataMonthly(chartType,drawChartMonthlyCallBack);
    };
    
    return {
        drawChartDaily: function (chartType,vm,dateFrom,dateTo) {
            return _drawChartDaily(chartType,vm,dateFrom,dateTo);
        },
        drawChartMonthly: function (chartType, vm) {
            return _drawChartMonthly(chartType, vm);
        }
    };
}()
