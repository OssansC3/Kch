var endpoint = 'http://localhost:8080/axis2/services/GetKchService';

$(function() {
	var userId = $('#userId').val();

	$.ajax({
		type: 'GET',
		url: endpoint + '/GetKchImageURI',
		data: {
			userId: userId,
		},
		success: function(xml) {
			$('#KchScore').text($('return',xml).text());
		},
  });
});