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
            return arr[2]+"-"+arr[1]+"-"+arr[0]+" 00:00";
        } 
    };

    this.drawChart = function () {
        var fromDay, toDay;
        fromDay = $('#fromDateFilterVal').val();
        toDay = $('#toDateFilterVal').val();

        var labels = [];
        var data = [];
        var callBack = function (res) {
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
        documentService.getPageViewMillions(toBackEndData(fromDay), toBackEndData(toDay), callBack);
    };

};