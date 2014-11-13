var check = 'http://' + location.host + ':8080/axis2/services/GetKchService';

$('#load').click(function() {
	var userId = $('#input').val();
	if(userId!="") {
		$.ajax({
			type : 'GET',
			async : false,
			url : check + '/isRegistered',
			data : {
				userId : userId,
			},
			success : function(xml) {
				if ($('return', xml).text() == 'true') {
					location.href = 'kch.html?userId=' + userId;
				} else {
					$('#message').text("ユーザー" + userId + "は登録されていません");
				}
			},
		});
	} else {
		$('#message').text("ユーザーIDが入力されていません");
	}
});
