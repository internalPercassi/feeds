'use strict';

var documentService = function () {
    var _getParsedDocuments = function(collectionName,md5,callback){
        filterFactory.reset();
        filterFactory.addFilter('md5', '$eq', md5);
        var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
        restService.post(url, callback);

    };

    var _getPageViewMillions = function(fromDate,toDate,callback){
        filterFactory.reset();
        filterFactory.addFilter('day', '$gt', fromDate);
        filterFactory.addFilter('day', '$lt', toDate);
        filterFactory.addFilter('metricName', '$eq', 'EndUser');
        filterFactory.addFilter('metricValue', '$eq', 'callCount');
        var url = urlFactory.getDocs(appConstants.collectionNames.newRelic, filterFactory.getFilters());
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
        getPageViewMillions: function (fromDate,toDate,callback) {
            return _getPageViewMillions(fromDate,toDate,callback);
        }
    }
}();