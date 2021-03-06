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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >
</head>
<body class=mt-1>
	<div class="container p-2 m-auto">
		<div class="row">
			<h4 class="border-bottom border-danger m-3 pb-2 display-4" id="form_title">Company Creation</h4>
			<div class="col-2 align-right ml-auto pl-5 mt-4 mr-5"><i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm mt-4" style="font-size: .6em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
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
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Company Details Updated</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		
		<!-- 
			Company Form 
			Fields :
				1) Company Name - on focus out system will check if company already exists and if user has 
					edit rights company values will be returned.
				2) Company Contact - Auto populate based on company name if company already exists.
				
				3) Company Address - Auto populate for existing company
				
				4) Compan Email - Auto populate for existing company.
		 -->
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
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
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
		$("#name").focusout( e =>{
			$.ajax({
				type: "POST",
				url : "${home}get_company",
				data : {"name":$("#name").val()},
				success: function(result, status, xhr){
					console.log(result);
					if(result != null && result != ""){
						let json = JSON.parse(result);
						console.log(json)
						$("#name").val(json.name);
						$("#phone").val(json.contact);
						$("#address").val(json.address);
						$("#email").val(json.email);
						
					}
				},
				error : function(result,status,xhr){
				}
			});
		});
	
	</script>
</body>
</html>