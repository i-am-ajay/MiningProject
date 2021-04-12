// disable buttons on to prevent double entry.

/*$('.disable_button').click(function(event){
	$(this).prop("disabled",true);
});*/

$('form').submit(function (event) {
	let result = confirm("Do you want to save?");
	if(!result){
		event.preventDefault();
	}
    //$('.diabled_button').attr('disabled','disabled');
});