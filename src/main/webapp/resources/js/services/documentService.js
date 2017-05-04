'use strict';

var documentService = function () {
    var _getParsedDocuments = function(collectionName,md5,callback){
        filterFactory.reset();
        filterFactory.addFilter('md5', '$eq', md5);
        var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
        restService.post(url, callback);

    };
    
    var _getChartDataDaily = function(chartType,fromDate,toDate,callback){
        filterFactory.reset();
        filterFactory.addFilter('day', '$gte', fromDate);
        filterFactory.addFilter('day', '$lte', toDate);
        filterFactory.addFilter('metricName', '$eq', chartType.metricName);
        filterFactory.addFilter('valueName', '$eq', chartType.valueName);
        var sortConfig = {
            sortField:'day',
            sortType:1
        };
        var url = urlFactory.getDocs(appConstants.collectionNames.newRelicDaily, filterFactory.getFilters(),sortConfig);
        restService.post(url, callback);
    };
    
    var _getChartDataMonthly = function(chartType,callback){
        filterFactory.reset();
        filterFactory.addFilter('metricName', '$eq', chartType.metricName);
        filterFactory.addFilter('valueName', '$eq', chartType.valueName);
        var sortConfig = {
            sortField:'yearMonth',
            sortType:1
        };
        var url = urlFactory.getDocs(appConstants.collectionNames.newRelicMonthly, filterFactory.getFilters(),sortConfig);
        restService.post(url, callback);
    };
    
    return {
        getGL: function (md5,callback) {
            return _getParsedDocuments(appConstants.collectionNames.GL,md5,callback);
        },
        getFacebook: function (md5,callback) {
            return _getParsedDocuments(appConstants.collectionNames.FacebookProduct,md5,callback);
        },
        getOS: function (md5,callback) {
            return _getParsedDocuments(appConstants.collectionNames.OS,md5,callback);
        },
        getChartDataDaily: function (chartType,fromDate,toDate,callback) {
            return _getChartDataDaily(chartType,fromDate,toDate,callback);
        },
        getChartDataMonthly: function (chartType,callback) {
            return _getChartDataMonthly(chartType,callback);
        }
    }
}();