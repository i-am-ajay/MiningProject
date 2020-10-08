<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Company</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body class=mt-1>
	<div class="container p-2 m-auto">
		<h4 class="border-bottom border-danger m-3 pb-2 display-4" id="form_title">Company Creation</h4>
		<!-- Success msg -->
		<c:if test="${status.equalsIgnoreCase('success')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Company Created Successfully.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  			</button></div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('exists')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>Company already exists.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('fails')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>There is some error in company creation.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		
		<c:if test="${status.equalsIgnoreCase('updated')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>There is some error in company creation.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		
		<f:form method="POST" modelAttribute="company" action="company_saved">
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
					      		<label class="font-weight-bold">Company Name</label>
					      		<f:input class="form-control form-control-sm" id="name" placeholder="Enter Name" path="name"/>
					    	</div>
					    	<div class="form-group">
					      		<label for="testDate" class="font-weight-bold">Company Contact</label>
					      		<f:input class="form-control form-control-sm" id="phone" placeholder="Contact Number" path="companyContact"/>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Company Address</label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="address" placeholder="Enter Address" path="location"/>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Company Email</label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="email" placeholder="Enter Email" path="companyEmail"/>
					    	</div>
			  			</div>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-50 mx-auto" value="Save Company"/>
	<input type="hidden" id="role" value="${role}" />
	</f:form>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
		$(document).ready(
			function(){
				$("#name").attr("required","true");
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
		
		// on registration focus out get employee details through ajax call 
		$("#registration").focusout( e =>{
			$.ajax({
				type: "POST",
				url : "${home}get_company",
				data : {"name":$("#name").val()},
				success: function(result, status, xhr){
					if(result != null && result != ""){
						let json = JSON.parse(result);
						$("#name").val(json.name);
						$("#phone").val(json.contact);
						$("#address").val(json.address);
						$("#email").val(json.email);
						
				},
				error : function(result,status,xhr){
				}
			});
		});
	
	</script>
</body>
</html>