var endpoint = 'http://' + location.host + ':8080/axis2/services/GetKchController';
var srcH = '<img src="images/';
var srcT = '"/>';

$(function() {
	var userId = getUrlVars()["userId"];
	if (userId == "undefined") {
		userId = "";
	}
	$('#message').text("アカウント" + userId);

	$.ajax({
		type : 'GET',
		async : true,
		url : endpoint + '/executeImage',
		data : {
			userId : userId,
		},
		success : function(xml) {
			$('#kchImage').append(srcH + $('return', xml).text() + srcT);
		},
	});
});

function drawChart() {
	var dataArray = [['Number', 'Like', 'Joy', 'Anger']];
	var scoreArray = getEmotionArray();
	$.merge(dataArray, scoreArray);
	var line_data = google.visualization.arrayToDataTable(dataArray);

	var line_options = {
		legend : {
			position : 'top'
		},
		title : '感情の変化',
		vAxis : {
			textPosition : 'none',
			gridlines : {
				count : 9
			},
			viewWindow : {
				min : -4,
				max : 4
			}
		},
		hAxis : {
			textPosition : 'none'
		}
	};

	var line_chart = new google.visualization.LineChart(document.getElementById('line_chart'));
	line_chart.draw(line_data, line_options);

}

function getEmotionArray() {
	var userId = getUrlVars()["userId"];
	var scoreArray = [];

	$.ajax({
		type : 'GET',
		async : false,
		url : endpoint + '/executeChart',
		data : {
			userId : userId,
		},
		success : function(xml) {
			var result = $('return', xml);
			for (var i = 0; i < result.length; i++) {
				var array = $('array', result.eq(i));
				scoreArray[i] = ["EM" + (i + 1), eval(array.eq(0).text()), eval(array.eq(1).text()), eval(array.eq(2).text())];
			}
		},
	});

	return scoreArray;
}

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

google.load("visualization", "1", {
	packages : ["corechart"]
});

google.setOnLoadCallback(drawChart);
