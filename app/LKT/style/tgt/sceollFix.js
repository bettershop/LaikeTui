$(function(){
	var common = {
		currentMenu: $("#currentMenu").val(),

	    bindEvents: function(bindings) {
	        for (var i in bindings) {
	            if(bindings[i].selector) {
	                $(bindings[i].element)
	                    .on(bindings[i].event, bindings[i].selector, bindings[i].handler);

	            }else{
	                $(bindings[i].element)
	                    .on(bindings[i].event, bindings[i].handler);
	            }
	        }
	    },
		init: function(){
			common.initEvent();
		},
		initEvent: function(){
            var bindings =
        	[
        		{
        			element: window,
        			//selector: '',
        			event: 'scroll',
        			handler: function(event){

	        				common.scrollNav();



        			}
        		}
	        ];
            common.bindEvents(bindings);
            common.scrollNav();
		},

		scrollNav: function(){
				var scrollTop = $(window).scrollTop();
				if(common.currentMenu=="fixTab"){
					if(scrollTop>=391){
						$(".fixTab").css({"position":"fixed","top":"73px","width":"90.3%","z-index":"20"});
						$(".fixTab td").css({"border-bottom":"1px solid #ddd"});
					}else{
						$(".fixTab").css({"width":"100%","position":"static"});
						$(".fixTab td").css({"border-bottom":"none"});
					}
				}
		},
	};
	common.init();	
});
