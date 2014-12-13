$("#ajaxform").submit(function(e)
{
	var postData = $(this).serializeArray();
	var formURL = $(this).attr("action");
	$.ajax(
	{
		url : formURL,
		type: 'POST',
		dataType : 'json',
		contentType:'application/json;charset=UTF-8',
		data : postData,
		error: function(jqXHR, textStatus, errorThrown)
		{

		}
	});
    //e.preventDefault();	//STOP default action
});

$("#ajaxform").submit(); //SUBMIT FORM