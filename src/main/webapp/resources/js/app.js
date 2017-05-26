
var app = $.sammy('#app', function () {
    this.use('Template');
    this.get('#/', function (context) {});
    this.vm;
    
    this.get('#/FacebookProduct/:id', function (context) {
        context.app.swap('');
        var md5 = context.params['id'];
        context.load('resources/views/pages/facebook.template')
                .then(function (response) {
                    this.vm = new facebookViewModel();
                    loadView(response, this.vm);
                    filterFactory.reset();
                    setTimeout(function(){facebookController.loadDataTable(md5, this.vm)},200);
                });
    });

    this.get('#/GL/:id', function (context) {
        context.app.swap('');
        var md5 = context.params['id'];
        context.load('resources/views/pages/gl.template')
                .then(function (response) {
                    this.vm = new glViewModel();
                    loadView(response, this.vm);
                    filterFactory.reset();
                    setTimeout(function(){glController.loadDataTable(md5, this.vm)},200);
                });

    });

    this.get('#/OS/:id', function (context) {
        context.app.swap('');
        var md5 = context.params['id'];
        context.load('resources/views/pages/os.template')
                .then(function (response) {
                    this.vm = new osViewModel()
                    loadView(response, this.vm);
                    setTimeout(function(){osController.loadDataTable(md5, this.vm)},200);
                });
    });

    this.get('#/statistics/', function (context) {
        context.app.swap('');
        context.load('resources/views/pages/statistics.template')
                .then(function (response) {
                    this.vm = new statisticsViewModel();
                    statisticsController.setViewModel(this.vm);
                    loadView(response, this.vm);
                    vm.initPage();
                });
    });

    this.get('#/history/', function (context) {
        context.app.swap('');
        context.load('resources/views/pages/history.template')
                .then(function (response) {
                    this.vm = new historyViewModel();
                    historyController.init();
                    historyController.setViewModel(this.vm);
                    loadView(response, this.vm);
                    historyController.showUploadedFiles();
                });
    });

    this.before('.*', function () {
        var hash = document.location.hash;
        $(".nav.nav-sidebar").find("li").removeClass("active");
        $(".nav.nav-sidebar").find("a[href='" + hash + "']").parent().addClass("active");
    });

    var loadView = function (response, viewModel) {
        var $container = $('#app'),
                $view = $container.find('.view'),
                $newView = $('<div>').addClass('view').html(response);

        if ($view.length)
            ko.removeNode($view[0]);

        $container.append($newView);
        ko.applyBindings(viewModel, $newView[0]);
    }
});


$(document).ready(function () {
    app.run('#/statistics/');
});

$.notify({
    // options
    message: 'Hello World'
}, {
    // settings
    type: 'danger'
});