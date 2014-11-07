var endpoint = 'http://'+location.host+':8080/axis2/services/SignUpController';

$('#signup').click(function() {
	var userId = $('#userId').val();

	$.ajax({
		type: 'GET',
		url: endpoint + '/execute',
		data: {
			userId: userId,
		},
		success: function(xml) {
			switch($('return',xml).text())
			{
				case '0':
					$('#message').text('アカウント"'+userId+'"を登録しました。');
					break;
				case '1':
					$('#message').text('アカウント"'+userId+'"は既に登録済みです。');
					break;
				case '2':
					$('#message').text('アカウント"'+userId+'"はTwitter上に存在しません。');
					break;
				case '3':
					$('#message').text('データベース上でエラーが発生しました。');
					break;
				default:
					$('#message').text('予期せぬエラーが発生しました。');
					break;
			}
		},
  });
});
