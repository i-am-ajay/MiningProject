<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Fuel</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >

</head>
<!-- 
	* Add Fuel to the system from this screen.
	* On top a label will show total fuel available.
 -->
<body class=mt-1>

		<div class="container p-2 m-auto">
		<div class="row">
			<h4 class="border-bottom border-danger m-3 pb-2 display-4 col-8" id="form_title">Add Fuel</h4>
			<div class="col-2 align-right ml-auto pl-5 mt-4 mr-5"><i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm mt-4" style="font-size: .6em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<!-- Success msg -->
		<c:if test="${status.equalsIgnoreCase('success')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Fuel Stock Up.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
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
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>There is some error in Fuel entry.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		
		<f:form method="POST" modelAttribute="fuel_dist_obj" action="save_fuel_record">
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
					      		<label class="font-weight-bold">Fuel On Hand</label>
					      		<input class="form-control form-control-sm not_eligible" id="total_qty" value="${total_qty}" readonly/>
					      		<input type="hidden" name="page" value="add_fuel" />
					      		<input type="hidden" id="original_qty" value="${total_qty}" />
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Choose Date</label>
					      		<f:input type="datetime-local" class="form-control form-control-sm not_eligible" id="entry_date" path="entryDate" required="true"/>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Entry Type</label>
					      		<f:select class="form-control form-control-sm" id="entry_type" placeholder="Enter Material Type" path="entryType" >
					      			<c:forEach var="item" items="${entry_type}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Fuel Quantity (Ltrs)</label>
					      		<f:input type="number" step=".01" class="form-control form-control-sm not_eligible" id="fuel_qty" path="fuelQty" />
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Purchase Type</label>
					      		<f:select class="form-control form-control-sm" id="purchase_type" placeholder="Enter Purchase Type" path="purchaseType" required="true">
					      			<f:option value=""></f:option>
					      			<f:option value="Cash">Cash</f:option>
					      			<f:option value="Credit">Credit</f:option>
					      		</f:select>
					    	</div>
					    	<div class="form-group">
					    		<label class="font-weight-bold">Rate</label>
					    		<f:input type="number" step=".01" class="form-control form-control-sm" id="fuel_rate" path="rate"/>
					    	</div>
					    	<div class="form-group">
					    		<label class="font-weight-bold">Amount</label>
					    		<f:input type="number" step=".01" class="form-control form-control-sm" id="fuel_amount" path="amount" readonly="true"/>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Remarks (If Any)</label>
					      		<f:input class="form-control form-control-sm not_eligible" placeholder="Remarks If Any" id="entry_remark" path="remarks" />
					    	</div>
			  			</div>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-50 mx-auto" id="submit_btn" value="Update"/>
	<input type="hidden" id="role" value="${role}" />
	</f:form>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/utility.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
		$(document).ready(e => {
			$("#home_icon").hover( e => {
				$("#home_icon").css({"cursor":"pointer"})
			});

			$("#home_icon").click( e =>{
				window.location.href = "fuel_panel";
			});
			$("#logout").hide();
			}
		);

		$(document).ready(e =>{
			$("#entry_type").find('option[value="Fuel Received"]').attr("selected",true);
		});

		$(document).ready(e =>{
			$("#fuel_rate").focusout(e => {
				let amount = 0.0;
				let rate = $("#fuel_rate").val();
				let qty = $("#fuel_qty").val();
				let entryType = $("#entry_type").val();
				amount = rate * qty;
				if(entryType == 'Fuel Given'){
					amount = amount * -1;
				}
				$("#fuel_amount").val(amount);
			});
			let now = new Date();
			now.setMinutes(now.getMinutes()-now.getTimezoneOffset())
			console.log(now.toISOString());
			now = now.toISOString().substring(0,16);
			console.log(now);
			$("#entry_date").val(now);

			$("#fuel_qty").focusout(e =>{
				let fuelQty = parseInt($("#fuel_qty").val());
				let totalFuel = parseInt($("#total_qty").val());
				let entryType = $("#entry_type").val();
				if(entryType == 'Fuel Given'){
					fuelQty = fuelQty * -1;
				}
				totalFuel = parseInt(totalFuel);
				totalFuel = totalFuel + fuelQty;
				$("#total_qty").val(totalFuel);
			})
		})
		
		$("#entry_type").change(e =>{
			$("#fuel_rate").val(0.0);
			$("#fuel_qty").val(0.0);
			$("#fuel_amount").val();
			$("#purchase_type option[value='']").attr("selected",true);
			$("#total_qty").val($("#original_qty").val());
			$("#fuel_amount").val(0.0);
		});
		
	</script>
</body>
</html>