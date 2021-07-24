<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Daywise Sales Summary</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
</head>
<!-- 
	1) Datewise summary of sales
-->
<body class=mt-1>
	<div class="px-2 pb-2 m-auto" style="width:95%;">
		<div class="row">
			<h4 class=" col-8 border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5" id="form_title">Daywise Sales Report</h4>
			<div class="col-2 align-right ml-auto pl-5 mr-5"><i id="home_icon" class="fa fa-home fa-x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm" style="font-size: .5em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<form method="POST" action="daywise_summary" id="sales_daywise">
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <div class="row pt-1">
		   <div class="col">
		   <div class="text-dark bg-light px-3 mx-auto" style="{transform:scale(0.8);}">
  				<div class="card-body">
  					<div class="row">
					    <div class="col-4">
					    	<div class="form-group">
					    		<label class="font-weight-bold">Sales Start Date</label>
					    		<input class="form-control form-control-sm" type="date" name="s_date" />
					    	</div>
					    </div>
					    <div class="col-4">
					    	<div class="form-group">
					    		<label class="font-weight-bold">Sales End Date</label>
					    		<input class="form-control form-control-sm" type="date" name="e_date" />
					    	</div>
					    </div>
					    <div class="col-4">	    	
						    	<label>&nbsp;</label>
						    	<input type="submit" class="btn btn-sm btn-success btn-block mx-auto" value="submit"/>
					    </div>
				 	</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="hidden" id="role" value="${role}" />
	</form>
	</div>
	<div class="row">
		<div class="col small my-1 mx-4">Sales Summary From : <span class="font-weight-bold px-1">${s_date}</span> to <span class="font-weight-bold px-1">${e_date}</span></div>
	</div>
	<div id="table_section" class="row">
		<div class="col" id="credit_table">
			<table id="data_table" class="table table-striped table-sm display mx-auto col" style="width:95%; font-size:13px;">
		        <thead class="thead-dark">
		            <tr class="text-center">
		                <th>Date</th>
		                <th>Count of Trucks</th>
		                <th>Count of Tralla/Trally</th>
		                <th>Cash</th>
		                <th>Credit</th>
		                <th>Bank</th>
					</tr>
		        </thead>
		        <tbody id="table_body">
			        <c:forEach var="record" items="${summary_sales}">
			        	<tr>
	        				<td>${record[0]}</td>
	        				<td>${record[1] != null ? record[1] : 0.0}</td>
	        				<td>${record[2] != null ? record[2] : 0.0}</td>
	        				<td>${record[3] != null ? record[3] : 0.0}</td>
	        				<td>${record[4] != null ? record[4] : 0.0}</td>
	        				<td>${record[5] != null ? record[5] : 0.0}</td>
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
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
	// ------------------------------ Page Load Initialization -----------------------------------
		$(document).ready(e =>{
			$(".fa").hover(e =>{
				$(".fa").css({"cursor":"pointer"});
			});

			$("#home_icon").click( e =>{
				window.location.href = "report_panel";
			});
		});

		// ------------------------------ Page Load Configuration End ---------------------------------
		
		// ------------------------------ On Page Actions ------------------------------------------
		
	
		//------------------------------------ On Page Action End --------------------------------------
		
		// ----------------------------------- Ajax Calls from Page ----------------------------------
		 
		//------------------------------------ End Ajax Calls ----------------------------------------
		
	// --------------------------- Support Methods ----------------------------------------
	
	
	// --------------------------- Support Methods End -----------------------------------
	
	</script>
</body>
</html>