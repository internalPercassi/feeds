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
            {"title": "md5",mongoName:"md5" },
            {"title": "Unique Product Code",mongoName:"uniqueProductCode"},
            {"title": "Stocked Qty",mongoName:"stockedQty"},
            {"title": "Booked Qty",mongoName:"bookedQty"},
            {"title": "Accounting State",mongoName:"accountingState"},
        ]
    };

    var _getCSV = function () {
        tableFactory.downloadCsv(collectionName);
    };

    var _getExcel = function () {
        tableFactory.downloadExcel(collectionName);
    };
    
    var _setViewModel = function (vm) {
        _that.vm = vm;
    };

    var _loadDataTable = function (md5, viewModel) {
        _setViewModel(viewModel);
        if (glTable) {
            glTable.destroy();
            glTable = undefined;
            $(selectorId).empty();
        }
        glTable = tableFactory.loadTable(selectorId,tableOptions, appConstants.collectionNames.GL,md5);
    };
    return {
        setViewModel: function (vm) {
            _setViewModel(vm);
        },
        getCSV: function () {
            _getCSV();
        },
        getExcel: function () {
            _getExcel();
        },
        loadDataTable: function (md5, viewModel) {
            _loadDataTable(md5, viewModel);
        }
    }
}($);

