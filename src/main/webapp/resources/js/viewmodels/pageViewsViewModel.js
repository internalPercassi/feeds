/**
 * 
 */
var pageViewsViewModel = function () {
    var that = this;

    that.fromDateFilter = ko.observable();
    that.toDateFilter = ko.observable();
    that.chartData = ko.observable();


    var toBackEndData = function (aDate) {
        if (aDate) {
            var arr = aDate.split("-");
            return arr[2] + "-" + arr[1] + "-" + arr[0] + " 00:00";
        }
    };

    this.drawChartDaily = function () {
        var fromDay, toDay;
        fromDay = $('#fromDateFilterVal').val();
        toDay = $('#toDateFilterVal').val();

        var labels = [];
        var data = [];
        var drawChartDailyCallBack = function (res) {
            _.forEach(res.data, function (value, key) {
                var dateTmp = new Date(value.day.$date);
                var dateStr = dateTmp.getDate() + "-" + (dateTmp.getMonth() + 1) + "-" + dateTmp.getFullYear();
                labels.push(dateStr);
                data.push(value.value);
            });

            console.log(labels);
            console.log(data);
            var chartDataTmp = {
                labels: labels,
                datasets: [
                    {
                        label: "Page Views",
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

            that.chartData(chartDataTmp);
        };
        documentService.getPageViewMillionsDaily(toBackEndData(fromDay), toBackEndData(toDay), drawChartDailyCallBack);
    };


    this.drawChartMonthly = function () {
        var fromDay, toDay;
        fromDay = $('#fromDateFilterVal').val();
        toDay = $('#toDateFilterVal').val();

        var labels = ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Gug', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'];
        var initYearValue = {
            '01': 1,
            '02': 1,
            '03': 1,
            '04': 1,
            '05': 1,
            '06': 1,
            '07': 1,
            '08': 1,
            '09': 1,
            '10': 1,
            '11': 1,
            '12': 1};
        var data = [];
        var years = {};
        var drawChartMonthlyCallBack = function (res) {
            var defDatasets =
                    {
                        label: "Page Views",
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
                    };
            _.forEach(res.data, function (value, key) {
                var yearMonth = value.yearMonth.toString();
                var year = yearMonth.substring(0, 4);
                if (!years.hasOwnProperty(year)) {
                    var defValTmp = $.extend({}, initYearValue);
                    years[year] = defValTmp;
                }
            });

            _.forEach(res.data, function (value, key) {
                var yearMonth = value.yearMonth.toString();
                var year = yearMonth.substring(0, 4);
                var month = yearMonth.substring(4);
                years[year][month] = value.value;
            });

            console.log(years);
            
            var datasets = [];
            _.forEach(years, function (value, key) {
                var dataSetTmp = $.extend({}, defDatasets);
                dataSetTmp.data = value;
                dataSetTmp.label = key;
                datasets.push(dataSetTmp);
            });

            
            var chartDataTmp = {
                labels: labels,
                height: "30",
                datasets:datasets
            };

            that.chartData(chartDataTmp);
        };
        documentService.getPageViewMillionsMonthly(drawChartMonthlyCallBack);
    };

};