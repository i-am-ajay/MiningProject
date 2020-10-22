<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Sign In</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="container mt-3">
	<div class="card bg-light">
	<article class="card-body mx-auto" style="max-width: 350px;">
		<h4 class="card-title text-center display-4 border-bottom border-danger py-2 my-2">Sign In</h4>
		<!-- Form -->
		<form method="POST" action="authenticate_user">
		<div class="form-group input-group">
			<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
			 </div>
	        <input name="username" class="form-control" placeholder="Enter Username" type="text">
	    </div> <!-- form-group// -->

	    <div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
			</div>
	        <input name="password" class="form-control" placeholder="Enter Password" type="password">
	    </div> <!-- form-group// -->
	                              
	    <div class="form-group">
	        <button type="submit" class="btn btn-success btn-block"> Sign In</button>
	    </div> <!-- form-group// -->                                                              
	</form>
	</article>
	</div> <!-- card.// -->
	
	</div> 
	<!--container end.//-->
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script>
		$(document).ready(e => {
			$("#home_icon").hide();
		})
		
	</script>
</body>
</html>