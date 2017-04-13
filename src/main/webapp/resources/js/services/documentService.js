'use strict';

var documentService = function () {
    var _getGL = function (md5,callback) {
        filterFactory.reset();
        filterFactory.addFilter('md5', '$eq', md5);
        var url = urlFactory.getDocs(appConstants.collectionNames.GL, filterFactory.getFilters());
        restService.post(url, callback);
    };

    return {
        getGL: function (md5,callback) {
            return _getGL(md5,callback);
        },
        getFacebook: function () {
            return _getFacebook();
        },
        getOS: function () {
            return _getOS();
        },
    }
}();