<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mining Reports</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
	
</head>
<body class=mt-1>	
	<div class="row">
			<h4 class=" col-8 border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5" id="form_title">Update Rates</h4>
			<div class="col-2 align-right ml-auto pl-5 mr-5"><i id="home_icon" class="fa fa-home fa-x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm" style="font-size: .5em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
	</div> 
	<!-- <h6 class="border-bottom mb-1 text-muted pb-2" id="form_title">Update Rates</h6> -->
		<div id="table" class="mx-auto container">
			<table id="example" class="table table-striped table-sm display mx-auto col" style="width:950%; font-size:13px;">
	        	<thead class="thead-dark">
	            	<tr>
		                <th>Vehicle Type</th>
		                <th>Tyre Type</th>
		               	<th>Quantity</th>
		               	<th>Material</th>
		               	<th>Rate</th>
		               	<th>Update</th>
		            </tr>
	        	</thead>
	        	<tbody>
	        		<c:forEach var="rateList" items="${rateList}">
        				<tr class="py-2 text-justify">
        				<td class="demo">${rateList.truckType}</td>
	        			<td class="demo">${rateList.tyreType}</td>
	        			<td class="demo">${rateList.quantity} </td>
	        			<td class="demo">${rateList.materialType}</td>
	        			<td><input type="text" id="r_${rateList.rateId}" class="form-control form-control-sm w-75 border" value="${rateList.rate}"/></td>
	        			<td><button id="${rateList.rateId}" class="btn btn-sm btn-danger text-left">Update Rate</button></td>
	        			</tr>
	        		</c:forEach>
	        	</tbody>
    		</table>
    		
		</div>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_max_cdn_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/font_awesome.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.21/af-2.3.5/b-1.6.3/b-html5-1.6.3/datatables.min.js"></script>
	<script>
		$(document).ready(e =>{
			$(".fa").hover(e =>{
				$(".fa").css({"cursor":"pointer"});
			});

			$("#home_icon").click( e =>{
				window.location.href = "report_panel";
			});

			$("#report").hide();
		});

		// on button click update rate
		$(".btn").click(function(){
			let id = this.id;
			$.ajax({
				type : "POST",
				url:"${home}update_rate",
				data:{'rate_id':this.id,'rate':$("#r_"+this.id).val()},
				success:function(result, status, xhr){
					let innerId = "#"+id;
					$(innerId).removeClass("btn-danger");
					$(innerId).addClass("btn-success");
				},
				"error":function(result, status,xhr){
				}
			});
		});		
	</script>
	
	
 

	
</body>
</html>