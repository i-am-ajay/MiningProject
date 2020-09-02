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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body class=mt-1>
	<div class="container p-2 m-auto">
		<h4 class="border-bottom border-danger m-3 pb-2 display-4" id="form_title">Client Creation</h4>
		<f:form method="POST" modelAttribute="client" action="save_client">
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <div class="row pt-2">
		   <div class="col">
		   <div class="card text-dark bg-light px-3 mb-3 w-50 mx-auto" >
  				<div class="card-body">
  					<div class="row">
  						<div class="col-md-6">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Client Name</label>
					      		<f:input class="form-control form-control-sm" id="name" placeholder="Enter Name" path="name"/>
					    	</div>
					    	<div class="form-group">
					      		<label for="testDate" class="font-weight-bold">Client Phone No</label>
					      		<f:input class="form-control form-control-sm" id="phone" placeholder="Mobile Number" path="clientContact" title="Valid Number"/>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Client Address</label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="address" placeholder="Enter Address" path="clientAddress"/>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Client Type</label>
					      		</div>
					      		<select class="form-control form-control-sm" id="type" placeholder="Enter Client Type" name="client_type">
					      			<c:forEach var="item" items="${lookup}">
					      				<option value="${item.key}">${item.value}</option>
					      			</c:forEach>
					      		</select>
					    	</div>
					    	
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Client Discount/Commission</label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="address" placeholder="Enter Discount" path="discount"/>
					    	</div>
			  			</div>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-50 mx-auto" value="Save Client"/>
	<input type="hidden" id="role" value="${role}" />
	</f:form>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
		$(document).ready(
			function(){
				$("#name").attr("required","true");
				$("#phone").attr("required","true");
				$("#address").attr("required","true");
			});
				/* // disable oxygen device.
				let oValue = $("#oSupplementation").val().toLowerCase();
				if(oValue === "nil"){
					$("#o2Device").attr("disabled",true);
					$("#o2Device").val(null);
				}
				$("#oSupplementation").change(
					e =>{
						if($("#oSupplementation").val().toLowerCase() === "nil"){
							$("#o2Device").attr("disabled",true);
							$("#o2Device").val(null);
						}
						else{
							$("#o2Device").attr("disabled",false);
							$("#o2Device").val("Nasal Prongs");
						}
					}
				);

				// ventilator optio
				if( $('input[name="ventilationNeeded"]:checked').val() === 'false'){
					$("#ventilatorMode").attr("disabled",true);
					$("#ventilatorMode").val(null);
				}
				$('input[name="ventilationNeeded"]').change(
					e =>{
						if( $('input[name="ventilationNeeded"]:checked').val() === 'false'){
							$("#ventilatorMode").attr("disabled",true);
							$("#ventilatorMode").val(null);
						}
						else{
							$("#ventilatorMode").attr("disabled",false);
							$("#ventilatorMode").val("NIV");
						}
					}
				);			
			// ventilator optio
			if( $('input[name="changeInTreatment"]:checked').val() === 'false'){
				$("#reason").attr("disabled",true);
				$("#reason").attr("placeholder",null);
			}
			$('input[name="changeInTreatment"]').change(
				e =>{
					if( $('input[name="changeInTreatment"]:checked').val() === 'false'){
						$("#reason").attr("disabled",true);
						$("#reason").attr("placeholder",null);
					}
					else{
						$("#reason").attr("disabled",false);
						$("#reason").attr("placeholder","Reason of Change");
						
					}
				}
			);
		}
			
		) */
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
		
		// Changes the page heading for mobile screen and tablets.
		$(document).ready( e => {
			const screenSize = window.screen.width;
			if(screenSize < 1000){
				$("#middle_col").replaceWith("<div id='middle_col' class='col-8'><h6 class='text-center display-5'>Sir Ganga Ram Hospital</h6><p class='text-center'>Patient Health Report Card</p></div>");
				$("#form_title").removeClass("m-3");	
				//$("#farewell_note").removeClass("display-4").addClass("display-5");
			}
		});
		// on registration focus out get employee details through ajax call 
		$("#registration").focusout( e =>{
			$.ajax({
				type: "POST",
				url : "${home}patient_details",
				data : {"reg_no":$("#registration").val()},
				success: function(result, status, xhr){
					if(result != null && result != ""){
						let json = JSON.parse(result);
						console.log(json.name)
						$("#name").attr("disabled","true");
						$("#phone").attr("disabled","true");
						$("#fgender").attr("disabled","true");
						$("#mgender").attr("disabled","true");

						$("#name").val(json.name);
						$("#phone").val(json.phone);
						$("#reg_no").val(json.reg_no);
						let gender = json.gender;
						if(gender == 'm'){
							$("#mgender").prop("checked",true);
							
						}
						else{
							$("#fgender").prop("checked",true);
						}
					}
					else{
						$("#name").attr("disabled",false);
						$("#phone").attr("disabled",false);
						$("#fgender").attr("disabled",false);
						$("#mgender").attr("disabled",false);
					}
				},
				error : function(result,status,xhr){
					$("#name").attr("disabled","false");
					$("#phone").attr("disabled","false");
					$("#fgender").attr("disabled","false");
					$("#mgender").attr("disabled","false");
				}
			});
		});
	
	</script>
</body>
</html>