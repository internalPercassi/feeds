'use strict';

var urlFactory = function () {
    var constats = {
        getDocUrl: 'getDocuments',
        getXlsUrl: 'getXls',
        uploadFileUrl: 'parseFile',
        defaultDocsNum: 10
    };
    var docDefUrl = constats.getDocUrl + '?';
    var getXlsUrl = constats.getXlsUrl + '?';
    var _uploadFile = function (fileType) {
        return constats.uploadFileUrl + '?fileType=' + fileType;
    }
    var _getUploadedFiles = function () {
        return docDefUrl + "&collectionName=uploadedFile&start=0&length=5000&sortField=date&sortType=-1";
    };
    var _getDocs = function (collectionName, filters, sortConfig, isCsv, isXls) {
        var url = docDefUrl + "&collectionName=" + collectionName;
        if (isCsv) {
            url = constats.getDocUrl + "?csv=true&collectionName=" + collectionName;
        }
        if (isXls) {
            url = getXlsUrl + "&collectionName=" + collectionName;
        }
        if (filters) {
            var filterstr = JSON.stringify(filters);
            url += ("&filters=" + filterstr);
        }
        if (sortConfig) {
            url += ("&sortField=" + sortConfig.sortField + "&sortType=" + sortConfig.sortType);
        }
        return url
    };

    return {
        uploadFile: function (fileType) {
            return encodeURI(_uploadFile(fileType));
        },
        getUploadedFiles: function () {
            return encodeURI(_getUploadedFiles());
        },
        getDocs: function (collectionName, filters, sortConfig) {
            return  encodeURI(_getDocs(collectionName, filters, sortConfig, false));
        },
        getCsv: function (collectionName, filters) {
            return encodeURI(_getDocs(collectionName, filters, null, true));
        },
        getXls: function (collectionName, filters) {
            return encodeURI(_getDocs(collectionName, filters, null, false, true));
        }
    }
}($);