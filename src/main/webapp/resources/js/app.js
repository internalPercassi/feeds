
var app = $.sammy('#app', function () {
    this.use('Template');

    this.get('#/', function (context) {});

    this.get('#/FacebookProduct/:id', function (context) {
        context.app.swap('');
        var md5 = context.params['id'];
        context.load('/PerParserSPA/resources/views/pages/facebook.template')
                .then(function (response) {
                    var vm = new facebookViewModel();
                    facebookController.loadNewGrid(md5, vm);
                    loadView(response, vm);
                });
    });

    this.get('#/GL/:id', function (context) {
        context.app.swap('');
        var md5 = context.params['id'];
        context.load('/PerParserSPA/resources/views/pages/gl.template')
                .then(function (response) {
                    var vm = new glViewModel();
                    glController.loadNewGrid(md5, vm);
                    loadView(response, vm);
                });

    });

    this.get('#/OS/:id', function (context) {
        context.app.swap('');
        var md5 = context.params['id'];
        context.load('/PerParserSPA/resources/views/pages/os.template')
                .then(function (response) {
                    var vm = new osViewModel()
                    osController.loadNewGrid(md5, vm);
                    loadView(response, vm);
                });
    });

    this.get('#/pageViews', function (context) {
        context.app.swap('');
        context.load('/PerParserSPA/resources/views/partials/newRelic/pageViews.template')
                .then(function (response) {
                    var vm = new pageViewsViewModel();
                    pageViewsController.setViewModel(vm);
                    loadView(response, vm);
                });
    });

    this.get('#/loadTime', function (context) {
        context.app.swap('');
        context.load('/PerParserSPA/resources/views/partials/newRelic/loadTime.template')
                .then(function (response) {
                    var vm = new loadTimeViewModel();
                    loadTimeController.setViewModel(vm);
                    loadView(response, vm);
                });
    });

    this.get('#/history/', function (context) {
        context.app.swap('');
        context.load('/PerParserSPA/resources/views/pages/history.template')
                .then(function (response) {
                    var vm = new historyViewModel();
                    historyController.init();
                    historyController.setViewModel(vm);
                    loadView(response, vm);
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
    app.run('#/history/');
});