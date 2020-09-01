<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Supply and Sales</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body class=mt-1>
	<div class="px-2 pb-2 m-auto">
		<h4 class="border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5" id="form_title">Supply And Sales</h4>
		<f:form method="POST" modelAttribute="supply" action="save_supply">
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <div class="row pt-1">
		   <div class="col">
		   <div class="text-dark bg-light px-3 mb-3 mx-auto" style="{transform:scale(0.8);}">
  				<div class="card-body">
  					<div class="row">
  						<div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Driver Name</label>
					      		<f:input id="driver_name" class="form-control form-control-sm" placeholder="Enter Driver Name" path="driverName" />
					    	</div>
					    </div>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Driver Number</label>
					      		<f:input id="driver_number" class="form-control form-control-sm" placeholder="Enter Driver Number" path="driverNumber" />
					    	</div>
					    </div>
  						<div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Vehicle Number</label>
					      		<f:input id="vehicle_no" class="form-control form-control-sm" placeholder="Enter Vehicle No" path="vehicle.vehicleNo" />
					    	</div>
					    </div>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Vehicle Type</label>
					      		<f:input id="vehicle_type" class="form-control form-control-sm" placeholder="Tyre Type" path="vehicle.vehicleType" />
					    	</div>
					    </div>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Tyre Type</label>
					      		<f:input id="tyre_type" class="form-control form-control-sm" placeholder="Tyre Type" path="vehicle.tyreType" />
					    	</div>
					    </div>
					     <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Material</label>
					      		<f:select class="form-control form-control-sm" id="type" placeholder="Enter Tyre Type" path="material" >
					      			<c:forEach var="item" items="${material_lookup}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    </div>
				 	</div>
				    <div class="row">
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Payment</label>
					      		<f:input id="tyre_type" class="form-control form-control-sm" placeholder="Vehicle Type" path="tyreType" />
					    	</div>
					    </div>
				  	<div class="col-2">
					    <label class="font-weight-bold">Rate</label>
					     <f:input id="rate" class="form-control form-control-sm" placeholder="Rate" path="rate" />
			  		</div>
			  		<div class="col-2">
					    <label class="font-weight-bold">Discount</label>
					     <f:input id="discount" class="form-control form-control-sm" placeholder="Discount" path="discount" />
			  		</div>
			  		<div class="col-2"></div>
			  		<div class="col-4">
			  		<label class="font-weight-bold"></label>
					    <input type="submit" class="btn btn-small btn-secondary btn-block mx-auto" value="Save Client"/>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
			    
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
				$("#vehicle_no").attr("required","true");
				$("#name").attr("required","true");
				$("#phone").attr("required","true");
				$("#address").attr("required","true");
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
		// check if vehicle already exists
		$('#vehicle_no').focusout(function(){
			$.ajax({
				type: "POST",
				url : "${home}check_vehicle_duplicacy",
				data : {"vehicle_no":this.value},
				success: function(result, status, xhr){
					if(result != null && result != ""){
						let json = JSON.parse(result);
						if(json['vehicleStatus'] == "1"){
							$("#vehicle_no").val(null);
							alert("Vehicle already registered.");
						}
					}
				},
				error : function(result,status,xhr){
					console.log("error");
				}
			});

		});
		
		$('input[type="radio"][name="belongsTo"]').change( function(){
				$("#client").attr("disabled",false);
				$.ajax({
					type: "POST",
					url : "${home}client_list",
					data : {"client_id":this.value},
					success: function(result, status, xhr){
						if(result != null && result != ""){
							$("#client").empty();
							let json = JSON.parse(result);
							let array = Object.keys(json);
							array.forEach(e=>{
								$("#client")
								.append("<option value="+e+">"+json[e]+"</option>");
							});
							
						}
					},
					error : function(result,status,xhr){
						console.log("error");
					}
				});
		});
	</script>
</body>
</html>