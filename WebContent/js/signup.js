var endpoint = 'http://localhost:50080/axis2/services/SignUpAccount';

$('#signup').click(function() {
	var userId = $('#userId').val();

	$.ajax({
		type: 'GET',
		url: endpoint + '/registerAccount',
		data: {
			userId: userId,
		},
		success: function(xml) {
			switch($('return',xml))
			{
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
			}
		},
  });
});