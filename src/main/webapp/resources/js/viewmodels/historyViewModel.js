
var historyViewModel = function () {

    var _that = this;

    this.types = [
        {name: "GL"},
        {name: "OS"},
        {name: "FacebookProduct"}
    ];

    this.filters = {
        name: ko.observable(''),
        type: ko.observable('')
    };
    
    this.dateFrom = ko.observable(moment().add(-365, 'day'));
    this.dateTo = ko.observable(moment());

    this.resetFilters = function () {
        _that.filters.name('');
        _that.filters.type(null);
        _that.dateFrom(moment().add(-365, 'day'));
        _that.dateTo(moment());
        _that.filteredSearch();
    }

    this.filteredSearch = function () {
        var fileName = _that.filters.name();
        var fileType;
        if (_that.filters.type()) {
            fileType = _that.filters.type().name;
        }

        filterFactory.reset();
        if (fileName) {
            filterFactory.addFilter("fileName", "$eq", fileName);
        }
        if (fileType) {
            filterFactory.addFilter("type", "$eq", fileType);
        }
        var dateFrom = _that.dateFrom()
        if (dateFrom) {
            dateFrom.setHours(0, 0, 0, 0);
        }
        var dateTo = _that.dateTo()
        if (dateTo) {
            dateTo.setHours(23, 59, 59, 0);
        }
        var dateFromS = utilsService.toBackEndDataFrom(dateFrom);
        var dateToS = utilsService.toBackEndDataTo(dateTo);
        filterFactory.addFilter('date', '$gte', dateFromS);
        filterFactory.addFilter('date', '$lte', dateToS);

        historyController.search();
    }
};