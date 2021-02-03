<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Journal Entries</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >
	<!--  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css" />-->
</head>
<!-- 
	1) There will be source party and target party.
	2) Amount field will be there to specify how much amount is sent to target party.
	3) A remark field to give any remark.
	4) Type will be Journal Entry
 -->
<body class=mt-1>
	<div class="px-2 pb-2 m-auto" style="width:95%;">
		<div class="row">
			<h4 class="border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5 col-8" id="form_title">Journal Entry</h4>
			<div class="col-2 align-right ml-auto pl-5 mt-2 mr-5"><i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i></div>
			<div class="col-1"><a class="btn btn-danger btn-sm mt-2" style="font-size: .6em;" href="${pageContext.request.contextPath}/logout">Logout</a></div>
		</div>
		<form method="POST" action="journal_entries" id="eledger">
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
					      		<label class="font-weight-bold">Debtor</label>
					      		<input list="party_d" class="form-control form-control-sm" placeholder="Choose Party" id="party_debtor" name="debtor">
					      		<datalist id="party_d">
					      		<c:forEach var="party" items="${party_list}"> 
					      			<option>${party.value}</option>
					      		</c:forEach>
					      		</datalist>
					    	</div>
					    </div>
  						<div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Creditor</label>
					      		<input list="party_c" class="form-control form-control-sm" placeholder="Choose Party" id="party_creditor" name="creditor">
					      		<datalist id="party_c">
					      		<c:forEach var="party" items="${party_list}"> 
					      			<option>${party.value}</option>
					      		</c:forEach>
					      		</datalist>
					    	</div>
					    </div>
					    <%-- <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Transaction Type</label>
					      		<select class="form-control form-control-sm" id="subType_list" class="form-control form-control-sm" name="sub_type">
					      			<c:forEach var="subtype" items="${subtype_list}">
					      				<option value="${subtype.key}">${subtype.value}</option>
					      			</c:forEach>
					      		</select>
					    	</div>
					    </div> --%>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Amount</label>
					      		<input id="amount_id" class="form-control form-control-sm" placeholder="Enter Amount" name="amount" />
					    	</div>
					    </div>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Remark</label>
					      		<input type="text" id="remark" class="form-control form-control-sm" placeholder="Remark If Any" name="remarks" />
					    	</div>
					    </div>
					    <c:if test="${enable_date == true }">
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Date</label>
					      		<input type="date" id="date_" class="form-control form-control-sm" name="entry_date" min="${minDate }" max="${maxDate}"/>
					    	</div>
					    </div>
					   <!--  <div class="col-1">
					    	<div class="form-check">
					      		<br/>
					      		<input type="checkbox" id="enable_date" class="form-check-input align-middle mt-3"/>
					    	</div>
					    </div> -->
					    </c:if>
				 	</div>
				 	<input type="submit" id="save_btn" class="btn btn-sm btn-success btn-block w-50 mx-auto" value="submit"/>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="hidden" id="role" value="${role}" />
	</form>
	<h5 class="display-5">Ledger of - ${debtor_}</h5>
	</div>
	<div id="table_section" class="row">
		<div class="col" id="credit_table">
			<table id="data_table" class="table table-striped table-sm display mx-auto col" style="width:95%; font-size:13px;">
		        <thead class="thead-dark">
		            <tr class="text-center">
		            	<th>Date</th>
		                <th>Particulars</th>
		                <th>Debtor Amount</th>
		                <th>Remarks</th>
		            </tr>
		        </thead>
		        <tbody id="table_body">
			        	<c:forEach var="record" items="${ledger_records}">
			        		<c:choose>
			        			<c:when test="${(record[1].equalsIgnoreCase('opening balance') || record[1].equalsIgnoreCase('closing balance')) &&  !record[2].equalsIgnoreCase('')}">
			        				<tr class="table-success">
				        				<td>${record[0]}</td>
				        				<td class="text-success font-weight-bold">${record[1]}</td>
				        				<td class="font-weight-bold">${record[2]}</td>
				        				<td>${record[4]}</td>
			        				</tr>
			        			</c:when>
			        			<c:when test="${!record[2].equalsIgnoreCase('') && !record[2].equalsIgnoreCase('0.0')}">
			        				<tr class="table-success">
				        				<td>${record[0]}</td>
				        				<td>${record[1]}</td>
				        				<td>${record[2]}</td>
				        				<td>${record[4]}</td>
			        				</tr>
			        			</c:when>
			        		</c:choose>
			        		<%-- <c:if test="${!record[2].equalsIgnoreCase('')}">
			        			<tr class="table-success">
			        				<td>${record[0]}</td>
			        				<td>${record[1]}</td>
			        				<td>${record[2]}</td>
			        				<td>${record[4]}</td>
			        			</tr>
			        		</c:if> --%>
			        	</c:forEach>
			    </tbody>
		 	</table>
		</div>
		<div class="col" id="debit_table">
			<table id="data_table" class="table table-striped table-sm display mx-auto col" style="width:95%; font-size:13px;">
		        <thead class="thead-dark">
		            <tr class="text-center">
		            	<th>Date</th>
		                <th>Particulars</th>
		                <th>Credit Amount</th>
		                <th>Remarks</th>
		            </tr>
		        </thead>
		        <tbody id="table_body">
			        	<c:forEach var="record" items="${ledger_records}">
			        		<c:choose>
			        			<c:when test="${(record[1].equalsIgnoreCase('opening balance') || record[1].equalsIgnoreCase('closing balance')) &&  (!record[3].equalsIgnoreCase(''))}">
			        				<tr class="table-danger">
				        				<td>${record[0]}</td>
				        				<td class="text-danger font-weight-bold">${record[1]}</td>
				        				<td class="font-weight-bold">${record[3]}</td>
				        				<td>${record[4]}</td>
			        				</tr>
			        			</c:when>
			        			<c:when test="${!record[3].equalsIgnoreCase('') && !record[3].equalsIgnoreCase('0.0')}">
			        				<tr class="table-danger">
				        				<td>${record[0]}</td>
				        				<td>${record[1]}</td>
				        				<td>${record[3]}</td>
				        				<td>${record[4]}</td>
			        				</tr>
			        			</c:when>
			        		</c:choose>
			        		<%-- <c:if test="${!record[3].equalsIgnoreCase('')}">
			        			<tr class="table-danger">
			        				<td>${record[0]}</td>
			        				<td>${record[1]}</td>
			        				<td>${record[3]}</td>
			        				<td>${record[4]}</td>
			        			</tr>
			        		</c:if> --%>
			        	</c:forEach>
			    </tbody>
		 	</table>
		</div>
		<%-- <div class="col-6" id="debit_table"></div>
		<table id="data_table" class="table table-striped table-sm display mx-auto col" style="width:95%; font-size:13px;">
        <thead class="thead-dark">
            <tr class="text-center">
            	<th>Date</th>
                <th>Particulars</th>
                <th>Credit Amount</th>
                <th>Remarks</th>
                <th>&emsp;</th>
                <th>Date</th>
                <th>Particular</th>
                <th>Debit Amount</th>
                <th>Remarks</th>
            </tr>
        </thead>
        <tbody id="table_body">
        	<tr>
	        	<c:forEach var="record" items="${stringList}">
	        		
	        	</c:forEach>
        	</tr>
	            <tr>
	            	<td>Hello</td>
	            	<td>Bollo</td>
	           </tr>
	           </tbody>
	             	<td>${item.token}</td>
	                <td>${item.driverName}</td>
	                <td>${item.driverNumber}</td>
	                <td>${item.vehicle.vehicleNo}</td>
	                <td>${item.vehicle.vehicleType}</td>
	                <td>${item.vehicle.tyreType}</td>
	                <td>${item.material }</td>
	                <td>${item.quantity}</td>
	                <td>${item.paymentType }</td>
	                <td>${item.rate}</td>
	                <td>${item.discount}</td>
	                <td>${item.finalRate}</td>
	                <td>${item.nrl}</td>
	                <td>${item.driverReturn}</td>
	                <td>${item.salesDate}</td>
	            </tr>
            </c:forEach>
        </tbody>
        </table> --%>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
	// ------------------------------ Page Load Initialization -----------------------------------
		$(document).ready( e=>{
				$("#home_icon").hover( e => {
					$("#home_icon").css({"cursor":"pointer"})
				});

				$("#home_icon").click( e =>{
					window.location.href = "admin_panel";
				});
		});
		// disable back button

		// disable submit button on submit.
		$('form').submit(function () {
		    //$(this).find(':submit').attr('disabled', 'disabled');
		    $('#save_btn').attr('disabled','disabled');
		});

		// ------------------------------ Page Load Configuration End ---------------------------------
		
		// ------------------------------ On Page Actions ------------------------------------------
		// If type changed to expense then show expense type option i.e cash / credit.
		$("#type_id").change(e=>{
			if($("#type_id").val().toLowerCase() == "expense"){
				$("#e_type").append('<option value="credit_expense">Credit</option>');
			}
			else{
				$("#e_type").find('option[value="credit_expense"]').remove();
			}
		})
		

		// submit button click validations
		$("#save_btn").click(e =>{});
		//------------------------------------ On Page Action End --------------------------------------
		
		// ----------------------------------- Ajax Calls from Page -------------------------------------

		// will populate ledger table.
		// call an ajax function on client selection
		$("#party_name").focusout(e =>{
			let val = $("#party_type").val();
			$.ajax({
				type: "POST",
				url : "${home}get_party_ledger",
				data : {"name":$("#party_name").val()},
				success: function(result, status, xhr){
					console.log("success");
					//console.log(result);
					//console.log(status);
					$("#table_body").empty();
					if(result){
						let array = JSON.parse(result);
						console.log(array);
						array.forEach(e =>{
							let row = "<tr>"+
										"<td class='table-success'>"+e.date+"</td>"+
										"<td class='table-success'>"+e.cParticular+"</td>"+
										"<td class='table-success'>"+e.creditAmount+"</td>"+
										"<td class='table-success'>"+e.cRemarks+"</td>"+
										"<td>&emsp;</td>"+
										"<td class='table-danger'>"+e.date+"</td>"+
										"<td class='table-danger'>"+e.dParticular+"</td>"+
										"<td class='table-danger'>"+e.debitAmount+"</td>"+
										"<td class='table-danger'>"+e.dRemarks+"</td>"+
										"<tr>"
							$("#table_body").append(row);
						})
						
					}
				},
				error: function(result, status, xhr){
					console.log("error");
					console.log(result);
				}
			})
		});

	// --------------------------- Support Methods ----------------------------------------
	
	
	// --------------------------- Support Methods End -----------------------------------
	
	
	</script>
</body>
</html>