
var facebookViewModel = function () {
    var _that = this;

    this.filters = {
        id: ko.observable('')
    };

    this.resetFilters = function () {
        _that.filters.id('');
        _that.searchFilters();
    }

    this._setFilters = function () {
        var id = _that.filters.id();
        var md5Filter = filterFactory.getFilter("md5");
        filterFactory.reset();
        if (md5Filter) {
            filterFactory.addFilter(md5Filter.field, md5Filter.searchOperator, md5Filter.searchVal);
        }
        if (id) {
            filterFactory.addFilter("id", "$eq", id);
        }
    }

    this.searchFilters = function () {
        _that._setFilters();
        var md5Filter = filterFactory.getFilter("md5");
        facebookController.loadDataTable(md5Filter.searchVal, _that);
    }

    this.getCSV = function () {
        _that._setFilters();
        facebookController.getCSV();
    }
};