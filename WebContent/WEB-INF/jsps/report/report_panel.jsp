<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resource/css/style.css" >
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
				<p class="text-center mt-3"><i class="fa fa-building fa-4x" aria-hidden="true"></i></i></p>	
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="client">
			<article class="card-body mx-auto">
				<h4 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Client<br/> Report</h4>
				<p class="text-center mt-3"><i class="fa fa-user-plus fa-4x" aria-hidden="true"></i></p>
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
			<!-- <div class="card bg-light my-1 py-1" id="report">
			<article class="card-body mx-auto">
				<h3 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Add<br/> Vehicle</h3>
				<p class="text-center mt-3"><i class="fa fa-file-text-o fa-3x" aria-hidden="true"></i></p>
			</article>
			</div> -->
		</div>
	</div>
	</div> 
	
	<form id="hidden_form" action="home" method="POST">
		<input type="hidden" name="page" value="admin" />
	</form>
	
	
	<!--container end.//-->
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
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