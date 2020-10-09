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
		<h4 class="border-bottom border-danger mx-3 pb-2 display-4" id="form_title">Vehicle Creation</h4>
		
		<c:if test="${status.equalsIgnoreCase('success')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Vehicle Created Successfully.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('exists')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>Vehicle already exists.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('fails')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>There is some error in Vehicle creation.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		
		<c:if test="${status.equalsIgnoreCase('updated')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Vehicle Details Updated</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		
		<f:form method="POST" modelAttribute="vehicle" action="save_vehicle">
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <div class="row pt-2">
		   <div class="col">
		   <div class="card text-dark bg-light px-3 mb-3 w-50 mx-auto" >
  				<div class="card-body">
  					<div class="row">
  						<div class="col">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Vehicle No</label>
					      		<f:input id="vehicle_no" class="form-control form-control-sm" placeholder="Enter Vehicle No" path="vehicleNo" />
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Vehicle Type</label>
					      		<f:select class="form-control form-control-sm" id="type" placeholder="Enter Vehicle Type" path="vehicleType">
					      			<c:forEach var="item" items="${vehicle_lookup}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Tyre Type</label>
					      		<f:select class="form-control form-control-sm" id="tyre_type" placeholder="Enter Tyre Type" path="tyreType" >
					      			<c:forEach var="item" items="${tyre_lookup}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    	<div class="">
					    		<div><label class="font-weight-bold">Vehicle Belongs To</label></div>
					    		<div class="form-check form-check-inline">
					    			<input type="radio" class="form-check-input belong" value="1" id="sbelongs" name="belongsTo"/><label class="form-check-label">Self</label></div>
					    		<div class="form-check form-check-inline">
					    			<input type="radio" class="form-check-input belong" value="2" id="obelongs" name="belongsTo"/><label class="form-check-label">Owner</label></div>
					    		<div class="form-check form-check-inline">
					    			<input type="radio" class="form-check-input belong" value="3" id="cbelongs" name="belongsTo"/><label class="form-check-label">Contractor</label></div>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Vehicle Owner/Contractor</label>
					      		</div>
					      		<select id="client" class="form-control form-control-sm" name="client_name">
					      		</select>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Discount</label>
					      		</div>
					      		<f:input id="discount" class="form-control form-control-sm" placeholder="Enter Discount Amount" path="discount" />
					    	</div>
			  			</div>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-50 mx-auto" value="Save/Update Vehicle"/>
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
		$('#vehicle_no').focusout(function(){
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