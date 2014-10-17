var endpoint = 'http://localhost:8080/axis2/services/Axis2Demo';

$('#plus').click(function() {
	var p1 = $('#p1').val();
	var p2 = $('#p2').val();

	$.ajax({
		type: 'GET',
		url: endpoint + '/plus',
		data: {
			p1: p1,
			p2: p2,
		},
		success: function(xml) {
			$('#ans').val($('return', xml).text());
		},
  });
});