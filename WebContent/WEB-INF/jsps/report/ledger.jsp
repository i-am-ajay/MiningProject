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
		3) Contractor
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
		<h4 class="border-bottom border-danger mt-1 mx-3 mb-3 pb-2 display-5" id="form_title">Deposite and Expense</h4>
		<f:form method="POST" modelAttribute="supply" action="ledger_entry" id="eledger">
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
					      		<label class="font-weight-bold">Income/Expense</label>
					      		<select id="type" class="form-control fomr-control-sm">
					      			<option value="income">Income</option>
					      			<option value="expense">Expense</option>
					      		</select>
					    	</div>
					    </div>
  						<div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Party</label>
					      		<datalist class="form-conrol form-control-sm" name="party">
					      			<option></option>
					      		</datalist>
					    	</div>
					    </div>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Transaction Type</label>
					      		<datalist class="form-control form-control-sm" name="subType">
					      			<option></option>
					      		</datalist>
					    	</div>
					    </div>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Amount</label>
					      		<input id="amount" class="form-control form-control-sm" placeholder="Enter Amount" name="amount" />
					    	</div>
					    </div>
					    <div class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Remark</label>
					      		<input type="text" id="remark" class="form-control form-control-sm" placeholder="Remark If Any" name="remark" />
					    	</div>
					    </div>
					    <div id="expense_type" class="col-2">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Expense Type</label>
					      		<select id="e_type" class="form-control fomr-control-sm" name="expenseType">
					      			<option value="cash_expense">Cash</option>
					      			<option value="credit_expense">Credit</option>
					      		</select>
					    	</div>
					    </div>
				 	</div>
			  	</div>
			</div>
		</div>
	</div>
			    
	<input type="hidden" id="role" value="${role}" />
	</f:form>
	</div>
	
	<h5 class="display-5">Ledger Of - ${ }</h5>
	<div id="table_section">
		<table id="data_table" class="table table-striped table-sm display mx-auto" style="width:95%; font-size:13px;">
        <thead class="thead-dark">
            <tr>
            	<th>Date</th>
                <th>Particulars</th>
                <th>Credit Amount</th>
                <th>Token</th>
                <th>Remarks</th>
                <th>&emsp;</th>
                <th>Date</th>
                <th>Particular</th>
                <th>Debit Amount</th>
                <th>Token</th>
                <th>Remarks</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach var="item" items="${data_list}">
	            <tr>
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
        </table>
	</div>
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	
<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_max_cdn_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/font_awesome.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/data_table.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.21/af-2.3.5/b-1.6.3/b-html5-1.6.3/datatables.min.js"></script>	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/static_resources/js/header_manipulate.js"></script>-->
	<script>
	// ------------------------------ Page Load Initialization -----------------------------------
		$(document).ready(e =>{
			$("#expense_type").hide();
		});
		$(document).ready();

		// ------------------------------ Page Load Configuration End ---------------------------------
		
		// ------------------------------ On Page Actions ------------------------------------------
		// If type changed to expense then show expense type option i.e cash / credit.
		$("#type").change(e=>{
			if($("#type").val() == "Expense" ){
				$("#expense_type").show();
			}
		})
		

		// submit button click validations
		$("#save_btn").click(e =>{});
		//------------------------------------ On Page Action End --------------------------------------
		
		// ----------------------------------- Ajax Calls from Page -------------------------------------

		// will populate ledger table.
		$('#vehicle_no').focusout(function(){
			$.ajax({
				type: "POST",
				url : "${home}fetch_vehicle",
				data : {"vehicle_no":this.value},
				success: function(result, status, xhr){
					if(result != null && result != ""){
						let json = JSON.parse(result);
						$("#vehicle_type").val(json['vehicle_type']);
						$("#tyre_type").val(json['tyre_type']);
						$("#discount").val(json['discount']);
						
						$("#vehicle_type").attr("readonly",true);
						$("#tyre_type").attr("readonly",true);

					}
					else{
						alert("This vehicle is not registered.");
					}
				},
				error : function(result,status,xhr){
					console.log("error");
				}
			});

		});

	// --------------------------- Support Methods ----------------------------------------
	
	
	// --------------------------- Support Methods End -----------------------------------
	
	
	</script>
</body>
</html>