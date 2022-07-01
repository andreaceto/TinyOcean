/**
 * 
 */
$(document).ready(function(event) {

	$(".grid-item img").mouseenter(function(event) {
		
		
		$(this).css('transform', 'scale(1.25)')
		
	
	});

	$(".grid-item img").mouseleave(function(event) {
		
		
		$(this).css('transform', 'scale(1.0)')
		
	
	});

	

})









