(function($) {

  var app = $.sammy('#app', function() {
	  
    this.use('Template');

    this.get('#/', function(context) {


    });
    
    
    this.get('#/GL/:id', function(context) {
  	  
    	context.app.swap('');
    	context.load('/PerParserSPA/resources/views/pages/gl.template')
    	
    	.then(function(response){
    		
    		
	        var $container = $('#app'),
            $view = $container.find('.view'),
            $newView = $('<div>').addClass('view').html(response);
	        
	        
	        if ($view.length) {
	            ko.removeNode($view[0]); // Clean up previous view
	        }
	      
	        $container.append($newView);
	        tableFactory.showDocs('GL');
	        ko.applyBindings(null, $newView[0]);
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
    
    this.get('#/newRelic', function(context) {
  	  
    	context.app.swap('');
    	context.load('/PerParserSPA/resources/views/pages/newRelic.template')
    	.appendTo(context.$element())
    	.then(function(){
    		
    	});      
    	   
      });

    
    

    this.before('.*', function() {

        var hash = document.location.hash;
        $("nav").find("a").removeClass("current");
        $("nav").find("a[href='"+hash+"']").addClass("current");
   });

  });

  $(document).ready(function() {
    app.run('#/');
  });

})(jQuery);