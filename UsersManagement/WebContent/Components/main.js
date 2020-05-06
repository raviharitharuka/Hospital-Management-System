 $(document).ready(function() {
 if ($("#alertSuccess").text().trim() == "") {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
 });
 
//SAVE ============================================
 $(document).on("click", "#btnSave", function(event) {
 // Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();

 // Form validation-------------------
 var status = validateUsersForm();
 if (status != true) {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }

 // If valid------------------------

 var type = ($("#hidUsersIDSave").val() == "") ? "POST" : "PUT";

 $.ajax({
 	url : "UsersAPI",
 	type : type,
 	data : $("#formUsers").serialize(),
 	dataType : "text",
 	complete : function(response, status) {
 		onUsersSaveComplete(response.responseText, status);
 	}
 });
 });	
 function onUsersSaveComplete(response, status) {
 	if (status == "success") {
 		var resultSet = JSON.parse(response);
 		if (resultSet.status.trim() == "success") {
 			$("#alertSuccess").text("Successfully saved.");
 			$("#alertSuccess").show();
 			$("#divUsersGrid").html(resultSet.data);
 		} else if (resultSet.status.trim() == "error") {
 			$("#alertError").text(resultSet.data);
 			$("#alertError").show();
 		}
 	} else if (status == "error") {
 		$("#alertError").text("Error while saving.");
 		$("#alertError").show();
 	} else {
 		$("#alertError").text("Unknown error while saving..");
 		$("#alertError").show();
 	}
 	$("#hidUsersIDSave").val("");
 	$("#formUsers")[0].reset();
 }


	// UPDATE==========================================
	 $(document).on("click",".btnUpdate",function(event) {
	 $("#hidUsersIDSave").val($(this).closest("tr").find('#hidUsersIDUpdate').val());
	 $("#name").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#email").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#password").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#role").val($(this).closest("tr").find('td:eq(3)').text());
	 });
	

 // Remove==========================================
	$(document).on("click",".btnRemove",function(event) {
		$.ajax(
			{
			url : "UsersAPI",
			type : "DELETE",
			data : "id="+$(this).data("id"),
			dataType : "text",
			complete : function(response, status) {
			
				onUsersDeleteComplete(response.responseText, status);
			}
		});
	});
	
	 function onUsersDeleteComplete(response, status) {
		 	if (status == "success") {
		 		var resultSet = JSON.parse(response);
		 		if (resultSet.status.trim() == "success") {
		 			$("#alertSuccess").text("Successfully deleted.");
		 			$("#alertSuccess").show();
		 			$("#divUsersGrid").html(resultSet.data);
		 		} else if (resultSet.status.trim() == "error") {
		 			$("#alertError").text(resultSet.data);
		 			$("#alertError").show();
		 		}
		 	} else if (status == "error") {
		 		$("#alertError").text("Error while deleting.");
		 		$("#alertError").show();
		 	} else {
		 		$("#alertError").text("Unknown error while deleting..");
		 		$("#alertError").show();
		 	}
		 }

	// CLIENT-MODEL============================================
    function validateUsersForm() {

    	// RegNo-------------------------------
    		if ($("#name").val().trim() == "") {
    			return "Insert Name.";
    		}
    		// NAME-----------------------------
    		if ($("#email").val().trim() == "") {
    			return "Insert Email.";
    		}

    		// Address------------------------------
    		if ($("#password").val().trim() == "") {
    			return "Insert Password.";

    		}   
    		// Phone-------------------------------
    		if ($("#role").val().trim() == "") {
    			return "Insert Role.";

    		}
	  	if(($("#name").val().trim() != "") && ($("#email").val().trim() != "") && ($("#password").val().trim() != "") && ($("#role").val().trim() != "")) {
      			 window.location.reload();
    	}
    		return true;
    		
    }
    

