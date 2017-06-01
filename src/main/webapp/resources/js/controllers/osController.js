var osController = function () {
    var _that = {};
    var collectionName = appConstants.collectionNames.OS;
    var selectorId = '#osTable';
    var osTable;

    var tableOptions = {
        pageable: true,
        columnDefs: [
            {
                targets: [0],
                visible: false,
            }
        ],
        columns: [
            {title: "md5",mongoName:"md5" },
            {title: "Product Code",mongoName:"productCode" },
            {title: "Model Code",mongoName:"modelCode" },
            {title: "Warehouse",mongoName:"warehouse" },
            {title: "Physical Inventory",mongoName:"physicalInventory" },
            {title: "Order Status",mongoName:"orderStatus" },
            {title: "Replenishment Level",mongoName:"replenishmentLevel" },
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
        if (osTable) {
            osTable.destroy();
            osTable = undefined;
            $(selectorId).empty();
        }
        osTable = tableFactory.loadTable(selectorId,tableOptions, appConstants.collectionNames.OS,md5);
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
