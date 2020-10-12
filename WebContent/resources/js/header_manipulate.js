
$(document).ready( e => {
	const screenSize = window.screen.width;
	if(screenSize < 700){
		$("#header_div").replaceWith("<h6 class='text-center display-5'>Sir Ganga Ram Hospital</h6>" +
				"<p class='text-center'>Patient Feedback.</p>");
		$("#form_title").removeClass("m-3");
		
		$("#farewell_note").removeClass("display-4").addClass("display-5");
	}
})
