<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Client</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body class=mt-1>
	<div class="container p-2 m-auto">
		<h4 class="border-bottom border-danger m-3 pb-2 display-4" id="form_title">Client Creation</h4>
		
		<c:if test="${status.equalsIgnoreCase('success')}">
			<div class="alert alert-success alert-dismissible fade show w-50 mx-auto" role="alert"><small>Client Created Successfully.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('exists')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>Client already exists.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  				</button>
  			</div>
		</c:if>
		<c:if test="${status.equalsIgnoreCase('fails')}">
			<div class="alert alert-danger alert-dismissible fade show w-50 mx-auto" role="alert"><small>There is some error in Client creation.</small><button type="button" class="close" data-dismiss="alert" aria-label="Close">
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
		<f:form method="POST" modelAttribute="client" action="save_client">
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
					      		<label class="font-weight-bold">Client Name</label>
					      		<f:input class="form-control form-control-sm" id="name" placeholder="Enter Name" path="name"/>
					    	</div>
					    	<div class="form-group">
					      		<label for="testDate" class="font-weight-bold">Client Phone No</label>
					      		<f:input class="form-control form-control-sm" id="phone" placeholder="Mobile Number" path="clientContact" title="Valid Number"/>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Client Address</label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="address" placeholder="Enter Address" path="clientAddress"/>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Client Type</label>
					      		</div>
					      		<select class="form-control form-control-sm" id="type" placeholder="Enter Client Type" name="client_type">
					      			<c:forEach var="item" items="${lookup}">
					      				<c:if test = "${!item.value.equalsIgnoreCase('self')}">
					      					<option value="${item.key}">${item.value}</option>
					      				</c:if>
					      			</c:forEach>
					      		</select>
					    	</div>
					    	
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Client Discount</label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="discount" placeholder="Enter Discount" path="discount"/>
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Contractor Commission</label>
					      		</div>
					      		<f:input class="form-control form-control-sm" id="comission" placeholder="Enter Comission" path="comission"/>
					    	</div>
			  			</div>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-50 mx-auto" value="Save Client"/>
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
				$("#phone").attr("required","true");
				$("#address").attr("required","true");
				$("#comission").attr("readonly","true");
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

		// on selection of owner enable comission.
		$("#type").change(e =>{
			if($("#type option:selected").text() == "Contractor"){
				$("#comission").attr("readonly",false);
			}
		});
		// on registration focus out get employee details through ajax call 
		$("#name").focusout( e =>{
			$.ajax({
				type: "POST",
				url : "${home}get_client",
				data : {"name":$("#name").val()},
				success: function(result, status, xhr){
					console.log(result);
					if(result != null && result != ""){
						let json = JSON.parse(result);
						console.log(json.name)
						$("#name").attr("readonly","true");
						$("#name").val(json.name);
						$("#phone").val(json.contact);
						$("#address").val(json.address);
						$("#discount").val(json.discount);
						$("#comission").val(json.comission);
						if(json.type_desc == 'Contractor'){
							$("#comission").attr("readonly",false);
						}

						// select value of select box.

						$("#type").find("option[value="+json.client_type+"]").attr("selected","selected");
					}
				},
				error : function(result,status,xhr){
					$("#name").attr("disabled","false");
					$("#phone").attr("disabled","false");
					$("#fgender").attr("disabled","false");
					$("#mgender").attr("disabled","false");
				}
			});
		});
	
	</script>
</body>
</html>