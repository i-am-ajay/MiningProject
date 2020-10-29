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
<!-- 
	1) There are three new rate options bucket, ton, foot which has different logic of rate calcuation.
	2) For these quantities rate based on number of bucket,ton or foot and material type.
	3) When user select any of these quantities, user has to enter a number too, rate calculation will be 
		based on these quantities. 
	
 -->
<body class="mt-1 p-1 m-1">
	<div class="main-section mx-auto">
	<!-- <div class="section-1 my-3 mb-5 mx-3 border p-2">
		<div class="header mb-2 row">
			<div class="col-2"></div>
			<div class="col-8 text-center border-bottom border-dark w-50"><h4>Mining Token</h4></div>
			<div class="col-2">Date: <span>2020-10-20</span></div>
		</div>
		<div class="row mb-3">
			<div class="col-2 font-weight-bold">Token No: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Driver Name: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Driver Contact: <span class="pl-2 font-weight-normal">768976854</span></div>
			<div class="col-2 font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col-2 font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div>
		</div>
		<div class="row">
			<div class="col-2 font-weight-bold">Vehicle Type: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Material Type: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Quantity: <span class="pl-2 font-weight-normal">768976854</span></div>
			<div class="col font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div>
		</div>
	</div>
	
	<div class="section-2 my-3 mb-5 mx-3 border p-2">
		<div class="header mb-2 row">
			<div class="col-2"></div>
			<div class="col-8 text-center border-bottom border-dark w-50"><h4>Mining Token</h4></div>
			<div class="col-2">Date: <span>2020-10-20</span></div>
		</div>
		<div class="row mb-3">
			<div class="col-2 font-weight-bold">Token No: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Driver Name: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Driver Contact: <span class="pl-2 font-weight-normal">768976854</span></div>
			<div class="col-2 font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col-2 font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div>
		</div>
		<div class="row">
			<div class="col-2 font-weight-bold">Vehicle Type: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Material Type: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Quantity: <span class="pl-2 font-weight-normal">768976854</span></div>
			<div class="col font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div>
		</div>
	</div> -->
	
	<div class="section-3 my-3 mb-5 border p-1">
		<div class="row" style="font-size: .90em;">
		<div class="col-6">
			<div class="header mb-2 row">
			<div class="col-6 text-left border-bottom border-dark w-50"><h4>Mining Token</h4></div>
			<div class="col-4">Date: <span>2020-10-20</span></div>
		</div>
		<div class="row mb-3">
			<div class="col-2 font-weight-bold">Token No: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Driver Name: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Driver Contact: <span class="pl-2 font-weight-normal">768976854</span></div>
			<div class="col-2 font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col-2 font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div>
		</div>
		<div class="row">
			<div class="col-2 font-weight-bold">Vehicle Type: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Material Type: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Quantity: <span class="pl-2 font-weight-normal">768976854</span></div>
			<!-- <div class="col font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div> -->
		</div>
		
		</div>
		<div class="col-6">
			<div class="header mb-2 row">
			<div class="col-6 text-left border-bottom border-dark w-50"><h4>Mining Token</h4></div>
			<div class="col-4">Date: <span>2020-10-20</span></div>
		</div>
		<div class="row mb-3">
			<div class="col-2 font-weight-bold">Token No: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Driver Name: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Driver Contact: <span class="pl-2 font-weight-normal">768976854</span></div>
			<div class="col-2 font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col-2 font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div>
		</div>
		<div class="row">
			<div class="col-2 font-weight-bold">Vehicle Type: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Material Type: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Quantity: <span class="pl-2 font-weight-normal">768976854</span></div>
			<!-- <div class="col font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div> -->
		</div>
		</div>
		<div class="col mt-5">
		<div class="header mb-2 row">
			<div class="col-7 text-left border-bottom border-dark w-50"><h4>Mining Token <span style="font-size:.5em;">(Office Copy)</span></h4></div>
			<div class="col-4">Date: <span>2020-10-20</span></div>
		</div>
		<div class="row mb-3 p-1">
			<div class="col-2 font-weight-bold">Token No: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Driver Name: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Driver Contact: <span class="pl-2 font-weight-normal">768976854</span></div>
			<div class="col-2 font-weight-bold">Vehicle No: <span class="pl-2 font-weight-normal">MH-1222</span></div>
			<div class="col-2 font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div>
		</div>
		<div class="row p-1">
			<div class="col-2 font-weight-bold">Vehicle Type: <span class="pl-2 font-weight-normal">OCT-1</span></div>
			<div class="col-2 font-weight-bold">Material Type: <span class="pl-2 font-weight-normal">Shyam</span></div>
			<div class="col-2 font-weight-bold">Quantity: <span class="pl-2 font-weight-normal">768976854</span></div>
			<div class="col-2 font-weight-bold">Rate: <span class="pl-2 font-weight-normal">20000</span></div>
			<!-- <div class="col font-weight-bold">Vehicle Tyre: <span class="pl-2 font-weight-normal">12</span></div> -->
		</div>
		</div>
		</div>
	</div>
	</div>
</body>
</html>