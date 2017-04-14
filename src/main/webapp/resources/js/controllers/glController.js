var glController = function () {
    var _that = {};
    var collectionName = 'GL';
    var selectorId = '#glTable';
    var glTable;

    var tableOptions = {
        pageable: true,
        columnDefs: [
            {
                "targets": [0],
                "visible": false,
            }
        ],
        columns: [
            {"title": "md5", },
            {"title": "Unique Product Code"},
            {"title": "Depositor"},
            {"title": "Stocked Qty"},
            {"title": "Booked Qty"},
            {"title": "Accounting State"},
        ]
    };

    var _search = function () {
        var url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
        _loadGlGrid(collectionName, url);
    };

    var _getCSV = function () {
        tableFactory.downloadCsv(collectionName);
    };

    var _resetFilter = function () {
        filterFactory.reset();
    };

    var _loadGlGrid = function (collectionNamePar, url) {
        _that.vm.isLoading(true);

        if (collectionNamePar) {
            collectionName = collectionNamePar;
        }
        if (!url) {
            url = urlFactory.getDocs(collectionName, filterFactory.getFilters());
        }
        var callback = function (res) {
            var tabOpt = jQuery.extend(true, {}, tableOptions);
            tabOpt.data = tableFactory.getRowsForDatatables(res);
            if (glTable) {
                glTable.destroy();
                glTable = undefined;
                $(selectorId).empty();
            }
            glTable = $(selectorId).DataTable(tabOpt);
            _that.vm.isLoading(false);
        }
        restService.post(url, callback);
    };

    var _setViewModel = function (vm) {
        _that.vm = vm;
    };

     var _loadNewGrid = function (md5,viewModel) {
        _setViewModel(viewModel);
        _that.vm.isLoading(true);
        var callback = function (res) {
            var tabOpt = jQuery.extend(true, {}, tableOptions);
            tabOpt.data = tableFactory.getRowsForDatatables(res);
            tabOpt.columns = tableFactory.getColumnsForDatatables(res);
            if (glTable) {
                glTable.destroy();
                glTable = undefined;
                $(selectorId).empty();
            }
            glTable = $(selectorId).DataTable(tabOpt);
            _that.vm.isLoading(false);
        }
        documentService.getGL(md5,callback);
    };
    
    var _init = function () {};

    return {
        init: function () {
            _init();
        },
        setViewModel: function (vm) {
            _setViewModel(vm);
        },
        search: function () {
            _search();
        },
        getCSV: function () {
            _getCSV();
        },
        resetFilter: function () {
            _resetFilter();
            _showDocs();
        },
        loadNewGrid: function (md5,viewModel) {
            _loadNewGrid(md5,viewModel);
        }
    }
}($);

