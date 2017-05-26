'use strict';

var tableFactory = function () {

    var dataArr = [];

    var _loadTable = function (selectorId,tableOptions, collectionName,md5) {
        var url = documentService.getDocumentsURL(collectionName, md5);
        var tabOpt = jQuery.extend(true, {}, tableOptions);
        tabOpt.serverSide = true;
        tabOpt.processing = true;
        tabOpt.paging = true;
        tabOpt.ajax = {
            url: url,
            type: 'POST',
            data: function (d) {
                if (d && d.order && d.order[0]) {
                    d.sortField = tableOptions.columns[d.order[0].column].mongoName;
                    d.sortType = 1;
                    if (d.order[0].dir == 'desc') {
                        d.sortType = -1;
                    }
                }
            },
            dataSrc: function (json) {
                var ret = [];
                var tmpArr = [];
                for (var i = 0; i < json.data.length; i++) {
                    var obj = json.data[i];
                    tmpArr = [];
                    $.each(obj, function (k, v) {
                        tmpArr.push(v);
                    });
                    ret.push(tmpArr);
                }
                json.recordsTotal = json.recordsTotal;
                json.recordsFiltered = json.recordsTotal;
                return ret;
            }
        };
        
        return $(selectorId).DataTable(tabOpt);
    };

    var _getRowsForDatatables = function (jsonData) {
        /*il formato che abbiamo in ingresso è questo
         * {data:[{key1:value1,key2:value2,key2:value2},...],recordsTotal:x,columnNames:['a','b,'c']}
         * in uscita questo */
        dataArr = [];
        $.map(jsonData.data, function (el) {
            var objArr = [];
            $.each(el, function (key, value) {
                if (value && typeof value === 'object') {
                    var keys = Object.keys(value);
                    value = value[keys[0]];
                }
                objArr.push(value);
            });
            dataArr.push(objArr);
        });
        return  dataArr;
    };

    var _downloadCsv = function (collectionName) {
        var url = urlFactory.getCsv(collectionName, filterFactory.getFilters());
        var form$ = $('<form/>').attr("method", "post");
        form$.attr('action', url);
        $(document.body).append(form$);
        form$.submit();
        form$.remove();
    };

    return {
        downloadCsv: function (collectionName) {
            return _downloadCsv(collectionName);
        },
        getRowsForDatatables: function (jsonData) {
            return _getRowsForDatatables(jsonData);
        },
        loadTable: function (selectorId,tableOptions, collectionName,md5) {
            return _loadTable(selectorId,tableOptions, collectionName,md5);
        }
    }
}();