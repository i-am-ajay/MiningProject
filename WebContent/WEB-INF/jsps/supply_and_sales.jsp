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
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css" />
</head>
<body class=mt-1>
	<div class="px-2 pb-2 m-auto">
		<h4 class="border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5" id="form_title">Supply And Sales</h4>
		<f:form method="POST" modelAttribute="supply" action="save_supply" id="sales_form">
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
					      		<f:input id="vehicle_type" class="form-control form-control-sm" placeholder="Vehicle Type" path="vehicle.vehicleType" />
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
					      		<f:select class="form-control form-control-sm" id="material_type" placeholder="Enter Material Type" path="material" >
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
					      		<label class="font-weight-bold">Quantity</label>
					      		<f:select class="form-control form-control-sm" id="quantity" placeholder="Enter Tyre Type" path="quantity" >
					      			<c:forEach var="item" items="${quantity_lookup}">
					      				<f:option value="${item.value}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    </div>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Payment Type</label>
					      		<f:select class="form-control form-control-sm" id="payment_type" placeholder="Choose Payment" path="paymentType" >
					      			<f:option value="cash">Cash</f:option>
					      			<f:option value="credit">Credit</f:option>
					      		</f:select>
					    	</div>
					    </div>
				  		<div class="col-2">
				  			<div class="form-group">
					    		<label class="font-weight-bold">Rate</label>
					     		<f:input id="rate" class="form-control form-control-sm" placeholder="Rate" path="rate" />
			  				</div>
			  			</div>
			  			<div class="col-2">
			  				<div class="form-group">
					    		<label class="font-weight-bold">Discount</label>
					     		<f:input id="discount" class="form-control form-control-sm" placeholder="Discount" path="discount" />
			  				</div>
			  			</div>
			  			<div class="col-2">
			  				<div class="form-group">
					    		<label class="font-weight-bold">Final Rate</label>
					     		<f:input id="final_rate" class="form-control form-control-sm" placeholder="Final Rate" path="finalRate"/>
			  				</div>
			  			</div>
			  			<div class="col-2">
			  				
			  			</div>
				  </div>
				  <div class="row">
				  	<div class="col-2">
				  		<div class="form-group">
		
					    		<label class="font-weight-bold form-check-label">Non Royalty</label>
					     		<input type="checkbox" id="nrl" class="form-check-input mx-4 mt-2" value=true/>
					     		<f:input type="hidden" id ="royalty_save" path="nrl" />
					     		<input type="hidden" id="royalty" value="${parameter.royalty}" />
			  			</div>
				  	</div>
				  	<div class="col-2">
				  		<div class="form-group">
					    		<label class="font-weight-bold form-check-label">Driver Return</label>
					     		<input type="checkbox" id="driver_return" class="form-check-input mx-4 mt-2" value=true/>
					     		<f:input type="hidden" id="driver_return_save" path = "driverReturn" />
					     		<input type="hidden" id="hidden_driver_return" value="${parameter.driverReturn}" />
			  			</div>
				  	</div> 
				  	<div class="col-2"></div>
				  	<div class="col-4">
				  		<input type="submit" class="btn btn-sm btn-secondary btn-block mx-auto" value="Save"/> 
				  	</div>
				  </div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="hidden" id="role" value="${role}" />
	</f:form>
	</div>
	<div id="table_section">
		<table id="data_table" class="table table-striped table-sm display mx-auto" style="width:95%; font-size:15px;">
        <thead class="thead-dark">
            <tr>
            	<th>Token No</th>
                <th>Driver Name</th>
                <th>Driver Contact</th>
                <th>Vehicle No</th>
                <th>Vehicle Type</th>
                <th>Vehicle Tyre</th>
                <th>Material</th>
                <th>Quantity</th>
                <th>Payment Type</th>
                <th>Rate</th>
                <th>Discount</th>
                <th>Final Rate</th>
                <th>NRL</th>
                <th>Driver Return</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach var="item" items="${data_list}">
	            <tr>
	            	<td>${item.token}</td>
	                <td>${item.driverName}</td>
	                <td>${item.driverNumber}</td>
	                <td>${item.vehicle.vehicleNo}</td>
	                <td>${item.vehicle.vehicleType}</td>
	                <td>${item.vehicle.tyreType}</td>
	                <td>${item.material }</td>
	                <td>${item.quantity}</td>
	                <td>${item.paymentType }</td>
	                <td>${item.rate}</td>
	                <td>${item.discount}</td>
	                <td>${item.finalRate}</td>
	                <td>${item.nrl}</td>
	                <td>${item.driverReturn}</td>
	                <td>${item.salesDate}</td>
	            </tr>
            </c:forEach>
        </tbody>
        </table>
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
				$("#driver_name").attr("required","true");
				$("#driver_number").attr("required","true");
				$("#address").attr("required","true");
				$("#final_rate").attr("readonly",true);
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
				url : "${home}fetch_vehicle",
				data : {"vehicle_no":this.value},
				success: function(result, status, xhr){
					if(result != null && result != ""){
						let json = JSON.parse(result);
						$("#vehicle_type").val(json['vehicle_type']);
						$("#tyre_type").val(json['tyre_type']);
						$("#discount").val(json['discount']);
						
						$("#vehicle_type").attr("readonly",true);
						$("#tyre_type").attr("readonly",true);

					}
					else{
						alert("This vehicle is not registered.");
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

	// data table script
		$(document).ready(function() {
    	$('#data_table').DataTable( {
        	"scrollY":        "200px",
        	"scrollCollapse": true,
        	"paging":         false
    	} );
	} );
	
	// driver return logic. If discount enabled then driver return is 0. If there is no discount driver 
	// driver return is given.
	
	// find rate of vehicle.
	var driverReturnRemoved = false;
	
	$("#driver_return").click(e =>{
		if($("#driver_return").prop("checked")){
			driverReturnRemoved = false;
		}
		else{
			driverReturnRemoved = true;
		}
		console.log(driverReturnRemoved);
	});
	
	$("#rate").focusout(e =>{
		// make sure all the required fields are populated. 
		let vehicleType = $("#vehicle_type").val();
		let tyreType = $("#tyre_type").val();	
		if(!(vehicleType) || !(tyreType)){
			alert("Please select a Vehicle.");
			return 1; 
		}
		if($("#discount").val() != 0){
			$("#driver_return").attr("disabled",true);
			$("#driver_return_save").val(0.0);
		}
		else{
			if(driverReturnRemoved){
				$("#driver_return").prop("checked",false);
				$("#driver_return").attr("disabled",false);
				$("#driver_return_save").val(0.0);
			}
			else{
				$("#driver_return").prop("checked",true);
				$("#driver_return").attr("disabled",false);
				$("#driver_return_save").val($("#hidden_driver_return").val());
			}
		}
		// fetch rate of vehicle.
		$.ajax({
			url : "${home}get_rate",
			data: {"tyre_type":tyreType,
					"vehicle_type": vehicleType,
					"material_type":$("#material_type").val(),
					"quantity" : $("#quantity").val()
			},
			success: function(result, status, xhr){
				if(result != null || result != ''){
					result = JSON.parse(result);
					let rate = result['rate'];
					let discount = $("#discount").val();
					// check if nrl is selected.
					if($("#nrl").prop("checked")){
						rate = rate - $("#royalty").val();
						$("#royalty_save").val($("#royalty").val());
					}
					else{
						$("#royalty_save").val(0.0);
					}
					
					$("#rate").val(rate || '0.0');
					
					if(rate <= 0){
						alert("Rate can not be 0 or -ve. Kindly select your parameters again.");
						$("#sales_form").trigger("reset");
						return 1;
					}
					
					let finalRate = rate - discount;
					if(finalRate < 0){
						alert("Final Rate can't be negative, Kindly select your parameters again.");
						$("#sales_form").trigger("reset");
						return 1;
					}
					$("#final_rate").val(finalRate);
				}
			}
		});
	});
	</script>
</body>
</html>