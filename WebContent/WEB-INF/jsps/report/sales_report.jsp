<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sales Report</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
	
</head>
<body class=mt-1>
	<div class="p-2 m-auto">
		<div id="search_div" class="container">
		<div class="row">
			<h6 class="border-bottom mx-1 text-muted pb-2 col-8" id="form_title">Sales Report</h6>
			<div class="col-2 align-right ml-auto pl-5 mr-5"><i id="home_icon" class="fa fa-home fa-x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm" style="font-size: .5em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<div class="mt-3">
			<form action="sales_report" method="POST">
			 <div class="">
		  	   <div class="row pt-1">
		   			<div class="col">
		   				<div class="text-dark bg-light px-3 mb-3 mx-auto" style="{transform:scale(0.8);}">
  						<!-- <div class="card-body"> -->
  							<div class="row">
  								<div class="col-md-2">
					    			<div class="form-group">
					      				<label class="font-weight-bold">Vehicle Number</label>
					      				<input type="text" id="vehicle_no" class="form-control form-control-sm" placeholder="Enter Vehicle No" name="vehicle_no" />
					    			</div>
					    		</div>
					     		<div class="col-md-2">
					    			<div class="form-group">
					      				<label class="font-weight-bold">Material</label>
					      				<select class="form-control form-control-sm" id="material_type" name="material" >
						      				<option value=""></option>
						      				<c:forEach var="item" items="${material_lookup}">
						      					<option value="${item.value}">${item.value}</option>
						      				</c:forEach>
					      				</select>
					    			</div>
					    		</div>
					    		<div class="col-md-2">
						    		<div class="form-group">
							      		<label class="font-weight-bold">Quantity</label>
							      		
							      		<select class="form-control form-control-sm" id="quantity" placeholder="Enter Tyre Type" name="quantity" >
							      			<option value=""></option>
							      			<c:forEach var="item" items="${quantity_lookup}">
							      				<option value="${item.value}">${item.value}</option>
							      			</c:forEach>
							      		</select>
							    	</div>
						    	</div>
						    	<div class="col-md-2">
							    	<div class="form-group">
							      		<label class="font-weight-bold">Payment Type</label>
							      		<select class="form-control form-control-sm" id="payment_type" placeholder="Choose Payment" name="payment_type" >
							      			<option value=""></option>
							      			<option value="cash">Cash</option>
							      			<option value="credit">Credit</option>
							      			<option value="bank">Bank</option>
							      		</select>
						    		</div>
					    		</div>
					    		<div class="form-group col-md-2">
				      				<label for="f_date" class="font-weight-bold">From Date</label>
							      	<input type="date" class="form-control form-control-sm" id="fDate" name="f_date"/>
							    </div>	
							    <div class="form-group col-md-2">
							      	<label for="tDate" class="font-weight-bold">To Date</label>
							      	<input type="date" class="form-control form-control-sm" id="tDate" name="t_date" />
							    </div>	
			 				</div>
							<input id="button" type="submit" class=" btn btn-secondary btn-sm btn-block w-50 mt-2 mx-auto text-center" value="search" />
						
						<!-- </div> -->
					</div>
				</div>
			</div>
			</div>
		</form>
		</div>
		</div>
		<h6 class="border-bottom mb-1 text-muted pb-2" id="form_title"></h6>
		<div id="table" class="mx-auto container">
			<table id="example" class="display compact cell-border" style="width:100%">
	        	<thead>
	            	<tr>
		            	<th>Token No</th>
		                <th>Driver Name</th>
		                <th>Driver Contact</th>
		                <th>Vehicle Of</th>
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
		                <th>Status</th>
		                <th>Date</th>
           			 </tr>
	        	</thead>
	        	<tbody>
	        		<c:forEach var="item" items="${salesList}">
		            	<%-- <c:choose>
	        				<c:when test="${item.status == false}">
		            			<tr class="table-danger">
		            		</c:when>
		            		<c:otherwise>
		            			<tr>
		            		</c:otherwise>
		       			 </c:choose> --%>
		       			 	<tr class="${item.status ? '' : 'table-danger'}">
			            		<td>${item.token}</td>
				                <td>${item.driverName}</td>
				                <td>${item.driverNumber}</td>
				                <td>${item.clientName}</td>
				                <td>${item.vehicle.vehicleNo}</td>
				                <td>${item.vehicle.vehicleType}</td>
				                <td>${item.vehicle.tyreType}</td>
				                <td>${item.material }</td>
				                <td>${item.getQuantity()}</td>
				                <td>${item.paymentType }</td>
				                <td>${item.rate}</td>
				                <td>${item.discount}</td>
				                <td>${item.finalRate}</td>
				                <td>${item.nrl}</td>
				                <td>${item.driverReturn}</td>
				                <td>${item.status}</td>
				                <td>${item.salesDate}</td>
		              		</tr>
		             </c:forEach>
	        	</tbody>
    		</table>
		</div>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_max_cdn_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/font_awesome.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/data_table.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.21/af-2.3.5/b-1.6.3/b-html5-1.6.3/datatables.min.js"></script>
	<script>
	var table = null;
		$(document).ready(function(){
			table = $('#example').DataTable({
		        "scrollY": 230,
		        "scrollX" : true,
		        "info" : false,
		        "autoWidth" : false,
		        "dom": 'Bfrtip',
		        "buttons": [
		        	 {extend : 'copyHtml5', className : ' btn btn-sm px-4'}
		        ]
		        });
		})
		.ready(e =>{
			$(".fa").hover(e =>{
				$(".fa").css({"cursor":"pointer"});
			});

			$("#home_icon").click( e =>{
				window.location.href = "report_panel";
			});

			$("#report").hide();
		});
	</script>
</body>
</html>