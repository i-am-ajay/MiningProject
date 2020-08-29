function lookup(lookup_for){
	$.ajax({
		type: "POST",
		url : "${home}lookup_details",
		data : {"reg_no":lookup_for},
		success: function(result, status, xhr){
			console.log("success");
		},
		error : function(result,status,xhr){
			console.log("failure");
		}
	});
}