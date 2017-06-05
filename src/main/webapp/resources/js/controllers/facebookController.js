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
            {"title": "imageLink",mongoName:"image_link"},
            {"title": "link",mongoName:"link"},
            {"title": "Title",mongoName:"title"},
            {"title": "price",mongoName:"price" },
            {"title": "brand",mongoName:"brand"},
            {"title": "additionalImageLink", searchable: false,mongoName:"additional_image_link"},
            {"title": "ageGroup", searchable: false,mongoName:"age_group"},
            {"title": "color", searchable: false,mongoName:"color"},
            {"title": "expirationDate", searchable: false,mongoName:"expiration_date"},
            {"title": "gender", searchable: false,mongoName:"gender"},
            {"title": "itemGroupId", searchable: false,mongoName:"item_group_id"},
            {"title": "googleProductCategory", searchable: false,mongoName:"google_product_category"},
            {"title": "material", searchable: false,mongoName:"material"},
            {"title": "pattern", searchable: false,mongoName:"pattern"},
            {"title": "productType", searchable: false,mongoName:"product_type"},
            {"title": "salePrice", searchable: false,mongoName:"sale_price"},
            {"title": "shipping", searchable: false,mongoName:"shipping"},
            {"title": "shippingWeight", searchable: false,mongoName:"shipping_weight"},
            {"title": "customLabel0", searchable: false,mongoName:"custom_label_0"},
            {"title": "customLabel1", searchable: false,mongoName:"custom_label_1"},
            {"title": "customLabel2", searchable: false,mongoName:"custom_label_2"},
            {"title": "customLabel3", searchable: false,mongoName:"custom_label_3"},
            {"title": "customLabel4", searchable: false,mongoName:"custom_label_4"}
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


