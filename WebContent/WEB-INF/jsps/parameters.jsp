<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Parameters</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >

</head>
<body class=mt-1>
	<div class="container p-2 m-auto">
		<div class="row">
		<h4 class="border-bottom border-danger m-3 pb-2 display-4 col-8" id="form_title">Parameter Modification</h4>
		<div class="col-2 align-right ml-auto pl-5 mt-4 mr-5"><i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i></div>
		<div class="col-1"><a class="btn btn-danger btn-sm mt-4" style="font-size: .6em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<c:if test="${status.equalsIgnoreCase('success')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Client Created Successfully.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('exists')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>You are not authorized to change Parameters.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('fails')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>There is some error in Parameter Updation.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('updated')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Parameters Updated</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
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
		<f:form method="POST" modelAttribute="parameters" action="update_parameters">
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <f:hidden path="id"/>
	
		   <div class="row pt-2">
		   <div class="col">
		   <div class="card text-dark bg-light px-3 mb-3 mx-auto" >
  				<div class="card-body">
  					<div class="row">
  						<div class="col-3">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Driver Return <sup>Normal</sup></label>
					      		<f:input class="form-control form-control-sm" id="driverReturn" path="driverReturnNormal"/>
					    	</div>
					    </div>	
					    <div class="col-3">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Driver Return <sup>Small Vehicle</sup></label>
					      		<f:input class="form-control form-control-sm" id="driverReturnSm" path="driverReturnSmallVehicle"/>
					    	</div>
					    </div>	
					    <div class="col-3">	
					    	<div class="form-group">
					      		<label for="testDate" class="font-weight-bold">Non Royalty</label>
					      		<f:input class="form-control form-control-sm" id="nrl" path="royalty"/>
					    	</div>
					    </div>	
					    <div class="col-3">	
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Free Vehicle Amount</label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="freeAmount" path="freeLimit"/>
					    	</div>
					    </div>
					</div>    
				    <div class="row">
				    	<div class="col-3">
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Sanchalan Amount&nbsp;<sup>Normal</sup></label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="amountBeforeLimit" path="sanchalanNormal"/>
				    		</div>
				    	</div>
					    <div class="col-3">	
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Sanchalan Amount&nbsp;<sup>Small Vehicle</sup></label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="amountAfterLimit" path="sanchalanTrolly"/>
					    	</div>
					    </div>
		  			</div>
			 
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-50 mx-auto" value="Update Parameters"/>
	<input type="hidden" id="role" value="${role}" />
	</f:form>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
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

		// on selection of owner enable comission.
		$("#type").change(e =>{
			manageDisscount_Commission();
		});

		//----------------------------- Support Method -----------------------------------
		
		//----------------------------- End Support Method -------------------------------
	
	</script>
</body>
</html>