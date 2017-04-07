(function($) {

  var app = $.sammy('#app', function() {
	  
    this.use('Template');

    this.get('#/', function(context) {


    });
    
    
    this.get('#/GL/:id', function(context) {
  	  
    	context.app.swap('');
    	context.load('/PerParserSPA/resources/views/pages/gl.template')
    	.appendTo(context.$element())
    	.then(function(){
    		tableFactory.showDocs('GL');
    	});      
    	   
      });
    
    this.get('#/OS/:id', function(context) {
    	  
    	context.app.swap('');
    	context.load('/PerParserSPA/resources/views/pages/os.template')
    	.appendTo(context.$element())
    	.then(function(){
    		tableFactory.showDocs('OS');
    	});      
    	   
      });

/*
    this.before('.*', function() {

        var hash = document.location.hash;
        $("nav").find("a").removeClass("current");
        $("nav").find("a[href='"+hash+"']").addClass("current");
   });*/

  });

  $(document).ready(function() {
    app.run('#/');
  });

})(jQuery);