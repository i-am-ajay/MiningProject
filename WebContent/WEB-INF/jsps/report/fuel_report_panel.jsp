<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report Dashboard</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static_resources/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

	<div class="container mt-4">
		<div class="row">
			<h5
				class="border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5 col-8"
				id="form_title">Fuel Report Dashboard</h5>
			<div class="col-2 align-right ml-auto pl-5 mt-2 mr-5">
				<i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i>
			</div>
			<div class="col-1">
				<a class="btn btn-danger btn-sm mt-2" style="font-size: .5em;"
					href="${pageContext.request.contextPath}/logout">Logout</a>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col-sm-12 col-md-2">
				<div class="card bg-light my-1 py-1" id="machine_report">
					<article class="card-body mx-auto">
						<h5
							class="card-title text-center display-5 border-bottom border-danger py-2 my-2">
							Machine<br />Report
						</h5>
						<p class="text-center mt-3">
							<i class="fa fa-truck fa-2x" aria-hidden="true"></i>
						</p>
					</article>
				</div>
			</div>
			<div class=" col-sm-12 col-md-2">
				<div class="card bg-light my-1 py-1" id="fuel_distribution">
					<article class="card-body mx-auto">
						<h5
							class="card-title text-center display-5 border-bottom border-danger py-2 my-2">
							Fuel Dist<br /> Report
						</h5>
						<p class="text-center mt-3">
							<i class="fa fa-file-text fa-2x" aria-hidden="true"></i>
						</p>
					</article>
				</div>
			</div>
		</div>
	</div>

	<form id="hidden_form" action="home" method="POST">
		<input type="hidden" name="page" value="admin" />
	</form>


	<!--container end.//-->
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/bootstrap_max_cdn_min.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/font_awesome.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/data_table.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/v/bs4/dt-1.10.21/af-2.3.5/b-1.6.3/b-html5-1.6.3/datatables.min.js"></script>
	<script>
		$(".card").hover(e => {
			$(".card").css({'cursor':'pointer'});
		});

		// go to create user
		$("#machine_report").click(function(e) {
			//$("#hidden_form").submit();
			window.location.href ="machine_report";
		});
		
		// go to graphs
		$("#fuel_distribution").click( e=>{
			window.location.href ="fuel_distribution_report";
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

		$("#daywise_sales").click( e=>{
			window.location.href ="daywise_summary";
		});

		$("#jr").click(e=>{
			window.location.href="journal_list";
		})

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