(function($) {

  var app = $.sammy('#app', function() {
	  
    this.use('Template');

    this.get('#/', function(context) {
      context.app.swap('');
      $.each(this.items, function(i, item) {
        context.render('../templates/article.template', {id: i, item: item})
               .appendTo(context.$element());
      });
    });
    
    this.get('#/history/', function(context) {
    	
    	var script = document.createElement('script');
    	script.type = 'application/javascript';
    	script.src = '/PerParserSPA/resources/js/controllers/historyController.js';
    	document.head.appendChild(script);
    	
        var str=location.href.toLowerCase();
        context.app.swap('');
        context.render('/PerParserSPA/resources/views/pages/history.template', {})
               .appendTo(context.$element());
    });

    this.get('#/article/:id', function(context) {
      this.item = this.items[this.params['id']];
      if (!this.item) { return this.notFound(); }
      this.partial('templates/article-detail.template');
    });


    this.before('.*', function() {

        var hash = document.location.hash;
        $("nav").find("a").removeClass("current");
        $("nav").find("a[href='"+hash+"']").addClass("current");
   });

  });

  $(function() {
    app.run('#/');
  });

})(jQuery);