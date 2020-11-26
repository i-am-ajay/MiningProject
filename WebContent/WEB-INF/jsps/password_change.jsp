<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Change Password</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resource/css/bootstrap.min.css" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="container mt-3">
	<div class="card bg-light">
	<article class="card-body mx-auto" style="max-width: 350px;">
		<h4 class="card-title text-center display-4 border-bottom border-danger py-2 my-3">Change Password</h4>
		<form id="frm" method="POST" action="change_password">
		<!--  alert msgs -->
			<c:if test='${status.equals("updated")}'>
				<div class="alert alert-success col-xs-offset-1 col-xs-10">Password Changed successfully.</div>
			</c:if>
		
		<div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
			</div>
	        <input id="password" name="password" class="form-control" placeholder="Enter a n password" type="password">
	    </div>
	    <div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
			</div>
	        <input id="rpass" name="rpassword" class="form-control" placeholder="Retype password" type="password">
	    </div> <!-- form-group// -->
	    <!--  
	    <div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
			</div>
	        <input id="rpassword" name="repeat_password" class="form-control" placeholder="Repeat password" type="password">
	    </div> <!-- form-group// -->                                      
	    <div class="form-group"> 
	        <button id="btn" type="button" class="btn btn-primary btn-block"> Change Password</button>
	    </div> <!-- form-group// -->                                                              
	</form>
	</article>
	</div> <!-- card.// -->
	<!-- <div>
		<p class="text-center mt-2"><i id="home_icon" class="fa fa-home fa-2x text-center" aria-hidden="true"></i></p>
	</div> -->
	</div> 
	<!--container end.//-->
	
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	 -->
	 <script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script>
		$(document).ready(e =>{
			$("#home_icon").click(
				(e)=>{
					window.location.href = "admin_panel";
				})
		}).ready( e => {
			$("#home_icon").hover(
				(e) => {
					$("#home_icon").css({"cursor":"pointer"});
			})
		}).ready(e =>{
			$("#deactivate_div").hide();
			$("#report").hide();
		});

		

		/* // search for user on focusout
		$("#user").focusout(e =>{
				$.ajax({
					type: "POST",
					url : "${home}user_load",
					data: {"user":$("#user").val()},
					success: function(result, status, xhr){
						let resultObj = JSON.parse(result);
						$("#deactivate_div").show();
						$("#user").val(resultObj.user);
						$("#password").val(resultObj.password);
						$("#role").attr("select",resultObj.role);
						if(resultObj.deactive == true){
							$("#check").prop("checked",true);
						}
						
					},
					error: function(result, status, xhr){
					}
				});
		});
 */
 	$("#btn").click( function(event){
		let password = $("#password").val();
		let repeatPassword = $("#rpass").val();
		if(password !== repeatPassword){
			event.preventDefault();
			alert("Password and Repeate Password must be same.");
		}
		else{
			$("#frm").submit();
		}
 	});	
	</script>
</body>
</html>