<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ledger</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
</head>
<!-- 
	1) We have to change client to party.
	2) Different type of parties will be :
		1) Self
		2) Owner
		3) Comission Agent
		4) Sanchalan Person
		5) Office 
		6) Other
		
	3) There will be subtypes that will decide type of transactions based on party.
		If Income :
		Party client -> Client Deposite
		Party any other than client -> Other Deposite
		
		If expense :
		Type cash : 
		Party Comission Agent -> comission_expense
		Party Sanchalan Person -> sanchalan_expense
		Party Office -> Office_expense
		Any other situation -> other_expense
		
		Type Credit : 
		Party Sanchalan -> credit_sanchalan_expense
		Any Other situation -> credit_other_expense
 -->
<body class=mt-1>
	<div class="px-2 pb-2 m-auto" style="width:95%;">
		<div class="row">
			<h4 class=" col-8 border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5" id="form_title">Ledger</h4>
			<div class="col-2 align-right ml-auto pl-5 mr-5"><i id="home_icon" class="fa fa-home fa-x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm" style="font-size: .5em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<form method="POST" action="ledger_report" id="eledger">
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <div class="row pt-1">
		   <div class="col">
		   <div class="text-dark bg-light px-3 mb-3 mx-auto" style="{transform:scale(0.8);}">
  				<div class="card-body">
  					<div class="row">
  						<div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Party</label>
					      		<input list="party" class="form-control form-control-sm" placeholder="Choose Party" id="party_name" name="party">
					      		<datalist id="party">
					      		<option>Cash</option>
					      		<option>Credit</option>
					      		<option>Bank</option>
					      		<c:forEach var="party" items="${party_list}"> 
					      			<option>${party.value}</option>
					      		</c:forEach>
					      		</datalist>
					    	</div>
					    </div>
					<div class="form-group col-md-2">
	      				<label for="f_date" class="font-weight-bold">From Date</label>
				      	<input type="date" class="form-control form-control-sm" id="fDate" name="f_date"/>
				    </div>	
				    <div class="form-group col-md-2">
				      	<label for="tDate" class="font-weight-bold">To Date</label>
				      	<input type="date" class="form-control form-control-sm" id="tDate" name="t_date" />
				    </div>	
				 	</div>
				 	<input type="submit" class="btn btn-sm btn-success btn-block w-50 mx-auto" value="submit"/>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="hidden" id="role" value="${role}" />
	</form>
	</div>
	<h5 class="display-5">Ledger of - ${party_}</h5>
	<div id="table_section" class="row">
		<div class="col" id="credit_table">
			<table id="data_table" class="table table-striped table-sm display mx-auto col" style="width:95%; font-size:13px;">
		        <thead class="thead-dark">
		            <tr class="text-center">
		            	<th>Date</th>
		            	<th>Token</th>
		                <th>Particulars</th>
		                <th>Debit Amount</th>
		                <th>Credit Amount</th>
		                <th>Remarks</th>
		                <c:if test="${allow_cancel == true }">
		                	<th>Cancel Record</th>
		                </c:if>
		            </tr>
		        </thead>
		        <tbody id="table_body">
			        <c:forEach var="record" items="${ledger_records}">
			        	<tr id="b_${record[6]}">
	        				<td>${record[0]}</td>
	        				<td>${record[7]}</td>
	        				<td>${record[1]}</td>
	        				<td class="table-success">${record[2]}</td>
	        				<td class="table-danger">${record[3]}</td>
	        				<td>${record[4]}</td>
	        				<c:if test="${allow_cancel == true }">
	        					<td>
	        						<c:choose>
	        							<c:when test="${record[5].equals('f') }">
	        								<button class="btn btn-danger cancel_btn" id="${record[6]}" disabled></button>
	        							</c:when>
	        							<c:otherwise>
	        								<button class="btn btn-danger cancel_btn" id="${record[6]}"></button>
	        							</c:otherwise>
	        						</c:choose>
	        					</td>
	        				</c:if>
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
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
	// ------------------------------ Page Load Initialization -----------------------------------
		$(document).ready(e =>{
			$(".fa").hover(e =>{
				$(".fa").css({"cursor":"pointer"});
			});

			$("#home_icon").click( e =>{
				window.location.href = "report_panel";
			});
		});

		// ------------------------------ Page Load Configuration End ---------------------------------
		
		// ------------------------------ On Page Actions ------------------------------------------
		// If type changed to expense then show expense type option i.e cash / credit.
		

		// submit button click validations
		$("#save_btn").click(e =>{});
		
		$(".cancel_btn").click(function(){
			let id = this.id;
			let rowId = "#b_"+id;
			cancelEntry(id, rowId)
		})
		//------------------------------------ On Page Action End --------------------------------------
		
		// ----------------------------------- Ajax Calls from Page ----------------------------------
		 function cancelEntry(id, rowId){
			let result = confirm("Do you want to cancel ledger entry?"); 
			if(result){
				$.ajax({
					type : 'POST',
					url : "${home}cancel_entries",
					data: {"id":id},
					success: function(result, status, xhr){
						if(result){
							$(rowId).hide();
						}
					},
					error: function(resut, status, xhr){
						
					}
				});
			}
		}
		//------------------------------------ End Ajax Calls ----------------------------------------
		
	// --------------------------- Support Methods ----------------------------------------
	
	
	// --------------------------- Support Methods End -----------------------------------
	
	
	</script>
</body>
</html>