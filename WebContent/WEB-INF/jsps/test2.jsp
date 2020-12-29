<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Analysis</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >

</head>
<body class=mt-1>
	<input type="text" id="test" />
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
		$(document).ready(
			function(){
				$("#vehicle_no").attr("required","true");
				$("#type").attr("required","true");
				$(".belong").attr("required","true");
			});
		$(document).ready(e => {
			$("#home_icon").hover( e => {
				$("#home_icon").css({"cursor":"pointer"})
			});

			$("#home_icon").click( e =>{
				window.location.href = "admin_panel";
			});
			$("#logout").hide();
			}
		);
	
		// check if vehicle already exists if exists pull the values.
		$('#test').focusout(function(){
			$.ajax({
				type: "POST",
				url : "${home}check_vehicle_duplicacy",
				data : {"vehicle_no":this.value},
				success: function(result, status, xhr){
					if(result != null && result != ""){
						let json = JSON.parse(result);
						/* if(json['vehicleStatus'] == "1"){
							$("#vehicle_no").val(null);
							//alert("Vehicle already registered.");
						} */
						console.log(json['vehicle_no']);
						$('vehicle_no').val(json['vehicle_no']);
						$('#vehicle_no').prop('readOnly',true);
						$('#discount').val(json['discount']);

						// set belongs to checkbox.
						let belongsTo = json['belongs_to'];
						if(belongsTo == 1){
							$("#sbelongs").prop("checked",true);
						}
						else if(belongsTo == 2){
							$("#obelongs").prop("checked",true);
						}
						else if(belongsTo == 3){
							$("#cbelongs").prop("checked",true);
						}

						// set tyre type select box
						$('#tyre_type').find('option[value="'+json["tyre_type"]+'"]').attr("selected",'selected');

						// set gadi type select box
						$('#type').find('option[value="'+json["vehicle_type"]+'"]').attr("selected",'selected');

						getClientList(json['client']);
						console.log(json['client']);
						$('#client').find('option[value='+json["client"]+']').attr("selected",'selected');
						// set owner / contractor
					}
				},
				error : function(result,status,xhr){
					console.log("error");
				}
			});

		});
		
		$('input[type="radio"][name="belongsTo"]').change(getClientList);

		function getClientList(val){
			$("#client").attr("disabled",false);
			$.ajax({
				type: "POST",
				url : "${home}client_list",
				data : {"client_id":this.value ? this.value : $("input[name='belongsTo']:checked").val()},
				success: function(result, status, xhr){
					$("#client").empty();
					let client_id = null;
					if(result != null && result != ""){
						let json = JSON.parse(result);
						let array = Object.keys(json);
						array.forEach(e=>{
							if(val == e){
								$("#client")
								.append("<option value="+e+" selected>"+json[e]+"</option>");
							}
							$("#client")
							.append("<option value="+e+">"+json[e]+"</option>");
						});
						
					}
				},
				error : function(result,status,xhr){
					console.log("error");
				}
			});
		}
	</script>
</body>
</html>