jQuery('document').ready(function(){
	
	 
	
	$('tr').on('dblclick',function(){
					
		getOrder($(this).children('.id').text());		
		
	});
	
	function getOrder(orderId){
        location.href="order?id="+orderId;
	};
});