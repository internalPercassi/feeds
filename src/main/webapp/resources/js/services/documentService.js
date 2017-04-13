'use strict';

var documentService = function () {
    var _getParsedDocuments = function(collectionName,md5,callback){
        filterFactory.reset();
        filterFactory.addFilter('md5', '$eq', md5);
        var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
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
    }
}();