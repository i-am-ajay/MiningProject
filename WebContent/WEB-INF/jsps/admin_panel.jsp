<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Panel</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	
	<div class="container mt-4">
	<div class="row">
		<h4 class="border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5 col-10" id="form_title">Dashboard</h4>
		<div class="col-1"><a class="btn btn-danger btn-sm" style="font-size: .5em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
	</div>
	<div class="row mt-3">
	<div class=" col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="sales">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Supply<br/>& Sales</h5>
				<p class="text-center mt-3"><i class="fa fa-calculator fa-2x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		
		<div class=" col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="vehicle">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Add<br/> Vehicle</h5>
				<p class="text-center mt-3"><i class="fa fa-truck fa-2x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		
		<div class=" col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="client">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Create<br/> Client</h5>
				<p class="text-center mt-3"><i class="fa fa-user-plus fa-2x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		
		
		<div class="col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="rates">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Add<br/>Rates</h5>
				<p class="text-center mt-3"><i class="fa fa-money fa-2x" aria-hidden="true"></i></p>	
			</article>
			</div>
		</div>
		<div class="col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="inc_exp">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Income<br/>Expense</h5>
				<p class="text-center mt-3"><i class="fa fa-laptop fa-2x" aria-hidden="true"></i></p>	
			</article>
			</div>
		</div>
		
	</div>
	<div class="row mt-3">
		<div class=" col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="journal">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Journal<br/>Entry</h5>
				<p class="text-center mt-3"><i class="fa fa-pencil fa-2x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="params">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Parameters<br/>&nbsp;</h5>
				<p class="text-center mt-3"><i class="fa fa-plus fa-2x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="report">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Reports<br/>&nbsp;</h5>
				<p class="text-center mt-3"><i class="fa fa-file-text-o fa-2x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		<div class="col-sm-12 col-md-2">
			<div class="card bg-light my-1 py-1" id="company">
			<article class="card-body mx-auto">
				<h5 class="card-title text-center display-5 border-bottom border-danger py-2 my-2">Create<br/>Company</h5>
				<p class="text-center mt-3"><i class="fa fa-building fa-2x" aria-hidden="true"></i></i></p>	
			</article>
			</div>
		</div>
	</div>
	
	<!-- <div class="row mt-3">
		<div class="col-sm-12 col-md-3">
			<div class="card bg-light my-1 py-1" id="rates">
			<article class="card-body mx-auto">
				<h4 class="card-title text-center display-3 border-bottom border-danger py-2 my-2">Add<br/>Rates</h4>
				<p class="text-center mt-3"><i class="fa fa-money fa-3x" aria-hidden="true"></i></p>	
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="sales">
			<article class="card-body mx-auto">
				<h4 class="card-title text-center display-3 border-bottom border-danger py-2 my-2">Supply<br/>& Sales</h4>
				<p class="text-center mt-3"><i class="fa fa-calculator fa-3x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
		<div class=" col-sm-12 col-md-4">
			<div class="card bg-light my-1 py-1" id="report">
			<article class="card-body mx-auto">
				<h3 class="card-title text-center display-3 border-bottom border-danger py-2 my-2">Reports<br/> &nbsp;&nbsp;</h3>
				<p class="text-center mt-3"><i class="fa fa-file-text-o fa-3x" aria-hidden="true"></i></p>
			</article>
			</div>
		</div>
	</div> -->
	</div> 
	
	<form id="hidden_form" action="home" method="POST">
		<input type="hidden" name="page" value="admin" />
	</form>
	
	
	<!--container end.//-->
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script>
		$(".card").hover(e => {
			$(".card").css({'cursor':'pointer'});
		});

		// go to create user
		$("#company").click(function(e) {
			//$("#hidden_form").submit();
			window.location.href ="company_creation";
		});
		
		// go to graphs
		$("#client").click( e=>{
			window.location.href ="client_creation";
		});
		// go to feedback
		$("#vehicle").click( e=>{
			window.location.href ="create_vehicle";
		});

		$("#rates").click( e=>{
			window.location.href ="add_rate";
		});
		
		$("#sales").click( e=>{
			window.location.href ="display_sales_page";
		});

		$("#report").click( e=>{
			window.location.href ="report_panel";
		});

		$("#inc_exp").click( e=>{
			window.location.href ="ledger_entries_screen";
		});

		$("#params").click( e=>{
			window.location.href ="parameter_display";
		});

		$("#journal").click( e=>{
			window.location.href ="journal_entries";
		});

		$(document).ready( e=>{
			$("#home_icon").hide();
		});

		// Changes the page heading for mobile screen and tablets.
		$(document).ready( e => {
			const screenSize = window.screen.width;
			if(screenSize < 1000){
				$("#header_div").replaceWith("<h6 class='text-center display-5'>Sir Ganga Ram Hospital</h6>" +
						"<p class='text-center'>Patient Data Portal</p>");
				$("#form_title").removeClass("m-3");
				
				//$("#farewell_note").removeClass("display-4").addClass("display-5");
			}
		})
	</script>
</body>
</html>