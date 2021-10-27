<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add 24hrs Unit</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static_resources/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css">

</head>
<!-- 
	For quantities bucket, ton and foot rate is only based on material type. As these are sold in number like
	1 ton, 10 bucket, 200 foot etc.
	
	1) When any of these options checked in system disable all other parameters except quality and save rate.
 -->
<body class=mt-1>

	<div class="container p-2 m-auto">
		<div class="row">
			<h4 class="border-bottom border-danger m-3 pb-2 display-4 col-8"
				id="form_title">Add Units</h4>
			<div class="col-2 align-right ml-auto pl-5 mt-4 mr-5">
				<i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i>
			</div>
			<div class="col-1">
				<a class="btn btn-danger btn-sm mt-4" style="font-size: .6em;"
					href="${pageContext.request.contextPath}/logout">Logout</a>
			</div>
		</div>
		<!-- Success msg -->
		<c:if test="${status.equalsIgnoreCase('success')}">
			<div
				class="alert alert-success alert-dismissible fade show w-50 mx-auto"
				role="alert">
				<small>Rate Added Successfully.</small>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('exists')}">
			<div
				class="alert alert-danger alert-dismissible fade show w-50 mx-auto"
				role="alert">
				<small>Rate already exists.</small>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('fails')}">
			<div
				class="alert alert-danger alert-dismissible fade show w-50 mx-auto"
				role="alert">
				<small>There is some error in Rate creation.</small>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<form method="POST" action="units_24_hrs">
			<div class="row">
				<div class="col-7"></div>
				<div class="col-3">
					<div class="form-group row">
					      		<label class="font-weight-bold col-sm-3 col-form-label">Date</label>
					      		<div class="col-sm-9">
					      			<input type="date" id="date" name="unit_date" class="form-control form-control-sm" value="${unit_date}" readonly="true"/>
					    		</div>
					    	</div>
				</div>
			</div>
			</form>
		<f:form method="POST" modelAttribute="candidate_machines"
			action="save_units_24_hrs">
			${candidate_machines.reset()}
			<!-- Patient Vitals -->
			<!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
			<!-- Card Vitals -->
			<%-- <f:hidden value="test" path="machineList" /> --%>
			<input type="hidden" id="u_date" name="u_date" value="${unit_date}"/>
			<div class="row pt-1">
				<div class="col">
					<div class="text-dark bg-light px-3 mb-3 mx-auto"
						style="transform: scale(0.8);">
						<div class="card-body">
							<div class="row">
								<div class="col-3">
									<div class="form-group">
										<label class="font-weight-bold">Machine Name</label>
									</div>
								</div>
								<div class="col-3">
									<div class="form-group">
										<label class="font-weight-bold">Previous Unit</label>
									</div>
								</div>
								<div class="col-3">
									<div class="form-group">
										<label class="font-weight-bold">Current Unit</label>
									</div>
								</div>
								<div class="col-3">
									<div class="form-group">
										<label class="font-weight-bold">Hours</label>
									</div>
								</div>
							</div>
							<%-- <c:forEach begin="0" end="${list_size-1}"  varStatus="loop" > --%>
							<c:forEach var="unitCapture" items="${candidate_machines.machineList}" varStatus="i">
								<div class="row">
								<div class="col-3">
									<div class="form-group">
										<f:hidden path="machineList[${i.index}].machineId.id" />
										<f:hidden path="machineList[${i.index}].id" />
										<f:input id="machine" class="form-control form-control-sm" path="machineList[${i.index}].machineId.name" readonly="true"/>
									</div>
								</div>
								<div class="col-3">
									<div class="form-group">
										<f:input id="previous_unit" class="form-control form-control-sm" path="machineList[${i.index}].lastUnit" readonly="true"/>
									</div>
								</div>
								<div class="col-3">
									<div class="form-group">
										<f:input id="current_unit" class="form-control form-control-sm" path="machineList[${i.index}].currentUnit"/>
									</div>
								</div>
								<div class="col-3">
									<div class="form-group">
										<f:input id="hours" class="form-control form-control-sm" path="machineList[${i.index}].hours" readonly="true"/>
									</div>
								</div>
							</div>		
							</c:forEach>
						</div>
					</div>
				</div>
			</div>


			<input type="submit"
				class="btn btn-small btn-secondary btn-block w-50 mx-auto"
				value="Save Units" />
			<input type="hidden" id="role" value="${role}" />
		</f:form>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script
		src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script
		src="${pageContext.request.contextPath}/static_resources/js/utility.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
		$(document).ready(
			function(){
				$("#rate_id").attr("required",true);
				let quantityVal = $("#quantity_type").val();
				quantityVal = quantityVal.toLowerCase();
				if(quantityVal == 'bucket' || quantityVal == 'ton' || quantityVal == 'foot'){
					$(".not_eligible").attr("disabled",true);
				}
			});
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

		// bucket, ton or foot selection will result in disabling fields with not_eligible class.
		$("#quantity_type").change(e =>{
			let quantityVal = $("#quantity_type").val();
			quantityVal = quantityVal.toLowerCase();
			if(quantityVal == 'bucket' || quantityVal == 'ton' || quantityVal == 'foot'){
				$(".not_eligible").attr("disabled",true);
			}
			else{
				$(".not_eligible").attr("disabled",false);
			}
		});
	</script>
</body>
</html>