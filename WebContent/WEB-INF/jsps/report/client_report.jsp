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
	<div class="p-2 m-auto">
		<div id="search_div" class="container">
		<div class="row">
			<h6 class="border-bottom mx-1 text-muted pb-2 col-8" id="form_title">Client Report</h6>
			<div class="col-2 align-right ml-auto pl-5 mr-5"><i id="home_icon" class="fa fa-home fa-x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm" style="font-size: .5em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<div  class="mt-3 bg-light">
			<form action="client_report" method="POST">
			 <div class="form-row search p-1">
		  	 <div class="form-group col-md-2">
			      <label for="vehicle_no" class="font-weight-bold">Client Name</label>
			      <input type="text" class="form-control form-control-sm" id="c_name" placeholder="Enter Client Name" name="name"/>
			    </div>
			     <div class="form-group col-md-2">
			      <label for="name" class="font-weight-bold">Client Contact</label>
			      <input type="text" class="form-control form-control-sm" id="c_contact" placeholder="Enter Client Contact" name="contact" />
			    </div>
			    <div class="col-md-1"></div>
			    <div class="form-group col-md-3">
		    		<div><label class="font-weight-bold">Client Type</label></div>
		    		<div class="form-check form-check-inline">
		    			<input type="radio" class="form-check-input belong" value="1" id="sbelongs" name="belongs_to"/><label class="form-check-label">Self</label></div>
		    		<div class="form-check form-check-inline">
		    			<input type="radio" class="form-check-input belong" value="2" id="obelongs" name="belongs_to"/><label class="form-check-label">Owner</label></div>
		    		<div class="form-check form-check-inline">
		    			<input type="radio" class="form-check-input belong" value="3" id="cbelongs" name="belongs_to"/><label class="form-check-label">Contractor</label></div>
				</div>
				<div class="form-group col-md-1"></div>
				<div class="form-group col-md-3">
					<input id="button" type="submit" class=" btn btn-secondary btn-sm btn-block mt-2 mx-auto text-center" value="search" />
				</div>	
			 	<!--  <div class="form-group col-md-2">
			      	<label for="f_date" class="font-weight-bold">From Date</label>
			      	<input type="date" class="form-control form-control-sm" id="fDate" name="f_date"/>
			    </div>	
			    <div class="form-group col-md-2">
			      	<label for="tDate" class="font-weight-bold">To Date</label>
			      	<input type="date" class="form-control form-control-sm" id="tDate" name="t_date" />
			    </div>	 -->
			   </div>
			    <!-- <input id="button" type="submit" class=" btn btn-secondary btn-sm btn-block w-50 mt-2 mx-auto text-center" value="search" /> -->
			</form>
		</div>
		</div>
		<h6 class="border-bottom mb-1 text-muted pb-2" id="form_title"></h6>
		<div id="table" class="mx-auto container">
			<table id="example" class="display compact cell-border" style="width:100%">
	        	<thead>
	            	<tr>
	                	<th>Name</th>
		                <th>Type</th>
		                <th>Contact</th>
		                <th>Address</th>
		                <th>Commission</th>
		                <th>Discount</th>
		                <th>Creation Date</th>
		            </tr>
	        	</thead>
	        	<tbody>
	        		<c:forEach var="clientList" items="${clientList}">
        			<tr class="py-2 text-justify">
        				<td class="demo">${clientList.name}</td>
	        			<td class="demo">${clientType.description}</td>
	        			<td class="demo">${clientList.clientContact} </td>
	        			<td class="demo">${clientList.clientAddress}</td>
	        			<td class="demo">${clientList.comission}</td>
	        			<td id="address">${clientList.discount}</td>
	        			<td>${clientList.creationDate}</td>
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
				window.location.href = "report_panel";
			});

			$("#report").hide();
		});
		
		/// calculate age
		function calculate_age(birth_date){
			let dob = moment(birth_date);
			let current_date = moment(Date.now());
			$("#age").val(current_date.diff(dob,'Years'));
		}

		// Changes the page heading for mobile screen and tablets.
		$(document).ready( e => {
			const screenSize = window.screen.width;
			if(screenSize < 1000){
				$("#middle_col").replaceWith("<div id='middle_col' class='col-8'><h6 class='text-center display-5'>Sir Ganga Ram Hospital</h6><p class='text-center'>Patient Feedback Report</p></div>");
				$("#form_title").removeClass("m-3");	
				$("#search_div").hide();
			}
		});

		// on update checkbox click
		function updateFunction(x){
			let id = x.id.replace("c_","#");
			let id_server = id.replace("#","");
			let icmr_id = "#"+x.id.replace("c_","i_");
			let srf_id = "#"+x.id.replace("c_","s_")
			
			// ajax call to update data in database.
			$.ajax({
				type: "POST",
				url : "${home}update_data",
				data : {"id":id_server , "icmr_id": $(icmr_id).val(), "srf_id":$(srf_id).val() }, //,  , "srf_Id": srf_id
				success: function(result, status, xhr){
					console.log(result);
					//table.draw();
				},
				error : function(result,status,xhr){
					$("#name").attr("disabled",false);
					$("#phone").attr("disabled",false);
					$("#fgender").attr("disabled",false);
					$("#mgender").attr("disabled",false);
				}
			});
			
			table.row(id).remove().draw();
		}
		
		$("input[type=checkbox]").click( e => {
			alert('hello');
			console.log(this);
		});
	</script>
	
	
 

	
</body>
</html>