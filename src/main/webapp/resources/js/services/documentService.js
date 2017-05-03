'use strict';

var documentService = function () {
    var _getParsedDocuments = function(collectionName,md5,callback){
        filterFactory.reset();
        filterFactory.addFilter('md5', '$eq', md5);
        var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
        restService.post(url, callback);

    };

    var _getPageViewMillionsDaily = function(fromDate,toDate,callback){
        filterFactory.reset();
        filterFactory.addFilter('day', '$gt', fromDate);
        filterFactory.addFilter('day', '$lt', toDate);
        filterFactory.addFilter('metricName', '$eq', 'EndUser');
        filterFactory.addFilter('valueName', '$eq', 'callCount');
        var sortConfig = {
            sortField:'day',
            sortType:1
        };
        var url = urlFactory.getDocs(appConstants.collectionNames.newRelicDaily, filterFactory.getFilters(),sortConfig);
        restService.post(url, callback);
    };
    
    var _getPageViewMillionsMonthly = function(callback){
        filterFactory.reset();
        filterFactory.addFilter('metricName', '$eq', 'EndUser');
        filterFactory.addFilter('valueName', '$eq', 'callCount');
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
        getPageViewMillionsDaily: function (fromDate,toDate,callback) {
            return _getPageViewMillionsDaily(fromDate,toDate,callback);
        },
        getPageViewMillionsMonthly: function (callback) {
            return _getPageViewMillionsMonthly(callback);
        }
    }
}();