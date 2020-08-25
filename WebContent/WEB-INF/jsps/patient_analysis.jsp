<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Analysis</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body class=mt-1>
	<%@ include file = "header_health_report_card.jsp" %>
	<div class="container p-2 m-auto">
		<h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Analysis</h4>
		<f:form method="POST" modelAttribute="patientAnalysis" action="analysis_save">
		  <div class="form-row">
		  	 <div class="form-group col-md-3">
			      <label for="phone" class="font-weight-bold">Reg No <span class="text-danger">*</span></label>
			      <f:input class="form-control form-control-sm" id="registration" placeholder="Registration Number" path="patientMaster.registrationNumber" title="Valid Number"/>
			    </div>
			    <div class="form-group col-md-4">
			      <label for="patientName" class="font-weight-bold">Patient Name <span class="text-danger">*</span></label>
			      <f:input class="form-control form-control-sm" id="name" placeholder="Patient Name" path="patientMaster.name"/>
			    </div>
			   
			    <div class="form-group col-md-3">
			      <label for="phone" class="font-weight-bold">Phone No <span class="text-danger">*</span></label>
			      <f:input class="form-control form-control-sm" id="phone" placeholder="Mobile Number" path="patientMaster.mobileNo" title="Valid Number"/>
			    </div>
			    <div class="form-group col-md-2">
			      	<label for="Gender" class="font-weight-bold">Gender <span class="text-danger">*</span></label>
			      	<div>
			      		<div class="form-check-inline">
    						<f:radiobutton class="form-check-input mx-2" name="gender" id="mgender" value="m" path="patientMaster.gender" /><span class="mx-2"> Male</span> 
    						<f:radiobutton class="form-check-input ml-4" name="gender" id="fgender" value="f" path="patientMaster.gender" /><span class="mx-2">Female</span>
    					</div>
    				</div>
			    </div>	    
		   </div>
		   
		   <!-- Patient Vitals -->
		   <!--  <h4 class="border-bottom m-3 text-muted pb-2" id="form_title">Patient Report Card</h4>-->
		   <!-- Card Vitals -->
		   <div class="row border-top pt-2">
		   <div class="col">
		   <div class="card text-dark bg-light px-3 mb-3" >
  				<div class="card-header mt-1">Patient Analysis</div>
  				<div class="card-body">
  					<div class="row">
  						<div class="col-md-6">
					    	<div class="form-group">
					      		<label class="font-weight-bold">Test Result</label>
					      		<div>
				      				<div class="form-check-inline py-1">
	    								<f:radiobutton class="form-check-input mx-2"  id="presult" value="positive" path="covidResult" /><span class="mx-2"> Positive</span>
	    								<f:radiobutton class="form-check-input ml-4" id="nresult" value="negative" path="covidResult" /><span class="mx-2">Negative</span>
	    							</div>
    							</div>
					    	</div>
					    	<div class="form-group">
					      		<label for="testDate" class="font-weight-bold">Covid Test Result Date</label>
					      		<f:input type="date" id="testDate" class="form-control form-control-sm" path="covidTestDate" />
					    	</div>
					    	<div class="form-group">
					    		<div>
					      			<label class="font-weight-bold">Tested In</label>
					      		</div>
					      		<div class="form-check-inline py-1">
	    								<f:radiobutton class="form-check-input mx-2"  id="slocation" value="SGRH" path="testSite" /><span class="mx-2"> SGRH</span>
	    								<f:radiobutton class="form-check-input ml-4" id="olocation" value="Outside" path="testSite" /><span class="mx-2">Outside</span>
	    							</div>
					    	</div>
					    	<div class="form-group">
					      		<label for="pCondition" class="font-weight-bold">Patient Condition</label>
					      		<f:select class="form-control form-control-sm" id="pCondition" path="patientCondition" placeholder="Patient Condition">
									<option selected>Fair</option>
									<option>Poor</option>
									<option>Sick</option>
									<option>Stable</option>
								</f:select>
					    	</div>
					    	<div class="form-group">
					      		<label for="pCategory" class="font-weight-bold">Patient Category</label>
					      		<f:select class="form-control form-control-sm" id="pCategory" path="patientCategory" placeholder="Patient Category">
									<option selected>Asymptomatic</option>
									<option>Mild</option>
									<option>Moderate</option>
									<option>Severe</option>
									<option>Whether critical / Non Critical</option>
								</f:select>
					    	</div>
					    	<!-- Clinical Status -->
					    	<div class="form-group">
					      		<label for="pStatus" class="font-weight-bold">Clinical Status</label>
					      		<f:select class="form-control form-control-sm" id="pStatus" path="clinicalStatus" placeholder="Clinical Status">
									<option selected>1. In isolation ward with oxygen.</option>
									<option>2. In isolation ward - room air</option>
									<option>3. In ICU on oxygen including NIV</option>
									<option>4. In ICU room air</option>
									<option>5. In ICU invasive ventilator</option>
								</f:select>
					    	</div>
					    	<!-- Symptoms -->
					    	<div>
					      		<label for="pStatus" class="font-weight-bold">Symptoms</label>
									<div class="form-check">
					      				<f:checkbox class="form-check-input mr-3 ml-1" id="psymptoms" path="symptoms" value="Absence of Fever"/><label class="ml-4 px-2">Absence of Fever</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Fever"/><label class="ml-4 px-2">Fever</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Absence of Fever"/><label class="ml-4 px-2">Cough</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Absence of Fever"/><label class="ml-4 px-2">Fatigue</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Shortness of Breath/Breathing Difficulty"/><label class="ml-4 px-2">Shortness of Breath/Breathing Difficulty</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Expectoration"/><label class="ml-4 px-2">Expectoration</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Myalgia"/><label class="ml-4 px-2">Myalgia</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Rhinorrhea, sore throat"/><label class="ml-4 px-2">Rhinorrhea, sore throat</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Diarrhea"/><label class="ml-4 px-2">Diarrhea</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Loss of smell(anosmia) or loss of taste(ageusia)"/><label class="ml-4 px-2">Loss of smell(anosmia) or loss of taste(ageusia)</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Reduced Alertness"/><label class="ml-4 px-2">Reduced Alertness</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Reduced Mobility"/><label class="ml-4 px-2">Reduced Mobility</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Loss of appetite"/><label class="ml-4 px-2">Loss of appetite</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="psymptoms" path="symptoms" value="Dellirium"/><label class="ml-4 px-2">Dellirium</label>
					      			</div>
					    	</div>
					    	<div>
					      		<label for="pStatus" class="font-weight-bold">Treatment Given</label>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="ptreatment" path="treatmentGiven" value="Azithromycine"/><label class="ml-4 px-2">Azithromycine</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="ptreatment" path="treatmentGiven" value="Remdesivir"/><label class="ml-4 px-2">Remdesivir</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="ptreatment" path="treatmentGiven" value="Plasma Therapy"/><label class="ml-4 px-2">Plasma Therapy</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="ptreatment" path="treatmentGiven" value="Hydroxychloroquine"/><label class="ml-4 px-2">Hydroxychloroquine</label>
					      			</div>
					      			<div class="form-check">
					      				<f:checkbox class="form-check-input mr-2 ml-1" id="ptreatment" path="treatmentGiven" value="Shortness of Breath/Breathing Difficulty"/><label class="ml-4 px-2">Tocilizumab f. Dexamethasone</label>
					      			</div>
				    		</div>
				    </div>
			  </div>
			  </div>
			  </div>
	</div>
	</div>
			    
	<input type="submit" class="btn btn-small btn-secondary btn-block w-75 mx-auto" value="Save Analysis"/>
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
				$("#gender").attr("required","true");
				$("#address").attr("required","true");
			});
				/* // disable oxygen device.
				let oValue = $("#oSupplementation").val().toLowerCase();
				if(oValue === "nil"){
					$("#o2Device").attr("disabled",true);
					$("#o2Device").val(null);
				}
				$("#oSupplementation").change(
					e =>{
						if($("#oSupplementation").val().toLowerCase() === "nil"){
							$("#o2Device").attr("disabled",true);
							$("#o2Device").val(null);
						}
						else{
							$("#o2Device").attr("disabled",false);
							$("#o2Device").val("Nasal Prongs");
						}
					}
				);

				// ventilator optio
				if( $('input[name="ventilationNeeded"]:checked').val() === 'false'){
					$("#ventilatorMode").attr("disabled",true);
					$("#ventilatorMode").val(null);
				}
				$('input[name="ventilationNeeded"]').change(
					e =>{
						if( $('input[name="ventilationNeeded"]:checked').val() === 'false'){
							$("#ventilatorMode").attr("disabled",true);
							$("#ventilatorMode").val(null);
						}
						else{
							$("#ventilatorMode").attr("disabled",false);
							$("#ventilatorMode").val("NIV");
						}
					}
				);			
			// ventilator optio
			if( $('input[name="changeInTreatment"]:checked').val() === 'false'){
				$("#reason").attr("disabled",true);
				$("#reason").attr("placeholder",null);
			}
			$('input[name="changeInTreatment"]').change(
				e =>{
					if( $('input[name="changeInTreatment"]:checked').val() === 'false'){
						$("#reason").attr("disabled",true);
						$("#reason").attr("placeholder",null);
					}
					else{
						$("#reason").attr("disabled",false);
						$("#reason").attr("placeholder","Reason of Change");
						
					}
				}
			);
		}
			
		) */
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
		
		// Changes the page heading for mobile screen and tablets.
		$(document).ready( e => {
			const screenSize = window.screen.width;
			if(screenSize < 1000){
				$("#middle_col").replaceWith("<div id='middle_col' class='col-8'><h6 class='text-center display-5'>Sir Ganga Ram Hospital</h6><p class='text-center'>Patient Health Report Card</p></div>");
				$("#form_title").removeClass("m-3");	
				//$("#farewell_note").removeClass("display-4").addClass("display-5");
			}
		});
		// on registration focus out get employee details through ajax call 
		$("#registration").focusout( e =>{
			$.ajax({
				type: "POST",
				url : "${home}patient_details",
				data : {"reg_no":$("#registration").val()},
				success: function(result, status, xhr){
					if(result != null && result != ""){
						let json = JSON.parse(result);
						console.log(json.name)
						$("#name").attr("disabled","true");
						$("#phone").attr("disabled","true");
						$("#fgender").attr("disabled","true");
						$("#mgender").attr("disabled","true");

						$("#name").val(json.name);
						$("#phone").val(json.phone);
						$("#reg_no").val(json.reg_no);
						let gender = json.gender;
						if(gender == 'm'){
							$("#mgender").prop("checked",true);
							
						}
						else{
							$("#fgender").prop("checked",true);
						}
					}
					else{
						$("#name").attr("disabled",false);
						$("#phone").attr("disabled",false);
						$("#fgender").attr("disabled",false);
						$("#mgender").attr("disabled",false);
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