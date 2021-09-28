<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Machine</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >


</head>
<body class=mt-1>
	<div class="container p-2 m-auto">
		<div class="row">
		<h4 class="border-bottom border-danger m-3 pb-2 display-4 col-8" id="form_title">Create Machine</h4>
		<div class="col-2 align-right ml-auto pl-5 mt-4 mr-5"><i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i></div>
		<div class="col-1"><a class="btn btn-danger btn-sm mt-4" style="font-size: .6em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<c:if test="${status.equalsIgnoreCase('success')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Machine Created Successfully.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('exists')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>Machine already exists.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('fails')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>There is some error in Machine creation.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('updated')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Client Updated</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<!-- 
			1) User based on their roles will be able to add or update client.
			2) On saving based on user roll and action a successful creation, updation or failure msg
				will be displayed to user.
			3) Name for each client should be unique, if a user is try to save an already existing role
				and error msg will be shown for admin on enterin already existing role, role will be 
				updated.
				
			4) A discount and commission option is needed for contractor. 
		 -->
		<f:form method="POST" modelAttribute="machine" action="save_machine">
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <div class="row pt-2">
		   <div class="col">
		   <div class="card text-dark bg-light px-3 mb-3 w-50 mx-auto" >
  				<div class="card-body">
  					<div class="row">
  						<div class="col">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Machine Name</label>
					      		<f:input class="form-control form-control-sm" id="name" placeholder="Enter Name" path="name"/>
					      		<f:input type="hidden" id="machine_id" path="id" />
					    	</div>
					    	<div class="form-group">
					      		<label for="testDate" class="font-weight-bold">Creation Date</label>
					      		<f:input type="date" class="form-control form-control-sm" id="entry_date" path="entryDate" title="Entry Date"/>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Machine Rate</label>
					      		<f:input type="number" class="form-control form-control-sm" id="machine_rate" placeholder="Enter Machine Rate" path="machineRate"/>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Fixed Hours</label>
					      		<f:input type="number" class="form-control form-control-sm" id="fixed_hours" placeholder="Enter Fixed Hourse" path="fixedHours"/>
					    	</div>
					    	<div class="form-group">
					      		<label class="font-weight-bold">Vendor</label>
					      		<f:select class="form-control form-control-sm" id="vendor_id" placeholder="Enter Material Type" path="vendorId" >
					      			<c:forEach var="item" items="${vendor_list}">
					      				<f:option value="${item.key}">${item.value}</f:option>
					      			</c:forEach>
					      		</f:select>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">End Date</label>
					      		</div>
					      		<f:input type="date" class="form-control form-control-sm" id="end_date" path="endDate"/>
					    	</div>
			
					    	<div id="deactivate_div" class="form-check">
	        					<label class="form-check-label"><input type="checkbox" class="form-check-input mr-3" id="check" name="status"/> Deactivate Machine</label>
	    					</div>
			  			</div>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-50 mx-auto disable_button" value="Save Machine"/>
	<input type="hidden" id="role" value="${role}" />
	</f:form>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/utility.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
		$(document).ready(
			function(){
				$("#name").attr("required","true");
				$("#entry_date").attr("required","true");

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

		// get machine details if it's already exists.
		$("#name").focusout( e =>{
			$.ajax({
				type: "POST",
				url : "${home}get_machine",
				data : {"name":$("#name").val()},
				success: function(result, status, xhr){
					console.log("success");
					if(result != null && result != ""){
						console.log(result);
						let json = JSON.parse(result);
						console.log(json.name)
						$("#name").attr("readonly","true");
						$("#machine_id").val(json.machine_id);
						$("#entry_date").val(json.entry_date);
						$("#rate").val(json.rate);
						$("#fixed_hours").val(json.fixed_hours);
						$("#vendor_id").val(json.vendorId);
						//$("#end_date").val(json.end_date);
						//$("end_date").val(json.)
					}
				},
				error : function(result,status,xhr){
					console.log("error")
					$("#name").attr("disabled",false);
				}
			});
		});
		// Client list.
		function getClientList(val){
			$("#client").attr("disabled",false);
			$.ajax({
				type: "POST",
				url : "${home}client_list",
				data : {"client_id":"46"},
				success: function(result, status, xhr){
					$("#client").empty();
					let client_id = null;
					if(result != null && result != ""){
						let json = JSON.parse(result);
						let array = Object.keys(json);
						array.forEach(e=>{
							if(val == e){
								$("#client")
								.append("<option value="+e+" selected>"+json[e]+"</option>");
							}
							$("#client")
							.append("<option value="+e+">"+json[e]+"</option>");
						});
						
					}
				},
				error : function(result,status,xhr){
					console.log("error");
				}
			});
		}

		//----------------------------- Support Method -----------------------------------
		
		function manageDisscount_Commission(val){
			if(!val){
				val = $("#type option:selected").text() == "Contractor"
			}
			if(val){
				$("#comission").attr("readonly",false);
				$("#discount").val(0.0);
				$("#discount").attr("readonly",true);
			}
			else{
				$("#comission").val(0.0);
				$("#comission").attr("readonly",true);
				$("#discount").attr("readonly",false);
			}
		}
		//----------------------------- End Support Method -------------------------------
	
	</script>
</body>
</html>