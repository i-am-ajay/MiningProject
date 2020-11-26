<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Rate</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >

</head>
<!-- 
	For quantities bucket, ton and foot rate is only based on material type. As these are sold in number like
	1 ton, 10 bucket, 200 foot etc.
	
	1) When any of these options checked in system disable all other parameters except quality and save rate.
 -->
<body class=mt-1>

		<div class="container p-2 m-auto">
		<div class="row">
			<h4 class="border-bottom border-danger m-3 pb-2 display-4 col-8" id="form_title">Add New Rate</h4>
			<div class="col-2 align-right ml-auto pl-5 mt-4 mr-5"><i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm mt-4" style="font-size: .6em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<!-- Success msg -->
		<c:if test="${status.equalsIgnoreCase('success')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Rate Added Successfully.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  			</button></div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('exists')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>Rate already exists.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('fails')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>There is some error in Rate creation.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		
		<f:form method="POST" modelAttribute="rate" action="save_rate">
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <div class="row pt-2">
		   <div class="col">
		   <div class="card text-dark bg-light px-3 mb-1 w-50 mx-auto" >
  				<div class="card-body">
  					<div class="row">
  						<div class="col">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Vehicle Type</label>
					      		<f:select class="form-control form-control-sm not_eligible" id="vehicle_type" placeholder="Enter Vehicle Type" path="truckType">
					      			<c:forEach var="item" items="${vehicle_lookup}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Tyre Type</label>
					      		<f:select class="form-control form-control-sm not_eligible" id="tyre_type" placeholder="Enter Tyre Type" path="tyreType" >
					      			<c:forEach var="item" items="${tyre_lookup}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Material Type</label>
					      		<f:select class="form-control form-control-sm" id="material_type" placeholder="Enter Material Type" path="materialType" >
					      			<c:forEach var="item" items="${material_lookup}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Quantity</label>
					      		<f:select class="form-control form-control-sm" id="quantity_type" placeholder="Enter Tyre Type" path="quantity" >
					      			<c:forEach var="item" items="${quantity_lookup}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    	<div class="form-group">
					    		<label class="font-weight-bold">Rate</label>
					      		<f:input id="rate_id" class="form-control form-control-sm" placeholder="Enter Rate" path="rate" />
					    	</div>
			  			</div>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-50 mx-auto" value="Save Rate"/>
	<input type="hidden" id="role" value="${role}" />
	</f:form>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
		$(document).ready(
			function(){
				$("#rate_id").attr("required",true);
				let quantityVal = $("#quantity_type").val();
				quantityVal = quantityVal.toLowerCase();
				if(quantityVal == 'bucket' || quantityVal == 'ton' || quantityVal == 'foot'){
					$(".not_eligible").attr("disabled",true);
				}
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

		// bucket, ton or foot selection will result in disabling fields with not_eligible class.
		$("#quantity_type").change(e =>{
			let quantityVal = $("#quantity_type").val();
			quantityVal = quantityVal.toLowerCase();
			if(quantityVal == 'bucket' || quantityVal == 'ton' || quantityVal == 'foot'){
				$(".not_eligible").attr("disabled",true);
			}
			else{
				$(".not_eligible").attr("disabled",false);
			}
		});
	</script>
</body>
</html>