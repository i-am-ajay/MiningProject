<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Print Sales Token</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
</head>
<!-- 
	1) There are three new rate options bucket, ton, foot which has different logic of rate calcuation.
	2) For these quantities rate based on number of bucket,ton or foot and material type.
	3) When user select any of these quantities, user has to enter a number too, rate calculation will be 
		based on these quantities. 
	
 -->
<body class="mt-1 p-1 m-1">
	<!-- <div class="main-section mx-auto">-->
	<div class="section-3 my-3 ml-3 mb-5 p-1">
		<div class="row" style="font-size: 1.2em;">
		<div class="col-6">
			<div class="header mb-2 row">
			<div class="col-6 text-left border-bottom border-dark w-50"><h4>Mining Token</h4></div>
			<div class="col-4">Date: <span>${supply.salesDate}</span></div>
		</div>
		<div class="row mb-3">
			<div class="col-3 font-weight-bold px-2">Token No: <span class="font-weight-normal">${supply.token}</span></div>
			<div class="col-3 font-weight-bold px-2">Driver Name: <span class="font-weight-normal">${supply.driverName}</span></div>
			<div class="col-3 font-weight-bold px-2">Driver Contact: <span class="font-weight-normal">${supply.driverNumber}</span></div>
			<div class="col-3 font-weight-bold px-2">Vehicle No: <span class="font-weight-normal">${supply.vehicle.vehicleNo }</span></div>
			
		</div>
		<div class="row">
			<div class="col-3 font-weight-bold px-2">Vehicle Tyre: <span class="font-weight-normal">${supply.vehicle.tyreType}</span></div>
			<div class="col-3 font-weight-bold px-2">Vehicle Type: <span class="font-weight-normal">${supply.vehicle.vehicleType}</span></div>
			<div class="col-3 font-weight-bold px-2">Material Type: <span class="font-weight-normal">${supply.material}</span></div>
			<div class="col-3 font-weight-bold px-2">Quantity: <span class="font-weight-normal">${qty}</span></div>
			<!-- <div class="col font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div> -->
		</div>
		<c:if test="${supply.nrl == 0.0}">
			<div class="row">
				<div class="col-3 font-weight-bold small px-2 p-1">R : <span class="font-weight-normal">Yes</span></div>
			
			<%-- <div class="col-3 font-weight-bold small px-2">Royalty Given: <span class="font-weight-normal">${supply.nrl != 0.0 ? 'Yes':'No'}</span></div> --%>
			</div>
		</c:if>	
		
		</div>
		<div class="col-6 ">
			<div class="header mb-2 row">
			<div class="col-6 text-left border-bottom border-dark w-50"><h4>Mining Token</h4></div>
			<div class="col-4">Date: <span>${supply.salesDate }</span></div>
		</div>
		<div class="row mb-3">
			<div class="col-3 font-weight-bold px-2">Token No: <span class="font-weight-normal">${supply.token}</span></div>
			<div class="col-3 font-weight-bold px-2">Driver Name: <span class="font-weight-normal">${supply.driverName}</span></div>
			<div class="col-3 font-weight-bold px-2">Driver Contact: <span class="font-weight-normal">${supply.driverNumber}</span></div>
			<div class="col-3 font-weight-bold px-2">Vehicle No: <span class="font-weight-normal">${supply.vehicle.vehicleNo}</span></div>
			
		</div>
		<div class="row">
			<div class="col-3 font-weight-bold px-2">Vehicle Tyre: <span class="font-weight-normal">${supply.vehicle.tyreType}</span></div>
			<div class="col-3 font-weight-bold px-2">Vehicle Type: <span class="font-weight-normal">${supply.vehicle.vehicleType}</span></div>
			<div class="col-3 font-weight-bold px-2">Material Type: <span class="font-weight-normal">${supply.material}</span></div>
			<div class="col-3 font-weight-bold px-2">Quantity: <span class="font-weight-normal">${qty}</span></div>
			<!-- <div class="col font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div> -->
		</div>
		<c:if test="${supply.nrl == 0.0}">
			<div class="row">
				<div class="col-3 font-weight-bold small px-2 p-1">R : <span class="font-weight-normal">Yes</span></div>
			
			<%-- <div class="col-3 font-weight-bold small px-2">Royalty Given: <span class="font-weight-normal">${supply.nrl != 0.0 ? 'Yes':'No'}</span></div> --%>
			</div>
		</c:if>	
		</div>
		</div>
		<div class="row">
		<div class="col-6 mt-5" style="font-size: 1.2em;">
		<div class="header mb-2 row">
			<div class="col-7 text-left border-bottom border-dark w-50"><h4>Mining Token <span style="font-size:.5em;">(Office Copy)</span></h4></div>
			<div class="col-4">Date: <span>${supply.salesDate}</span></div>
		</div>
		<div class="row mb-3 p-1">
			<div class="px-2 col-3 font-weight-bold">Token No: <span class="font-weight-normal">${supply.token}</span></div>
			<div class="px-2 col-3 font-weight-bold">Driver Name: <span class="font-weight-normal">${supply.driverName}</span></div>
			<div class="px-2 col-3 font-weight-bold">Driver Contact: <span class="font-weight-normal">${supply.driverNumber}</span></div>
			<div class="px-2 col-3 font-weight-bold">Vehicle No: <span class="font-weight-normal">${supply.vehicle.vehicleNo}</span></div>
		</div>
		<div class="row p-1">
		<div class="px-2 col-3 font-weight-bold">Vehicle Tyre: <span class="font-weight-normal">${supply.vehicle.tyreType}</span></div>
			<div class="px-2 col-3 font-weight-bold p-1">Vehicle Type: <span class="font-weight-normal">${supply.vehicle.vehicleType}</span></div>
			<div class="px-2 col-3 font-weight-bold p-1">Material Type: <span class="font-weight-normal">${supply.material}</span></div>
			<div class="px-2 col-3 font-weight-bold p-1">Quantity: <span class="font-weight-normal">${qty}</span></div>
			<!-- <div class="col ont-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div> -->
		</div>
		<div class="row p-1">
			<div class="px-2 col-3 font-weight-bold p-1">Rate: <span class="font-weight-normal">${supply.finalRate}</span></div>
			<div class="px-2 col-3 font-weight-bold p-1">Vehicle Of:<span class="font-weight-normal">&nbsp;${supply.vehicle.clientId.name}</span></div>
			<c:if test="${supply.nrl == 0.0}">
				<div class="col-3 font-weight-bold small px-2 p-1">R : <span class="font-weight-normal">Yes</span></div>
			</c:if>	
		</div>
		</div>
		</div>
	</div>
	<button id="print_btn" class="btn btn-danger btn-block">Print Token</button>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script>
		/* $(document).ready(function(){
			window.print();
			window.location.href = "print_token";
		}); */
		$("#print_btn").click(e =>{
			$("#print_btn").hide();
			window.print();
			window.location.href ="${pageContext.request.contextPath}/display_sales_page" ;
		});
	</script>
</body>
</html>