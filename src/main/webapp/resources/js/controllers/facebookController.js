var facebookController = function () {
    var _that = {};
    var collectionName = appConstants.collectionNames.FacebookProduct;
    var selectorId = '#facebookTable';
    var facebookTable;
    var tableOptions = {
        pageable: true,
        columnDefs: [
            {
                targets: [0, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27],
                visible: false,
            }
        ],
        columns: [
            {"title": "md5", searchable: false,mongoName:"md5"},
            {"title": "id",mongoName:"id"},
            {"title": "availability",mongoName:"availability"},
            {"title": "Condition",mongoName:"condition"},
            {"title": "description",mongoName:"description"},
            {"title": "imageLink",mongoName:"imageLink"},
            {"title": "link",mongoName:"link"},
            {"title": "Title",mongoName:"title"},
            {"title": "price",mongoName:"price" },
            {"title": "brand",mongoName:"brand"},
            {"title": "additionalImageLink", searchable: false,mongoName:"additionalImageLink"},
            {"title": "ageGroup", searchable: false,mongoName:"ageGroup"},
            {"title": "color", searchable: false,mongoName:"color"},
            {"title": "expirationDate", searchable: false,mongoName:"expirationDate"},
            {"title": "gender", searchable: false,mongoName:"gender"},
            {"title": "itemGroupId", searchable: false,mongoName:"itemGroupId"},
            {"title": "googleProductCategory", searchable: false,mongoName:"googleProductCategory"},
            {"title": "material", searchable: false,mongoName:"material"},
            {"title": "pattern", searchable: false,mongoName:"pattern"},
            {"title": "productType", searchable: false,mongoName:"productType"},
            {"title": "salePrice", searchable: false,mongoName:"salePrice"},
            {"title": "shipping", searchable: false,mongoName:"shipping"},
            {"title": "shippingWeight", searchable: false,mongoName:"shippingWeight"},
            {"title": "customLabel0", searchable: false,mongoName:"customLabel0"},
            {"title": "customLabel1", searchable: false,mongoName:"customLabel1"},
            {"title": "customLabel2", searchable: false,mongoName:"customLabel2"},
            {"title": "customLabel3", searchable: false,mongoName:"customLabel3"},
            {"title": "customLabel4", searchable: false,mongoName:"customLabel4"}
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
        if (facebookTable) {
            facebookTable.destroy();
            facebookTable = undefined;
            $(selectorId).empty();
        }
        facebookTable = tableFactory.loadTable(selectorId,tableOptions, appConstants.collectionNames.FacebookProduct,md5);
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


