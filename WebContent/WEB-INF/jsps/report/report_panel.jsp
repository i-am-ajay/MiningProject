<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report Dashboard</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	
	<div class="container mt-4">
	<div class="row">
		<h4 class="border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5 col-8" id="form_title">Report Dashboard</h4>
		<div class="col-2 align-right ml-auto pl-5 mt-2 mr-5"><i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i></div>
		<div class="col-1"><a class="btn btn-danger btn-sm mt-2" style="font-size: .5em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
	</div>
	<div class="row mt-3">
		<div class="col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="sales_report">
			<article class="card-body mx-auto">
				<h4 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Sales<br/>Report</h4>
				<p class="text-center mt-3"><i class="fa fa-line-chart fa-4x" aria-hidden="true"></i></i></p>	
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="client">
			<article class="card-body mx-auto">
				<h4 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Client<br/> Report</h4>
				<p class="text-center mt-3"><i class="fa fa-user fa-4x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="vehicle">
			<article class="card-body mx-auto">
				<h4 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Vehicle<br/> Report</h4>
				<p class="text-center mt-3"><i class="fa fa-truck fa-4x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		
	</div>
	<div class="row mt-3">
		<div class="col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="rates">
			<article class="card-body mx-auto">
				<h4 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Rates<br/>Report</h4>
				<p class="text-center mt-3"><i class="fa fa-money fa-4x" aria-hidden="true"></i></p>	
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="ledger">
			<article class="card-body mx-auto">
				<h4 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Ledger<br/>&nbsp;</h4>
				<p class="text-center mt-3"><i class="fa fa-calculator fa-4x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="ledger_summary">
			<article class="card-body mx-auto">
				<h3 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Ledger<br/> Summary</h3>
				<p class="text-center mt-3"><i class="fa fa-file-text-o fa-3x" aria-hidden="true"></i></p>
			</article>
			</div
		</div>
	</div>
	</div> 
	
	<form id="hidden_form" action="home" method="POST">
		<input type="hidden" name="page" value="admin" />
	</form>
	
	
	<!--container end.//-->
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_max_cdn_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/font_awesome.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/data_table.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.21/af-2.3.5/b-1.6.3/b-html5-1.6.3/datatables.min.js"></script>
	<script>
		$(".card").hover(e => {
			$(".card").css({'cursor':'pointer'});
		});

		// go to create user
		$("#sales_report").click(function(e) {
			//$("#hidden_form").submit();
			window.location.href ="sales_report";
		});
		
		// go to graphs
		$("#client").click( e=>{
			window.location.href ="client_report";
		});
		// go to feedback
		$("#vehicle").click( e=>{
			window.location.href ="vehicle_report";
		});

		$("#rates").click( e=>{
			window.location.href ="rate_report";
		});
		
		$("#sales").click( e=>{
			window.location.href ="display_sales_page";
		});

		$("#ledger").click( e=>{
			window.location.href ="ledger_report";
		});
		
		$("#ledger_summary").click( e=>{
			window.location.href ="ledger_summary";
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

	</script>
</body>
</html>