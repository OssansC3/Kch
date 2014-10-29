var endpoint = 'axis2/services/GetKchService';

$(function() {
	var userId = getUrlVars()["userId"];
	if (userId == "undefined") {
		userId = "";
	}
	$('#message').text("アカウント" + userId);
	$.ajax({
		type : 'GET',
		async: false,
		url : endpoint + '/GetKchImageURI',
		data : {
			userId : userId,
		},
		success : function(xml) {
			$('#KchScore').text($('return', xml).text());
		},
	});
});

function getUrlVars() {
	var vars = [], hash;
	var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for (var i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = hash[1];
	}
	return vars;
}
