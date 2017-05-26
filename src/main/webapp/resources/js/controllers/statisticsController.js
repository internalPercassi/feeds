/**
 * 
 */
var statisticsController = function () {
    var _that = this;
    var _setViewModel = function (vm) {
        _that.vm = vm;
    };

    return {
        setViewModel: function (vm) {
            _setViewModel(vm);
        }
    }
}($);
