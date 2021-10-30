<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Feedback Report</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
	
</head>
<body class=mt-1>
<c:if test="${status.equalsIgnoreCase('error')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>You are not allowed to update already posted units.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  			</button></div>
		</c:if>
	<div class="p-2 m-auto">
		<div id="search_div" class="container">
		<div class="row">
			<h6 class="border-bottom mx-1 text-muted pb-2" id="form_title">Fuel Distribution Report</h6>
			<div class="col-2 align-right ml-auto pl-5 mr-5"><i id="home_icon" class="fa fa-home fa-x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm" style="font-size: .5em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<div  class="mt-3 bg-light p-1">
			<form action="update_units" method="POST">
			 <div class="form-row search">
				<input type="hidden" name="submitted" value="true" />
			     <div class="form-group col-md-2">
			      <label for="name" class="font-weight-bold">Machine<span class="text-danger">*</span></label>
			      <select class="form-control form-control-sm" id="machine_id" name="machine">
		      			<option value=""></option>
		      			<c:forEach var="item" items="${machineList}">
		      				<c:if test="${not empty item}">
		      					<option value="${item.id}">${item.name}</option>
		      				</c:if>
		      			</c:forEach>
				  </select>
			    </div>
			 	<div class="form-group col-md-3">
			      	<label for="f_date" class="font-weight-bold">Date</label>
			      	<input type="date" class="form-control form-control-sm" id="date" name="date"/>
			    </div>	
			    <div class="col-md-7">
			    <label for="f_date" class="font-weight-bold">&nbsp;</label>
			    	<input id="button" type="submit" class=" btn btn-secondary btn-sm btn-block w-50 mx-auto text-center" value="search" />
			    </div>
			    </div>
			</form>
		</div>
		</div>
			<div class="row mx-auto mt-3" style="width:95%;">
				<div class="col-6 border text-center">
					<h5 class="display-4 border-bottom">Fuel Units</h5>
					<div class="m-2"></div>
					<c:forEach var="unit" items="${fuel_unit_list}" >
					<div class="row">
						<div class="col-3 display-5 badge badge-light text-monospace mx-3 my-1 py-3" style="font-size: 1em;">${unit.entryDate}</div>
						<div class="col-3 mt-2"><input class="form-control" id="unit${unit.id}" value="${unit.currentUnits}"/></div>
						<div class="col-3 mt-2"><button class="btn btn-block btn-sm btn-secondary update_fuel_unit" id="btn${unit.id}">Update</button></div>
					</div>
					</c:forEach>
				</div>
				<div class="col-6 border text-center ">
					<h5 class="display-4 border-bottom">24 Hours Unit</h5>
					<c:forEach var="unit" items="${machine_24Hrs_Unit_list}" >
					<div class="row">
						<div class="col-3 display-5 badge badge-light text-monospace m-1 py-3" style="font-size: 1.1em;">${unit.unitDate}</div>
						<div class="col-3 mt-2"><input class="form-control" id="unit${unit.id}" value="${unit.currentUnit}"/></div>
						<div class="col-3 mt-2"><button class="btn btn-block btn-sm btn-secondary update_24hrs_unit" id="btn${unit.id}">Update</button></div>
					</div>
					</c:forEach>
				</div>
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
		$(document).ready(function() {
			table = $('#example').DataTable( {
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
				window.location.href = "fuel_panel";
			});

			$("#report").hide();
		});
		
		// on update button press
		$('.update_fuel_unit').click( e=>{
			let idBtn = e.currentTarget.id;
			let recordId = idBtn.substring(3,idBtn.length);
			let updatedValue = $("#unit"+recordId).val();
			$.ajax({
				type:"POST",
				url:"${home}save_fuel_unit",
				data:{"id":recordId, "unitValue":updatedValue},
				success: function(result,status, xhr){
					console.log(result);
				},
				error: function(result,status, xhr){
				}
			});
		})
		
		$('.update_24hrs_unit').click(e =>{
			let idBtn = e.currentTarget.id;
			let recordId = idBtn.substring(3,idBtn.length);
			let updatedValue = $("#unit"+recordId).val();
			alert(updatedValue);
			$.ajax({
				type:"POST",
				url:"${home}save_update_24hrs_unit",
				data:{"id":recordId, "unitValue":updatedValue},
				success: function(result,status, xhr){
					console.log(result);
				},
				error: function(result,status, xhr){
				}
			});
		})
		
	</script>
</body>
</html>